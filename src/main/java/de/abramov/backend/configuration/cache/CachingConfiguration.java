package de.abramov.backend.configuration.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used by generic rest Service to separate cache memory. With this class each service
 * implementation can use own memory cache.
 * 
 * @author Samuel Abramov Oct 2, 2019
 *
 */
@Configuration
@EnableCaching
public class CachingConfiguration extends CachingConfigurerSupport {

  public static final String CACHE_RESOLVER_NAME = "simpleCacheResolver";

  @Bean
  @Override
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }

  @Bean(CACHE_RESOLVER_NAME)
  public CacheResolver cacheResolver(CacheManager cacheManager) {
    return new RuntimeCacheResolver(cacheManager);
  }
  
  @Override
  @Bean("customKeyGenerator")
  public KeyGenerator keyGenerator() {
      return new CustomCacheKeyGenerator();
  }
}