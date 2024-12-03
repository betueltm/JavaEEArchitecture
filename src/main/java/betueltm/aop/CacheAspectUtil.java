package betueltm.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import betueltm.architecture.cache.Cachable;
import betueltm.architecture.cache.CacheOperationContext;

public class CacheAspectUtil {

	protected static CacheOperationContext createCacheOperationContext(ProceedingJoinPoint proceedingJoinPoint, Cachable cachable) {
		Object[] args = proceedingJoinPoint.getArgs();
		Method method = getMethodFromSignature(args, proceedingJoinPoint.getSignature());
		Object target = proceedingJoinPoint.getTarget();
		String cacheName = System.getProperty("environment") + "-" + cachable.cacheName();
		return new CacheOperationContext(target, method, args, cacheName);
	}

	@SuppressWarnings("unchecked")
	private static Method getMethodFromSignature(Object[] args, Signature signature) {
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
