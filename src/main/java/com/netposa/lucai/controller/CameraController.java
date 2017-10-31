package com.netposa.lucai.controller;


import com.netposa.lucai.service.ICameraService;
import com.netposa.lucai.util.PageInfo;
import com.netposa.lucai.util.ResponseData;
import com.netposa.lucai.vo.CameraVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/camera")
@Slf4j
public class CameraController {

	@Autowired
	private ICameraService cameraService;

	@PostMapping(value = "/save")
	public ResponseData saveCamera(CameraVo cameraVo) {
		log.debug("摄像机参数：{}",cameraVo);
		return ResponseData.bulid().putContent("camera",cameraService.save(cameraVo));
	}

	@GetMapping(value = "/query_camera")
	public ResponseData queryCamera(PageInfo pageInfo) {
		return ResponseData.bulid().setPageInfo(cameraService.queryCamera(pageInfo));
	}

	@GetMapping(value = "/get_camera/{id}")
	public ResponseData getCamera(@PathVariable("id") Integer id) {
		return ResponseData.bulid().putContent("camera",cameraService.getCamera(id));
	}

	@DeleteMapping(value = "/del_camera/{id}")
	public ResponseData delCamera(@PathVariable("id") Integer id) {
		cameraService.delCamera(id);
		return ResponseData.bulid();
	}

	@PostMapping(value = "/upload_img")
	public ResponseData uploadImg(MultipartFile file) {
		return ResponseData.bulid().putContent("img",cameraService.uploadImg(file));
	}

	@DeleteMapping(value = "/del_img")
	public ResponseData delImg(String fileName,String id) {
		cameraService.delImg(fileName,id);
		return ResponseData.bulid();
	}

}
