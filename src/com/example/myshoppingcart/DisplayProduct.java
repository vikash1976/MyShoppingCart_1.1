package com.example.myshoppingcart;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayProduct extends Activity {

   int from_Where_I_Am_Coming = 0;
   private DBHelper mydb ;
   TextView title ;
   TextView desc;
   TextView cat;
   TextView price;
   //TextView place;
   String id_To_Update;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_display_product);
      title = (TextView) findViewById(R.id.editTextTitle);
      desc = (TextView) findViewById(R.id.editTextDesc);
      cat = (TextView) findViewById(R.id.editTextCat);
      price = (TextView) findViewById(R.id.editTextPrice);
     // place = (TextView) findViewById(R.id.editTextCity);

      mydb = new DBHelper(this);

      Bundle extras = getIntent().getExtras(); 
      if(extras !=null)
      {
         String Value = extras.getString("id");
         if(!Value.isEmpty()){
            //means this is the view part not the add contact part.
            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();
            String nam = rs.getString(rs.getColumnIndex(DBHelper.PRODUCTS_COLUMN_TITLE));
            String phon = rs.getString(rs.getColumnIndex(DBHelper.PRODUCTS_COLUMN_DESC));
            String emai = rs.getString(rs.getColumnIndex(DBHelper.PRODUCTS_COLUMN_CATEGORY));
            String stree = rs.getString(rs.getColumnIndex(DBHelper.PRODUCTS_COLUMN_PRICE));
            
            if (!rs.isClosed()) 
            {
               rs.close();
            }
            Button b = (Button)findViewById(R.id.button1);
            b.setVisibility(View.INVISIBLE);

            title.setText((CharSequence)nam);
            title.setFocusable(false);
            title.setClickable(false);

            desc.setText((CharSequence)phon);
            desc.setFocusable(false); 
            desc.setClickable(false);

            cat.setText((CharSequence)emai);
            cat.setFocusable(false);
            cat.setClickable(false);

            price.setText((CharSequence)stree);
            price.setFocusable(false); 
            price.setClickable(false);

            
           }
      }
   }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      Bundle extras = getIntent().getExtras(); 
      if(extras !=null)
      {
         String Value = extras.getString("id");
         if(!Value.isEmpty()){
            getMenuInflater().inflate(R.menu.display_contact, menu);
         }
         else{
            getMenuInflater().inflate(R.menu.main, menu);
         }
      }
      return true;
   }

   public boolean onOptionsItemSelected(MenuItem item) 
   { 
      super.onOptionsItemSelected(item); 
      switch(item.getItemId()) 
   { 
      case R.id.Edit_Contact: 
      Button b = (Button)findViewById(R.id.button1);
      b.setVisibility(View.VISIBLE);
      title.setEnabled(true);
      title.setFocusableInTouchMode(true);
      title.setClickable(true);

      desc.setEnabled(true);
      desc.setFocusableInTouchMode(true);
      desc.setClickable(true);

      cat.setEnabled(true);
      cat.setFocusableInTouchMode(true);
      cat.setClickable(true);

      price.setEnabled(true);
      price.setFocusableInTouchMode(true);
      price.setClickable(true);

      return true; 
      case R.id.Delete_Contact:

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage(R.string.deleteProduct)
     .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
            mydb.deleteProduct(id_To_Update);
            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();  
            Intent intent = new Intent(getApplicationContext(),com.example.myshoppingcart.ProductListActivity.class);
            startActivity(intent);
         }
      })
     .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
         }
      });
      AlertDialog d = builder.create();
      d.setTitle("Are you sure");
      d.show();

      return true;
      default: 
      return super.onOptionsItemSelected(item); 

      } 
   } 

   public void run(View view)
   {	
      Bundle extras = getIntent().getExtras();
      if(extras !=null)
      {
         String  Value = extras.getString("id");
         if(!Value.isEmpty()){
            if(mydb.updateProduct(title.getText().toString(), cat.getText().toString(), desc.getText().toString(), Double.parseDouble(price.getText().toString()))){
               Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();	
               Intent intent = new Intent(getApplicationContext(),com.example.myshoppingcart.ProductListActivity.class);
               startActivity(intent);
             }		
            else{
               Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();	
            }
		 }
         else{
        	 int lastProdId = mydb.getLastID("products") + 1;
            if(mydb.insertProduct(lastProdId, title.getText().toString(), cat.getText().toString(), desc.getText().toString(), Double.parseDouble(price.getText().toString()))){
               Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();	
            }		
            else{
               Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
            }
            Intent intent = new Intent(getApplicationContext(),com.example.myshoppingcart.ProductListActivity.class);
            startActivity(intent);
            }
      }
   }
   public void onStop(){
	   super.onStop();
	   mydb.close();
   }
}