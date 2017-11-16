package com.netposa.lucai.controller;


import com.netposa.lucai.service.IGroupService;
import com.netposa.lucai.util.ResponseData;
import com.netposa.lucai.vo.GroupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "分组管理")
@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private IGroupService groupService;

	@ApiOperation(value = "保存修改分组")
	@PostMapping(value = "/save")
	public ResponseData saveGroup(GroupVo groupVo) {
		return ResponseData.bulid().putContent("group",groupService.save(groupVo));
	}

	@ApiOperation(value = "查询所有分组")
	@PostMapping(value = "/list")
	public ResponseData listGroup() {
		return ResponseData.bulid().putContent("groups",groupService.listGroup());
	}


	@ApiOperation(value = "名称是否重复")
	@GetMapping(value = "/is_exists_name")
	public ResponseData existsName(
			@ApiParam("组id") @RequestParam(required = false) Integer id ,
			@ApiParam("名称") @RequestParam String name) {
		return ResponseData.bulid().putContent("isExists",groupService.existsName(id,name));
	}

}
