package com.netposa.lucai.exception;

import com.netposa.lucai.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常处理
@ControllerAdvice
@Order
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseData goalException(Exception ex) {
		log.error(String.format("服务异常:%s", ex.getMessage()), ex);
		ResponseData data = ResponseData.bulid();
		int code = 500;
		String message = "服务异常";
		if (ex instanceof BusinessException) {
			message = ex.getMessage();
			code = ((BusinessException) ex).getCode();
		}
		data.setCode(code);
		data.setMessage(message);
		return data;
	}
}
