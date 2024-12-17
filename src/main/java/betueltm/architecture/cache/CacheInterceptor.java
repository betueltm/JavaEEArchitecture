package betueltm.architecture.cache;

import java.util.Objects;

public abstract class CacheInterceptor extends CacheInvoker {

	protected abstract Object invoke(CacheOperationContext context, CacheOperationInvoker invoker);
	
	protected Object generateKey(CacheOperationContext context) {
		Object key = context.getKey();
		if(Objects.nonNull(key)) return generateCustomKey(context, key);
		
		return generateGenericKey(context);
	}
	
	private Object generateCustomKey(CacheOperationContext context, Object key) {
		Object target = context.getTarget();
		Class<? extends Object> targetClass = target.getClass();
		String canonicalName = targetClass.getCanonicalName();
		
		return key.hashCode() + canonicalName.hashCode();
	}

	private Object generateGenericKey(CacheOperationContext context) {
		Object target = context.getTarget();
		Class<? extends Object> targetClass = target.getClass();
		String canonicalName = targetClass.getCanonicalName();
		
		return context.hashCode() + canonicalName.hashCode();
	}

	protected Cache resolveCache(CacheOperationContext context) {
		String cacheName = context.getCacheName();
		return CacheInfinispanProvider.getInstance().resolveCache(cacheName);
	}
}
