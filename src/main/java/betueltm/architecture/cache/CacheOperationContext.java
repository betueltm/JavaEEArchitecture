package betueltm.architecture.cache;

import java.lang.reflect.Method;

public interface CacheOperationContext {
	Object getTarget();
	Method getMethod();
	Object[] getArgs();
}
