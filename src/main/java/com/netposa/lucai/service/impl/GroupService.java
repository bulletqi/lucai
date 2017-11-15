package com.netposa.lucai.service.impl;

import com.netposa.lucai.domain.Camera;
import com.netposa.lucai.domain.Group;
import com.netposa.lucai.mapper.CameraMapper;
import com.netposa.lucai.mapper.GroupMapper;
import com.netposa.lucai.service.ICameraService;
import com.netposa.lucai.service.IGroupService;
import com.netposa.lucai.util.ImgUtils;
import com.netposa.lucai.util.PageInfo;
import com.netposa.lucai.util.PageModel;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.GroupVo;
import com.netposa.lucai.vo.ImgVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;


@Service
public class GroupService implements IGroupService {

	@Autowired
	private GroupMapper groupMapper;

	@Override
	public Integer save(GroupVo groupVo) {
		Integer id = groupVo.getId();
		Group group = new Group();
		BeanUtils.copyProperties(groupVo, group);
		if (groupVo.getId() == null) { //新增
			groupMapper.save(group); //保存数据
			id = group.getId();
		} else {
			groupMapper.update(group); //update
		}
		return id;
	}

	@Override
	public List<Group> listGroup() {
		return groupMapper.listGroup();
	}

	@Override
	public boolean existsName(Integer id, String name) {
		return groupMapper.existsName(id,name) > 0;
	}
}
