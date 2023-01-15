/**
 * A shoe is a product that has additional information - e.g. size, colour, stock count
 * A shoe also comes in different formats per size and colour (6Black, 10Brown)
 * The format is specified as a specific "stock type" in get/set/reduce stockCount methods.
 */
public class Shoes extends Product{
  private int size;
  private String colour;
  private String productOptions;
  private int black6stock;    // Variables representing stock for each size/colour combination
  private int black7stock;
  private int black8stock;
  private int black9stock;
  private int black10stock;
  private int brown6stock;
  private int brown7stock;
  private int brown8stock;
  private int brown9stock;
  private int brown10stock;

  /**
   * Constructs a Shoe object , subclass of product
   * @param name - Shoe's name
   * @param id - Uniquely generated ID for shoe
   * @param price - Shoe's price
   * @param stock - Amount of shoes
   * @param productOptions - Size and colour of shoes
   */
  public Shoes(String name, String id, double price, int stock, String productOptions) {
      super(name, id, price, 0, Product.Category.SHOES, 0.0);
      this.productOptions = productOptions;
      setSize(productOptions);    // Sets size based on productOptions
      setColour(productOptions);  // Sets colour based on productOptions
      setStockCount(stock, size, colour); // Sets stock based on size and colour
  }

  /**
   * Sets the size of the shoe by getting the appropriate substring of the productOptions string.
   * @param productOptions - The size and colour of the shoe concatenated into one string.
   */
  public void setSize(String productOptions) {
    if(productOptions.charAt(0) == '1') {
      this.size = Integer.valueOf(productOptions.substring(0,2));    // Ensures size 10 is accounted for
    } else {
      this.size = Integer.valueOf(productOptions.substring(0,1));
    }
  }

  /**
   * Sets the colour of the shoes by getting the appropriate substring of the productOptions string.
   * @param productOptions - The size and colour of the shoe concatenated into one string.
   */
  public void setColour(String productOptions) {
    if (productOptions.charAt(0) == '1') {                       // Ensures size 10 is accounted for
      this.colour = productOptions.substring(2);
    } else {
      this.colour = productOptions.substring(1);
    }
  }

  /**
   * Checks if productOptions is correctly formatted (e.g. 6Black, 10Brown)
   * @param productOptions - The size and colour of the shoe concatenated into one string.
   * @return boolean - True if productOptions is correctly formatted, else false.
   */
  public boolean validOptions(String productOptions)
  {
    for (int i = 6; i <= 10; i ++) { // Loops through all possible sizes
        if (productOptions.equals(i + "Black") || productOptions.equals(i + "Brown")) {     //Checks in the formatting of productOptions
            return true;
        }
    }
    return false;
  }

  /**
   * Gets the stock count of this shoe based on the size and colour.
   * @param productOptions - The size and colour of the shoe
   * @return stockCount - Returns the corresponding variable to the productOption string given.
   */
  public int getStockCount(String productOptions) {
    // Seperates productOptions to variables size and colour
    // Checks for each possible combination to get the
    // stock of the specified shoe
    setSize(productOptions);
    setColour(productOptions);
    if(size == 6) {
      if (colour.equals("Black")) {
        return black6stock;
      }else if (colour.equals("Brown")) {
        return brown6stock;
      }
    }else if(size == 7) {
      if (colour.equals("Black")) {
        return black7stock;
      }else if (colour.equals("Brown")) {
        return brown7stock;
      }
    }else if(size == 8) {
      if (colour.equals("Black")) {
        return black8stock;
      }else if (colour.equals("Brown")) {
        return brown8stock;
      }
    }else if(size == 9) {
      if (colour.equals("Black")) {
        return black9stock;
      }else if (colour.equals("Brown")) {
        return brown9stock;
      }
    }else if(size == 10) {
      if (colour.equals("Black")) {
        return black10stock;
      }else if (colour.equals("Brown")) {
        return brown10stock;
      }
    }
    return 0;
  }
  
  /**
   * Sets the stock of a specified shoe
   * @param stock - Amount of shoe
   * @param size - Size of shoe
   * @param colour - Colour of shoe
   */
  public void setStockCount(int stock, int size, String colour) {
    // Uses size and colour to determine which type of shoe stock
    // to assign the stock to
    if(size == 6) {
      if (colour.equals("Black")) {
        black6stock = stock;
      }else if (colour.equals("Brown")) {
        brown6stock = stock;
      }
    }else if(size == 7) {
      if (colour.equals("Black")) {
        black7stock = stock;
      }else if (colour.equals("Brown")) {
        brown7stock = stock;
      }
    }else if(size == 8) {
      if (colour.equals("Black")) {
        black8stock = stock;
      }else if (colour.equals("Brown")) {
        brown8stock = stock;
      }
    }else if(size == 9) {
      if (colour.equals("Black")) {
        black9stock = stock;
      }else if (colour.equals("Brown")) {
        brown9stock = stock;
      }
    }else if(size == 10) {
      if (colour.equals("Black")) {
        black10stock = stock;
      }else if (colour.equals("Brown")) {
        brown10stock = stock;
      }
    }
  }

  /**
   * When a shoe is ordered, reduce the shoe count for the specific shoe type
   * @param productOptions - Size and colour of shoe
   */
  public void reduceStockCount(String productOptions) {
    setSize(productOptions);    // Sets size based on given productOptions
    setColour(productOptions);  // Sets colour based on given productOptions
    if(size == 6) {
      if (colour.equals("Black")) {
        black6stock -= 1;
      }else if (colour.equals("Brown")) {
        brown6stock -= 1;
      }
    }else if(size == 7) {
      if (colour.equals("Black")) {
        black7stock -= 1;
      }else if (colour.equals("Brown")) {
        brown7stock -= 1;
      }
    }else if(size == 8) {
      if (colour.equals("Black")) {
        black8stock -= 1;
      }else if (colour.equals("Brown")) {
        brown8stock -= 1;
      }
    }else if(size == 9) {
      if (colour.equals("Black")) {
        black9stock -= 1;
      }else if (colour.equals("Brown")) {
        brown9stock -= 1;
      }
    }else if(size == 10) {
      if (colour.equals("Black")) {
        black10stock -= 1;
      }else if (colour.equals("Brown")) {
        brown10stock -= 1;
      }
    }
  }

  /**
   * Print product information in super class and append Book specific information: size, colour, and stock
   */
  public void print() {
    super.print();
    System.out.print("     ");
    System.out.printf("Size:%-5s Colour:%-5s Stock:%-5s", size, colour, getStockCount(productOptions));
  }
}
