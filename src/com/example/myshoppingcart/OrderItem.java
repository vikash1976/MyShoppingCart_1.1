package com.example.myshoppingcart;

import java.util.HashMap;

public class OrderItem {
	private String productName;
	private String productDesc;
	private int quantity;
	public OrderItem(String name, String desc, int quantity) {
		this.productName = name;
		this.productDesc = desc;
		this.quantity = quantity;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
