package com.netposa.lucai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

	@RequestMapping(value = "/api-ui")
	public String swaggerUI() {
		return "redirect:swagger-ui.html";
	}
}
