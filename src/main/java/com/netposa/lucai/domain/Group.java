package com.netposa.lucai.domain;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Group{
	private Integer id;
	private String name;
	private String color;
}
