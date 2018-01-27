package com.netposa.lucai.vo;

import lombok.Data;

@Data
public class CodeDTO {
	private Integer id;
	private Integer attrId;
	private String code;
	private Boolean exists = true;
}
