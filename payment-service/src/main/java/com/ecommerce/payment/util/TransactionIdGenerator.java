package com.ecommerce.payment.util;

import java.util.UUID;

public class TransactionIdGenerator {
	public static String generate() {
		return "txn-"+UUID.randomUUID() ;
	}
}
