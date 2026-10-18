package com.ecommerce.payment.service;

import com.ecommerce.payment.DTO.PaymentRequest;
import com.ecommerce.payment.DTO.PaymentResponse;
import com.ecommerce.payment.DTO.PaymentStatusUpdateRequest;
import com.ecommerce.payment.entity.Payment;
import com.ecommerce.payment.enums.PaymentStatus;
import com.ecommerce.payment.exception.PaymentNotFoundException;
import com.ecommerce.payment.repository.PaymentRepository;
import com.ecommerce.payment.util.TransactionIdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl  implements PaymentService{
	private final PaymentRepository paymentRepo;

	@Override
	public PaymentResponse createPayment(PaymentRequest request) {
		 Payment payment =  Payment.builder().orderId(request.getOrderId())
								.amount(request.getAmount())
								.paymentMethod(request.getPaymentMethod())
								.paymentStatus(PaymentStatus.PENDING)
								.transactionId(TransactionIdGenerator.generate()).build();
		 Payment savedPayment = paymentRepo.save(payment);
		return mapToResponse(savedPayment);
	}

	@Override
	public List<PaymentResponse> getPaymentByOrderId(String orderId) {

		List<Payment> payments = paymentRepo.findByOrderId(orderId)
				.filter(list -> !list.isEmpty())
				.orElseThrow(() ->
						new PaymentNotFoundException(
								"Payment not found for this orderId: " + orderId
						)
				);

		return payments.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public PaymentResponse updatePaymentStatus(Long PaymentId, PaymentStatusUpdateRequest request) {
		// TODO Auto-generated method stub
		Payment payment = paymentRepo.findById(PaymentId).orElseThrow(()-> new PaymentNotFoundException("payment not found with id "+PaymentId));
		payment.setPaymentStatus(request.getPaymentStatus());
		return mapToResponse(payment);
	}
	private PaymentResponse mapToResponse(Payment payment) {
		return new PaymentResponse(payment.getId(),
				payment.getOrderId(),
				payment.getAmount(),
				payment.getPaymentStatus(),
				payment.getTransactionId());
	}
}
