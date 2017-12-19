package com.netposa.lucai.mapper;

import com.netposa.lucai.domain.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GroupMapper {

	Integer save(Group group);

	void update(Group group);

	List<Group> listGroup();

	int existsName(@Param("id")Integer id , @Param("name") String name);

	void delete(Integer id);
}