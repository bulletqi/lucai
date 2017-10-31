/**
 * 
 */
package com.netposa.lucai.service;


import com.netposa.lucai.domain.User;

public interface IUserService {
	User login(String name, String password);
}
