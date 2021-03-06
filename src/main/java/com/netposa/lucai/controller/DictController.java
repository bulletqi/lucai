package com.netposa.lucai.controller;


import com.netposa.lucai.mapper.DictMapper;
import com.netposa.lucai.util.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "字段管理")
@RestController
@RequestMapping("/dict")
public class DictController {

	@Autowired
	private DictMapper dictMapper;

	@ApiOperation(value = "摄像机类型字典")
	@GetMapping(value = "/get_camera_type")
	@Cacheable("cameraType")
	public ResponseData cameraType() {
		return ResponseData.bulid().putContent("type", dictMapper.list("cameraType"));
	}


	@ApiOperation(value = "方位类型字典")
	@GetMapping(value = "/get_camera_direction")
	@Cacheable("cameraDirection")
	public ResponseData cameraAngle() {
		return ResponseData.bulid().putContent("direction", dictMapper.list("cameraDirection"));
	}

}
