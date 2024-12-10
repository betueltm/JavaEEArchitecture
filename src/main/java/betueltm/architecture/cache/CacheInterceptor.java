package betueltm.architecture.cache;

import betueltm.architecture.util.StringUtil;

public abstract class CacheInterceptor extends CacheInvoker {

	protected abstract Object invoke(CacheOperationContext context, CacheOperationInvoker invoker);
	
	protected Object generateKey(CacheOperationContext context) {
		String key = context.getKey();
		if(StringUtil.notEmpty(key)) return generateSpecificKey(key, context);
		
		return generateGenericKey(context);
	}

	private Object generateGenericKey(CacheOperationContext context) {
		Object target = context.getTarget();
		Class<? extends Object> targetClass = target.getClass();
		String canonicalName = targetClass.getCanonicalName();
		
		return context.hashCode() + canonicalName.hashCode();
	}

	private Object generateSpecificKey(String key, CacheOperationContext context) {
		Object[] args = context.getArgs();
		for (Object arg : args) {
			;
		}
		return null;
	}

	protected Cache resolveCache(CacheOperationContext context) {
		String cacheName = context.getCacheName();
		return CacheInfinispanProvider.getInstance().resolveCache(cacheName);
	}
}
