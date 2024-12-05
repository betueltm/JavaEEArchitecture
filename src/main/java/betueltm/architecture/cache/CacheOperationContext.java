package betueltm.architecture.cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import betueltm.architecture.util.StringUtil;

public class CacheOperationContext {
	private String key;
	private Object target;
	private Method method;
	private Object[] args;
	private String cacheName;
	
	public CacheOperationContext(Object target, Method method, Object[] args, String cacheName) {
		this.target = target;
		this.method = method;
		this.args = args;
		this.cacheName = cacheName;
	}
	
	public CacheOperationContext(String key, Object target, Method method, Object[] args, String cacheName) {
		this.key = key;
		this.target = target;
		this.method = method;
		this.args = args;
		this.cacheName = cacheName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getTarget() {
		return target;
	}
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(args);
		result = prime * result + Objects.hash(method);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheOperationContext other = (CacheOperationContext) obj;
		return Arrays.deepEquals(args, other.args) && Objects.equals(method, other.method);
	}
	
	public boolean hasKey() {
		return StringUtil.notEmpty(key);
	}
}
