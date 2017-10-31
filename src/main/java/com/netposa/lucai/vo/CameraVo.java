package com.netposa.lucai.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CameraVo {
	private Integer id;
	private String code;
	private String name;
	private Double longitude;
	private Double latitude;
	private String localtion;
	private String toward; //朝向
	private Integer type ; // 类型
	private Double rodHeight; //杆高度
	private Double rodLength; //杆长度
	private List<String> files; // 图片
	private String remark;
	private Integer userId; // 用户id
}
