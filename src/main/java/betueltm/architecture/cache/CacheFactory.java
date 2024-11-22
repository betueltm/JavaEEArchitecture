package betueltm.architecture.cache;

public class CacheFactory {

	public static Cache getCache() {
		//TODO: check if the cache is enabled in the application property
		return new CacheInfinispan();
	}
}
