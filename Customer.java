/**
 *  class Customer defines a registered customer. It keeps track of the customer's name and address. 
 *  A unique id is generated when when a new customer is created. 
 *  A cart for the customer is generated to store all their products.
 *  
 *  Implements the Comparable interface and compares two customers based on name
 */
public class Customer implements Comparable<Customer>
{
	private String id;  
	private String name;
	private String shippingAddress;
	private Cart cart;
	
	/**
	 * Constructs a default Customer object.
	 * @param id - Unique ID to identify customer
	 */
	public Customer(String id)
	{
		this.id = id;
		this.name = "";
		this.shippingAddress = "";
	}

	/**
	 * Constructs a Customer object
	 * @param id - Unique ID to represent customer
	 * @param name - Customer's name
	 * @param address - Customer's shipping address
	 */
	public Customer(String id, String name, String address)
	{
		this.id = id;
		this.name = name;
		this.shippingAddress = address;
		this.cart = new Cart();
	}
	/**
	 * @return cart - Customer's cart (object)
	 */
	public Cart getCart() {
		return cart;
	}
	/**
	 * @return id - Customer's id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param id - Customer's id
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * @return name - Customer's name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name - Customer's name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return shippingAddress - Customer's address
	 */
	public String getShippingAddress()
	{
		return shippingAddress;
	}
	
	/**
	 * @param shippingAddress - The address of the customer
	 */
	public void setShippingAddress(String shippingAddress)
	{
		this.shippingAddress = shippingAddress;
	}
	
	/**
	 * Prints this customer's information, e.g. name, ID, and shippingAddress.
	 */
	public void print()
	{
		System.out.printf("\nName: %-20s ID: %3s Address: %-35s", name, id, shippingAddress);
	}
	
	/**
	 * Checks if two customers are the same based on their ID
	 * @param other - An object representing the other customer
	 * @return boolean - Returns true if the customers are equal, else it returns false.
	 */
	public boolean equals(Object other)
	{
		Customer otherC = (Customer) other;
		return this.id.equals(otherC.id);
	}

	/**
	 * Compares two customers based on their name (alphabetical order)
	 * @param Customer o - Other customer
	 * @return Customer - The customer whose name comes first alphabetically
	 */
	@Override
	public int compareTo(Customer o) {
		return this.getName().compareTo(o.getName());
	}
	
}
