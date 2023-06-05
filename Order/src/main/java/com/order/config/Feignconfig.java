package com.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.order.external.client.decoder.CustonErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class Feignconfig {
	
	@Bean
	ErrorDecoder decoder()
	{
		return new CustonErrorDecoder();
	}

}
