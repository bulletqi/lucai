package com.netposa.lucai.util;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "system")
public class SystemProperties {
	private Img img = new Img();

	@Data
	public class Img {
		private String location;
		private String tempDir = "temp";
	}
}
