import java.util.ArrayList;

/**
 * Class Cart creates a Cart object for a customer. The Cart stores a list of
 * CartItem's that the customer has added to their cart.
 */
public class Cart {
    private ArrayList<CartItem> cart;
    
    /**
     * Constructs a cart object.
     */
    public Cart() {
        cart = new ArrayList<CartItem>();
    }

    /**
     * @return cart - List of items in the customer's cart.
     */
    public ArrayList<CartItem> getCartList() {
        return cart;
    }


}
