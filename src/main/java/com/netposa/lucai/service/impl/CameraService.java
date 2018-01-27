package com.netposa.lucai.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.netposa.lucai.domain.Camera;
import com.netposa.lucai.mapper.CameraMapper;
import com.netposa.lucai.service.ICameraService;
import com.netposa.lucai.service.IExcelService;
import com.netposa.lucai.util.ImgUtils;
import com.netposa.lucai.util.PageModel;
import com.netposa.lucai.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Service
@Slf4j
public class CameraService implements ICameraService {

	@Autowired
	private CameraMapper cameraMapper;

	@Autowired
	private IExcelService excelService;

	@Override
	@Transactional
	public Integer save(CameraVo cameraVo) {
		Integer id = cameraVo.getId();
		Camera camera = new Camera();
		BeanUtils.copyProperties(cameraVo, camera);
		if (id == null) { //新增
			cameraMapper.save(Lists.newArrayList(camera)); //保存数据
			id = camera.getId();
		} else {
			cameraMapper.update(camera); //update
		}
		//保存一杆多头信息
		this.saveCameraAttr(id, cameraVo.getAttr());
		//保存图片信息
		this.saveCameraImg(id, cameraVo.getFiles());
		return id;
	}

	private void saveCameraAttr(Integer cameraId, String attr) {
		if (StringUtils.isNotBlank(attr)) {
			try {
				List<CameraAttr> cameraAttrs = JSONArray.parseArray(attr, CameraAttr.class);
				//覆盖更新
				cameraMapper.delAttr(cameraId);
				cameraMapper.saveAttr(cameraAttrs, cameraId);
			} catch (Exception e) {
				log.error("一杆多头属性格式不正确:{}", attr);
			}
		}
	}

	private void saveCameraImg(Integer cameraId, String files) {
		if (StringUtils.isNoneBlank(files)) {
			List<String> filesList = Arrays.asList(files.split(","));
			cameraMapper.delImgByCameraId(cameraId); //先删除在增加
			cameraMapper.saveImg(cameraId, filesList);
			this.archiveFile(cameraId, filesList);
		}
	}

	//文件归档,以摄像机id为文件夹进行归档
	private void archiveFile(Integer id, List<String> files) {
		for (String file : files) {
			ImgUtils.archiveFile(file, id + "");
		}
	}

	@Override
	public ImgVo uploadImg(MultipartFile file) {
		Pair<String, byte[]> pair = ImgUtils.writeImg(file);
		return new ImgVo(pair.getLeft(), Base64Utils.encodeToString(pair.getRight()));
	}

	@Override
	public void delImg(String fileName, String id) {
		cameraMapper.delImg(fileName);
		ImgUtils.delImg(fileName, id);
	}

	@Override
	@Transactional
	public void delCamera(Integer id) {
		cameraMapper.delCamera(id);
		cameraMapper.delImgByCameraId(id);
		cameraMapper.delAttr(id);
		ImgUtils.delImgByCamera(id + "");
	}

	@Override
	public PageModel queryCamera(SearchCondition searchCondition) {
		searchCondition = this.buildParam(searchCondition);
		PageModel<CameraDTO> pageModel = new PageModel<>();
		pageModel.setList(cameraMapper.queryCamera(searchCondition.getBegin_page(),searchCondition.getPage_size(), searchCondition));
		pageModel.setTotalRecords(cameraMapper.countCamera(searchCondition.getBegin_page(), searchCondition.getPage_size(), searchCondition));
		pageModel.setPageNo(searchCondition.getCurrent_page());
		return pageModel;
	}

	private SearchCondition buildParam(SearchCondition searchCondition) {
		String cameraName = searchCondition.getCameraName();
		if (StringUtils.isNoneBlank(cameraName)) {
			searchCondition.setSearchName("%" + cameraName + "%");
		}
		String groupId = searchCondition.getGroupId();
		if (StringUtils.isNoneBlank(groupId)) {
			searchCondition.setGroups(Arrays.asList(groupId.split(",")));
		}
		return searchCondition;
	}

	@Override
	public CameraVo getCamera(Integer id) {
		CameraDTO vo = cameraMapper.getById(id);
		if (vo != null) {
			List<String> files = cameraMapper.queryImg(id);
			//摄像机图片
			if (!CollectionUtils.isEmpty(files)) {
				vo.setFiles(StringUtils.join(files, ","));
			}
			//一杆多头信息
			List<CameraAttr> attrs = cameraMapper.queryAttrs(id);
			if (!CollectionUtils.isEmpty(attrs)) {
				vo.setAttrs(attrs);
				vo.setAttr(JSON.toJSONString(attrs));
			}
			return vo;
		}
		return null;
	}

	@Override
	public boolean existsCode(Integer id, Integer attrId, String code) {
		return cameraMapper.existsCode(id, code) > 0 ||
				cameraMapper.existsAttrCode(attrId,id, code) > 0;
	}

	@Override
	public void importExcel(MultipartFile file, Integer userId, Integer group) {
		excelService.importCamerasData(file, userId, group);
	}


	@Override
	public List<CodeDTO> existsCodeBatch(List<CodeVo> voList) {
		final Set<String> codeSet = new HashSet<>();
		return Lists.transform(voList, new Function<CodeVo, CodeDTO>() {
			@Override
			public CodeDTO apply(CodeVo codeVo) {
				CodeDTO dto = new CodeDTO();
				BeanUtils.copyProperties(codeVo,dto);
				String code = dto.getCode();
				if(!codeSet.contains(code)){
					dto.setExists(existsCode(dto.getId(),dto.getAttrId(),code));
				}
				codeSet.add(code);
				return dto;
			}
		});
	}

}
