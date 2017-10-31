package com.netposa.lucai.controller;


import com.netposa.lucai.service.IUserService;
import com.netposa.lucai.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping(value = "/login")
	public ResponseData login(String name , String password) {
		return ResponseData.bulid().putContent("user", userService.login(name, password));
	}

}
