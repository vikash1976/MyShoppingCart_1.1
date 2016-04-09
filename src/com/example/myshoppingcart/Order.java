package com.example.myshoppingcart;

import java.io.Serializable;
import java.util.ArrayList;


public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8298559170481630187L;
	private ArrayList <OrderItem> listOfOrderItems = new ArrayList<OrderItem>();
	private double orderValue;
	
	public ArrayList<OrderItem> getListOfOrderItems() {
		return listOfOrderItems;
	}
	public void setListOfOrderItems(ArrayList<OrderItem> listOfOrderItems) {
		this.listOfOrderItems = listOfOrderItems;
	}
	public double getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(double orderValue) {
		this.orderValue = orderValue;
	}
	
	public Order(){
		
	}
	
}
