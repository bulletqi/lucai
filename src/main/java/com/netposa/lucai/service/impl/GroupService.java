package com.netposa.lucai.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.netposa.lucai.domain.Group;
import com.netposa.lucai.mapper.CameraMapper;
import com.netposa.lucai.mapper.GroupMapper;
import com.netposa.lucai.service.ICameraService;
import com.netposa.lucai.service.IGroupService;
import com.netposa.lucai.vo.GroupDTO;
import com.netposa.lucai.vo.GroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupService implements IGroupService {

	@Autowired
	private GroupMapper groupMapper;

	@Autowired
	private ICameraService cameraService;
	@Autowired
	private CameraMapper cameraMapper;


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
	public List<GroupDTO> listGroup() {
		List<Group> groups = groupMapper.listGroup();
		return Lists.transform(groups, new Function<Group, GroupDTO>() {
			@Override
			public GroupDTO apply(Group group) {
				//返回分组内摄像机个数
				GroupDTO dto = new GroupDTO();
				BeanUtils.copyProperties(group,dto);
				dto.setCameraCount(cameraMapper.countCameraByGroup(group.getId()));
				return dto;
			}
		});
	}

	@Override
	public boolean existsName(Integer id, String name) {
		return groupMapper.existsName(id,name) > 0;
	}

	@Override
	public void delete(Integer id) {
		groupMapper.delete(id);
		//删除摄像机
		for(Integer cameraId : cameraMapper.queryCameraIdByGroup(id)){
			cameraService.delCamera(cameraId);
		}
	}
}
