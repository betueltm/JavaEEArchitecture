package betueltm.architecture.cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

public class CacheInfinispanProvider {

	private static CacheInfinispanProvider cacheInfinispanProvider = null;
	private RemoteCacheManager remoteCacheManager = null;
	private Map<String,CacheInfinispan> remoteCaches = new ConcurrentHashMap<String,CacheInfinispan>();
	
	private CacheInfinispanProvider() {
		configureInfinispanRemoteCacheManager();
	}
	
	public static CacheInfinispanProvider getInstance() {
		if(Objects.isNull(cacheInfinispanProvider)) cacheInfinispanProvider = new CacheInfinispanProvider();
		return cacheInfinispanProvider;
	}
	
	private void configureInfinispanRemoteCacheManager() {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.clientIntelligence(ClientIntelligence.HASH_DISTRIBUTION_AWARE);
		configurationBuilder.addServers("localhost:11223");
//		configurationBuilder.addCluster("infinispan-site-A").addClusterNode("localhost", 11223);
		configurationBuilder.security().authentication().saslMechanism("SCRAM-SHA-512").username("admin").password("password");
		configurationBuilder.marshaller(new CustomSerializationMarshaller());
		configurationBuilder.statistics().enable();
		
		remoteCacheManager = new RemoteCacheManager(configurationBuilder.build());
	}
	
	public CacheInfinispan resolveCache(String cacheName) {
		CacheInfinispan cacheInfinispan = remoteCaches.get(cacheName);
		if(Objects.nonNull(cacheInfinispan)) return cacheInfinispan;
		
		CacheInfinispan newCacheInfinispan = createNewCacheInfinispan(cacheName);
		remoteCaches.put(cacheName, newCacheInfinispan);
		return newCacheInfinispan;
	}

	private CacheInfinispan createNewCacheInfinispan(String cacheName) {
		RemoteCache<Object,Object> remoteCache = remoteCacheManager.getCache(cacheName);
		if(Objects.isNull(remoteCache)) {
			remoteCache = remoteCacheManager.administration().createCache(cacheName, DefaultTemplate.DIST_ASYNC);
		};
		return new CacheInfinispan(cacheName, remoteCache);
	}
}
