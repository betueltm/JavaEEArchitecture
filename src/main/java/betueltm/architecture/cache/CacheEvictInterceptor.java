package betueltm.architecture.cache;

public class CacheEvictInterceptor extends CacheInterceptor {

	@Override
	public Object invoke(CacheOperationContext context, CacheOperationInvoker invoker) {
		Object invocationResult = invoker.invoke();
		
		Cache cache = resolveCache(context);
		Object key = generateKey(context);
		
		doEvict(cache, key);
		return invocationResult;
	}

}
