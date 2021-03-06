package com.netposa.lucai.controller;


import com.netposa.lucai.service.ICameraService;
import com.netposa.lucai.util.FileUtils;
import com.netposa.lucai.util.ResponseData;
import com.netposa.lucai.util.ResponseUtil;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.CodeVo;
import com.netposa.lucai.vo.SearchCondition;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


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
		log.debug("摄像机参数:{}", cameraVo);
		return ResponseData.bulid().putContent("camera", cameraService.save(cameraVo));
	}


	@ApiOperation(value = "查询摄像机(地图撒点，具体摄像机详细信息用详情接口)")
	@GetMapping(value = "/query_camera")
	public ResponseData queryCamera(SearchCondition searchCondition) {
		return ResponseData.bulid().setPageInfo(cameraService.queryCamera(searchCondition));
	}


	@ApiOperation(value = "摄像机详情信息")
	@GetMapping(value = "/get_camera/{id}")
	public ResponseData getCamera(
			@ApiParam("摄像机id") @PathVariable("id") Integer id) {
		return ResponseData.bulid().putContent("camera", cameraService.getCamera(id));
	}

	@ApiOperation(value = "删除摄像机信息")
	@DeleteMapping(value = "/del_camera/{id}")
	public ResponseData delCamera(@ApiParam("摄像机id") @PathVariable("id") Integer id) {
		cameraService.delCamera(id);
		return ResponseData.bulid();
	}


	@ApiOperation(value = "检查摄像机编号是否重复")
	@GetMapping(value = "/is_exists_code")
	public ResponseData existsCode(
			@ApiParam("摄像机id") @RequestParam(required = false) Integer id,
			@ApiParam("一杆多头id") @RequestParam(required = false) Integer attrId,
			@ApiParam("摄像机编号") @RequestParam String code) {
		return ResponseData.bulid().putContent("isExists", cameraService.existsCode(id, attrId, code));
	}


	@ApiOperation(value = "批量检查编号重复")
	@PostMapping(value = "/is_exists_code_batch")
	public ResponseData existsCodeBatch(
			@ApiParam("校验对象(支持多个)") @RequestBody List<CodeVo> codeVo) {
		return ResponseData.bulid().putContent("result", cameraService.existsCodeBatch(codeVo));
	}

	@ApiOperation(value = "上传图片")
	@PostMapping(value = "/upload_img")
	public ResponseData uploadImg(
			@ApiParam("图片文件") MultipartFile file) {
		return ResponseData.bulid().putContent("img", cameraService.uploadImg(file));
	}


	@ApiOperation(value = "删除图片")
	@DeleteMapping(value = "/del_img")
	public ResponseData delImg(
			@ApiParam("文件名称") @RequestParam String fileName,
			@ApiParam("摄像机id") @RequestParam String id) {
		cameraService.delImg(fileName, id);
		return ResponseData.bulid();
	}

	@ApiOperation(value = "摄像机模板导入")
	@PostMapping(value = "/import_excel")
	public ResponseData importExcel(
			@ApiParam("模板文件") @RequestParam MultipartFile file,
			@ApiParam("用户Id") @RequestParam Integer userId,
			@ApiParam("所属分组") @RequestParam Integer group) {
		cameraService.importExcel(file, userId, group);
		return ResponseData.bulid();
	}

	@ApiOperation(value = "下载摄像机模板")
	@GetMapping(value = "/download_excel")
	public void downloadExcel(HttpServletResponse response) throws Exception {
		ResponseUtil.showExec(FileUtils.loadStream("/file/camera.xls"), "路踩摄像机模板", response);
	}

}
