package com.netposa.lucai.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CameraVo {
	@ApiParam("摄像机id,空为新增")
	private Integer id;
	@ApiParam("摄像机编码")
	private String code;
	@ApiParam("摄像机名称")
	private String name;
	@ApiParam("经度")
	private Double longitude;
	@ApiParam("纬度")
	private Double latitude;
	@ApiParam("位置")
	private String localtion;
	@ApiParam("朝向")
	private String toward;
	@ApiParam("摄像机类型")
	private String type ;
	@ApiParam("杆高度")
	private Double rodHeight;
	@ApiParam("杆长度")
	private Double rodLength;
	@ApiParam("图片名称 多个用逗号分隔")
	private String files;
	@ApiParam("备注")
	private String remark;
	@ApiParam("用户id")
	private Integer userId;
	@ApiParam("所属分组")
	private Integer group;
}
