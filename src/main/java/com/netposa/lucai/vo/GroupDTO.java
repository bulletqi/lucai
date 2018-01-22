package com.netposa.lucai.vo;

import com.netposa.lucai.domain.Group;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GroupDTO extends Group {
	//分组内摄像机数量
	private Integer cameraCount;
}
