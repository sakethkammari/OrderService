package com.order.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;

import com.order.external.client.request.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	
//private long productId;
    
    private long productId;
    private long totalAmount;
    private long quantity;
    private String paymentMode;
    

    
  

}
