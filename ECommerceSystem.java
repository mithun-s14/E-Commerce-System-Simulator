import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Models a simple ECommerce system. Keeps track of products for sale,
 * registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem {
  private Map<String, Product> prods = new TreeMap<String, Product>();
  private Map<String, Integer> orderStats = new TreeMap<String, Integer>();
  private ArrayList<Product> products = new ArrayList<Product>();
  private ArrayList<Customer> customers = new ArrayList<Customer>();

  private ArrayList<ProductOrder> orders = new ArrayList<ProductOrder>();
  private ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();

  // These variables are used to generate order numbers, customer id's, product
  // id's
  private int orderNumber = 500;
  private int customerId = 900;
  private int productId = 700;

  // General variable used to store an error message when something is invalid
  // (e.g. customer id does not exist)
  String errMsg = null;

  // Random number generator
  Random random = new Random();

  public ECommerceSystem() {

    // Create some products. Notice how generateProductId() method is used
    addProds();

    // Create some customers. Notice how generateCustomerId() method is used
    customers.add(new Customer(generateCustomerId(), "Inigo Montoya", "1 SwordMaker Lane, Florin"));
    customers.add(new Customer(generateCustomerId(), "Prince Humperdinck", "The Castle, Florin"));
    customers.add(new Customer(generateCustomerId(), "Andy Dufresne", "Shawshank Prison, Maine"));
    customers.add(new Customer(generateCustomerId(), "Ferris Bueller", "4160 Country Club Drive, Long Beach"));

    
  }
  // Adds all products in products.txt to a map whichs maps the productId, to a product.
  private void addProds() {
    File input = new File("products.txt");
    try {
      Scanner in = new Scanner(input);
      while (in.hasNextLine()) {
        String temp = in.next().trim();
        if (temp.equals("BOOKS")) {
          String name = in.next();
          double price = in.nextDouble();
          int paperbackStock = in.nextInt();
          int hardcoverStock = in.nextInt();
          in.nextLine(); // bypasses rest of line
          String line = in.nextLine(); // Recieves title, author, year
          String[] titleAuthorYear = line.split(":"); // Uses : to split the title, author, year into seperate index
          String title = titleAuthorYear[0];
          String author = titleAuthorYear[1];
          int year = Integer.valueOf(titleAuthorYear[2]);
          String prodId = generateProductId();
          prods.put(prodId, new Book(name, prodId, price, paperbackStock, hardcoverStock, title, author, year));
        } else {
          in.nextLine(); // bypass rest of line
          String name = in.nextLine().trim();
          double price = in.nextDouble();
          int stock = in.nextInt();
          
          // Determines the category of the product
          if (temp.equals("COMPUTERS")) {
            String prodId = generateProductId();
            prods.put(prodId, new Product(name, prodId, price, stock, Product.Category.COMPUTERS, 0.0));
          } else if (temp.equals("FURNITURE")) {
            String prodId = generateProductId();
            prods.put(prodId, new Product(name, prodId, price, stock, Product.Category.FURNITURE, 0.0));
          } else if (temp.equals("CLOTHING")) {
            String prodId = generateProductId();
            prods.put(prodId, new Product(name, prodId, price, stock, Product.Category.CLOTHING, 0.0));
          } else if (temp.equals("GENERAL")) {
            String prodId = generateProductId();
            prods.put(prodId, new Product(name, prodId, price, stock, Product.Category.GENERAL, 0.0));
          }

        }
      }
      in.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  /**
   * Generates an order number
   * @return orderNumber - Order number of product ordered
   */
  private String generateOrderNumber() {
    return "" + orderNumber++;
  }

  /**
   * Generates a customer ID for a newly registered customer
   * @return customerId - ID number to represent customer
   */
  private String generateCustomerId() {
    return "" + customerId++;
  }

  /**
   * Generates a product ID for a product
   * @return productId - ID number to represent a product
   */
  private String generateProductId() {
    return "" + productId++;
  }

  /**
   * @return errMsg - An string that lets user know what 
   * they have done incorrectly
   */
  public String getErrorMessage() {
    return errMsg;
  }

  /**
   * Prints all products
   */
  public void printAllProducts() {
    for (String id: prods.keySet()) {
      prods.get(id).print();
    }
  }

  /**
   * Print all products that are books. See getCategory() method in class Product
   */
  public void printAllBooks() {
    for (String id: prods.keySet())
      if (prods.get(id).getCategory() == Product.Category.BOOKS) { // Determines if product is a book
        prods.get(id).print();
      }
  }

  /**
   * Prints all orders
   */
  public void printAllOrders() {
    for (ProductOrder o : orders) {
      o.print();
    }
  }

  /**
   * Print all shipped orders
   */
  public void printAllShippedOrders() {
    for (ProductOrder o : shippedOrders) {
      o.print();
    }
  }

  /**
   * Print all customers
   */
  public void printCustomers() {
    for (int i = 0; i < customers.size(); i++) {
      customers.get(i).print();
    }
  }

  /**
   * Given a customer id, print all the current orders and shipped orders for them
   * (if any)
   */
  public void printOrderHistory(String customerId) {
    boolean found = false;
    for (Customer c : customers) {
      if (c.getId().equals(customerId)) {   // Checks if customer exists
        found = true;
      }
    }
    if (!found) {   // If customerId doesn't exist, throws custom UnknownCustomerException.
      throw new UnknownCustomerException("Customer " + customerId + " Not Found");
    }

    // Print current orders of this customer
    System.out.println("Current Orders of Customer " + customerId);
    for (ProductOrder o : orders) {
      if (o.getCustomer().getId().equals(customerId)) {   // Checks all orderId's that correlate to the customerId given.
        o.print();
      }
    }

    // Print shipped orders of this customer
    System.out.println("\nShipped Orders of Customer " + customerId);
    for (ProductOrder o : shippedOrders) {
      if (o.getCustomer().getId().equals(customerId)) { // Checks all shippedOrders orderId's that correlate to the customerId given.
        o.print();
      }
    }
  }

  /**
   * 
   * @param productId - ID of product being ordered
   * @param customerId - ID of ordering customer
   * @param productOptions - Format of product being ordered
   * @return orderNum - Generated number that identifies an order placed by a customer
   */
  public String orderProduct(String productId, String customerId, String productOptions) {
    String orderNum = "";
    boolean custIdExist = false;
    Customer cust = new Customer("0");
    for (Customer c : customers) {
      if (c.getId().equals(customerId)) { // Checks if the customerId given exists
        custIdExist = true;
        cust = c;
      }
    }
    if (!custIdExist) {
      throw new UnknownCustomerException("Customer " + customerId + " Not Found");
    }

    boolean prodIdExist = false;
    Product product = new Product();
    for (String id: prods.keySet()) {
      if (id.equals(productId)) { // Checks if the given productId exists
        prodIdExist = true;
        product = prods.get(id);
      }
    }
    if (!prodIdExist) { // Throws new UnknownProductException if prodId does not exist.
      throw new UnknownProductException("Product " + productId + " Not Found");
    }
    
    // Check if customer used ORDER keyword to order book or shoe instead of their
    // specified keyword
    if (productOptions != (null)) {
      for (String id: prods.keySet()) {
        if (id.equals(productId)) {
          if (!prods.get(id).getCategory().equals(Product.Category.BOOKS) && !prods.get(id).getCategory().equals(Product.Category.SHOES)) {
            throw new IncorrectOrderingException("Ensure correct ordering function is being used.");
          }
        }
      }
    }
    if(productOptions == null && product.getCategory().equals(Product.Category.BOOKS) || product.getCategory().equals(Product.Category.SHOES)) {
      throw new IncorrectOrderingException("Ensure correct ordering function is being used.");
    }

    if (product.getCategory().equals(Product.Category.BOOKS)) {
      if (product.validOptions(productOptions)) {
        if (!product.getCategory().equals(Product.Category.BOOKS)) {
          throw new InvalidProductOptionException("ProductId " + productId + " is not a book");
        }
      } else {
        throw new InvalidProductOptionException("Product Book ProductId " + productId + " Invalid Options: " + productOptions);
      }

    // Checks if the size and colour are valid
    } else if (product.getCategory().equals(Product.Category.SHOES)) {
      if (product.validOptions(productOptions)) {
        if (!product.getCategory().equals(Product.Category.SHOES)) {
          throw new InvalidProductOptionException("ProductId " + productId + " is not a shoe");
        }
      }else {
        throw new InvalidProductOptionException("Product Shoe ProductId " + productId + " Invalid Options: " + productOptions);
      }
    }

    // Check if the product has stock available (i.e. not 0)
    if (product.getStockCount(productOptions) <= 0) {
      throw new NoStockException("No Stock");
    }

    // Create a ProductOrder and adds to orders
    // list and return order number string
    product.reduceStockCount(productOptions);
    ProductOrder pO = new ProductOrder(generateOrderNumber(), product, cust, productOptions);
    orders.add(pO);
    orderNum = pO.getOrderNumber();
    addOrders(productId);   // Keeping track of the product ordered
    return orderNum;
  }

  /**
   * Create a new Customer object and add it to the list of customers
   * @param name - The new customer's name
   * @param address - The new customer's address
   */
  public void createCustomer(String name, String address) {
    // Check name and address parameter to make sure it is not null or ""
    if (name == null || name == "") {
      throw new InvalidNameException("Invalid Customer Name");
    } else if (address == null || address == "") {
      throw new InvalidAddressException("Invalid Customer Address");
    }
    // Create a Customer object and add to array list
    customers.add(new Customer(generateCustomerId(), name, address));
  }

  /**
   * Ships order from the ProductOrder's list
   * @param orderNumber - ID of order to be shipped
   * @return ord - Reference to the order
   */
  public ProductOrder shipOrder(String orderNumber) {
    ProductOrder ord = new ProductOrder("0", new Product(), new Customer("0"), "null");
    boolean existingOrder = false;
    for (ProductOrder o : orders) {
      if (o.getOrderNumber().equals(orderNumber)) { // Checks if the order number exists
        existingOrder = true;
        ord = o;
      }
    }
    if (!existingOrder) {
      throw new InvalidOrderException("Order " + orderNumber + " Not Found");
    }
    if (orders.contains(ord)) { // Removes order from orders list
      orders.remove(ord);
    }

    shippedOrders.add(ord); // Adds order to shippedOrders list 
    return ord;
  }

  /**
   * Cancel a specific order based on order number
   * @param orderNumber - ID of order to be cancelled
   */
  public void cancelOrder(String orderNumber) {
    boolean existingOrder = false;
    ProductOrder ord = new ProductOrder("0", new Product(), new Customer("0"), "null");
    for (ProductOrder o : orders) {
      if (o.getOrderNumber().equals(orderNumber)) { // Checks if order number exists
        existingOrder = true;
        ord = o;
      }
    }
    if (!existingOrder) {
      throw new InvalidOrderException("Order " + orderNumber + " Not Found"); // Sets errMsg and returns false, if order number does not exist
    } else {
      orders.remove(ord); // Removes order from orders list
    }

  }

  /**
   * Checks if the product category is a book using productId
   * @param productId - ID number representing product
   * @return true/false - Returns true if product is a book, else returns false
   */
  public boolean isBook(String productId) 
  {
    Product product = prods.get(productId);
    if(product == null) {
      return false;
    } else if (product.getCategory().equals(Product.Category.BOOKS)){
      return true;
    }
  
  return false;
}
  
  /**
   * Checks if the product category is shoe using productId
   * @param productId - ID number representing product
   * @return true/false - Returns true if product is a shoe, else returns false
   */
  public boolean isShoe(String productId)
  {
      Product product = prods.get(productId);
      if(product == null) {
        return false;
      } else if (product.getCategory().equals(Product.Category.SHOES)){
        return true;
      }
    
    return false;
  }
  
  /**
   * Finds the customer using the given customerId
   * @param customerId - ID number representing customer to be found
   * @return cust - Customer object, if customer is found
   */
  public Customer findCustomer(String customerId) {
    Customer cust = null;
    boolean custIdExist = false;
    
    for (Customer c : customers) {
      if (c.getId().equals(customerId)) { // Checks if the customerId given exists
        custIdExist = true;
        cust = c;
      }
    }
    if (!custIdExist) {
      return null;
    }
    return cust;
  }

  /**
   * Adds specified product to customer's cart
   * @param productId - ID representing the product to be added to the cart
   * @param customerId - ID representing the customer whose cart the product will be added to
   * @param productOptions - Format of product to be added
   * @return msg - To let customer know that the product has been added to the cart
   */
  public String addToCart(String productId, String customerId, String productOptions) 
  {
    Customer c = findCustomer(customerId);
    Product product = prods.get(productId);
    if(c == null || c.getName().isEmpty()) {  // Throws UnknownCustomerException if the customerId given does not exist.
      throw new UnknownCustomerException("Customer " + customerId + " Not Found");
    } else if (product == null) {   // Throws UnknownProductException if the productId given does not exist.
      throw new UnknownProductException("Product " + productId + " Not Found");
    } 
    Cart cart = c.getCart();
    if(product.validOptions(productOptions)) {
      CartItem item = new CartItem(productOptions, product);  // Converts the product to type CartItem
      cart.getCartList().add(item);
      return "Product " + productId + " has been added to Customer's " + customerId + " Cart.";
    } else {
      throw new InvalidProductOptionException("Product Book ProductId " + productId + " Invalid Options: " + productOptions);
    }
    
  }

  /**
   * Removes specified product to customer's cart
   * @param productId - ID of product to be removed
   * @param customerId - ID of customer whose cart the product will be removed from
   * @return msg - To let customer know that the product has been removed from the cart
   */
  public String removeFromCart(String productId, String customerId) 
  {
      Customer c = findCustomer(customerId);
      Product product = prods.get(productId);
      if(c == null || c.getName().isEmpty()) {  // Throws UnknownCustomerException if the customerId given does not exist.
        throw new UnknownCustomerException("Customer " + customerId + " Not Found");
      } else if (product == null) {  // Throws UnknownProductException if the productId given does not exist.
        throw new UnknownProductException("Product " + productId + " Not Found");
      } 
      Cart cart = c.getCart();    // Gets the customer's cart
      boolean found = false;
      for(CartItem cItem: cart.getCartList()) {     // Finds the cart item assoicated with the given productId
        if(cItem.getProduct().equals(product)) {    // Checks if the given product is in the customers cart.
          cart.getCartList().remove(cItem);
          return "Product " + productId + " has been removed to Customer " + customerId + "'s Cart.";
        }
      }

      if(!found) {
        return "Product " + productId + " was not found in Customer " + customerId + "'s' Cart.";
      }
    return "";
  }

  /**
   * Prints all items in customer's cart
   * @param customerId - ID of customer whose cart will be printed
   */
  public void printCart(String customerId) 
  {
    Customer cust = findCustomer(customerId);
    if(cust == null) {    // Throws UnknownCustomerException if the given customerId does not exist.
      throw new UnknownCustomerException("Customer " + customerId + " Not Found");
    }

    ArrayList<CartItem> cart = cust.getCart().getCartList();  // Get's customer's cart.

    System.out.println(cust.getName() + "'s Cart:");  // Print's items in customers cart
    for(CartItem item: cart) {
      item.print();
    }

  }

  /**
   * Orders all items in customers cart (Creates productOrder for each product).
   * @param customerId - ID of customer whose cart will be ordered
   * @return msg - A string to let customer know that their cart has been ordered
   */
  public String orderItems(String customerId) 
  {
    Customer c = findCustomer(customerId);
    if(c == null || c.getName().isEmpty()) {    // Checks if the given customerId is a real customer.
      throw new UnknownCustomerException("Customer " + customerId + " Not Found");
    }
    Cart cart = c.getCart();

    for (CartItem item: cart.getCartList()) {   // Loops through all the items in the cart. 
      orderProduct(item.getProduct().getId(), customerId, item.getProductOptions());
    }
    cart.getCartList().clear();   // Clears the customer's list
    return "Customer " + customerId + "'s cart has been ordered.";
  }

  /**
   * Adds +1 order count for given productId.
   * @param productId - ID of product to be incremented
   */
  public void addOrders (String productId) 
  {
    if(orderStats.containsKey(productId)) {
      int count = orderStats.get(productId);
      orderStats.put(productId, count + 1);   // Adds 1 to the order count of the given product.
    } else {
      orderStats.put(productId, 1); // Creates new key since the given product has not been ordered.
    }
  }

  /**
   * Prints the stats of the orders
   */
  public void printStats() 
  {
    ArrayList<Integer> counts = new ArrayList<>();

    for (String id: orderStats.keySet()) {
      counts.add(orderStats.get(id));   // Adds all the order counts of each product to ArrayList.
    }

    Collections.sort(counts);   // Sorts it from largest counts to least.

    while(counts.size() >= 1 && counts.size()-1 >= 0) { // Ensures all counts are accounted for.
      for(String id: orderStats.keySet()) {
        if(counts.get(counts.size()-1) == orderStats.get(id)) { // Checks if the count matches its corresponding product.
          System.out.printf("\nName: %-5s Id: %-5s Times Ordered: %-10s", prods.get(id).getName(), id, orderStats.get(id));
          counts.remove(counts.size()-1);
        }
        if(counts.size()-1 < 0) {   // Breaks the for loop if there is no more counts to check.
          break;
        }
      }
    }
  }

  /**
   * Assigns the given rating to the given product
   * @param productId - ID of product to be rated
   * @param rating - Number (1-5) that product will be rated
   * @return msg - To let customer know the product has been successfully rated
   */
  public String rateProduct(String productId, int rating) {
    Product prod = prods.get(productId);
    if(prod == null) {  // Checks if given product exists
      throw new UnknownProductException("Product " + productId + " Not Found");
    }
    if(rating < 1 || rating > 5) {  // Checks if rating is sensible
      throw new IllegalRatingException("Rating: " + rating + " is not in range 1-5");
    }
    prod.setRatings(rating);
    return "You have successfully rated Product " + productId + " " + rating + "/5";
  }

  /**
   * Prints the ratings of the given product
   * @param productId - ID of product whose ratings will be printed
   */
  public void printRatings(String productId) {
    Product prod = prods.get(productId);
    if(prod == null) {  // Checks if given product exists.
      throw new UnknownProductException("Product " + productId + " Not Found");
    }
    Map<Integer, Integer> ratings= prod.getRatings();

    for(int r: ratings.keySet()) {    // Prints all ratings
      System.out.println(ratings.get(r) + ", " + r + " Star Ratings");
    }
    System.out.println("Product " + productId + " has an average rating of " + prod.getAverageRating());  // Prints the average rating.
  }

  /**
   * Prints all products that fit the customer's desired category and surpasses the rating given
   * @param category - Category to be printed
   * @param rating - Number (1-5) rating to be printed
   */
  public void printAverageRating(String category, int rating) {
    boolean categoryFound = false;
    for(Product.Category cats: Product.Category.values()) {
      if(category.equalsIgnoreCase(cats.toString())) {  // Checks if the given category exists
        categoryFound = true;
      }
    }
    if (!categoryFound) { // If given category does not exists throws UnknownCategoryException
      throw new UnknownCategoryException("Category " + category + " does not exist.");
    }
    if(rating > 5 || rating < 1) {  // If rating is out of range, throws IllegalRatingException
      throw new IllegalRatingException("Rating: " + rating + " is not in range 1-5");
    }

    for(String id: prods.keySet()) {
      Product p = prods.get(id);
      if(p.getAverageRating() >= rating && p.getCategory().toString().equalsIgnoreCase(category)) {   // Finds and prints all products fitting user's criteria.
        p.print();
        System.out.print("      Avg Rating: " +  p.getAverageRating());
      }
    }
  }

  /**
   * Compares two Books based on the year they were made.
   */
  public class YearComparator implements Comparator<Book> {
    public int compare(Book o1, Book o2) {
      if (o1.getYear() < o2.getYear()) {
        return -1;
      } else if (o1.getYear() > o2.getYear()) {
        return 1;
      }
      return 0; // Returns 0 if they are equal
    }
  }

  /**
   * Sorts books written by given author by year
   * @param author - Author's books the user wants
   * @return list - ArrayList of books written by given author
   */
  public ArrayList<Book> booksByAuthor(String author) {
    boolean authorExists = false;
    ArrayList<Book> yearOrderedBooks = new ArrayList<Book>();
    for (String id: prods.keySet()) { // Loops through all products
      if (prods.get(id).getCategory().equals(Product.Category.BOOKS)) { // Checks if the product is a book
        Book p1 = (Book) prods.get(id); // Casts the general product into a book
        if (p1.getAuthor().equals(author)) { // Checks if the author given exists
          authorExists = true;
        }
      }
    }
    if (!authorExists) { // Returns null if the author does not exist
      throw new InvalidNameException("Author: " + author + " does not exist.");
    }
    for (String id: prods.keySet()) {
      if (prods.get(id).getCategory().equals(Product.Category.BOOKS)) {
        Book p1 = (Book) prods.get(id);
        if (p1.getAuthor().equals(author)) {
          yearOrderedBooks.add(p1); // Adds all the books written by the given author to a list
        }
      }
    }
    Collections.sort(yearOrderedBooks, new YearComparator()); // Sorts the author's books by year
    return yearOrderedBooks;
  }

  /**
   * Compares two objects of type product based on their price.
   * Returns -1 when the first object is smaller, 1 when the first
   * object is larger, and 0 when they are equal.
   */
  public class PriceComparator implements Comparator<Product> {
    public int compare(Product o1, Product o2) {
      if (o1.getPrice() < o2.getPrice()) {
        return -1;
      } else if (o1.getPrice() > o2.getPrice()) {
        return 1;
      }
      return 0; // Returns 0 if they are equal
    }
  }

  /**
   * Sorts products by increasing price
   */
  public void sortByPrice() {
    for (String id: prods.keySet()) {
      products.add(prods.get(id));
    }
    Collections.sort(products, new PriceComparator());

    for(Product p: products) {
      p.print();
    }
    products = new ArrayList<Product>();
  }

  /**
   * Compares two object of type product based on their name.
   */
  public class NameComparator implements Comparator<Product> {
    public int compare(Product o1, Product o2) {
      return o1.getName().compareTo(o2.getName());
    }
  }

  /**
   * Sort products alphabetically by product name
   */
  public void sortByName() {
    for (String id: prods.keySet()) {
      products.add(prods.get(id));
    }
    Collections.sort(products, new NameComparator());

    for(Product p: products) {
      p.print();
    }
    products = new ArrayList<Product>();
  }

  /**
   * Sort products alphabetically by product name
   */
  public void sortCustomersByName() {
    Collections.sort(customers);
  }
}
/**
* Exception class for when the customerId given does not exist
*/
class UnknownCustomerException extends RuntimeException {
  public UnknownCustomerException() {}

  public UnknownCustomerException(String message) {
    super(message);
  }
}
/**
* Exception class for when the productId given does not exist
*/
class UnknownProductException extends RuntimeException {
  public UnknownProductException() {
  }

  public UnknownProductException(String message) {
    super(message);
  }
}
/**
* Exception class for when the productOptions given does not exist
*/
class InvalidProductOptionException extends RuntimeException {
  public InvalidProductOptionException() {
  }

  public InvalidProductOptionException(String message) {
    super(message);
  }
}
/*
* Exception class for when there is no stock for the product specified.
*/
class NoStockException extends RuntimeException {
  public NoStockException() {
  }

  public NoStockException(String message) {
    super(message);
  }
}

/**
* Exception class for when the order number given does not exist
*/
class InvalidOrderException extends RuntimeException {
  public InvalidOrderException() {
  }

  public InvalidOrderException(String message) {
    super(message);
  }
}
/**
* Exception class for when the customerName given does not exist
*/
class InvalidNameException extends RuntimeException {
  public InvalidNameException() {
  }

  public InvalidNameException(String message) {
    super(message);
  }
}
/**
* Exception class for when the customer address given does not exist
*/
class InvalidAddressException extends RuntimeException {
  public InvalidAddressException() {
  }

  public InvalidAddressException(String message) {
    super(message);
  }
}

/**
 * Exception class for when the user uses the incorrect ordering function.
 * Ex. Tries to order a book using ORDERSHOE command. 
 */
class IncorrectOrderingException extends RuntimeException {
  public IncorrectOrderingException() {
  }

  public IncorrectOrderingException(String message) {
    super(message);
  }
}

/**
* Exception class for when the customerId given does not exist
*/
class IllegalRatingException extends RuntimeException {
  public IllegalRatingException() {}

  public IllegalRatingException(String message) {
    super(message);
  }
}

/**
* Exception class for when the product category given does not exist
*/
class UnknownCategoryException extends RuntimeException {
  public UnknownCategoryException() {}

  public UnknownCategoryException(String message) {
    super(message);
  }
}
