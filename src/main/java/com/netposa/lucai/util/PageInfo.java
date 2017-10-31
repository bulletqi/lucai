package com.netposa.lucai.util;


import lombok.Data;

@Data
public class PageInfo {
	private Integer current_page;
	private Integer page_size;
	
	public Integer getBegin_page() {
		if(current_page == null || page_size == null){
			return null;
		}
		return (current_page-1) * page_size;
	}
}
