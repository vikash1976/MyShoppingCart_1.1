package com.example.myshoppingcart;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.sax.StartElementListener;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

	private String customerPhone;
	double amount;
	String customerName;
	String orderId;
	String orderDate;
   @Override
   public void onReceive(Context context, Intent intent) {
      //Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
      Log.d("com.example.myshoppingcart", "msg");
      // Due to internet connection issue can't send email, but chooser appeared.
      //sendEmail(context);
      amount = intent.getDoubleExtra("OrderValue", 0.0);
      customerName = intent.getStringExtra("CustomerName");
      orderDate = intent.getStringExtra("OrderDate");
      orderId = "O00" + intent.getIntExtra("OrderID", 0);
      customerPhone = intent.getStringExtra("CustomerPhone");
      sendSMSMessage(context);
   }
   
   protected void sendEmail(Context context) {
	      Log.i("Send email", "");

	      String[] TO = {"amrood.admin@gmail.com"};
	      String[] CC = {"mcmohd@gmail.com"};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);
	      emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");


	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      emailIntent.putExtra(Intent.EXTRA_CC, CC);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
	      emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

	      try {
	         context.startActivity(emailIntent, null);
	        
	         Log.i("Finished sending email...", "");
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(context, 
	         "There is no email client installed.", Toast.LENGTH_SHORT).show();
	      }
	   }
   
   protected void sendSMSMessage(Context context) {
	      Log.i("Send SMS", "");

	      String message = "Thank you " + customerName +  ". Received your order: "+ orderId + " on: "+ orderDate + " amounting to: " + amount;

	      try {
	         SmsManager smsManager = SmsManager.getDefault();
	         //loadFromConfig(context);
	         smsManager.sendTextMessage(customerPhone, null, message, null, null);
	         Log.d("sendSMS", message);
	         
	      } catch (Exception e) {
	         
	         e.printStackTrace();
	      }
	   }
   
   //Not required now
   /*public void loadFromConfig(Context context){
		String file = "mydata.properties";

		Properties prop = new Properties();

		try {
			FileInputStream fin = context.openFileInput(file);
			// load a properties file
			prop.load(fin);
			
			customerPhone = prop.getProperty("user_phone");
			fin.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}*/

}