/**
 * 
 */
package com.netposa.lucai.service;


import com.netposa.lucai.util.PageInfo;
import com.netposa.lucai.util.PageModel;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.ImgVo;
import org.springframework.web.multipart.MultipartFile;

public interface ICameraService {

	Integer save(CameraVo cameraVo);

	ImgVo uploadImg(MultipartFile file);

	void delImg(String imgId, String id);

	void delCamera(Integer id);

	PageModel queryCamera(PageInfo pageInfo);

}
