package com.order.external.client.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
	
	private long orderId;
    private long amount;
    private String referenceNumber;
    private String paymentMode;

}


//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class PaymentRequest {
//	
//	private long orderId;
//	    private long amount;
//	    private String referenceNumber;
//	    private PaymentMode paymentMode;
//
//}