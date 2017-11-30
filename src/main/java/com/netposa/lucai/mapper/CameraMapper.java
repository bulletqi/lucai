package com.netposa.lucai.mapper;

import com.netposa.lucai.domain.Camera;
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

	List<Camera> queryCamera(@Param("offset") Integer begin_page,
							 @Param("rows") Integer page_size,
							 @Param("search") SearchCondition searchCondition);

	Integer countCamera(@Param("offset") Integer begin_page,
						@Param("rows") Integer page_size,
						@Param("search") SearchCondition searchCondition);

	CameraVo getById(@Param("id")Integer id);

	List<String> queryImg(@Param("id")Integer id);

	int existsCode(@Param("id")Integer id, @Param("code") String code);

	void delCameraByCode(@Param("codes") List<String> codes);
}