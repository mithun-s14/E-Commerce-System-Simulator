import java.util.Map;
import java.util.TreeMap;

/**
 * class Product defines a product for sale by the system. 
 * 
 * A product belongs to one of the 5 categories below. 
 * 
 * Some products also have various options (e.g. size, color, format, style, ...). The options can affect
 * the stock count(s). In this generic class Product, product options are not used in get/set/reduce stockCount() methods  
 * 
 * Some products
 */
public class Product
{
	public static enum Category {GENERAL, CLOTHING, BOOKS, FURNITURE, COMPUTERS, SHOES};
	
	private String name;
	private String id;
	private Category category;
	private double price;
	private int stockCount;
	private Map<Integer, Integer> ratings;
	private double averageRating;
	
	/**
	 * Defualt constructor for object Product
	 */
	public Product()
	{
		this.name = "Product";
		this.id = "001";
		this.category = Category.GENERAL;
		this.stockCount = 0;
		this.ratings = new TreeMap<Integer, Integer>();
		this.averageRating = 0.0;
	}
	
	/**
	 * Default constructor for object Product with randomly generated ID
	 * @param id - Uniquely generated number to represent product
	 */
	public Product(String id)
	{
		this("Product", id, 0, 0, Category.GENERAL, 0.0);
	}

	/**
	 * Constructs a Product object
	 * @param name - Product's name
	 * @param id - Uniquely generated ID to represent product
	 * @param price - Product's price
	 * @param stock - Amount of product
	 * @param category - Product's category
	 * @param averageRating - Average rating of the product
	 */
	public Product(String name, String id, double price, int stock, Category category, double averageRating)
	{
		this.name = name;
		this.id = id;
		this.price = price;
		this.stockCount = stock;
		this.category = category;
		this.ratings = new TreeMap<Integer, Integer>();
		for(int i = 1; i <= 5; i++) {
			ratings.put(i, 0);
		}
		this.averageRating = averageRating;
	}

	/**
	 * This method always returns true in class Product. In subclasses, this method will be overridden
	 * and will check to see if the options specified are valid for this product.
	 * @param productOptions - Format of product
	 * @return true
	 */
	public boolean validOptions(String productOptions)
	{
		return true;
	}
	/**
	 * @return ratings - A Map of a ratings, where the key is the rating (e.g. 1,2,3,4,5) and the
	 * value is the amount of times the product has been rated that rating.
	 */
	public Map<Integer, Integer> getRatings() {
		return ratings;
	}
	/**
	 * Increases the rating amount by 1 of given rating
	 * @param rating - A rating between 1 and 5
	 */
	public void setRatings(int rating) {
		ratings.put(rating, ratings.get(rating)+1);
	}

	/**
	 * Calculates the average rating of product
	 * @return averageRating - The average rating of the product.
	 */
	public double getAverageRating() {
		int total = 0;
		int count = 0;
		for (int rate: ratings.keySet()) {
			total += rate * ratings.get(rate);
			count += ratings.get(rate);
		}
		averageRating = (double)total/count;
		return averageRating;
	}

	/**
	 * @param averageRating - The average rating of the product.
	 */
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	
	/**
	 * @return category - The category the product is in (e.g. General, Clothing, Books, Furniture, Computer, Shoes).
	 */
	public Category getCategory()
	{
		return category;
	}
	
	/**
	 * @return name - The name of the product.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - The name of the product.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return id - The product's unique ID.
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id - The product's unique ID.
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return price - The price of the product.
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price - The price of the product.
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}

	/**
	 * Gets the stock count of the product.
	 * Ignores productOptions parameter in the general class, however, it is used in the subclasses.
	 * @param productOptions - A special option for the product.
	 * @return stockCount - Number of items the product has in stock.
	 */
	public int getStockCount(String productOptions)
	{
		return stockCount;
	}

	/**
	 * Sets the stock count of the product.
	 * Ignores productOptions parameter in the general class, however, it is used in the subclasses.
	 * @param stockCount - Number of items the product has in stock.
	 * @param productOptions - A special option for the product.
	 */
	public void setStockCount(int stockCount, String productOptions)
	{
		this.stockCount = stockCount;
	}

	/**
	 * Reduces number of items currently in stock for this product by 1.
	 * Ignores productOptions parameter in the general class, however, it is used in the subclasses.
	 * @param productOptions - A special option for the product.
	 */
	public void reduceStockCount(String productOptions)
	{
		stockCount--;
	}
	
	/**
	 * Prints this product's information, e.g. ID, category, name, and price.
	 */
	public void print()
	{
		System.out.printf("\nId: %-5s Category: %-9s Name: %-20s Price: %7.1f", id, category, name, price);
	}
	
	/**
	 * Two products are equal if they have the same ID. This method is inherited
	 * from superclass Object and overriden here.
	 * @param other - Object representing the other product to be compared with this product.
	 * @return boolean - Returns true if both products have matching IDs, else false.
	 */
	public boolean equals(Object other)
	{
		Product otherP = (Product) other;
		return this.id.equals(otherP.id);
	}
}
