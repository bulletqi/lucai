package com.netposa.lucai.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.netposa.lucai.domain.Camera;
import com.netposa.lucai.exception.BusinessException;
import com.netposa.lucai.mapper.CameraMapper;
import com.netposa.lucai.service.IExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
	摄像机模板导入
 */
@Slf4j
@Service
public class ExcelService implements IExcelService {

	@Autowired
	private CameraMapper cameraMapper;

	private final int SAVE_DATA_THRESHOLD = 100 ;

	/**
	 * 解析摄像机数据
	 */
	@Override
	public void importCamerasData(MultipartFile file, Integer userId, Integer groupId) {
		Workbook workbook ;
		try {
			workbook = WorkbookFactory.create(file.getInputStream());
		} catch (Exception e) {
			throw new BusinessException("文件不存在或者格式有误");
		}
		Sheet hssfSheet = workbook.getSheetAt(0);
		List<Camera> list = new ArrayList<>();
		Camera camera ;
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			Row hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow != null && !isRowEmpty(hssfRow)) {
				String value = getValue(hssfRow.getCell(0));
				camera = new Camera();
				camera.setName(value);
				value = getValue(hssfRow.getCell(1));
				if(StringUtils.isBlank(value)){
					log.error("第{}行数据有误",rowNum);
					break;
				}
				camera.setLongitude(Double.parseDouble(value));
				value = getValue(hssfRow.getCell(2));
				if(StringUtils.isBlank(value)){
					log.error("第{}行数据有误",rowNum);
					break;
				}
				camera.setLatitude(Double.parseDouble(value));
				value = getValue(hssfRow.getCell(3));
				camera.setCode(value);
				camera.setGroup(groupId);
				camera.setUserId(userId);
				list.add(camera);
			}
		}
		this.saveDataBatch(list);
	}


	private void saveDataBatch(List<Camera> list){
		int dataSize = list.size();
		List<String> codes;
		for(int i = 0 ; i <= dataSize / SAVE_DATA_THRESHOLD ; i++){
			int endIndex = (i+1) * SAVE_DATA_THRESHOLD;
			int startIndex = i * SAVE_DATA_THRESHOLD;
			if(list.size() - startIndex < SAVE_DATA_THRESHOLD){
				endIndex = list.size();
			}
			codes =  Lists.newArrayList();
			List<Camera> cameras = list.subList(startIndex, endIndex);
			for(Camera camera: cameras){
				String code = camera.getCode();
				if(StringUtils.isNoneBlank(code)){
					codes.add(code);
				}
			}
			if(!codes.isEmpty()){
				cameraMapper.delCameraByCode(codes);
			}
			cameraMapper.save(cameras);
		}
		log.info("总共导入{}条数据",dataSize);
	}

	private static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK){return false;}

		}
		return true;
	}

	private static String getValue(Cell xssfRow) {
		if (xssfRow == null) {
			return null;
		}
		if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

}
