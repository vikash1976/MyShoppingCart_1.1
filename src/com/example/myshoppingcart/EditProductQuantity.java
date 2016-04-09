package com.example.myshoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditProductQuantity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_product_quantity);
		TextView tV1 = (TextView)findViewById(R.id.TextViewProductTitle1);
		TextView tV2 = (TextView)findViewById(R.id.TextViewProductDetails1);
		TextView tV3 = (TextView)findViewById(R.id.TextViewProductPrice1);
		
		TextView tV4 = (TextView)findViewById(R.id.textViewCurrentlyInCart1);
		
		Intent editProdIntent = getIntent();
		EditableProduct inProd = (EditableProduct)editProdIntent.getSerializableExtra("Selected_PRODUCT");
		tV1.setText(inProd.title);
		tV2.setText(inProd.description);
		tV3.setText("$" + inProd.price);
		tV4.setText("Currently in Cart: "+ inProd.quanity);		
	}
	
	public void setNewQuantity(View view){
		Intent retIntent = new Intent();
		retIntent.putExtra("newQuantity", Integer.parseInt(((EditText)findViewById(R.id.editTextQuantity1)).getText().toString()));
		setResult(RESULT_OK, retIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_product_quantity, menu);
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
