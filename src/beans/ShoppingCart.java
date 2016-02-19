package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingCart implements Serializable{
	
	private int totalItems = 0;
	private double totalPrice = 0.0;
	private ArrayList<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();
	
	public ShoppingCart() {	}

	public ArrayList<ShoppingCartItem> getShoppingCartItems() {
		return shoppingCartItems;
	}

	public void setShoppingCartItems(ArrayList<ShoppingCartItem> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
}
