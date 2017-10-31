package com.netposa.lucai.service.impl;

import com.netposa.lucai.domain.User;
import com.netposa.lucai.exception.BusinessException;
import com.netposa.lucai.mapper.UserMapper;
import com.netposa.lucai.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User login(String name, String password) {
		User user = userMapper.login(name, password);
		if(user != null){
			return user;
		}
		throw new BusinessException(400,"用户名或者密码不正确");
	}
}
