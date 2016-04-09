package com.example.myshoppingcart;

import java.io.Serializable;

public class EditableProduct implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7776795948412658898L;
	public String title;
	public String description;
	public double price;
	public int quanity;
	
	public EditableProduct(String title, String description, double price, int quantity) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.quanity = quantity;
	}
}
