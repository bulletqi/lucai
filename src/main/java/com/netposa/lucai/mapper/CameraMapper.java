package com.netposa.lucai.mapper;

import com.netposa.lucai.domain.Camera;
import com.netposa.lucai.vo.CameraAttr;
import com.netposa.lucai.vo.CameraDTO;
import com.netposa.lucai.vo.CameraVo;
import com.netposa.lucai.vo.SearchCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CameraMapper {
	Integer save(@Param("list") List<Camera> camera);

	void update(Camera camera);

	void saveImg(@Param("id") Integer id, @Param("files") List<String> files);

	void delImg(@Param("fileName") String fileName);

	void delImgByCameraId(@Param("id") Integer id);

	void delCamera(@Param("id") Integer id);

	List<CameraDTO> queryCamera(@Param("offset") Integer begin_page,
								@Param("rows") Integer page_size,
								@Param("search") SearchCondition searchCondition);

	Integer countCamera(@Param("offset") Integer begin_page,
						@Param("rows") Integer page_size,
						@Param("search") SearchCondition searchCondition);

	CameraDTO getById(@Param("id")Integer id);

	List<String> queryImg(@Param("id")Integer id);

	int existsCode(@Param("id")Integer id, @Param("code") String code);

	List<Integer> queryCameraIdByGroup(@Param("groupId")Integer id);

	void delCameraByCode(@Param("codes") List<String> codes);

	void saveAttr(@Param("list") List<CameraAttr> cameraAttrs , @Param("cameraId") Integer cameraId);

	List<CameraAttr> queryAttrs(@Param("id") Integer id);

	void delAttr(Integer id);
}