package com.example.myshoppingcart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.content.Context;
import android.content.res.Resources;

public class ShoppingCartHelper {

	public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

	private static List<Product> catalog;
	private static Map<Product, ShoppingCartEntry> cartMap = new HashMap<Product, ShoppingCartEntry>();

	public static List<Product> getCatalog(Resources res, Context context) {
		DBHelper myDB = new DBHelper(context);

		catalog = myDB.getAllProducts();
		myDB.close();

		return catalog;
	}

	public static void setQuantity(Product product, int quantity) {
		// Get the current cart entry
		ShoppingCartEntry curEntry = cartMap.get(product);

		// If the quantity is zero or less, remove the products
		if (quantity <= 0) {
			if (curEntry != null)
				removeProduct(product);
			return;
		}

		// If a current cart entry doesn't exist, create one
		if (curEntry == null) {
			curEntry = new ShoppingCartEntry(product, quantity);
			cartMap.put(product, curEntry);
			return;

		}

		// Update the quantity
		curEntry.setQuantity(quantity);
	}

	public static int getProductQuantity(Product product) {
		// Get the current cart entry
		ShoppingCartEntry curEntry = cartMap.get(product);

		if (curEntry != null)
			return curEntry.getQuantity();

		return 0;
	}

	public static void removeProduct(Product product) {
		cartMap.remove(product);
	}

	public static List<Product> getCartList() {
		List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
		for (Product p : cartMap.keySet()) {
			cartList.add(p);
		}

		return cartList;
	}

}

// ------------------------------------------Old One---------------------------
/*
 * import java.util.List; import java.util.Vector;
 * 
 * import android.content.res.Resources;
 * 
 * public class ShoppingCartHelper {
 * 
 * public static final String PRODUCT_INDEX = "PRODUCT_INDEX";
 * 
 * private static List<Product> catalog; private static List<Product> cart;
 * 
 * public static List<Product> getCatalog(Resources res){ if(catalog == null) {
 * catalog = new Vector<Product>(); catalog.add(new Product("History of India",
 * res .getDrawable(R.drawable.india), "Indian History in Details", 29.99));
 * catalog.add(new Product("History of China", res
 * .getDrawable(R.drawable.china), "History of China", 24.99)); catalog.add(new
 * Product("History of Japan", res .getDrawable(R.drawable.japan),
 * "Japan's History", 14.99)); }
 * 
 * return catalog; }
 * 
 * public static List<Product> getCart() { if(cart == null) { cart = new
 * Vector<Product>(); }
 * 
 * return cart; }
 * 
 * }
 */