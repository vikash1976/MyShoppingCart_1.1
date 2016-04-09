package com.example.myshoppingcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "Products.db";
	public static final String PRODUCTS_TABLE_NAME = "products";
	public static final String ORDERS_TABLE_NAME = "orders";
	public static final String ORDERITEM_TABLE_NAME = "orderitem";
	public static final String PRODUCTS_COLUMN_ID = "id";
	public static final String PRODUCTS_COLUMN_TITLE = "title";
	public static final String PRODUCTS_COLUMN_PRICE = "price";
	public static final String PRODUCTS_COLUMN_CATEGORY = "category";
	public static final String PRODUCTS_COLUMN_DESC = "description";

	private HashMap hp;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table "
				+ PRODUCTS_TABLE_NAME
				+ "(id integer , title text primary key,price real,category text, description text)");
		db.execSQL("create table "
				+ ORDERS_TABLE_NAME
				+ "(id integer primary key, customername text not null, ordervalue real, orderDate text)");

		db.execSQL("create table "
				+ ORDERITEM_TABLE_NAME
				+ "(id integer primary key autoincrement, orderid integer REFERENCES orders(id), itemname text REFERENCES products(title), quantity integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + ORDERITEM_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME);
		onCreate(db);
	}

	public boolean insertProduct(int id, String title, String category,
			String desc, double price) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(PRODUCTS_COLUMN_ID, id);
		contentValues.put(PRODUCTS_COLUMN_TITLE, title);
		contentValues.put(PRODUCTS_COLUMN_PRICE, price);
		contentValues.put(PRODUCTS_COLUMN_CATEGORY, category);
		contentValues.put(PRODUCTS_COLUMN_DESC, desc);

		db.insert(PRODUCTS_TABLE_NAME, null, contentValues);
		db.close();

		return true;
	}

	public boolean insertOrder(int id, String customerName, double orderValue,
			String orderDate) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("id", id);
		contentValues.put("customername", customerName);
		contentValues.put("ordervalue", orderValue);
		contentValues.put("orderDate", orderDate);

		db.insert(ORDERS_TABLE_NAME, null, contentValues);
		db.close();
		return true;
	}

	public int getLastID(String tableName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM " + tableName
				+ " ORDER BY id DESC LIMIT 1", null);
		int lastOrderId = 0;
		if (res != null && res.moveToFirst()) {

			lastOrderId = Integer.parseInt(res.getString(res
					.getColumnIndex("id")));
			res.close();

		}

		return lastOrderId;
	}

	public boolean insertOrderItem(int orderid, String productName, int quantity) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("orderid", orderid);
		contentValues.put("itemname", productName);
		contentValues.put("quantity", quantity);

		db.insert(ORDERITEM_TABLE_NAME, null, contentValues);
		db.close();
		return true;
	}

	public Cursor getData(String title) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + PRODUCTS_TABLE_NAME
				+ " where title= '" + title + "'", null);
		return res;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				PRODUCTS_TABLE_NAME);
		return numRows;
	}

	public boolean updateProduct(String title, String category, String desc,
			double price) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(PRODUCTS_COLUMN_TITLE, title);
		contentValues.put(PRODUCTS_COLUMN_PRICE, price);
		contentValues.put(PRODUCTS_COLUMN_CATEGORY, category);
		contentValues.put(PRODUCTS_COLUMN_DESC, desc);

		db.update(PRODUCTS_TABLE_NAME, contentValues,
				"title = '" + title + "'", null);
		db.close();
		return true;
	}

	public Integer deleteProduct(String title) {
		SQLiteDatabase db = this.getWritableDatabase();
		int affectedNumRows = db.delete(PRODUCTS_TABLE_NAME, "title = '" + title + "'", null);
		db.close();
		return affectedNumRows;
	}

	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> array_list = new ArrayList<Product>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + PRODUCTS_TABLE_NAME, null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			Product p = new Product(res.getString(res
					.getColumnIndex(PRODUCTS_COLUMN_TITLE)),  res.getString(res
					.getColumnIndex(PRODUCTS_COLUMN_CATEGORY)), res.getString(res
							.getColumnIndex(PRODUCTS_COLUMN_DESC)), res.getInt(res
					.getColumnIndex(PRODUCTS_COLUMN_PRICE)));
			// array_list.add(res.getString(res.getColumnIndex(PRODUCTS_COLUMN_TITLE)));
			array_list.add(p);
			res.moveToNext();
		}
		return array_list;
	}

	public ArrayList<String> getAllProductNames() {
		ArrayList<String> array_list = new ArrayList<String>();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + PRODUCTS_TABLE_NAME, null);
		if (res != null && res.getCount() > 0) {
			if (res.moveToFirst()) {
				do {
					array_list.add(res.getString(res
							.getColumnIndex(PRODUCTS_COLUMN_TITLE)));
				} while (res.moveToNext());
			}
		}
		/*
		 * while (res.isAfterLast() == false) {
		 * 
		 * Product p = new
		 * Product(res.getString(res.getColumnIndex(PRODUCTS_COLUMN_TITLE)),
		 * res.getString(res.getColumnIndex(PRODUCTS_COLUMN_DESC)),
		 * res.getString(res.getColumnIndex(PRODUCTS_COLUMN_CATEGORY)),
		 * res.getInt(res.getColumnIndex(PRODUCTS_COLUMN_PRICE)));
		 * 
		 * 
		 * // array_list.add(p); res.moveToNext(); }
		 */
		return array_list;
	}
}