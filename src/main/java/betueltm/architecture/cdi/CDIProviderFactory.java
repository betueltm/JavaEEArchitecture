package betueltm.architecture.cdi;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import betueltm.architecture.util.CollectionUtil;

public class CDIProviderFactory {

	@SuppressWarnings("unchecked")
	public static <T> List<T> getReferences(Type type) {
		BeanManager beanManager = CDI.current().getBeanManager();

		List<T> references = new ArrayList<T>();
		Set<Bean<?>> beans = beanManager.getBeans(type);
		for (Bean<?> bean : beans) {
			CreationalContext<?> createCreationalContext = beanManager.createCreationalContext(bean);
			Object reference = beanManager.getReference(bean, type, createCreationalContext);
			references.add((T) reference);
		}
		return references;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getRequiredReference(Type type) {
		Object reference = getReference(type);
		if(Objects.isNull(reference)) {
			throw new RuntimeException("CDI Reference not foud for " + type.getTypeName());
		}
		return (T) reference;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getReference(Type type) {
		return (T) CollectionUtil.getFirst(getReferences(type));
	}
	
}
