/**
 * 
 */
package com.netposa.lucai.service;


import com.netposa.lucai.domain.Group;
import com.netposa.lucai.vo.GroupVo;

import java.util.List;

public interface IGroupService {

	Integer save(GroupVo groupVo);

	List<Group> listGroup();

	boolean existsName(Integer id, String name);

	void delete(Integer id);

}
