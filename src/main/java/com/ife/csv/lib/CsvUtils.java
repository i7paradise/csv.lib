package com.ife.csv.lib;

import java.util.Collection;

public class CsvUtils {

	static final String EMPTY = StringUtils.EMPTY;
	static final char DOUBLE_QUOTE = '"';
	static final String DOUBLE_QUOTE_STRING = DOUBLE_QUOTE + EMPTY;
	static final char DEFAULT_DELIMITER = ',';
	static final String DEFAULT_LINE_SEPARATOR = System.lineSeparator();

	public static <T> CsvWriter<T> write(Collection<T> items) {
		return new CsvWriterImpl<T>(items);
	}
	
	public static <T> CsvReader<T> read(Class<T> clazz) {
		return new CsvReaderImpl<T>();
	}
	
}
