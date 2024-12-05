package betueltm.architecture.cache;

public abstract class CacheInvoker {

	protected Object doGet(Cache cache, Object key) {
		try {
			return cache.get(key);
		} catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
			return null;
		}
	}
	
	protected void doPut(Cache cache, Object key, Object value) {
		try {
			cache.put(key, value);
		}
		catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();
		}
	}
	
	protected void doEvict(Cache cache, Object key) {
		try {
			cache.evict(key);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
