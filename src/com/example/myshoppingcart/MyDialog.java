package com.example.myshoppingcart;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class MyDialog extends DialogFragment {

	private List<Product> mCartList;
	private ProductAdapter mProductAdapter;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){

		AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());

		myDialog.setTitle("Thank You");

		myDialog.setMessage("Thank you for your order");

		myDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			private Object mProductAdapter;

			@Override
			public void onClick(DialogInterface dialog, int which) {

				//mCartList = ShoppingCartHelper.getCart();
				
				//mCartList.clear();
				
				//Toast.makeText(getActivity(), "Have a nice time", Toast.LENGTH_SHORT)
						//.show();
				
				
				getActivity().setResult(Activity.RESULT_OK, new Intent());
				getActivity().finish();
			}
		});
		
		
		
		return myDialog.create();

	}

}
