package com.ife.csv.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface CsvReader<T> {

    CsvReader<T> separator(char theSeparator);

    CsvReader<T> lineSeparator(String theLineSeparator);

    CsvReader<T> includeFirstLine(boolean includeFirstLine);

    CsvReader<T> includeLastLine(boolean includeLastLine);

    CsvReader<T> content(String theContent);

    CsvReader<T> content(File file) throws FileNotFoundException, IOException;

    CsvReader<T> content(Path path) throws IOException;

    CsvReader<T> mapper(Function<CsvLine, T> theMapper);

    CsvReader<T> csvLineFilter(Predicate<String> filter);

    List<T> read();
}
