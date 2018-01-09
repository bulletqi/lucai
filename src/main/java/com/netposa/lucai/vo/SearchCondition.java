package com.netposa.lucai.vo;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class SearchCondition {
	@ApiParam("当前页")
	private Integer current_page;
	@ApiParam("页大小")
	private Integer page_size;
	@ApiParam("分组id")
	private String groupId;
	@ApiParam("点位名称(模糊搜素)")
	private String cameraName;
	@ApiParam(hidden = true)
	private String searchName;
	@ApiParam(hidden = true)
	private List<String> groups;

	public Integer getBegin_page() {
		if(current_page == null || page_size == null){
			return null;
		}
		return (current_page-1) * page_size;
	}

}
