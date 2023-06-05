package com.order.service;

import java.util.List;

import com.order.entity.Order;

import com.order.model.OrderRequest;
import com.order.model.OrderResponse;

public interface OrderService {

	 long placeOrder(OrderRequest orderRequest);
	
	   OrderResponse getOrderDetails(long orderId);
	   	
	//public long saveOrder(OrderRequest orderRequest);
		
	//public OrderRequest findOrderById(Long id);

	//public void updateOrder(OrderRequest request);

//-	public void updateOrder(String orderstatus, long savedOrderId);

	//public OrderResponse getOrderDetailsById(long id);
}
