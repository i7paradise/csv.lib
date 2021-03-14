package com.ife.csv.lib;

import java.util.List;
import java.util.function.Function;

public interface CsvWriter<T> {
    
	CsvWriter<T> separator(char theDelimiter);
	
	CsvWriter<T> lineSeparator(String theLineSeparator);
	
	CsvWriter<T> header(String... theHeader);
	
	CsvWriter<T> header(List<String> theHeader);
	
	CsvWriter<T> footer(String... theFooter);
	
	CsvWriter<T> footer(List<String> theFooter);
	
	CsvWriter<T> mapper(Function<T, List<Object>> theMapper);
	
	String generate();
}
