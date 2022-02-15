package com.serrano.app.forum.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanUtilities {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
