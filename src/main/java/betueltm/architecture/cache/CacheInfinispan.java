package betueltm.architecture.cache;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

public class CacheInfinispan implements Cache {

//	private CacheInfinispan cacheInfinispan = new CacheInfinispan();
	private RemoteCacheManager remoteCacheManager = null;
	private String CACHE_NAME = "teste";
	
	public CacheInfinispan() {
		configureCache();
	};
	
	private void configureCache() {
		ConfigurationBuilder clientBuilder = new ConfigurationBuilder();
		clientBuilder.clientIntelligence(ClientIntelligence.BASIC);
		clientBuilder.addCluster("infinispan-site-A").addClusterNode("localhost", 11222);
		clientBuilder.security().authentication().saslMechanism("SCRAM-SHA-512").username("admin").password("password");
		clientBuilder.marshaller(new CustomSerializationMarshaller());
		remoteCacheManager = new RemoteCacheManager(clientBuilder.build());
	}
	
	private <K, V> RemoteCache<K, V> getCache() {
		return remoteCacheManager.getCache(CACHE_NAME);
	}
	
	@Override
	public void put(Object key, Object value) {
		getCache().put(key, value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> type) {
		return (T) getCache().get(key);
	}

	@Override
	public void evict(Object key) {
		getCache().remove(key);
	}

	@Override
	public Object get(Object key) {
		return getCache().get(key);
	}
}
