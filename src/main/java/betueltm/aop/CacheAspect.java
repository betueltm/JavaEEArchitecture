package betueltm.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import betueltm.architecture.cache.Cachable;
import betueltm.architecture.cache.CacheOperationContext;
import betueltm.architecture.cache.CacheResultInterceptor;

@Aspect
public class CacheAspect {

	@Around("@annotation(cachable) && call(* *(..))")
	public Object around(ProceedingJoinPoint proceedingJoinPoint, Cachable cachable) throws NoSuchMethodException, SecurityException {
		Object[] args = proceedingJoinPoint.getArgs();
		
		Signature signature = proceedingJoinPoint.getSignature();
		String methodName = signature.getName();
		
		@SuppressWarnings({ "unchecked" })
		Method method = signature.getDeclaringType().getMethod(methodName, mapParameters(args));
		Object target = proceedingJoinPoint.getTarget();
		
		CacheOperationContext cacheOperationContext = new CacheOperationContext(target, method, args);
		CacheResultInterceptor cacheInterceptor = new CacheResultInterceptor();
		
		return cacheInterceptor.invoke(cacheOperationContext, () -> {
			try {
				return proceedingJoinPoint.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
				return null;
			}
		});
	}
	
	private Class<?>[] mapParameters(Object[] args){
		Class<?>[] classArray = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			classArray[i] = args[i].getClass();
		}
		return classArray;
	}
}
