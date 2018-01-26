package com.netposa.lucai.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CameraDTO extends CameraVo {

	@ApiParam("分组名称")
	private String groupName;//分组名称

	@ApiParam("点位颜色")
	private String color; //点位颜色

	@ApiParam("一杆多头属性")
	private List<CameraAttr>  attrs; // 摄像机属性

	@ApiParam("一杆多头时统计摄像机个数")
	private Integer cameraCount; //一杆多头时统计摄像机个数
}
