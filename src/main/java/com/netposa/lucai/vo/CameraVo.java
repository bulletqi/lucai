package com.netposa.lucai.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CameraVo {
	@ApiParam("摄像机id,空为新增")
	protected Integer id;
	@ApiParam("摄像机编码")
	protected String code;
	@ApiParam("摄像机名称")
	protected String name;
	@ApiParam("经度")
	protected Double longitude;
	@ApiParam("纬度")
	protected Double latitude;
	@ApiParam("位置")
	protected String localtion;
	@ApiParam("朝向")
	protected String toward;
	@ApiParam("摄像机类型")
	protected String type ;
	@ApiParam("杆高度")
	protected Double rodHeight;
	@ApiParam("杆长度")
	protected Double rodLength;
	@ApiParam("图片名称 多个用逗号分隔")
	protected String files;
	@ApiParam("备注")
	protected String remark;
	@ApiParam("用户id")
	protected Integer userId;
	@ApiParam("所属分组")
	protected Integer group;

}
