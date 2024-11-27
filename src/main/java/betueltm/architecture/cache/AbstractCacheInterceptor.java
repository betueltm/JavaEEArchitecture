package betueltm.architecture.cache;

public abstract class AbstractCacheInterceptor extends AbstractCacheInvoker{

	protected abstract Object invoke(CacheOperationContext context, CacheOperationInvoker invoker); 
}
