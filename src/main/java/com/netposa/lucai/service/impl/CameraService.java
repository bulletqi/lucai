package com.netposa.lucai.service.impl;

import com.google.common.collect.Lists;
import com.netposa.lucai.domain.Camera;
import com.netposa.lucai.mapper.CameraMapper;
import com.netposa.lucai.service.ICameraService;
import com.netposa.lucai.service.IExcelService;
import com.netposa.lucai.util.ImgUtils;
import com.netposa.lucai.util.PageModel;
import com.netposa.lucai.vo.CameraDTO;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.ImgVo;
import com.netposa.lucai.vo.SearchCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;


@Service
public class CameraService implements ICameraService {

	@Autowired
	private CameraMapper cameraMapper;

	@Autowired
	private IExcelService excelService;

	@Override
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
		String files = cameraVo.getFiles();
		if (StringUtils.isNoneBlank(files)) {
			List<String> filesList = Arrays.asList(files.split(","));
			cameraMapper.delImgByCameraId(id); //先删除在增加
			cameraMapper.saveImg(id, filesList);
			this.archiveFile(id, filesList);
		}
		return id;
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
	public void delCamera(Integer id) {
		cameraMapper.delCamera(id);
		cameraMapper.delImgByCameraId(id);
		ImgUtils.delImgByCamera(id+"");
	}

	@Override
	public PageModel queryCamera(SearchCondition searchCondition) {
		PageModel<CameraDTO> pageModel = new PageModel<>();
		pageModel.setTotalRecords(cameraMapper.countCamera(searchCondition.getBegin_page(),searchCondition.getPage_size(),searchCondition));
		pageModel.setList(cameraMapper.queryCamera(searchCondition.getBegin_page(),searchCondition.getPage_size(),searchCondition));
		pageModel.setPageNo(searchCondition.getCurrent_page());
		return pageModel;
	}


	@Override
	public CameraVo getCamera(Integer id) {
		CameraDTO vo = cameraMapper.getById(id);
		if (vo != null) {
			List<String> files = cameraMapper.queryImg(id);
			if (!CollectionUtils.isEmpty(files)) {
				//摄像机图片
				vo.setFiles(StringUtils.join(files, ","));
			}
			return vo;
		}
		return null;
	}

	@Override
	public boolean existsCode(Integer id, String code) {
		return cameraMapper.existsCode(id,code) > 0;
	}


	@Override
	public void importExcel(MultipartFile file, Integer userId, Integer group) {
		excelService.importCamerasData(file,userId,group);
	}

}
