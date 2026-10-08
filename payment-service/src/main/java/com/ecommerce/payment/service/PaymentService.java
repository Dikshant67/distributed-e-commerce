package com.ecommerce.payment.service;

import com.ecommerce.payment.DTO.PaymentResponse;
import com.ecommerce.payment.DTO.PaymentStatusUpdateRequest;
import com.ecommerce.payment.DTO.PaymentRequest;

public interface PaymentService {
		PaymentResponse createPayment(PaymentRequest request);
		PaymentResponse getPaymentByOrderId(String orderId);
		PaymentResponse updatePaymentStatus(Long PaymentId, PaymentStatusUpdateRequest request);
}
