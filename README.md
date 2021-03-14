# CsvUtils-Java8
Simple solution Read/write "csv" based on a POJO class

You can run the Demo.java to see how it works

## Prerequisites
Jdk 8

## Example

Both write/read examples based on the *Item* POJO class

```
public class Item {
  final String name;
  final double price;
  public Item(String name, double p) { this.name = name; this.price = p; }

  public double getPrice() { return price; }
  public String getName() { return name; }
}
```

### Generate csv file from a collection
```
List<Item> items = Arrays.asList(new Item("coffee", 10),
				new Item("tea", 5.5),
				new Item("milk", 8.2),
				new Item("soda", 10));
		
double totalPrice = items.stream()
                         .mapToDouble(Item::getPrice)
                         .sum();
		
String csvContent = CsvUtils.writer(Item.class)
                            .content(items)
                            .mapper(e -> Arrays.asList(
                                e.getName(),
                                e.getPrice()
                                ))
                            .header("item name", "price")
                            .footer("total", totalPrice+"")
                            .generate();
		
System.out.println(csvContent);
```

result : 
```
item name;price
coffee;10.0
tea;5.5
milk;8.2
soda;10.0
total;33.7
```
 
### Parse csv file into a collection

Will just reverse the previous example
We have a file at "/tmp/sample.csv" that contains:
```
item name;price
coffee;10.0
tea;5.5
milk;8.2
soda;10.0
total;33.7
```
To read sample.csv file into a collection of Item

```
List<Item> items = CsvUtils.reader(Item.class)
                          .content(new File("/tmp/sample.csv"))
                          .includeFirstLine(false) // to not parse the header
                          .includeLastLine(false) // to not parse the footer
                           // map the line into an Item object
                          .mapper(line -> new Item(line.get(0), 
                              Double.parseDouble(line.get(1)))
                              )
                          .read();

Consumer<Item> printer = e -> System.out.println("Item " + e.getName() + " " + e.getPrice());

items.forEach(printer);
```

results in console : 
```
Item coffee 10.0
Item tea 5.5
Item milk 8.2
Item soda 10.0
```
