package com.example.myshoppingcart;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ProductDetailsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.productdetails);

		List<Product> catalog = ShoppingCartHelper.getCatalog(getResources(), getBaseContext());

		int productIndex = getIntent().getExtras().getInt(
				ShoppingCartHelper.PRODUCT_INDEX);
		final Product selectedProduct = catalog.get(productIndex);
		//(Product)getIntent().getSerializableExtra("Selected_PRODUCT");
		/*Product foundProduct = null;
		List <Product> cartList = ShoppingCartHelper.getCartList();
		
		for(Product p: cartList){
			if (p.title == getIntent().getExtras().getString("SELECTED_TITLE")){
				foundProduct = p;
				break;
			}
		}

		final Product selectedProduct = foundProduct;	*/
		// Set the proper image and text
		TextView productCategoryView = (TextView) findViewById(R.id.TextViewProductCategory);
		productCategoryView.setText(selectedProduct.category);
		TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
		productTitleTextView.setText(selectedProduct.title);
		TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);
		productDetailsTextView.setText(selectedProduct.description);

		TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
		productPriceTextView.setText("$" + selectedProduct.price);

		// Update the current quantity in the cart
		TextView textViewCurrentQuantity = (TextView) findViewById(R.id.textViewCurrentlyInCart);
		textViewCurrentQuantity.setText("Currently in Cart: "
				+ ShoppingCartHelper.getProductQuantity(selectedProduct));

		// Save a reference to the quantity edit text
		final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);

		Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
		addToCartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Check to see that a valid quantity was entered
				int quantity = 0;
				try {
					quantity = Integer.parseInt(editTextQuantity.getText()
							.toString());

					if (quantity < 0) {
						Toast.makeText(getBaseContext(),
								"Please enter a quantity of 0 or higher",
								Toast.LENGTH_SHORT).show();
						return;
					}

				} catch (Exception e) {
					Toast.makeText(getBaseContext(),
							"Please enter a numeric quantity",
							Toast.LENGTH_SHORT).show();

					return;
				}

				// If we make it here, a valid quantity was entered
				ShoppingCartHelper.setQuantity(selectedProduct, quantity);

				// Close the activity
				finish();
			}
		});

	}

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * 
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.productdetails);
	 * 
	 * List<Product> catalog = ShoppingCartHelper.getCatalog(getResources());
	 * final List<Product> cart = ShoppingCartHelper.getCart();
	 * 
	 * int productIndex = getIntent().getExtras().getInt(
	 * ShoppingCartHelper.PRODUCT_INDEX); final Product selectedProduct =
	 * catalog.get(productIndex);
	 * 
	 * // Set the proper image and text ImageView productImageView = (ImageView)
	 * findViewById(R.id.ImageViewProduct);
	 * productImageView.setImageDrawable(selectedProduct.productImage); TextView
	 * productTitleTextView = (TextView)
	 * findViewById(R.id.TextViewProductTitle);
	 * productTitleTextView.setText(selectedProduct.title); TextView
	 * productDetailsTextView = (TextView)
	 * findViewById(R.id.TextViewProductDetails);
	 * productDetailsTextView.setText(selectedProduct.description);
	 * 
	 * Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
	 * addToCartButton.setOnClickListener(new OnClickListener() {
	 * 
	 * 
	 * @Override public void onClick(View v) { cart.add(selectedProduct);
	 * finish(); } });
	 * 
	 * // Disable the add to cart button if the item is already in the cart if
	 * (cart.contains(selectedProduct)) { addToCartButton.setEnabled(false);
	 * addToCartButton.setText("Item in Cart"); } }
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_details, menu);
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
