package com.netposa.lucai.util;


public class PageInfo {
	private Integer current_page;
	private Integer page_size;
	
	public Integer getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(Integer current_page) {
		this.current_page = current_page;
	}
	public Integer getPage_size() {
		return page_size;
	}
	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}
	
	public Integer getBegin_page() {
		if(current_page == null || page_size == null){
			return null;
		}
		return (current_page-1) * page_size;
	}
}
