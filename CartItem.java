/**
 * class CartItem defines a item for a user's cart with a given product and
 * its format (productOptions).
 */
public class CartItem {
    private String productOptions;
    private Product product;

    /**
     * Constructs a CartItem object.
     * @param productOptions - Format of product
     * @param product - Product object that customer has added into their cart
     */
    public CartItem(String productOptions, Product product) {
        this.productOptions = productOptions;
        this.product = product;
    }
    /**
     * @return product - Product object customer has in their cart.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @return productOptions - Format of product
     */
    public String getProductOptions() {
        return productOptions;
    }

    /**
     * Prints the CartItem's id, category, name, and price.
     */
    public void print()
	{
		product.print();
	}
}
