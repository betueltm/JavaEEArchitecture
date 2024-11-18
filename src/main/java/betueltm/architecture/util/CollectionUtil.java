package betueltm.architecture.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtil {

	public static <T> Collection<T> getNotNull(Collection<T> collection) {
		return Objects.isNull(collection) ? Collections.emptyList() : collection;
	}

	public static <T> Collection<T> getNotNull(T[] objectArray) {
		return Objects.isNull(objectArray) ? Collections.emptyList() : Arrays.asList(objectArray);
	}

	public static <T> List<T> getNotNullToList(T[] objectArray) {
		return new ArrayList<T>(getNotNull(objectArray));
	}
	
	public static boolean isNotNullOrEmpty(Collection<?> collection) {
		return !isNullOrEmpty(collection);
	}
	
	public static boolean isNullOrEmpty(Collection<?> collection) {
		return Objects.isNull(collection) || collection.isEmpty();
	}

	public static <T> List<T> filter(Collection<T> collection, Predicate<? super T> predicate) {
		return getNotNull(collection).stream().filter(predicate).collect(Collectors.toList());
	}

	public static <T> Optional<T> findFirstWithFilter(T[] array, Predicate<? super T> predicate) {
		return getNotNull(array).stream().filter(predicate).findFirst();
	}

	public static <T> long getSizeWithFilter(Collection<T> collection, Predicate<? super T> predicate) {
		return getNotNull(collection).stream().filter(predicate).count();
	}

	public static int getSize(Collection<?> collection) {
		return getNotNull(collection).size();
	}

	public static <T, R> List<R> getMappedList(Collection<T> collection, Function<? super T, ? extends R> mapper) {
		return getNotNull(collection).stream().map(mapper).collect(Collectors.toList());
	}

	public static boolean nonNullOrEmpty(Collection<?> collection) {
		return !isNullOrEmpty(collection);
	}

	public static <T> List<T> filterCollection(Collection<T> collection, Predicate<? super T> predicate) {
		return getNotNull(collection).stream().filter(predicate).collect(Collectors.toList());
	}

	public static <T> Optional<T> findFirstWithFilter(Collection<T> collection,
			Predicate<? super T> predicate) {
		return getNotNull(collection).stream().filter(predicate).findFirst();
	}

	public static <T, R> List<R> getMappedList(T[] objectArray, Function<? super T, ? extends R> mapper) {
		return getNotNull(objectArray).stream().map(mapper).collect(Collectors.toList());
	}

	public static <T> BigDecimal reduceToSumBigDecimal(Collection<T> collection, Function<? super T, BigDecimal> mapper) {
		return getNotNull(collection).stream().map(mapper).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public static <T, K> Map<K, List<T>> groupingBy(Collection<T> collection, Function<? super T, ? extends K> classifier) {
		return getNotNull(collection).parallelStream().collect(Collectors.groupingBy(classifier));
	}

	public static <T> boolean containsInCollection(Collection<T> collection, T objectToFind) {
		return getNotNull(collection).stream().filter(object -> (object.equals(objectToFind))).findFirst().isPresent();
	}

	public static <T> boolean containsInCollection(Collection<T> collection, Collection<T> otherCollection) {

		Collection<T> collectionNonNull = getNotNull(collection);
		Collection<T> otherCollectionNonNull = getNotNull(otherCollection);

		for (T object : otherCollectionNonNull) {
			boolean containsInCollection = Collections.frequency(collectionNonNull, object) >= 1;
			if (containsInCollection) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean haveMoreThanOne(Collection<T> collection) {
		return getSize(collection) > 1;
	}

	public static <T> void preserveDistinct(Collection<T> listObjects) {
		Set<T> setObjects = new HashSet<>();
		setObjects.addAll(listObjects);
		listObjects.clear();
		listObjects.addAll(setObjects);
	}

	public static <T> T getFirst(T[] objectArray) {
		if (Objects.isNull(objectArray) || objectArray.length == 0)
			return null;
		return objectArray[0];
	}

	public static <T> T getFirst(Collection<T> collection) {
		Iterator<T> iterator = getNotNull(collection).iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		return null;
	}

	public static <T> void removeDuplicateObjects(List<T> listObjects) {
		Set<T> setObjects = new HashSet<>();
		setObjects.addAll(listObjects);
		listObjects.clear();
		listObjects.addAll(setObjects);
	}

	public static String collectionToStringWithComma(Collection<String> collection) {
		StringBuilder stringBuilder = new StringBuilder();
		String comma = ",";
		Iterator<String> iterator = getNotNull(collection).iterator();

		while (iterator.hasNext()) {
			String value = iterator.next();
			stringBuilder.append(value);
			boolean hasNext = iterator.hasNext();
			if (hasNext) {
				stringBuilder.append(comma);
			}
		}
		return stringBuilder.toString();
	}
	
	public static String collectionToQuotedStringWithComma(Collection<String> collection) {
        Iterator<String> iterator = collection.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        String comma = ",";
        String quote = "'";

        while (iterator.hasNext()) {
        	String value = iterator.next();
        	boolean hasNext = iterator.hasNext();
        	stringBuilder.append(quote).append(value).append(quote);
        	if(hasNext) stringBuilder.append(comma);
		}
                
        return stringBuilder.toString();
    }

	public static String collectionLongToStringWithComma(Collection<Long> collection) {
		StringBuilder stringBuilder = new StringBuilder();
		String comma = ",";
		Iterator<Long> iterator = getNotNull(collection).iterator();

		while (iterator.hasNext()) {
			Long value = iterator.next();
			stringBuilder.append(value);
			boolean hasNext = iterator.hasNext();
			if (hasNext) {
				stringBuilder.append(comma);
			}
		}
		return stringBuilder.toString();
	}

	public static Object[] addToArray(Object[] array, Object object) {
		ArrayList<Object> newArray = new ArrayList<Object>(Arrays.asList(array));
		newArray.add(object);
		return newArray.toArray();
	}

	public static <T> ArrayList<T> removeIfNull(Collection<T> collection) {
		ArrayList<T> newCollection = new ArrayList<T>();
		for (T object : collection) {
			if (Objects.isNull(object))
				continue;
			newCollection.add(object);
		}
		return newCollection;
	}

	public static <T> Collection<T> copyToNewArrayCollection(Collection<T> collection) {
		Collection<T> newCollection = new ArrayList<T>();
		newCollection.addAll(getNotNull(collection));
		return newCollection;
	}

}
