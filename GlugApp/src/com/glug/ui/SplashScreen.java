package com.glug.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class SplashScreen extends Activity {

protected int _splashTime = 1500; 
	
	private Thread splashTread;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.glug_splash);
	    
	    
	    final SplashScreen sPlashScreen = this; 
	    
	    // thread for displaying the SplashScreen
	    splashTread = new Thread() {  
	        @Override 
	        public void run() {  
	            try {	            	
	            	synchronized(this){ 
	            		wait(_splashTime);
	            	}
	            	
	            } catch(InterruptedException e) {} 
	            finally {
	                 
	                Intent i = new Intent();
	                i.setClass(sPlashScreen, GlugNotifierActivity.class);
	        		startActivity(i);
	        		finish();
	               
	            }
	        }
	    };
	    
	    splashTread.start();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	    	synchronized(splashTread){
	    		splashTread.notifyAll();
	    	}
	    }
	    return true;
	}
	
}
