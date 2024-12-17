package betueltm.architecture.cache;

public class CacheEvictInterceptor extends CacheInterceptor {

	@Override
	public Object invoke(CacheOperationContext context, CacheOperationInvoker invoker) {
		Object invocationResult = invoker.invoke();
		
		Cache cache = resolveCache(context);
		Object key = generateKey(context);
		
		if(context.isEvictAllEntries()) doEvictAllEntries(cache);
		else doEvict(cache, key);
		
		return invocationResult;
	}

}
