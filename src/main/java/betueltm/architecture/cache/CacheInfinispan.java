package betueltm.architecture.cache;

import org.infinispan.client.hotrod.RemoteCache;

public class CacheInfinispan implements Cache {

	private RemoteCache<Object, Object> remoteCache = null;
	
	public CacheInfinispan(String cacheName, RemoteCache<Object, Object> remoteCache) {
		this.remoteCache = remoteCache;
	}
	
	@Override
	public void put(Object key, Object value) {
		remoteCache.put(key, value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> type) {
		return (T) remoteCache.get(key);
	}

	@Override
	public void evict(Object key) {
		remoteCache.remove(key);
	}

	@Override
	public Object get(Object key) {
		return remoteCache.get(key);
	}
	
	public void clearCache() {
		remoteCache.clear();
	}
}
