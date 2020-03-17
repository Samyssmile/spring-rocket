package de.abramov.backend.configuration.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Computes cache keys. Used by generic rest service to generate cache keys
 * @author Samuel Abramov
 *
 * 16.10.2019
 *
 */
public class CustomCacheKeyGenerator implements KeyGenerator {
  
  public Object generate(Object target, Method method, Object... params) {
      return target.getClass().getSimpleName() + "_"
        + method.getName() + "_"
        + StringUtils.arrayToDelimitedString(params, "_");
  }
}
