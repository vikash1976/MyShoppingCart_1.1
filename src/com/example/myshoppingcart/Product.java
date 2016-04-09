package com.example.myshoppingcart;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 378900062974192805L;
	public String title;
	public String category;
	public String description;
	public double price;
	public boolean selected;

	public Product(String title, String category, String description,
			double price) {
		this.title = title;
		this.category = category;
		this.description = description;
		this.price = price;
	}

}