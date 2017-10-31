package com.netposa.lucai.domain;


import lombok.Data;

@Data
public class User {
	private Integer id;
	private String name;
	private String loginName;
	private String password;
}
