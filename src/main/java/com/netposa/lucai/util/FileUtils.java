package com.netposa.lucai.util;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class FileUtils {

	public static InputStream loadStream(String fileName) {
		InputStream in = null;

		try {
			in = new FileInputStream(System.getProperty("user.dir") + File.separatorChar + fileName);
		} catch (FileNotFoundException var5) {
			try {
				in = (new ClassPathResource(fileName, FileUtils.class.getClassLoader())).getInputStream();
			} catch (IOException var4) {
				log.error("文件加载失败", var4);
			}
		}

		return in;
	}

	public static File loadFile(String fileName) {
		File file = new File(System.getProperty("user.dir") + File.separatorChar + fileName);
		if(!file.exists()) {
			if(!file.exists()) {
				file = new File(FileUtils.class.getResource(fileName).getFile());
			} else {
				log.error(String.format("文件找不到,%s", new Object[]{fileName}));
			}
		}

		return file;
	}

	public static FileSystemResource loadFileSystemResource(String fileName) {
		return new FileSystemResource(loadFile(fileName));
	}

	public static ClassPathResource loadClassPathResource(String fileName) {
		return new ClassPathResource(fileName, FileUtils.class.getClassLoader());
	}
}