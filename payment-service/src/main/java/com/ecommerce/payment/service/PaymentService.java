package com.ecommerce.payment.service;

import com.ecommerce.payment.DTO.PaymentRequest;
import com.ecommerce.payment.DTO.PaymentResponse;
import com.ecommerce.payment.DTO.PaymentStatusUpdateRequest;

import java.util.List;

public interface PaymentService {
		PaymentResponse createPayment(PaymentRequest request);
		List<PaymentResponse> getPaymentByOrderId(String orderId);
		PaymentResponse updatePaymentStatus(Long PaymentId, PaymentStatusUpdateRequest request);
}
