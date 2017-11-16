package com.netposa.lucai.controller;


import com.netposa.lucai.service.ICameraService;
import com.netposa.lucai.util.ResponseData;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.SearchCondition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api(tags = "摄像机管理")
@Slf4j
@RestController
@RequestMapping("/camera")
public class CameraController {

	@Autowired
	private ICameraService cameraService;

	@ApiOperation(value = "保存修改摄像机")
	@PostMapping(value = "/save")
	public ResponseData saveCamera(CameraVo cameraVo) {
		log.debug("摄像机参数:{}",cameraVo);
		return ResponseData.bulid().putContent("camera",cameraService.save(cameraVo));
	}


	@ApiOperation(value = "查询摄像机")
	@GetMapping(value = "/query_camera")
	public ResponseData queryCamera(SearchCondition searchCondition) {
		return ResponseData.bulid().setPageInfo(cameraService.queryCamera(searchCondition));
	}


	@ApiOperation(value = "摄像机详情信息")
	@GetMapping(value = "/get_camera/{id}")
	public ResponseData getCamera(
			@ApiParam("摄像机id") @PathVariable("id") Integer id) {
		return ResponseData.bulid().putContent("camera",cameraService.getCamera(id));
	}

	@ApiOperation(value = "删除摄像机信息")
	@DeleteMapping(value = "/del_camera/{id}")
	public ResponseData delCamera(
			@ApiParam("摄像机id") @PathVariable("id") Integer id) {
		cameraService.delCamera(id);
		return ResponseData.bulid();
	}


	@ApiOperation(value = "检查摄像机编号是否重复")
	@GetMapping(value = "/is_exists_code")
	public ResponseData existsCode(
			@ApiParam("摄像机id") @RequestParam(required = false) Integer id ,
			@ApiParam("摄像机编号") @RequestParam String code) {
		cameraService.delCamera(id);
		return ResponseData.bulid().putContent("isExists",cameraService.existsCode(id,code));
	}

	@ApiOperation(value = "上传图片")
	@PostMapping(value = "/upload_img")
	public ResponseData uploadImg(
			@ApiParam("图片文件") MultipartFile file) {
		return ResponseData.bulid().putContent("img",cameraService.uploadImg(file));
	}


	@ApiOperation(value = "删除图片")
	@DeleteMapping(value = "/del_img")
	public ResponseData delImg(
			@ApiParam("文件名称") @RequestParam  String fileName,
			@ApiParam("摄像机id") @RequestParam String id) {
		cameraService.delImg(fileName,id);
		return ResponseData.bulid();
	}

}
