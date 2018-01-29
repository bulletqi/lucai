package com.netposa.lucai.vo;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.annotations.Result;

@Data
@ToString
public class CameraAttr {
	protected String attrId;
	protected String toward;
	protected String type ;
	protected String code ;
	protected Integer groupId ;
	protected String groupName ;
}
