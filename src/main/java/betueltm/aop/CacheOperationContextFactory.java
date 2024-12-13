package betueltm.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import betueltm.architecture.cache.CacheEvict;
import betueltm.architecture.cache.CacheOperationContext;
import betueltm.architecture.cache.Cacheable;
import betueltm.architecture.util.PropertyUtil;

public class CacheOperationContextFactory {

	protected static CacheOperationContext createCacheOperationContext(ProceedingJoinPoint proceedingJoinPoint, Cacheable cacheable) {
		return newCacheOperationContext(cacheable.key(), cacheable.cacheName(), proceedingJoinPoint);
	}

	protected static CacheOperationContext createCacheOperationContext(ProceedingJoinPoint proceedingJoinPoint, CacheEvict cacheEvict) {
		return newCacheOperationContext(cacheEvict.key(), cacheEvict.cacheName(), proceedingJoinPoint);
	}
	
	private static CacheOperationContext newCacheOperationContext(String keyPattern, String cacheName,	ProceedingJoinPoint proceedingJoinPoint) {
		Object[] args = proceedingJoinPoint.getArgs();
		Method method = getMethodFromSignature(args, (CodeSignature) proceedingJoinPoint.getSignature());
		Object target = proceedingJoinPoint.getTarget();
		cacheName = PropertyUtil.getEnvironment() + "-" + cacheName;

		return new CacheOperationContext(keyPattern, target, method, args, cacheName);
	}

	@SuppressWarnings("unchecked")
	private static Method getMethodFromSignature(Object[] args, CodeSignature signature) {
		try {
			
			String methodName = signature.getName();
			return signature.getDeclaringType().getMethod(methodName, mapParameters(args));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private static Class<?>[] mapParameters(Object[] args){
		Class<?>[] classArray = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			classArray[i] = args[i].getClass();
		}
		return classArray;
	}
}
