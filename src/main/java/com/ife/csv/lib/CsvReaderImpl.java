package com.ife.csv.lib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CsvReaderImpl<T> implements CsvReader<T> {

    private String content;
    private boolean includeFirstLine = true;
    private boolean includeLastLine = true;
    private Function<CsvLine, T> mapper;
    private Character separator = CsvUtils.DEFAULT_SEPARATOR;
    private String lineSeparator = CsvUtils.DEFAULT_LINE_SEPARATOR;
    private Predicate<String> csvLineFilter = e -> true;

    @Override
    public CsvReader<T> separator(char theSeparator) {
        separator = theSeparator;
        return this;
    }

    @Override
    public CsvReader<T> lineSeparator(String theLineSeparator) {
        lineSeparator = theLineSeparator;
        return this;
    }

    @Override
    public CsvReader<T> includeFirstLine(boolean includeFirstLine) {
        this.includeFirstLine = includeFirstLine;
        return this;
    }

    @Override
    public CsvReader<T> includeLastLine(boolean includeLastLine) {
        this.includeLastLine = includeLastLine;
        return this;
    }

    @Override
    public CsvReader<T> content(String theContent) {
        this.content = theContent;
        return this;
    }

    @Override
    public CsvReader<T> content(File file) throws IOException {
        return content(file.toPath());
    }

    @Override
    public CsvReader<T> content(Path path) throws IOException {
        return content(new String(Files.readAllBytes(path)));
    }

    @Override
    public CsvReader<T> mapper(Function<CsvLine, T> theMapper) {
        mapper = theMapper;
        return this;
    }

    @Override
    public CsvReader<T> csvLineFilter(Predicate<String> filter) {
        if (filter != null)
            csvLineFilter = filter;
        return this;
    }

    @Override
    public List<T> read() {
        if (StringUtils.isEmpty(lineSeparator))
            throw new IllegalArgumentException("line separator cannot be empty, must provide a valid line separator");

        if (mapper == null)
            throw new IllegalArgumentException("line reader cannot be null");

        return splitLines().stream()
                .map(this::splitCells)
                .map(CsvLine::new)
                .map(mapper)
                .collect(Collectors.toList());
    }

    private List<String> splitLines() {
        List<String> lines = Stream.of(content.split(lineSeparator)).collect(Collectors.toList());

        boolean flagReDo;

        do {
            flagReDo = false;
            for (ListIterator<String> i = lines.listIterator(); i.hasNext(); ) {
                String line = i.next();
                if (StringUtils.count(line, CsvUtils.DOUBLE_QUOTE) % 2 == 1) {
                    i.remove();
                    String nextLine = i.next();
                    i.set(line + lineSeparator + nextLine);
                    flagReDo = true;
                    // break for loop, redo the checking from the beginning
                    break;
                }
            }
        } while (flagReDo);

        if (!includeFirstLine) {
            lines.remove(0);
        }
        if (!includeLastLine) {
            lines.remove(lines.size() - 1);
        }

        return lines.stream().filter(csvLineFilter).collect(Collectors.toList());
    }

    private List<String> splitCells(String line) {
        List<String> cells = Stream.of(line.split(separator.toString())).collect(Collectors.toList());

        boolean flagReDo;
        do {
            flagReDo = false;
            for (ListIterator<String> i = cells.listIterator(); i.hasNext(); ) {
                String cell = i.next();
                if (StringUtils.count(cell, CsvUtils.DOUBLE_QUOTE) % 2 == 1) {
                    i.remove();
                    String nextCell = i.next();
                    i.set(cell + separator + nextCell);
                    flagReDo = true;
                    break;
                }
            }
        } while (flagReDo);

        return cells.stream()
                .map(e -> isInQuote(e) ? removeQuotes(e) : e)
                .map(CsvReaderImpl::removeSkippingQuotes)
                .collect(Collectors.toList());
    }

    private static String removeSkippingQuotes(String s) {
        return s.replace(CsvUtils.DOUBLE_QUOTE_STRING + CsvUtils.DOUBLE_QUOTE_STRING, CsvUtils.DOUBLE_QUOTE_STRING);
    }

    private static String removeQuotes(String s) {
        return s.substring(1, s.length() - 1);
    }

    private static boolean isInQuote(String string) {
        return string != null && string.startsWith(CsvUtils.DOUBLE_QUOTE_STRING)
                && string.endsWith(CsvUtils.DOUBLE_QUOTE_STRING);
    }

}