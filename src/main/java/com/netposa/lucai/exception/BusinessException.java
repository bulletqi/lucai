package com.netposa.lucai.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Integer code = 500 ;

	public BusinessException() {
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Integer code ,String message) {
		super(message);
		this.code = code;
	} 

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}