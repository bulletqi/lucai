package com.netposa.lucai.mapper;

import com.netposa.lucai.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

	User login(@Param("name") String name,@Param("password")  String password);

}