package com.example.myshoppingcart;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCartActivity extends ActionBarActivity {

	protected static final int CHECKOUT_ACTIVITY = 1;
	protected static final int EDIT_PRODUCT = 2;
	private List<Product> mCartList;
	private ProductAdapter mProductAdapter;
	private Product selectedOne;
	// public static Order order;
	double subTotal = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoppingcart);

		mCartList = ShoppingCartHelper.getCartList();
		// order = new Order();

		// Make sure to clear the selections
		for (int i = 0; i < mCartList.size(); i++) {
			mCartList.get(i).selected = false;
		}

		// Create the list
		final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
		mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(),
				true);
		listViewCatalog.setAdapter(mProductAdapter);

		listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent productDetailsIntent = new Intent(getBaseContext(),
						EditProductQuantity.class);
				selectedOne = (Product) parent.getItemAtPosition(position);

				EditableProduct toBeEdited = new EditableProduct(
						selectedOne.title, selectedOne.description,
						selectedOne.price, ShoppingCartHelper
								.getProductQuantity(selectedOne));

				productDetailsIntent.putExtra("Selected_PRODUCT", toBeEdited);
				/*
				 * Product selectedOne = (Product)
				 * parent.getItemAtPosition(position); String selectedTitle =
				 * selectedOne.title;
				 * productDetailsIntent.putExtra(ShoppingCartHelper
				 * .PRODUCT_INDEX, position);
				 */
				startActivityForResult(productDetailsIntent, EDIT_PRODUCT);

			}
		});

		Button checkOutButton = (Button) findViewById(R.id.Button02);
		checkOutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCartList.size() == 0) {
					Toast.makeText(v.getContext(), "Your Cart is Empty",
							Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent(getBaseContext(),
							CheckOutActivity.class);
					intent.putExtra("OrderValue", subTotal);
					startActivityForResult(intent, CHECKOUT_ACTIVITY);

				}
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Refresh the data
		if (mProductAdapter != null) {
			mProductAdapter.notifyDataSetChanged();
		}

		for (Product p : mCartList) {
			int quantity = ShoppingCartHelper.getProductQuantity(p);
			subTotal += p.price * quantity;
			// OrderItem oItem = new OrderItem(p.title, p.description,
			// quantity);

			// order.getListOfOrderItems().add(oItem);
		}
		// order.setOrderValue(subTotal);
		TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
		productPriceTextView.setText("Subtotal: $" + subTotal);
	}

	public void onStop() {
		Log.d("onStop", "Stoping Cart Activity");
		super.onStop();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (CHECKOUT_ACTIVITY): {
			if (resultCode == Activity.RESULT_OK) {
				ShoppingCartHelper sCHelper = new ShoppingCartHelper();
				for (Product p : mCartList) {
					sCHelper.removeProduct(p);
				}
				mCartList.clear();
				//startBackofficeActivities();
				subTotal = 0.0;
				mProductAdapter.notifyDataSetChanged();
				
			}
			break;
		}
		case (EDIT_PRODUCT): {
			if (resultCode == Activity.RESULT_OK) {
				ShoppingCartHelper.setQuantity(selectedOne,
						data.getIntExtra("newQuantity", 1));
			}
			break;
		}
		}
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping_cart, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
