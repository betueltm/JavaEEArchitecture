package betueltm.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import betueltm.architecture.cache.CacheEvict;
import betueltm.architecture.cache.CacheEvictInterceptor;
import betueltm.architecture.cache.CacheOperationContext;
import betueltm.architecture.cache.CacheResultInterceptor;
import betueltm.architecture.cache.Cacheable;
import betueltm.architecture.util.PropertyUtil;

@Aspect
public class CacheAspect {

	@Around("@annotation(cacheable) && call(* *(..))")
	public Object around(ProceedingJoinPoint proceedingJoinPoint, Cacheable cacheable) {
		if(PropertyUtil.isCacheEnabled()) return useCacheResultInterceptor(proceedingJoinPoint, cacheable);
		
		try {
			return proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return null;
		}
	}

	private Object useCacheResultInterceptor(ProceedingJoinPoint proceedingJoinPoint, Cacheable cacheable) {
		CacheResultInterceptor cacheInterceptor = new CacheResultInterceptor();
		CacheOperationContext cacheOperationContext = CacheOperationContextFactory.createCacheOperationContext(proceedingJoinPoint, cacheable);
		
		return cacheInterceptor.invoke(cacheOperationContext, () -> {
			try {
				return proceedingJoinPoint.proceed();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
				return null;
			}
		});
	}
	
	@Around("@annotation(cacheEvict) && call(* *(..))")
	public Object around(ProceedingJoinPoint proceedingJoinPoint, CacheEvict cacheEvict) {
		if(PropertyUtil.isCacheEnabled()) return useCacheEvictInterceptor(proceedingJoinPoint, cacheEvict);
		
		try {
			return proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return null;
		}
	}

	private Object useCacheEvictInterceptor(ProceedingJoinPoint proceedingJoinPoint, CacheEvict cacheEvict) {
		CacheEvictInterceptor cacheInterceptor = new CacheEvictInterceptor();
		CacheOperationContext cacheOperationContext = CacheOperationContextFactory.createCacheOperationContext(proceedingJoinPoint, cacheEvict);
		
		return cacheInterceptor.invoke(cacheOperationContext, () -> {
			try {
				return proceedingJoinPoint.proceed();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
				return null;
			}
		});
	}
}
