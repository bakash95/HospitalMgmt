package com.akash.demo.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfigForInterceptor extends WebMvcConfigurerAdapter {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor();
	}

	public @Override void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor());
	}
}