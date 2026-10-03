package com.ecommerce.order.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ecommerce.order.client.InventoryRestClient;
import com.ecommerce.order.exception.InventoryServiceUnavailableException;
import com.ecommerce.order.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.Uuid;
import org.springframework.stereotype.Service;

import com.ecommerce.order.client.InventoryFeignClient;
import com.ecommerce.order.dto.InventoryResponse;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.event.OrderPlacedEvent;
import com.ecommerce.order.kafka.OrderEventProducer;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.order.Order;
import com.ecommerce.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
		private final OrderRepository orderRepo;
		private final OrderMapper orderMapper;
		private final OrderEventProducer orderEventProducer;
		private final InventoryRestClient inventoryRestClient;
		@Override
		public OrderResponse PlaceOrder(OrderRequest request) {

            InventoryResponse inventory = null;

                inventory = inventoryRestClient.isInStock(request.getSkuCode());


            log.info("Stock available "+ inventory.isInStock());

			if(!inventory.isInStock()) {
				throw new RuntimeException("product is out of stock");
			}


			Order order = orderMapper.toEntity(request);
			String orderId = UUID.randomUUID().toString();
			order.setOrderNumber(orderId);
			order.setOrderStatus("CREATED");
			orderRepo.save(order);

			log.info("Order saved in DB  {}",order);

			OrderPlacedEvent event = new OrderPlacedEvent();
			event.setEventId(Uuid.randomUuid().toString());
			event.setOrderId(orderId);
			event.setSkuCode(request.getSkuCode());
			event.setQuantity(request.getQuantity());
			
			event.setEventTime(LocalDateTime.now());

			orderEventProducer.sendOrderEvent(event);

			log.info("Order Placed Event sent to Kafka  {}",event);

			return orderMapper.toResponse(order);
		}
}
