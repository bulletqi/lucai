package com.netposa.lucai.util;

import com.netposa.lucai.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Slf4j
public class ImgUtils {
	public static String basePath;

	public static String tempPath;

	public static Pair<String, byte[]> writeImg(MultipartFile file) {
		String fileName;
		InputStream in = null;
		OutputStream out = null;
		try {
			String name = file.getOriginalFilename();
			log.debug("上传的文件名为:{}",name);
			fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(name);
			out = new FileOutputStream(getFilePathTemp(fileName));
			in = file.getInputStream();
			IOUtils.copy(in, out);
			return Pair.of(fileName, file.getBytes());
		} catch (Exception e) {
			throw new BusinessException("上传的文件不正确", e);
		} finally {
			IOUtils.closeQuietly(in, out);
		}
	}

	//文件全路径
	private static String getFilePath(String prefix, String fileName) {
		return basePath + File.separator + prefix + File.separator + fileName;
	}

	//归档目录
	private static String getArchiveDir(String prefix) {
		return basePath + File.separator + prefix;
	}


	//临时目录
	private static String getFilePathTemp(String fileName) {
		return basePath + File.separator + tempPath + File.separator + fileName;
	}

	@SuppressWarnings("all")
	public static void delImg(String fileName, String prefix) {
		File file = new File(getFilePath(prefix, fileName));
		file.delete();
	}

	public static void archiveFile(String fileName, String prefix) {
		try {
			if(!isExists(fileName,prefix)){//文件已经存在就不归档了
				File srcfile = new File(getFilePathTemp(fileName));
				File descfile = new File(getArchiveDir(prefix));
				FileUtils.moveFileToDirectory(srcfile, descfile, true);
			}
		} catch (IOException e) {
			log.error(String.format("文件归档失败:%s", e.getMessage()), e);
		}
	}

	private static boolean isExists(String fileName, String prefix) {
		return new File(basePath + File.separator
				+ prefix + File.separator + fileName).exists();
	}

	public static void delImgByCamera(String id) {
		FileUtils.deleteQuietly(new File(getArchiveDir(id)));
	}
}
