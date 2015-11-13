package com.example.httpintent;

import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	RequestReceiver receiver;
	IntentFilter filter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		filter = new IntentFilter(RequestReceiver.PROCESS_RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver =new RequestReceiver();
		registerReceiver(receiver, filter);
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	public void clear(View v) {
		EditText et= (EditText) findViewById(R.id.editText1);
		EditText et2= (EditText) findViewById(R.id.editText2);
		et.setText("");
		et2.setText("");
		
	}
	public void show(View v){
		RadioGroup radioGroup=(RadioGroup) findViewById(R.id.radioGroup1);
		double value;
		EditText et= (EditText) findViewById(R.id.editText1);
		if(!et.getText().equals(null)){
			String s=et.getText().toString();
			try {
				value =Double.parseDouble(s);
			} catch (Exception e) {
				value=-1;
			}
			
			
			int start=0 ,end=0;
			if(radioGroup.getCheckedRadioButtonId()==R.id.radio1){
				start=25;
				end=30;
			}else if(radioGroup.getCheckedRadioButtonId()==R.id.radio0){
				start=9;
				end=14;
				
			}
			Intent intent= new Intent(MainActivity.this,RequestService.class);
			intent.putExtra("VALUE", value);
			intent.putExtra("START", start);
			intent.putExtra("END", end);
			startService(intent);
			InputMethodManager keyBoard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		    keyBoard.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.unregisterReceiver(receiver);
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
	
	
	public class RequestReceiver extends BroadcastReceiver{

		public static final String PROCESS_RESPONSE="com.example.httpintent.intent.action.PROCESS_RESPONSE";
		
		
		@SuppressLint("ShowToast")
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String value=intent.getStringExtra("RESPONSE");
			String toastMsg=intent.getStringExtra("TOAST");
			EditText t= (EditText) findViewById(R.id.editText2);
			Toast.makeText(getApplicationContext(), toastMsg, 1000).show();
			t.setText(value);
		}
		
	}
	
}
