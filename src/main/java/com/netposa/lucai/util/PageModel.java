package com.netposa.lucai.util;

import lombok.Data;

import java.util.List;

@Data
public class PageModel<E>{
	private List<E> list;
	private Integer totalRecords;
	private Integer pageNo;
	private Integer totalPages;
}