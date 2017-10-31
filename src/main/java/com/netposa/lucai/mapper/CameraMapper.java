package com.netposa.lucai.mapper;

import com.netposa.lucai.domain.Camera;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CameraMapper {
	Integer save(Camera camera);

	void update(Camera camera);

	void saveImg(@Param("id") Integer id, @Param("files") List<String> files);

	void delImg(@Param("fileName") String fileName);

	void delImgByCameraId(@Param("id") Integer id);

	void delCamera(@Param("id") Integer id);

	List<Camera> queryCamera(@Param("offset") Integer begin_page,
							 @Param("rows") Integer page_size);

	Integer countCamera(@Param("offset") Integer begin_page,
							 @Param("rows") Integer page_size);

	Camera getById(@Param("id")Integer id);

	List<String> queryImg(@Param("id")Integer id);
}