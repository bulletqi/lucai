package com.netposa.lucai.util;

import com.alibaba.fastjson.JSONObject;

public class ResponseData {
	public int code = 200;
	private JSONObject data = new JSONObject();
	private String message = "成功";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	public ResponseData setCode(int code) {
		this.code = code;
		return this;
	}
	public JSONObject getData() {
		return data;
	}
	public ResponseData setData(JSONObject data) {
		this.data = data;
		return this;
	}
	public ResponseData setObject(Object data) {
		String messageText = JSONObject.toJSONString(data); 
		this.data =JSONObject.parseObject(messageText);
		return  this ;
	}
	
	public ResponseData putContent(String key , Object value){
		data.put(key, value);
		return this;
	}

	public static ResponseData bulid(){
		return new ResponseData();
	}
	
	public ResponseData setPageInfo(PageModel<?> model){
		putContent("count", model.getTotalRecords());
		putContent("page", model.getPageNo());
		putContent("data", model.getList());
		return this;
	}
	
}
