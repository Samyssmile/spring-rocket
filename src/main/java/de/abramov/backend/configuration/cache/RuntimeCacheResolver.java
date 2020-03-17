package de.abramov.backend.configuration.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;

import java.util.Arrays;
import java.util.Collection;

/**
 * Used by generic rest service to separate cache memory
 * 
 * @author Samuel Abramov Oct 2, 2019
 *
 */
public class RuntimeCacheResolver extends SimpleCacheResolver {

  /**
   * C'Tor
   * 
   * @param cacheManager cache manager
   */
  public RuntimeCacheResolver(CacheManager cacheManager) {
    super(cacheManager);
  }
  
  @Override
  protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
    return Arrays.asList(context.getTarget().getClass().getSimpleName());
  }
  
  
}
