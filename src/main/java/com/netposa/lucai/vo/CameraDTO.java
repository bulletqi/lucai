package com.netposa.lucai.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CameraDTO extends CameraVo {

	@ApiParam("分组名称")
	private String groupName;//分组名称

	@ApiParam("点位颜色")
	private String color; //点位颜色

}
