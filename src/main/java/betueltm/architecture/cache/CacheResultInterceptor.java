package betueltm.architecture.cache;

import java.util.Objects;

public class CacheResultInterceptor extends CacheInterceptor {

	@Override
	public Object invoke(CacheOperationContext context, CacheOperationInvoker invoker) {
		Cache cache = resolveCache(context);
		Object key = generateKey(context);
		
		Object cachedValue = doGet(cache, key);
		
		if(Objects.isNull(cachedValue)) {
			Object invocationResult = invoker.invoke();
			doPut(cache, key, invocationResult);
			
			return invocationResult;
		}
		
		return cachedValue;
	}
}
