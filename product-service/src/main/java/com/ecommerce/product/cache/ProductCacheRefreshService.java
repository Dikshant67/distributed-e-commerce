package com.ecommerce.product.cache;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCacheRefreshService {
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final CacheManager cacheManager;
	public void refreshProductCache(Long productId)
	{
		Product product = productRepository.findById(productId).orElse (null);
		if (product == null) {
			Cache cache = cacheManager.getCache ("productCache");
			if (cache != null) {
				cache.evict (productId);
			}
		}else {
			ProductDto dto = productMapper.toDto (product);
			Cache cache = cacheManager.getCache ("productCache");
			if (cache != null) {
				cache.put (productId, dto);
			}
		}

	}

}
