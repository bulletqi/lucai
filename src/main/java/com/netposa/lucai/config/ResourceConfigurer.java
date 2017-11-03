package com.netposa.lucai.config;

import com.netposa.lucai.util.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ResourceConfigurer   extends WebMvcConfigurerAdapter {

	@Autowired
	private SystemProperties systemProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String pathPatterns = "/img/**";  //映射路径
		String resourceLocations = String.format("file:%s/",systemProperties.getImg().getLocation());//图片存放路径
		registry.addResourceHandler(pathPatterns).addResourceLocations(resourceLocations);
		super.addResourceHandlers(registry);
	}

}
