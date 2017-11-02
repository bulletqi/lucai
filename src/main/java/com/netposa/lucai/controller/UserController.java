package com.netposa.lucai.controller;


import com.netposa.lucai.domain.User;
import com.netposa.lucai.service.IUserService;
import com.netposa.lucai.util.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@ApiOperation(value = "用户登录" , response = User.class)
	@PostMapping(value = "/login")
	public ResponseData login(
			@ApiParam(value = "登录名") String name,
			@ApiParam(value = "密码") String password) {
		return ResponseData.bulid().putContent("user", userService.login(name, password));
	}

}
