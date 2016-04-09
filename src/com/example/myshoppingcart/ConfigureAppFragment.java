package com.example.myshoppingcart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("WorldReadableFiles")
public class ConfigureAppFragment extends Fragment {
	
	//private String file = "mydata.properties";
	TextView name;
	TextView email;
	TextView phone;
	Button done;
	public ConfigureAppFragment(){
		
	}
	 public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  //ListAdapter myListAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new String [] {"a", "b"});
		  //myListAdapter = new ProductListAdaptor(getActivity(), DatabaseActivity.productList);
		  
		  //getListView().setOnItemClickListener(this);
		  
		  
		 }
	 ConfigureAppListener activityCallback;
	 public interface ConfigureAppListener {
	        public void onButtonClick(String name, String email, String phone);
	  }
	  
	  @Override
	  public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	            activityCallback = (ConfigureAppListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement ConfigureAppListener");
	        }
	    }
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
		    super.onViewCreated(view, savedInstanceState);
		    name = (TextView)view.findViewById(R.id.editTextname);
		    email = (TextView)view.findViewById(R.id.editTextemail);
		    phone = (TextView)view.findViewById(R.id.editTextphone);
		    		
		    final Button button = 
	                 (Button) view.findViewById(R.id.button1);
		        button.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
		                buttonClicked(v);
		            }
		        });
		        

		    
		    }
		
		@Override
		 public void onActivityCreated(Bundle savedInstanceState) {

	        super.onActivityCreated(savedInstanceState);
	        //setListAdapter(DatabaseActivity.myListAdapter);
	        //getListView().setOnItemClickListener(this);
	        
		}
		

		 @Override
		 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		   Bundle savedInstanceState) {
		  return inflater.inflate(R.layout.configure_app_fragment, container, false);
		 }
		 
		 public void onStart(){
			 super.onStart();
			// myListAdapter.notifyDataSetChanged();
		 }
		 
		 public void buttonClicked(View view){
			 if(!phone.getText().toString().startsWith("+")){
			 Toast.makeText(getActivity(), "Please prefix you phone with country code.", Toast.LENGTH_LONG).show();
			 return;
			 }
			 activityCallback.onButtonClick(name.getText().toString(), email.getText().toString(), phone.getText().toString());
			 
		 }
		 @Override
		 public void onDestroyView(){
			 super.onDestroyView();
			 Log.v("ConfigureFragment", "Destroying View");
		 }
	
}
