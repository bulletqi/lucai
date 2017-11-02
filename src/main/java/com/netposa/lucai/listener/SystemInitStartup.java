package com.netposa.lucai.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netposa.lucai.exception.BusinessException;
import com.netposa.lucai.util.BaseConstant;
import com.netposa.lucai.util.FileUtils;
import com.netposa.lucai.util.ImgUtils;
import com.netposa.lucai.util.SystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 初始化系统权限
 */
@Component
@Slf4j
public class SystemInitStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private SystemProperties systemProperties;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		ititConstant();
		initFileDir();
	}

	public void ititConstant(){
		InputStream fileStream = FileUtils.loadStream("/dict/dict.json");
		try {
			String dictInfo = IOUtils.toString(fileStream, Charset.forName("UTF-8"));
			JSONObject jsonObject = JSON.parseObject(dictInfo);
			BaseConstant.cameraType.addAll(jsonObject.getJSONArray("cameraType").toJavaList(String.class));
			BaseConstant.cameraAngle.addAll(jsonObject.getJSONArray("cameraAngle").toJavaList(String.class));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			IOUtils.closeQuietly(fileStream);
		}
	}

	public void initFileDir(){
		//初始化文件存储目录,临时文件目录
		SystemProperties.Img img = systemProperties.getImg();
		ImgUtils.basePath = img.getLocation();
		ImgUtils.tempPath = img.getTempDir();
		File file = new File(ImgUtils.basePath + File.separator + ImgUtils.tempPath);
		if (!file.exists()) {
			boolean flag = file.mkdirs();
			if (!flag) {
				throw new BusinessException("初始化文件存储目录失败");
			}
		}
	}
}
