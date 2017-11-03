package com.netposa.lucai.domain;

import lombok.Data;

@Data
public class Camera {
	private Integer id;
	private String code;
	private String name;
	private Double longitude;
	private Double latitude;
	private String localtion;
	private String toward; //朝向
	private String type; // 类型
	private Double rodHeight; //杆高度
	private Double rodLength; //杆长度
	private String remark;
	private Integer userId; // 用户id
}
