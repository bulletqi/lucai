package com.netposa.lucai.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GroupVo {
	@ApiParam("分组id,空为新增")
	private Integer id;
	@ApiParam("名称")
	private String name;
}
