package com.netposa.lucai.controller;


import com.netposa.lucai.util.BaseConstant;
import com.netposa.lucai.util.ResponseData;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "字段管理")
@RestController
@RequestMapping("/dict")
public class DictController {

	@GetMapping(value = "/get_camera_type")
	public ResponseData cameraType() {
		return ResponseData.bulid().putContent("type", BaseConstant.cameraType);
	}

	@GetMapping(value = "/get_camera_angle")
	public ResponseData cameraAngle() {
		return ResponseData.bulid().putContent("type", BaseConstant.cameraAngle);
	}

}
