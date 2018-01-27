/**
 * 
 */
package com.netposa.lucai.service;


import com.netposa.lucai.util.PageModel;
import com.netposa.lucai.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICameraService {

	Integer save(CameraVo cameraVo);

	ImgVo uploadImg(MultipartFile file);

	void delImg(String fileName, String id);

	void delCamera(Integer id);

	PageModel queryCamera(SearchCondition searchCondition);

	CameraVo getCamera(Integer id);

	boolean existsCode(Integer id, Integer attrId, String code);

	void importExcel(MultipartFile file, Integer userId, Integer group);

	List<CodeDTO> existsCodeBatch(List<CodeVo> voList);
}
