package com.ecommerce.order.service;

import com.ecommerce.order.client.InventoryRestClient;
import com.ecommerce.order.dto.InventoryResponse;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.event.OrderPlacedEvent;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
		private final OrderRepository orderRepo;
		private final OrderMapper orderMapper;
		private final OutboxService outboxService;
		private final InventoryRestClient inventoryRestClient;
		@Override
		@Transactional
		public OrderResponse PlaceOrder(OrderRequest request) {

            InventoryResponse inventory =
					inventoryRestClient.isInStock(request.getSkuCode());

            log.info("Stock available {}", inventory.isInStock());

			if(!inventory.isInStock()) {
				throw new RuntimeException("product is out of stock :"
				+ request.getSkuCode());
			}

			Order order = orderMapper.toEntity(request);

			String orderId = UUID.randomUUID().toString();
			order.setOrderNumber(orderId);
			order.setOrderStatus("CREATED");

			orderRepo.save(order);

			log.info("Order saved in DB  {}",order);

			OrderPlacedEvent event = new OrderPlacedEvent();

			event.setEventId(UUID.randomUUID().toString());
			event.setOrderId(orderId);
			event.setSkuCode(request.getSkuCode());
			event.setQuantity(request.getQuantity());
			event.setEventTime(LocalDateTime.now());

			outboxService.saveOrderPlacedEvent(event);

			log.info("Order Placed Event saved to outbx {}",event);

			return orderMapper.toResponse(order);
		}
}
