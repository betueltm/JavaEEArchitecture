package betueltm.architecture.cache;

public abstract class CacheInterceptor extends CacheInvoker {

	protected abstract Object invoke(CacheOperationContext context, CacheOperationInvoker invoker);
	
	protected Object generateKey(CacheOperationContext context) {
		if(context.hasKey()) return context.getKey();
		return context.hashCode();
	}

	protected Cache resolveCache(CacheOperationContext context) {
		String cacheName = context.getCacheName();
		return CacheInfinispanProvider.getInstance().resolveCache(cacheName);
	}
}
