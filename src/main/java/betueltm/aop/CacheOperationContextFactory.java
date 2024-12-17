package betueltm.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import betueltm.architecture.cache.CacheEvict;
import betueltm.architecture.cache.CacheKeyParser;
import betueltm.architecture.cache.CacheOperationContext;
import betueltm.architecture.cache.Cacheable;
import betueltm.architecture.util.PropertyUtil;

public class CacheOperationContextFactory {

	protected static CacheOperationContext createCacheOperationContext(ProceedingJoinPoint proceedingJoinPoint, Cacheable cacheable) {
		return newCacheOperationContext(cacheable.key(), cacheable.cacheName(), false, proceedingJoinPoint);
	}

	protected static CacheOperationContext createCacheOperationContext(ProceedingJoinPoint proceedingJoinPoint, CacheEvict cacheEvict) {
		return newCacheOperationContext(cacheEvict.key(), cacheEvict.cacheName(), cacheEvict.evictAllEntries(), proceedingJoinPoint);
	}
	
	private static CacheOperationContext newCacheOperationContext(String keyPattern, String cacheName,	boolean evictAllEntries, ProceedingJoinPoint proceedingJoinPoint) {
		Object[] args = proceedingJoinPoint.getArgs();
		CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
		Method method = getMethodFromSignature(args, signature);
		
		Object target = proceedingJoinPoint.getTarget();
		cacheName = PropertyUtil.getEnvironment() + "-" + cacheName;
		
		Object key = CacheKeyParser.parseKeyFromProcedingJointPoint(keyPattern, proceedingJoinPoint);
		return new CacheOperationContext(key, target, method, args, cacheName, evictAllEntries);
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
