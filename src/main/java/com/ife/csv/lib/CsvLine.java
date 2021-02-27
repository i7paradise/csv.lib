package com.ife.csv.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements ArrayList<String> with safe {@link ArrayList#get(int)}<br>
 * does not throw {@link IndexOutOfBoundsException}
 * 
 * @author iferdou
 *
 */
public class CsvLine extends ArrayList<String> {

	private static final long serialVersionUID = 1L;
	
	public CsvLine(List<String> cells) { super(cells); }

	/**
	 * return null in case of {@link IndexOutOfBoundsException}
	 */
	@Override
	public String get(int index) {
		try {
			return super.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
}