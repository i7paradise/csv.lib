package com.ife.csv.lib;

import java.util.Collection;

/**
 * 
 * @author iferdou
 *
 */
class CollectionUtils {

	/**
	 * check is the collection is <code>null</code> or empty
	 * @param collection
	 * @return <code>true</code> if empty or <code>null</code>, <code>false</code> otherwise
	 */
	static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

}
