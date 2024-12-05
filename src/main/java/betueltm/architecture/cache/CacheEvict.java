package betueltm.architecture.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheEvict {

	String cacheName() default CacheNameList.DEFAULT_CACHE_NAME;
	String key() default "";
}
