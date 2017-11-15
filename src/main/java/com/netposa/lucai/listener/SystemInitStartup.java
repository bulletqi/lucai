package com.netposa.lucai.listener;

import com.netposa.lucai.exception.BusinessException;
import com.netposa.lucai.util.ImgUtils;
import com.netposa.lucai.util.SystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;

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
		initFileDir();
	}

	private void initFileDir() {
		//初始化文件存储目录,临时文件目录
		SystemProperties.Img img = systemProperties.getImg();
		ImgUtils.basePath = img.getLocation();
		ImgUtils.tempPath = img.getTempDir();
		File file = new File(ImgUtils.basePath + File.separator + ImgUtils.tempPath);
		if (!file.exists()) {
			boolean flag = file.mkdirs(); //创建多级目录
			if (!flag) {
				throw new BusinessException("初始化文件存储目录失败");
			}
		}
	}
}
