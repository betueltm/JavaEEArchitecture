package betueltm.architecture.cache;

public class CacheInterceptor extends AbstractCacheInterceptor {

	@Override
	protected Object invoke(CacheOperationContext context, CacheOperationInvoker invoker) {
		Object invocationResult = invoker.invoke();
		
		Object key = generateKey(context);
		Cache cache = resolveCache(context);
		doPut(cache, key, invocationResult);
		return invocationResult;
	}

}
