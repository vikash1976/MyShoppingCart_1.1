package com.example.myshoppingcart;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import android.R.string;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import java.text.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOutActivity extends ActionBarActivity implements
		OnItemSelectedListener {
	double orderAmount;
	EditText name1;
	EditText email1;
	EditText phone1;
	EditText postal1;
	DBHelper dataBase;
	private ProgressDialog Dialog;
	private List<Product> mCartList;
	TextToSpeech ttobj;

	Order order;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_out);

		mCartList = ShoppingCartHelper.getCartList();
		order = new Order();
		dataBase = new DBHelper(this);
		Dialog = new ProgressDialog(CheckOutActivity.this);
		Spinner spinner = (Spinner) findViewById(R.id.city);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> planetsAdapter = ArrayAdapter
				.createFromResource(this, R.array.cities_array,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		planetsAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(planetsAdapter);

		spinner.setOnItemSelectedListener(this);

		name1 = (EditText) findViewById(R.id.name);
		email1 = (EditText) findViewById(R.id.email);
		phone1 = (EditText) findViewById(R.id.phone);
		postal1 = (EditText) findViewById(R.id.postal);
		postal1.requestFocus();
		
		ttobj=new TextToSpeech(getApplicationContext(), 
			      new TextToSpeech.OnInitListener() {
			      @Override
			      public void onInit(int status) {
			         if(status != TextToSpeech.ERROR){
			             ttobj.setLanguage(Locale.UK);
			            }				
			         }
			      });
		
		loadFromConfig();
		prepareOrder();
		
		Toast.makeText(
				getBaseContext(),
				"Order Amount is: $"
						+ String.valueOf(getIntent().getDoubleExtra(
								"OrderValue", 0.0)), Toast.LENGTH_LONG).show();
	}
	public void speakText(String custName, double orderValue){
	     // String toSpeak = write.getText().toString();
	      //Toast.makeText(getApplicationContext(), toSpeak, 
	      //Toast.LENGTH_SHORT).show();
	      ttobj.speak("Thanks Mr." + custName + "Your order amount is: "+ orderValue, TextToSpeech.QUEUE_FLUSH, null);

	   }

	public void giftWrapIt(View view) {
		double giftWrapCost = 3.0;
		
		
		if (((CheckBox) view).isChecked() && view.getId() == R.id.checkBox1) {
			orderAmount += giftWrapCost;
			Toast.makeText(
					view.getContext(),
					"$" + giftWrapCost + " been added. Total Amount is $: "
							+ orderAmount, Toast.LENGTH_LONG).show();
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_out, menu);
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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	public void onConfirmOrder(View view) {
		if(postal1.getText().toString().isEmpty()){
			Toast.makeText(this, "Please enter address.", Toast.LENGTH_LONG).show();
			return;
		}
		new ProcessOrder().execute("Process Orders");
		speakText(name1.getText().toString(), orderAmount);
		
		

		MyDialog confirmDialog = new MyDialog();
		confirmDialog.show(getFragmentManager(), "Thanks");
	}

	public void onDestroy() {
		Log.d("com.example.myshoppingcart.CheckOutActivity",
				"Destroying my self");

		super.onDestroy();
	}

	public void onPause() {
		Dialog.dismiss();
		if(ttobj !=null){
	         ttobj.stop();
	         ttobj.shutdown();
	      }
		super.onPause();
	}

	// Async Class to process order
	private class ProcessOrder extends AsyncTask<String, Void, Void> {

		// Required initialization

		private String Error = null;
		// private ProgressDialog Dialog = new
		// ProgressDialog(CheckOutActivity.this);

		String customerName;
		String orderDate;
		double orderAmount;
		int orderId;

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

		}

		// Call after onPreExecute method
		protected Void doInBackground(String... message) {
			//Order order = ShoppingCartActivity.order;
			ArrayList<OrderItem> orderItemList = order.getListOfOrderItems();
			
			orderAmount = order.getOrderValue();
			customerName = name1.getText().toString();
			Date now = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			orderDate = df.format(now);
			

			orderId = dataBase.getLastID("orders") + 1;
			try {
				if (dataBase.insertOrder(orderId, customerName, orderAmount,
						orderDate)) {
					Log.d("Oreder Added", "OrderAdded");
				} else {
					Log.d("Oreder Not Added", "OrderNotAdded");
				}
				
				for(OrderItem orderItem : orderItemList){
					if(dataBase.insertOrderItem(orderId, orderItem.getProductName(), orderItem.getQuantity())){
						Log.d("OrderItemAdded", "Order Item Added");
					} else {
						Log.d("OrderItemNotAdded", "OrderItem Not Added");
					}
				}
			} catch (SQLiteException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(Void unused) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();
			startBackofficeActivities(customerName, orderId, orderAmount, orderDate);
			

		}
	}
	
	public void startBackofficeActivities(String customerName, int orderId, double subTotal, String orderDate) {
		Intent broadcastIntent = new Intent();
		broadcastIntent
				.setAction("com.example.myshoppingcart.START_BACKGROUND_ACTIVITY");
		//Bundle bundle = new Bundle();
		
		broadcastIntent.putExtra("OrderValue", subTotal);
		broadcastIntent.putExtra("OrderID", orderId);
		broadcastIntent.putExtra("CustomerName", customerName);
		broadcastIntent.putExtra("CustomerPhone", phone1.getText().toString());
		broadcastIntent.putExtra("OrderDate", orderDate);
		//LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(broadcastIntent);
		Log.d(" com.example.myshoppingcart", "Backoffice broadcast sent");
		sendBroadcast(broadcastIntent);

	}
	public void loadFromConfig(){
		String file = "mydata.properties";

		Properties prop = new Properties();

		try {
			FileInputStream fin = openFileInput(file);
			// load a properties file
			prop.load(fin);
			name1.setText(prop.getProperty("user_name"));
			email1.setText(prop.getProperty("user_email"));
			phone1.setText(prop.getProperty("user_phone"));
			fin.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void prepareOrder(){
		orderAmount = getIntent().getDoubleExtra("OrderValue", 0.0);

		for (Product p : mCartList) {
			OrderItem oItem = new OrderItem(p.title, p.description, ShoppingCartHelper.getProductQuantity(p));

			
			order.getListOfOrderItems().add(oItem);
		}
		order.setOrderValue(orderAmount);
	}
	public void onStop()
	{
		super.onStop();
		dataBase.close();
	}

}
