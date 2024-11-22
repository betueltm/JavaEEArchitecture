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
	public Cache getInstance() {
		return null;
	}

	@Override
	public void setValue(String key, String value, Class<?> clazz) {
		key = addClassNameToKey(key, clazz);
		getCache().put(key, value);
	}

	@Override
	public void setValue(String key, Object value) {
		try {
			Class<? extends Object> objectClass = value.getClass();
			key = addClassNameToKey(key, objectClass);
			getCache().put(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> T getValue(String key, Class<T> clazz) {
		try {
			key = addClassNameToKey(key, clazz);
			return clazz.cast(getCache().get(key));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void del(String key, Class<?> clazz) {
		key = addClassNameToKey(key, clazz);
		getCache().remove(key);
	}
}
