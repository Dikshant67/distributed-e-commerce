package com.ecommerce.notification.service;

import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

	@Value("${twilio.from-number}")
	private String fromNumber;

	public void sendSms(String to, String message) {
		try {
			Message response = Message.creator(
					new PhoneNumber(to),
					new PhoneNumber(fromNumber),
					"sms_order_confirmation"
			).create();

			log.info("Twilio SMS status: {}, SID: {}",
					response.getStatus(), response.getSid());

		} catch (ApiException e) {
			log.error("Twilio SMS failed. Code: {}, Status: {}, Message: {}",
					e.getCode(), e.getStatusCode(), e.getMessage());
			throw e;
		}
	}
}