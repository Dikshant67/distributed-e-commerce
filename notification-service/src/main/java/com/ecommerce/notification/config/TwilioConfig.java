package com.ecommerce.notification.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@Slf4j
public class TwilioConfig {
		@Value("${twilio.account-sid}")
		private String accountSid;
		
		@Value("${twilio.auth-token}")
		private String authToken;

		@PostConstruct
		public void init() {
			Twilio.init(accountSid.trim(), authToken.trim());
		}
}
