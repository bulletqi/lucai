package com.netposa.lucai.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DictMapper {
	List<String> list(@Param("type") String type);
}