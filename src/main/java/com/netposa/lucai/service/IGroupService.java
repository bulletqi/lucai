/**
 * 
 */
package com.netposa.lucai.service;


import com.netposa.lucai.domain.Group;
import com.netposa.lucai.util.PageInfo;
import com.netposa.lucai.util.PageModel;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.GroupVo;
import com.netposa.lucai.vo.ImgVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IGroupService {

	Integer save(GroupVo groupVo);

	List<Group> listGroup();

	boolean existsName(Integer id, String name);

}
