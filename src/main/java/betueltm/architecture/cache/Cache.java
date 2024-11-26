package betueltm.architecture.cache;

public interface Cache {

	Cache getInstance();
	void setValue(String key, String value, Class<?> clazz);
	void setValue(String key, Object value);
	<T> T getValue(String key, Class<T> clazz);
	void del(String key, Class<?> clazz);
	
	default String addClassNameToKey(String key, Class<?> clazz) {
		String className = clazz.getName();
		return new StringBuilder().append(className).append(":").append(key).toString();
	}
}
