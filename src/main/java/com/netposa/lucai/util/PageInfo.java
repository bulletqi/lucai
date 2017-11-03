package com.netposa.lucai.util;


import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class PageInfo {

	@ApiParam("当前页")
	private Integer current_page;

	@ApiParam("页大小")
	private Integer page_size;
	
	public Integer getBegin_page() {
		if(current_page == null || page_size == null){
			return null;
		}
		return (current_page-1) * page_size;
	}
}
