package com.order.external.client.decoder;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.exception.CustomOrderException;
import com.order.exception.ErrorMessage;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Data;

@Data
public class CustonErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		
		System.out.println("insid decode ");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			ErrorMessage errorMessage = mapper.readValue(response.body().asInputStream(), ErrorMessage.class);
			
			return new CustomOrderException(errorMessage.getMessage(),errorMessage.getErrorCode(), 0);
		} catch (IOException e) {
				return new CustomOrderException("Internal server error", "INTERNAL_SERVER-ERROR", 0);
		}
		
		
	}

}
