import java.util.ArrayList;
import java.util.Scanner;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();
			
			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;

			else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
			{
				amazon.printAllProducts(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
			{
				amazon.printAllBooks(); 
			}
			else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
			{
				amazon.printCustomers();	
			}
			else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
			{
				amazon.printAllOrders();	
			}
			else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
			{
				amazon.printAllShippedOrders();	
			}
			else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
			{
				String name = "";
				String address = "";
				
				System.out.print("Name: ");
				if (scanner.hasNextLine())
					name = scanner.nextLine();
				
				System.out.print("\nAddress: ");
				if (scanner.hasNextLine())
					address = scanner.nextLine();
				try {
					amazon.createCustomer(name, address);
				} catch (InvalidNameException | InvalidAddressException e) {
					System.out.println(e.getMessage());
				} 
				
			}
			else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
			{
					String orderNumber = "";
        
					System.out.print("Order Number: ");
					// Get order number from scanner
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();
					// Ship order to customer see ECommerceSystem for the correct method to use
					try {
						ProductOrder valid = amazon.shipOrder(orderNumber);
						valid.print();
					} catch (InvalidOrderException e) {
						System.out.println(e.getMessage());
					}
					
			}
			else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
			{
				String customerId = "";

				System.out.print("Customer Id: ");
				// Get customer Id from scanner
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();
				try {
					amazon.printOrderHistory(customerId);
				} catch (UnknownCustomerException e){
					System.out.println(e.getMessage());
				}
				

			}
			else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
			{
				String productId = "";
				String customerId = "";

				System.out.print("Product Id: ");
			  // Get product Id from scanner
			  	if (scanner.hasNextLine())
			  		productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
			  // Get customer Id from scanner
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();
				// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
				try {
					String num = amazon.orderProduct(productId, customerId, null);
					System.out.println("Order #" + num);
				} catch (UnknownCustomerException | UnknownProductException | InvalidProductOptionException | NoStockException | IncorrectOrderingException e) {
					System.out.println(e.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
			{
				String productId = "";
				String customerId = "";
				String options = "";

				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine())
			  		productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();
				System.out.print("\nFormat [Paperback Hardcover EBook]: ");
				// get book forma and store in options string
				if (scanner.hasNextLine())
					options = scanner.nextLine();
				// Order product. Check for error mesage set in ECommerceSystem
				// Print order number string if order number is not null
				try {
					String num = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + num);
				} catch (UnknownCustomerException | UnknownProductException | InvalidProductOptionException | NoStockException | IncorrectOrderingException e) {
					System.out.println(e.getMessage());
				}
					
			}
			else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color 
			{
				String productId = "";
				String customerId = "";
				String options = "";
				
				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine()) {
					productId = scanner.nextLine();
				}
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine()) {
					customerId = scanner.nextLine();
				}
				System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
				// get shoe size and store in options	
				if (scanner.hasNextLine()) {
					options = scanner.nextLine();
				}
				System.out.print("\nColor: \"Black\" \"Brown\": ");
				// get shoe color and append to options
				if (scanner.hasNextLine()) {
					options += scanner.nextLine();
				}
				//order shoes
				try {
					String num = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + num);
				} catch (UnknownCustomerException | UnknownProductException | InvalidProductOptionException | NoStockException | IncorrectOrderingException e) {
					System.out.println(e.getMessage());
				}
			}
			
			
			else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
			{
				String orderNumber = "";

				System.out.print("Order Number: ");
				// get order number from scanner
				if (scanner.hasNextLine())
			  		orderNumber = scanner.nextLine();
				// cancel order. Check for error
				try {
					amazon.cancelOrder(orderNumber);
				} catch(InvalidOrderException e) {
					System.out.println(e.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("BOOKSBYAUTHOR")) // Sorts book by the given author by year
			{
				String author = "";
				System.out.print("Author: ");
				// get author from scanner
				if(scanner.hasNextLine())
					author = scanner.nextLine();
				
				// checks if author name is valid
				try {
					ArrayList<Book> valid = amazon.booksByAuthor(author);
					for(Book b: valid) {	// Prints books sorted by year
						b.print();
					}
				} catch (InvalidNameException e) {
					System.out.println(e.getMessage());
				} 
			}
			else if (action.equalsIgnoreCase("ADDTOCART"))	// Adds product to customer's cart
			{
				String productId = "";
				String customerId = "";
				String productOptions = null;

				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine()) {
					productId = scanner.nextLine();
				}
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine()) {
					customerId = scanner.nextLine();
				}
				if (amazon.isBook(productId)) {
					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					// get book format and store in options string
					if (scanner.hasNextLine())
						productOptions = scanner.nextLine();
				} else if (amazon.isShoe(productId)) {
					System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
					// get shoe size and store in options	
					if (scanner.hasNextLine()) {
						productOptions = scanner.nextLine();
					}
					System.out.print("\nColor: \"Black\" \"Brown\": ");
					// get shoe color and append to options
					if (scanner.hasNextLine()) {
						productOptions += scanner.nextLine();
					}
				}
				try {
					String message = amazon.addToCart(productId, customerId, productOptions);
					System.out.print(message);
				} catch (UnknownCustomerException | UnknownProductException | InvalidProductOptionException e){
					System.out.println(e.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("REMCARTITEM")) // Removes product from customer's cart
			{
				String customerId = "";
				String productId = "";
				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine()) {
					productId = scanner.nextLine();
				}
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine()) {
					customerId = scanner.nextLine();
				}
				try {
					System.out.print(amazon.removeFromCart(productId, customerId));
				} catch (UnknownCustomerException | UnknownProductException e) {
					System.out.println(e.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("PRINTCART"))	// Prints all products in the cart
			{
				String customerId = "";
				
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine()) {
					customerId = scanner.nextLine();
				}
				try { 
					amazon.printCart(customerId);
				} catch (UnknownCustomerException e) {
					System.out.println(e.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("ORDERITEMS")) // Orders all products in customer's cart
			{
				String customerId = "";

				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine()) {
					customerId = scanner.nextLine();
				}
				try {
					System.out.println(amazon.orderItems(customerId));
				} catch (UnknownCustomerException e) {
					System.out.println(e.getMessage());
				}
				
				
			}
			else if (action.equalsIgnoreCase("STATS"))	// Prints out list of each product name, id, and number of times it was ordered.
			{
				amazon.printStats();
			}
			else if (action.equalsIgnoreCase("RATE"))
			{
				String productId = "";
				int rating = 0;

				System.out.print("Product Id: ");
				if (scanner.hasNextLine()) {
					// gets the product ID
					productId = scanner.nextLine();
				}

				System.out.print("\nYour Rating: ");
				if (scanner.hasNextLine()) {
					// gets the user's rating
					rating = scanner.nextInt();
				}

				try {
					System.out.print(amazon.rateProduct(productId, rating));
				} catch (UnknownProductException | IllegalRatingException e) {
					System.out.println(e.getMessage());
				}
			}

			else if(action.equalsIgnoreCase("PRINTRATINGS")) 
			{
				String productId = "";

				System.out.print("\nProduct Id: ");
				if (scanner.hasNextLine()) {
					productId = scanner.nextLine();
				}

				try {
					amazon.printRatings(productId);
				} catch (UnknownProductException e) {
					System.out.println(e.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("PRINTAVGRATING")) 
			{
				String category = "";
				int rating = 0;

				System.out.print("\nEnter a category: ");
				if (scanner.hasNextLine()) {
					category = scanner.nextLine();
				}
				System.out.print("\nSpecify the average rating you'd like to see: ");
				if (scanner.hasNextLine()) {
					rating = scanner.nextInt();
				}
				try {
					amazon.printAverageRating(category, rating);
				} catch (IllegalRatingException | UnknownCategoryException e) {
					System.out.println(e.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
			{
				amazon.sortByPrice();
			}
			else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
			{
				amazon.sortByName();
			}
			else if (action.equalsIgnoreCase("SORTCUSTS")) // sort customers by name (alphabetic)
			{
				amazon.sortCustomersByName();
			}
			System.out.print("\n>");
		}
	
	}

}
