package com.ife.csv.lib;

class StringUtils {

	static final String EMPTY = "";

	static int count(String s, char c) {
		int count = 0;
		for (char e : s.toCharArray())
			if (e == c)
				count++;
		return count;
	}

	static boolean isEmpty(String s) {
		return s == null || s.isEmpty();
	}

	static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

}
