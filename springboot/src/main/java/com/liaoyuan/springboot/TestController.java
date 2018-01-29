package com.liaoyuan.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class TestController {
	@RequestMapping("/")
    @ResponseBody
	
	public String helloworld(){
		return "hello Lingkai";
	}
	public static void main(String[] args) throws Exception {
        SpringApplication.run(TestController.class, args);
    }
}
