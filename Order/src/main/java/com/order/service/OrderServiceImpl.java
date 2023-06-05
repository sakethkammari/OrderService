package com.order.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.order.entity.Order;

import com.order.exception.CustomOrderException;
import com.order.external.client.PaymentService;
import com.order.external.client.ProductService;
import com.order.external.client.request.PaymentRequest;
import com.order.external.client.response.PaymentResponse;
import com.order.external.client.response.ProductResponse;
import com.order.model.OrderRequest;
import com.order.model.OrderResponse;

import com.order.repo.OrderRepo;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2

public class OrderServiceImpl implements OrderService
{
	 @Autowired
	    private OrderRepo orderRepository;

	    @Autowired
	    private ProductService productService;

	    @Autowired
	    private PaymentService paymentService;

	    @Autowired
	    private RestTemplate restTemplate;
	
	@Override
	public long placeOrder(OrderRequest orderRequest) {
		//Order Entity -> Save the data with Status Order Created
        //Product Service - Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success-> COMPLETE, Else
        //CANCELLED

        log.info("Placing Order Request: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest
                = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order Places successfully with Order Id: {}", order.getId());
        return order.getId();
	}

	 @Override
	    public OrderResponse getOrderDetails(long orderId) {
	        log.info("Get order details for Order Id : {}", orderId);

	        Order order
	                = orderRepository.findById(orderId)
	                .orElseThrow(() -> new CustomOrderException("Order not found for the order Id:" + orderId,
	                        "NOT_FOUND",
	                        404));

	        log.info("Invoking Product service to fetch the product for id: {}", order.getProductId());
	        ProductResponse productResponse
	                = restTemplate.getForObject(
	                "http://localhost:8769/product/"  + order.getProductId(),
	                ProductResponse.class
	        );

	        log.info("Getting payment information form the payment Service");
	        PaymentResponse paymentResponse
	                = restTemplate.getForObject(
	                "http://localhost:8777/payment/" + "order/" + order.getId(),
	                PaymentResponse.class
	                );

	        OrderResponse.ProductDetails productDetails
	                = OrderResponse.ProductDetails
	                .builder()
	                .productName(productResponse.getProductName())
	                .productId(productResponse.getProductId())
	                .build();

	        OrderResponse.PaymentDetails paymentDetails
	                = OrderResponse.PaymentDetails
	                .builder()
	                .paymentId(paymentResponse.getPaymentId())
	                .paymentStatus(paymentResponse.getStatus())
	                .paymentDate(paymentResponse.getPaymentDate())
	                .paymentMode(paymentResponse.getPaymentMode())
	                .build();

	        OrderResponse orderResponse
	                = OrderResponse.builder()
	                .orderId(order.getId())
	                .orderStatus(order.getOrderStatus())
	                .amount(order.getAmount())
	                .orderDate(order.getOrderDate())
	                .productDetails(productDetails)
	                .paymentDetails(paymentDetails)
	                .build();

	        return orderResponse;
	    }
//	
//	@Autowired
//	private OrderRepo orderRepo;
//	
//	@Autowired
//	private ProductService productService;
//	
//	
//	
//
//	
//	@Autowired
//	private ModelMapper modelMapper; 
//
//	@Override
//	//@Transactional
//	public boolean placeOrder(OrderRequest orderRequest,long orderId) {
//		
//	log.info("3.1 - making rest call to product service to update quantity");
//		
//		List<OrderItemRequest> orderitems  = orderRequest.getOrderItems();
//		
//		for(OrderItemRequest item:orderitems)
//		{
//		     productService.updateQuantity(item);
//		}
//	 
//		log.info(" 3.2 updated products quantity!");
//		List<OrderItem> items = new ArrayList<>();
//		for(OrderItemRequest item:orderitems)
//		{
//			//System.out.println("for loop order item ");
//			OrderItem orderItem = this.modelMapper.map(item, OrderItem.class);
//				
//			orderItem.setOrderReference(orderRepo.findById(orderId).orElseThrow(()-> new CustomOrderException("invalid id", "IVALID_ID")));
//			orderItemRepo.save(orderItem);
//			log.info(" 3.3 - !saved order Item in repo");
//			//System.out.println(" ORDER ITEM::: ");
//			//System.out.println(orderItem.toString());
//			
//			//items.add(orderItem);
//			//orderItemRepo.save(orderItem);
//		}
//		
//		log.info("3.4 All order items  saved");
//	
//		
//		
//		//Order save = this.orderRepo.save(order);
//		
////		for(OrderItem item:items)
////		{
////			System.out.println("for loop order item "+item);
////			item.setOrderReference(order);
////			//item.set
////			//OrderItem save2 = this.orderItemRepo.save(item);
////			//	System.out.println(save2 + " saved order itrm ");
////		}
//		
//		
//		log.info("order placed sucessfully!");
//		
//		return true;
//		
//	}
//
//	@Override
//	public long saveOrder(OrderRequest orderRequest) {
////		orderRequest.setOrderDate(Instant.now());
////		orderRequest.setOrderStatus("saved");
//		log.info(" 2.1 order saved in repo");
//		Order order = this.modelMapper.map(orderRequest, Order.class);
//		order.setOrderDate(Instant.now());
//		order.setOrderStatus("saved");
////		order.setOrderItems(null);
//		Order savedOrder = this.orderRepo.save(order);
//		  long orderId = savedOrder.getOrderId();
//		return  orderId;
//		 
//	}
//
//	@Override
//	public OrderRequest findOrderById(Long id) {
//		return this.modelMapper.map(orderRepo.findById(id), OrderRequest.class);
//	}
//
//	@Override
//	public void updateOrder(String orderstatus, long id) {
//		
//		
//		 Order order = orderRepo.findById(id).orElseThrow(()->
//				 
//		 						new CustomOrderException(" Invalid order Id", "ID_NOT_FOUND"));
//		
//		// order.setOrderDate(Instant.now());
//		 order.setOrderStatus(orderstatus);
//	///	 order.setOrderItems(order.getOrderItems());
//		 this.orderRepo.save(order);
//		 
//	}
//
//	@Override
//	public OrderResponse getOrderDetailsById(long id) {
//		
//		Order order = this.orderRepo.findById(id).orElseThrow(()-> new CustomOrderException(" invalid order id", "ID_NOT_FOUND"));
//		
//		
//		List<OrderItem> items = this.orderItemService.getProductsByOrderId(id);
//		
//		OrderResponse orderResponse = OrderResponse.builder()
//										.orderId(order.getOrderId())
//										.orderItems(items)
//										.build();
//		return orderResponse;
//	}

	
	
	

}
