package com.ife.csv.lib.demo;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ife.csv.lib.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CsvUtilsTest {

	@Test
	public void demoWriter() {
		List<Item> items = Arrays.asList(new Item("coffee \"Lavazza\"", 13),
				new Item("tea; so nice", 10),
				new Item("milk" + System.lineSeparator() + "in three" + System.lineSeparator() + "lines", 25),
				new Item("\"riz\"", 130));
		double total = items.stream()
				.mapToDouble(Item::getPrice)
				.sum();

		String csvContent = CsvUtils.write(items)
				// header if you want to
				.header("Item name", "price")
				// mapper that will map the POJO instance to csv strings
				.mapper(e -> Arrays.asList(e.getName(), e.getPrice()))
				// footer line if you want
				.footer("Total", total)
				// finally call generate() to give you csv content as a String
				.generate();

		System.out.println(csvContent);
		assertThat(csvContent, containsString("Item name"));
		assertThat(csvContent, containsString("coffee \"\"Lavazza\"\""));
		assertThat(csvContent, containsString("\"\"riz\"\""));
	}

	@Test
	public void demoReader() throws Exception {
		File file = Paths.get(ClassLoader.getSystemResource("test1.csv").toURI()).toFile();

		List<Item> items = CsvUtils.read(Item.class)
				// content of file; or you can use content(String) to give the content of csv as
				// a String
				.content(file)
				.separator(';')
				// false to not include the first line; because we don't want to parse the
				// header
				.includeFirstLine(false)
				// false to not include the last line; because we don't want to parse the footer
				.includeLastLine(false)
				// mapper to create the Item instance from the given line, line is
				// ArrayList<String> that returns null if index not found
				.mapper(line -> new Item(line.get(0), Item.parsePrice(line.get(1))))
				// if you want filter the lines before start parsing, so only the lines that
				// passes the filter will be parsed
				// .csvLineFilter(line -> line.startsWith("riz"))
				// finally we call read() to parse the file (or the content)
				.read();

		assertNotNull(items);
		assertEquals(4, items.size());
		items.forEach(System.out::println);
		assertTrue(existItem(items, "coffee \"Lavazza\"", 13.99));
		assertTrue(existItem(items, "tea;;;;; so;;; nice;;", 0));
		assertTrue(existItem(items, "in three", 25.50));
		assertTrue(existItem(items, "riz", 130.45));
	}

	static boolean existItem(List<Item> items, String name, double price) {
		return items.stream()
				.anyMatch(e -> e.getName().contains(name) && e.getPrice() == price);
	}
}
