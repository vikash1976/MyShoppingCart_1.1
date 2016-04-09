package com.example.myshoppingcart;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CatalogActivity extends ActionBarActivity {

	private List<Product> mProductList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catalog);

		// Obtain a reference to the product catalog
		mProductList = ShoppingCartHelper.getCatalog(getResources(), getBaseContext());

		// Create the list
		ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
		listViewCatalog.setAdapter(new ProductAdapter(mProductList,
				getLayoutInflater(), false));

		listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent productDetailsIntent = new Intent(getBaseContext(),
						ProductDetailsActivity.class);
				productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX,
						position);
				/*Product selectedOne = (Product) parent.getItemAtPosition(position);
			    String selectedTitle = selectedOne.title;
			    productDetailsIntent.putExtra("SELECTED_TITLE",
			    		selectedTitle);
				
				 Product selectedOne = (Product) parent.getItemAtPosition(position);
				   
				    productDetailsIntent.putExtra("Selected_PRODUCT", selectedOne);*/
				startActivity(productDetailsIntent);
			}
		});

		Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
		viewShoppingCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent viewShoppingCartIntent = new Intent(getBaseContext(),
						ShoppingCartActivity.class);
				startActivity(viewShoppingCartIntent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catalog, menu);
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
