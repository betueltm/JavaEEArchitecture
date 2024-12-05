package betueltm.architecture.util;

public class PropertyUtil {

	public static boolean isCacheEnabled() {
		return Boolean.getBoolean("cache.enabled");
	}
	
	public static String getEnvironment() {
		return System.getProperty("environment");
	}
}
