package com.pkh.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;



public class BroadCastService extends BroadcastReceiver {

	Context context;
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context=context;
	    // TODO Auto-generated method stub
	//	Toast.makeText(context, "hi this is test internet="+isNetworkAvailable(), 0).show();
		
		if(isNetworkAvailable()){
			Toast.makeText(context, "is started service", 0).show();
			Intent serviceintent=new Intent(context, DataFetchService.class);
			serviceintent.putExtra(DataFetchService.IS_FROM_ACTIVITY_STRING, false);
			context.startService(serviceintent);
		}else{
			Toast.makeText(context, "is no network service", 0).show();
		}
		
		
	}
	private boolean isNetworkAvailable() {
		
		
		ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		//mobile
		State mobile = conMan.getNetworkInfo(0).getState();

		//wifi
		State wifi = conMan.getNetworkInfo(1).getState();


		if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING ||wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) 
		{
		    //mobile 
			return true;
		}
		else if (mobile == NetworkInfo.State.DISCONNECTED && mobile == NetworkInfo.State.DISCONNECTING && wifi == NetworkInfo.State.DISCONNECTED && wifi == NetworkInfo.State.DISCONNECTING) 
		{
		    //wifi
			return false;
		}
		return false;
		
		

		}

}
