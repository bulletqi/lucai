package com.netposa.lucai.service.impl;

import com.netposa.lucai.domain.Camera;
import com.netposa.lucai.mapper.CameraMapper;
import com.netposa.lucai.service.ICameraService;
import com.netposa.lucai.util.ImgUtils;
import com.netposa.lucai.util.PageInfo;
import com.netposa.lucai.util.PageModel;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.ImgVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;


@Service
public class CameraService implements ICameraService {

	@Autowired
	private CameraMapper cameraMapper;

	@Override
	public Integer save(CameraVo cameraVo) {
		Integer id = cameraVo.getId();
		Camera camera = new Camera();
		BeanUtils.copyProperties(cameraVo, camera);
		if (id == null) { //新增
			cameraMapper.save(camera); //保存数据
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
	public void delImg(String imgId, String id) {
		cameraMapper.delImg(imgId);
		ImgUtils.delImg(imgId, id);
	}


	@Override
	public void delCamera(Integer id) {
		ImgUtils.delImgByCamera(id+"");
		cameraMapper.delCamera(id);
		cameraMapper.delImgByCameraId(id);
	}

	@Override
	public PageModel queryCamera(PageInfo pageInfo) {
		PageModel<Camera> pageModel = new PageModel<>();
		pageModel.setTotalRecords(cameraMapper.countCamera(pageInfo.getBegin_page(),
				pageInfo.getPage_size()));
		pageModel.setList(cameraMapper.queryCamera(pageInfo.getBegin_page(),
				pageInfo.getPage_size()));
		pageModel.setPageNo(pageInfo.getCurrent_page());
		return pageModel;
	}


	@Override
	public CameraVo getCamera(Integer id) {
		Camera camera = cameraMapper.getById(id);
		if (camera != null) {
			CameraVo vo = new CameraVo();
			BeanUtils.copyProperties(camera, vo);
			List<String> files = cameraMapper.queryImg(id);
			if (!CollectionUtils.isEmpty(files)) {
				vo.setFiles(StringUtils.join(files, ",")); //摄像机图片
			}
			return vo;
		}
		return null;
	}

	@Override
	public boolean existsCode(Integer id, String code) {
		return cameraMapper.existsCode(id,code) > 0;
	}

}
