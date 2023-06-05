package com.order.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.order.model.OrderRequest;



@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {
	
	   @PutMapping("/reduceQuantity/{id}")
	    ResponseEntity<Void> reduceQuantity(
	            @PathVariable("id") long productId,
	            @RequestParam long quantity
	    );

}





















