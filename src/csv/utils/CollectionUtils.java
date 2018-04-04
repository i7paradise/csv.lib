package csv.utils;

import java.util.Collection;

/**
 * 
 * @author iferdou
 *
 */
public class CollectionUtils {

	/**
	 * check is the collection is <code>null</code> or empty
	 * @param collection
	 * @return <code>true</code> if empty or <code>null</code>, <code>false</code> otherwise
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * check is the collection is neither <code>null</code> or empty
	 * @param collection
	 * @return <code>true</code> if not empty, <code>false</code> otherwise
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
	
}
