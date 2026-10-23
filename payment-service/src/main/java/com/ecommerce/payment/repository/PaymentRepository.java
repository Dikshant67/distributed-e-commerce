package com.ecommerce.payment.repository;

import com.ecommerce.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
	Optional<List<Payment>> findByOrderId(String orderId);
	Optional<Payment> findByTransactionId(String transactionId);
}
