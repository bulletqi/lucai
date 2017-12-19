package com.netposa.lucai.service.impl;

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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
	摄像机模板导入
 */
@Slf4j
@Service
public class ExcelService implements IExcelService {

	@Autowired
	private CameraMapper cameraMapper;

	private ForkJoinPool forkJoinPool = new ForkJoinPool();

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
		forkJoinPool.invoke(new SaveDataTask(list));
		log.info("总共导入{}条数据",list.size());
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


	class SaveDataTask extends RecursiveAction {
		private List<Camera> cameraList;

		//数据大小阈值
		int SAVE_DATA_THRESHOLD = 200;

		SaveDataTask(List<Camera> cameraList){
			this.cameraList = cameraList;
		}

		@Override
		protected void compute() {
			if (cameraList.size() <= SAVE_DATA_THRESHOLD) {
				//处理保存逻辑
				List<String> codes = new ArrayList<>();
				for(Camera camera: cameraList){
					String code = camera.getCode();
					if(StringUtils.isNoneBlank(code)){
						codes.add(code);
					}
				}
				if(!codes.isEmpty()){
					cameraMapper.delCameraByCode(codes);
				}
				cameraMapper.save(cameraList);
			} else {
				//进行数据切割
				RecursiveAction excuteTask = new SaveDataTask(this.cameraList.subList(0, SAVE_DATA_THRESHOLD));
				excuteTask.fork();

				//剩余分割的任务
				RecursiveAction forkTask = new SaveDataTask(this.cameraList.subList(SAVE_DATA_THRESHOLD, cameraList.size()));
				forkTask.fork();
			}
		}
	}

}
