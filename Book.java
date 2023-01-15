/**
 * A book is a product that has additional information - e.g. title, author, year
 * A book also comes in different formats - e.g. "Paperback", "Hardcover", "EBook"
 * The format is specified as a specific "stock type" in get/set/reduce stockCount methods.
*/
public class Book extends Product 
{
  private String author;
  private String title;
  private int year;
  
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  int paperbackStock;
  int hardcoverStock;
  public Book(){
    this.author = "";
  }
  
  /**
   * Constructs a Book object , subclass of product
   * @param name - Book
   * @param id - Book's ID number
   * @param price - Cost of book
   * @param paperbackStock - Stock of paperback copies
   * @param hardcoverStock - Stock of hard cover copies
   * @param title - Book's title
   * @param author - Book's author
   * @param year - The year the book was created
   */
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, int year)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 // Set category to BOOKS 
    super(name, id, price, 100000, Product.Category.BOOKS, 0.0);
    this.paperbackStock = paperbackStock;
    this.hardcoverStock = hardcoverStock;
    this.title = title;
    this.author = author;
    this.year = year;
  }
    
  /** 
   * Checks if productOptions is a valid format
   * @param productOptions - Format of book: "Hardcover", "Paperback" or "EBook"
   * @return boolean - Returns true if the book is in a valid format, else it returns false.
   */
  public boolean validOptions(String productOptions)
  {
    if (productOptions.equalsIgnoreCase("Paperback") || productOptions.equalsIgnoreCase("Hardcover") || productOptions.equalsIgnoreCase("Ebook")) {
      return true;
    } else {
      return false;
    }
  }
  /**
   * @return author - Name of author
   */
  public String getAuthor() {
    return this.author;
  }
  /**
   * @return year - Year the book was made
   */
  public int getYear() {
    return this.year;
  }
  
  /** 
   * Override getStockCount() in super class. 
   * @param productOptions - The format of the book
   * @return stockCount - Stock of the given book
   */
  public int getStockCount(String productOptions)
	{
    if (productOptions.equalsIgnoreCase("Hardcover")) {
      return hardcoverStock;
    } else if (productOptions.equalsIgnoreCase("Paperback")) {
      return paperbackStock;
    } else if (productOptions.equalsIgnoreCase("EBook")) {
      return super.getStockCount(productOptions);
    }
  	return 1;
	}
  
  /**
   * Sets the specific stock count of given Book and its format.
   * @param stockCount - Number of copies for given book and format
   * @param productOptions - Format of book
   */
  public void setStockCount(int stockCount, String productOptions)
	{
    // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
    if (productOptions.equalsIgnoreCase("Hardcover")) {
      hardcoverStock = stockCount;
    } else if (productOptions.equalsIgnoreCase("Paperback")) {
      paperbackStock = stockCount;
    } else if (productOptions.equalsIgnoreCase("EBook")) {
      super.setStockCount(stockCount, productOptions);
    }
	}
  
  /** 
   * When a book is ordered, reduces the stock count for the specific stock type 
   * @param productOptions - Format of book: "Hardcover", "Paperback" or "EBook"
  */
  public void reduceStockCount(String productOptions)
	{
    if (productOptions.equalsIgnoreCase("Hardcover")) {
      hardcoverStock -= 1;
    } else if (productOptions.equalsIgnoreCase("Paperback")) {
      paperbackStock -= 1;
    } else if (productOptions.equalsIgnoreCase("EBook")) {
      super.setStockCount(-1, productOptions);
    }
	}
  /**
   * Print product information in super class and appends Book specific information title and author
   */
  public void print()
  {
    super.print();
    System.out.print("     ");
    System.out.printf("Paperback Stock:%-5s Hardcover Stock:%-5s Title:%-5s Author:%-5s Year:%-5s", paperbackStock, hardcoverStock, title, author, year);
  	
  }
}
