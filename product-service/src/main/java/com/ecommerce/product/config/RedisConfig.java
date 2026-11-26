
package com.ecommerce.product.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
	public ObjectMapper redisObjectMapper() {
		BasicPolymorphicTypeValidator ptv =
				BasicPolymorphicTypeValidator.builder()
						.allowIfSubType("com.ecommerce.product.")
						.build();

		return JsonMapper.builder()
				.activateDefaultTyping(
						ptv,
						DefaultTyping.NON_FINAL,
						JsonTypeInfo.As.PROPERTY
				)
				.build();
	}

	@Bean
	public CacheManager cacheManager(
			RedisConnectionFactory connectionFactory) {

		GenericJacksonJsonRedisSerializer serializer =
				new GenericJacksonJsonRedisSerializer(redisObjectMapper());

		RedisCacheConfiguration defaultConfig =
				RedisCacheConfiguration.defaultCacheConfig()
						.disableCachingNullValues()
						.prefixCacheNameWith("product-service::")
						.serializeValuesWith(
								RedisSerializationContext.SerializationPair
										.fromSerializer(serializer)
						);

		Map<String, RedisCacheConfiguration> cacheConfigurations =
				new HashMap<>();

		cacheConfigurations.put(
				"productCache",
				defaultConfig.entryTtl(Duration.ofMinutes(30))
		);

		cacheConfigurations.put(
				"categoryCache",
				defaultConfig.entryTtl(Duration.ofHours(2))
		);

		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(defaultConfig)
				.withInitialCacheConfigurations(cacheConfigurations)
				.transactionAware()
				.build();
	}
}
