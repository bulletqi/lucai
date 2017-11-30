/**
 * 
 */
package com.netposa.lucai.service;


import com.netposa.lucai.util.PageModel;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.ImgVo;
import com.netposa.lucai.vo.SearchCondition;
import org.springframework.web.multipart.MultipartFile;

public interface ICameraService {

	Integer save(CameraVo cameraVo);

	ImgVo uploadImg(MultipartFile file);

	void delImg(String imgId, String id);

	void delCamera(Integer id);

	PageModel queryCamera(SearchCondition searchCondition);

	CameraVo getCamera(Integer id);

	boolean existsCode(Integer id, String code);

	void importExcel(MultipartFile file, Integer userId, Integer group);
}
