package com.example.myshoppingcart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.example.myshoppingcart.ConfigureAppFragment.ConfigureAppListener;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity implements
		ConfigureAppListener {

	EditText loginID;
	CheckBox asEmployee;

	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	ConfigureAppFragment hello;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (findViewById(R.id.fragment_container) != null) {

			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.
			if (savedInstanceState != null) {
				return;
			}
		}

		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		hello = new ConfigureAppFragment();

	}

	private boolean lookupEmployee(String empID) {
		// TODO Auto-generated method stub
		if (empID.matches(".*[0-9].*")) {

			return true;
		} else {
			Toast.makeText(this,
					"Doesn't seem an employee, Entering as Customer",
					Toast.LENGTH_LONG).show();
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
		} else if (id == R.id.action_exit) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void login(View view) {
		loginID = (EditText) findViewById(R.id.editText1);
		asEmployee = (CheckBox) findViewById(R.id.checkBox1);

		if (asEmployee.isChecked()) {
			boolean isEmployee = lookupEmployee(loginID.getText().toString());
			if (isEmployee) {
				// TODO launch DB operations activity
				// Bundle dataBundle = new Bundle();
				// dataBundle.putInt("id", 0);
				Intent intent = new Intent(getApplicationContext(),
						com.example.myshoppingcart.ProductListActivity.class);
				// intent.putExtras(dataBundle);
				startActivity(intent);
				return;
			}
		} 
			startActivity(new Intent(getBaseContext(), CatalogActivity.class));
		
	}

	public void personalizeIt(View view) {

		fragmentTransaction.add(R.id.fragment_container, hello, "HELLO");
		fragmentTransaction.commit();
	}

	private String configFile = "mydata.properties";

	@Override
	public void onButtonClick(String name, String email, String phone) {
		// TODO Auto-generated method stub
		// fragmentTransaction.hide(hello);
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.detach(hello);
		fragmentTransaction.commit();
		Properties prop = new Properties();

		try {
			@SuppressWarnings("deprecation")
			FileOutputStream fOut = openFileOutput(configFile,
					MODE_WORLD_READABLE);
			// set the properties value
			prop.setProperty("user_name", name);
			prop.setProperty("user_email", email);
			prop.setProperty("user_phone", phone);

			// save properties to project root folder
			prop.store(fOut, null);
			fOut.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
