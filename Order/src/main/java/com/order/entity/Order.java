package com.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long id;

	    @Column(name = "PRODUCT_ID")
	    private long productId;

	    @Column(name = "QUANTITY")
	    private long quantity;

	    @Column(name = "ORDER_DATE")
	    private Instant orderDate;

	    @Column(name = "STATUS")
	    private String orderStatus;

	    @Column(name = "TOTAL_AMOUNT")
	    private long amount;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	@Id
//	@SequenceGenerator(
//			 name = "order_sequence",
//			 sequenceName = "order_sequence",
//			  allocationSize = 1
//			)
//	
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,
//						generator = "order_sequence"
//			)
//    private long orderId;
//
////    @Column(name = "PRODUCT_ID")
////    private long productId;
//    
////    @Column(name = "QUANTITY")
////    private long quantity;
//
//    @Column(name = "ORDER_DATE")
//    private Instant orderDate;
//
//    @Column(name = "STATUS")
//    private String orderStatus;
    
   
//-    @OneToMany(mappedBy = "orderReference",
//-            cascade = CascadeType.MERGE , fetch = FetchType.LAZY
//             
//			 )
	//@JoinColumn(name = "itemQuantityPairId",referencedColumnName = "itemQuantityPairId")
   // @Column(nullable = false)
   //- private List<OrderItem> orderItems = new ArrayList();

//    @Column(name = "TOTAL_AMOUNT")
//    private long amount;
//    
//    @OneToMany(cascade = CascadeType.MERGE , mappedBy = "productId")
//    @JoinColumn(nullable = false)
//    private List<OrderItem> orderItems;
}
