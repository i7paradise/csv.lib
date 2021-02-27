package com.ife.csv.lib.demo;

class Item {

	private final String name;
	private final double price;

	@Override
	public String toString() {
		return "Item [name=" + name + ", price=" + price + "]";
	}

	Item(String name, double p) {
		this.name = name;
		this.price = p;
	}

	static double parsePrice(String s) {
		return s == null ? 0 : Double.parseDouble(s.replace(',', '.'));
    }
    
    String getName() {
        return name;
    }
    double getPrice() {
        return price;
    }

	public String getPriceAsString() {
		return String.format("%.2f", price);
	}

}