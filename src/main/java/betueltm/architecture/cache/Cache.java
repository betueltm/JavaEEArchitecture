package betueltm.architecture.cache;

public interface Cache {

	Object get(Object key);
	<T> T get(Object key, Class<T> type);
	void put(Object key, Object value);
	void evict(Object key);
	void clear();
}
