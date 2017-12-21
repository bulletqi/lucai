package com.netposa.lucai.vo;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SearchCondition {
	@ApiParam("当前页")
	private Integer current_page;
	@ApiParam("页大小")
	private Integer page_size;
	@ApiParam("分组id")
	private Integer groupId;
	@ApiParam("点位名称(模糊搜素)")
	private String cameraName;


	public Integer getBegin_page() {
		if(current_page == null || page_size == null){
			return null;
		}
		return (current_page-1) * page_size;
	}
}
