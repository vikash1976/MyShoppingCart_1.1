package com.example.myshoppingcart;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductListActivity extends ActionBarActivity {

	public final static String EXTRA_MESSAGE = "com.example.MyShoppingCart.MESSAGE";

	   private ListView obj;	
	   DBHelper mydb;

	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_product_list);

	      mydb = new DBHelper(this);
	      ArrayList array_list = mydb.getAllProductNames();

	      ArrayAdapter arrayAdapter =      
	      new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

	      //adding it to the list view.
	      obj = (ListView)findViewById(R.id.listView1);
	      obj.setAdapter(arrayAdapter);

	      obj.setOnItemClickListener(new OnItemClickListener(){

	    

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String id_To_Search = parent.getItemAtPosition(position).toString();
	         Bundle dataBundle = new Bundle();
	         dataBundle.putString("id", id_To_Search);
	         Intent intent = new Intent(getApplicationContext(),com.example.myshoppingcart.DisplayProduct.class);
	         intent.putExtras(dataBundle);
	         startActivity(intent);
		}
	     });
	   }
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	      }
	   @Override 
	   public boolean onOptionsItemSelected(MenuItem item) 
	   { 
	      super.onOptionsItemSelected(item); 
	      switch(item.getItemId()) 
	      { 
	         case R.id.item1: 
	            Bundle dataBundle = new Bundle();
	            dataBundle.putString("id", "");
	            Intent intent = new Intent(getApplicationContext(),com.example.myshoppingcart.DisplayProduct.class);
	            intent.putExtras(dataBundle);
	            startActivity(intent);
	            return true; 
	         default: 
	            return super.onOptionsItemSelected(item); 

	       } 

	   } 
	   public boolean onKeyDown(int keycode, KeyEvent event) {
	      if (keycode == KeyEvent.KEYCODE_BACK) {
	         moveTaskToBack(true);
	      }
	      return super.onKeyDown(keycode, event);
	   }
	   public void onStop(){
		   super.onStop();
		   mydb.close();
	   }

}
