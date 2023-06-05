package com.order.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.entity.Order;

import com.order.external.client.PaymentService;
import com.order.external.client.request.PaymentRequest;

import com.order.model.OrderRequest;
import com.order.model.OrderResponse;
import com.order.repo.OrderRepo;
import com.order.service.OrderService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
	
    @Autowired
    private OrderService orderService;
    
    @Autowired
	private ModelMapper modelMapper; 
    
    @Autowired
    private PaymentService paymentService;
    
    @PostMapping("/create")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
      
    	 long orderId = orderService.placeOrder(orderRequest);
         log.info("Order Id: {}", orderId);
         return new ResponseEntity<>(orderId, HttpStatus.OK);
    	
//    	log.info(" 1. [] place order begin");
//    	
//    	List<OrderItem> items = new ArrayList<>();
//		List<OrderItemRequest> orderitems = orderRequest.getOrderItems();
//		for(OrderItemRequest item:orderitems)
//		{
//			System.out.println("for loop order item ");
//			OrderItem orderItem = this.modelMapper.map(item, OrderItem.class);
//			items.add(orderItem);
//			//orderItemRepo.save(orderItem);
//		}
//    	
//		log.info(" 2. [] saving order in repo..");
//    	
////    	Order order = Order.builder()
////				.orderItems(items)
////				.orderDate(Instant.now())
////				.orderStatus(1)
////				.build();
//    	
//    	long savedOrderId = this.orderService.saveOrder(orderRequest);
//    	
//    	log.info(" 3. [] order saved, updating qunatities of products");
//    	
//    	
//    	boolean placedOrder = orderService.placeOrder(orderRequest,savedOrderId);
//    	
//    	log.info("[] updated product quantities");
//    	
//    	// payment 
//    	log.info(" [] payment begin ");
//    	
//    	PaymentRequest paymentRequest = PaymentRequest.builder()
//    									.orderId(savedOrderId)
//    									.paymentMode(orderRequest.getPaymentMode())
//    									.referenceNumber("ZTK"+Math.random()+"UGh")
//    									.build();
//    	String orderstatus = null;
//    
//    	try {
//    		this.paymentService.doPayment(paymentRequest);
//    		orderstatus = "PLACED";
//    		log.info(" payment successsful");
//    		
//    	}
//    	catch (Exception e) {
//			orderstatus = "PAYMENT_FAILED";
//			log.info(" [] payment failed");
//		}
//    	
//    	//orderRequest.setOrderStatus(orderstatus);
//    	
//    	//OrderRequest request  = orderService.findOrderById(savedOrderId);
//    	//request.setOrderStatus(orderstatus);
//    	orderService.updateOrder(orderstatus,savedOrderId);
//    	
//    	log.info("[] place order end ");
//    	
//        return new ResponseEntity<>(placedOrder, HttpStatus.OK);
//   
    	}
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long id)
    {
    	 OrderResponse orderDetailsById = orderService.getOrderDetails(id);
    	 return new ResponseEntity<OrderResponse>(orderDetailsById, HttpStatus.OK);
    }
    

}























