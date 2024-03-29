package com.ife.csv.lib;

import java.util.List;
import java.util.function.Function;

public interface CsvWriter<T> {
    
	CsvWriter<T> separator(char theDelimiter);
	
	CsvWriter<T> lineSeparator(String theLineSeparator);
	
	CsvWriter<T> header(Object... theHeader);
	
	CsvWriter<T> header(List<Object> theHeader);
	
	CsvWriter<T> footer(Object... theFooter);
	
	CsvWriter<T> footer(List<Object> theFooter);
	
	CsvWriter<T> mapper(Function<T, List<Object>> theMapper);
	
	String generate();
}
