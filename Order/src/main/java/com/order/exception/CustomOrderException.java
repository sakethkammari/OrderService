package com.order.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomOrderException extends RuntimeException{
		
//	String msg;
//	String errorcode;
//	public CustomOrderException(String msg, String errorcode) {
//		super();
//		this.msg = msg;
//		this.errorcode = errorcode;
//	}
	
	private String errorCode;
    private int status;

    public CustomOrderException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
	
	
	
}
