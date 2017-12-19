/**
 * 
 */
package com.netposa.lucai.service;


import com.netposa.lucai.domain.Camera;
import com.netposa.lucai.domain.Group;
import com.netposa.lucai.vo.GroupVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IExcelService {

	void importCamerasData(MultipartFile file, Integer userId, Integer groupId);

}
