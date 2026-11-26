package com.ecommerce.product.cache;

import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshScheduler {
	private final ProductCacheRefreshService productCacheRefreshService;
	private final ProductService productService;
	@Scheduled (fixedDelay = 60000)
	public void refreshProductCache() {

		productCacheRefreshService.refreshProductCache (5434L);
	}
}
