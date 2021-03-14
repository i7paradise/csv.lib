package com.ife.csv.lib;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CsvWriterImpl<T> implements CsvWriter<T> {

	private final Collection<T> content;
	private List<Object> header;
	private List<Object> footer;
	private Function<T, List<Object>> mapper;
	private Character delimiter = CsvUtils.DEFAULT_SEPARATOR;
	private String lineSeparator = CsvUtils.DEFAULT_LINE_SEPARATOR;

	CsvWriterImpl(Collection<T> content) {
		this.content = content;
	}

	@Override
	public CsvWriterImpl<T> separator(char theDelimiter) {
		delimiter = theDelimiter;
		return this;
	}

	@Override
	public CsvWriterImpl<T> lineSeparator(String theLineSeparator) {
		lineSeparator = theLineSeparator;
		return this;
	}

	@Override
	public CsvWriterImpl<T> header(Object... theHeader) {
		return header(Stream.of(theHeader).collect(Collectors.toList()));
	}

	@Override
	public CsvWriterImpl<T> header(List<Object> theHeader) {
		header = theHeader;
		return this;
	}

	@Override
	public CsvWriterImpl<T> footer(Object... theFooter) {
		return footer(Stream.of(theFooter).collect(Collectors.toList()));
	}

	@Override
	public CsvWriterImpl<T> footer(List<Object> theFooter) {
		footer = theFooter;
		return this;
	}

	@Override
	public CsvWriterImpl<T> mapper(Function<T, List<Object>> theMapper) {
		mapper = theMapper;
		return this;
	}

	@Override
	public String generate() {
		if (delimiter == null)
			throw new IllegalArgumentException("delimiter cannot be null");
		if (StringUtils.isEmpty(lineSeparator))
			throw new IllegalArgumentException("lineSeparator cannot be null");
		if (mapper == null)
			throw new IllegalArgumentException("mapper cannot be null");

		return Stream.of(csvLine(header), contentToCsvLines(), csvLine(footer))
				.filter(StringUtils::isNotEmpty)
				.collect(Collectors.joining(lineSeparator));
	}

	private String csvLine(List<?> content) {
		if (CollectionUtils.isEmpty(content)) {
			return CsvUtils.EMPTY;
		}
		return content.stream()
				.map(Object::toString)
				.map(this::csvCell)
				.collect(Collectors.joining(delimiter.toString()));
	}

	private String contentToCsvLines() {
		if (content == null) {
			return CsvUtils.EMPTY;
		}
		return content.stream()
				.map(mapper)
				.map(this::csvLine)
				.collect(Collectors.joining(lineSeparator));
	}

	private String csvCell(String string) {
		if (string == null)
			return CsvUtils.EMPTY;

		string = string.replace(CsvUtils.DOUBLE_QUOTE_STRING,
				CsvUtils.DOUBLE_QUOTE_STRING + CsvUtils.DOUBLE_QUOTE_STRING);

		// check if double_quote is needed
		if (string.contains(CsvUtils.DOUBLE_QUOTE_STRING) || string.contains(delimiter.toString())
				|| string.contains(lineSeparator)) {
			string = CsvUtils.DOUBLE_QUOTE + string + CsvUtils.DOUBLE_QUOTE;
		}
		return string;
	}

}