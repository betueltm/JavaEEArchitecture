package betueltm.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import betueltm.architecture.cache.Cachable;
import betueltm.architecture.cache.CacheResultInterceptor;

@Aspect
public class CacheAspect {

	@Around("@annotation(cachable) && call(* *(..))")
	public Object around(ProceedingJoinPoint proceedingJoinPoint, Cachable cachable) throws NoSuchMethodException, SecurityException {
		CacheResultInterceptor cacheInterceptor = new CacheResultInterceptor();
		return cacheInterceptor.invoke(CacheAspectUtil.createCacheOperationContext(proceedingJoinPoint, cachable), () -> {
			try {
				return proceedingJoinPoint.proceed();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
				return null;
			}
		});
	}
}
