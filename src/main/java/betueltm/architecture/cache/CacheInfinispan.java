package betueltm.architecture.cache;

import java.util.Objects;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		Class<? extends Object> objectClass = value.getClass();
		key = addClassNameToKey(key, objectClass);
		getCache().put(key, convertObjectToJason(value));
	}

	@Override
	public <T> T getValueAsObject(String key, Class<T> clazz) {
		String value = getValue(key, clazz);
		if(Objects.isNull(value)) return null;
		return (T) convertJsonToObject(value, clazz);
	}

	private <T> T convertJsonToObject(String value, Class<T> clazz) {
		try {
			return getObjectMapper().readValue(value, clazz);
		} catch (JsonProcessingException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private String convertObjectToJason(Object object) {
		try {
			return getObjectMapper().writeValueAsString(object);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
//		objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return objectMapper;
	}

	@Override
	public String getValue(String key, Class<?> clazz) {
		key = addClassNameToKey(key, clazz);
		return (String) getCache().get(key);
	}

	@Override
	public void del(String key, Class<?> clazz) {
		key = addClassNameToKey(key, clazz);
		getCache().remove(key);
	}

}
