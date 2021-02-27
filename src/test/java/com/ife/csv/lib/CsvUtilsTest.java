package com.ife.csv.lib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CsvUtilsTest {

	@Test
	public void demoWriter() throws Exception {
		List<Item> items = Arrays.asList(new Item("coffe \"Lavazza\"", 13.99), new Item("tea; so nice", 0),
				new Item("milk" + System.lineSeparator() + "in three" + System.lineSeparator() + "lines", 25.5),
				new Item("\"riz\"", 130.45));

		String csvContent = CsvUtils.writer(Item.class)
				// header if you want to
				.header("Item name", "price")
				// list of content, must be provided of course
				.content(items)
				// mapper that will map the POJO instance to csv strings
				.mapper(e -> Arrays.asList(e.name, String.format("%.2f", e.price)))
				// footer line if you want
				.footer("Total", 158 + "")
				// finally call generate() to give you csv content as a String
				.generate();

		assertTrue(csvContent.contains("Item name"));

	}

	@Test
	public void demoReader() throws Exception {
		File file = Paths.get(ClassLoader.getSystemResource("test1.csv").toURI()).toFile();

		List<Item> items2 = CsvUtils.reader(Item.class)
				// content of file; or you can use content(String) to give the content of csv as
				// a String
				.content(file)
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

		assertNotNull(items2);
		assertEquals(4, items2.size());
		items2.forEach(System.out::println);
		

	}
}

class Item {

	String name;
	double price;

	@Override
	public String toString() {
		return "Item [name=" + name + ", price=" + price + "]";
	}

	public Item(String name, double p) {
		this.name = name;
		this.price = p;
	}

	public static double parsePrice(String s) {
		return s == null ? 0 : Double.parseDouble(s.replace(',', '.'));
	}

}
