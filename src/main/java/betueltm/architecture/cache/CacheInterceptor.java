package betueltm.architecture.cache;

public abstract class CacheInterceptor extends CacheInvoker {

	protected abstract Object invoke(CacheOperationContext context, CacheOperationInvoker invoker);
	
	protected Object generateKey(CacheOperationContext context) {
		//TODO : make sure that this will be sufficient
		return context.hashCode();
	}

	protected Cache resolveCache(CacheOperationContext context) {
		//TODO : implement a better strategy
		return new CacheInfinispan();
	}
}
