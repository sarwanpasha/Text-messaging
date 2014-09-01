package com.pasha.sms;

import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	Button sendSms;
	EditText msgText;
	EditText numText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sendSms=(Button) findViewById(R.id.btnsend);
        msgText=(EditText) findViewById(R.id.etmessage);
        numText=(EditText) findViewById(R.id.etnumber);
        sendSms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			String myMsg=msgText.getText().toString();
			String theNum=numText.getText().toString();
			sendMessage(theNum,myMsg);
				
				
			}
		});
        
        
    }


    protected void sendMessage(String theNum, String myMsg) {
    	String sent= "Message Send";
    	String Delivered= "Message Delivered";
    	PendingIntent sentPI= PendingIntent.getBroadcast(this, 0, new Intent(sent), 0);
    	PendingIntent deliverPI=PendingIntent.getBroadcast(this, 0, new Intent(Delivered), 0);
    	 registerReceiver(new BroadcastReceiver()
    	 {
    		 
    		 public void onReceive(Context arg0, Intent arg1){
    			 switch(getResultCode()){
    			 case Activity.RESULT_OK:
    				 Toast.makeText(MainActivity.this, "SMS Sent", Toast.LENGTH_LONG).show();
    				 break;
    			 case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
    				 Toast.makeText(getBaseContext(), "Generic Failure", Toast.LENGTH_LONG).show();
    				 break;
    			 case SmsManager.RESULT_ERROR_NO_SERVICE:
    				 Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_LONG).show();
    				 break;
    				 

    			 }
    		 }
    	 }, new IntentFilter(sent));
    	 
    	 registerReceiver(new BroadcastReceiver()
    	 {
    		 
    		 public void onReceive(Context arg0, Intent arg1){
    			 switch(getResultCode()){
    			 case Activity.RESULT_OK:
    				 Toast.makeText(MainActivity.this, "SMS Delivered", Toast.LENGTH_LONG).show();
    				 break;
    			 case Activity.RESULT_CANCELED:
    				 Toast.makeText(getBaseContext(), "SMS Not Delivered", Toast.LENGTH_LONG).show();
    				 break;
    			

    			 }
    		 }
    	 }, new IntentFilter(Delivered));
    	
		SmsManager sms= SmsManager.getDefault();
		sms.sendTextMessage(theNum, null, myMsg, sentPI, deliverPI);
		
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
