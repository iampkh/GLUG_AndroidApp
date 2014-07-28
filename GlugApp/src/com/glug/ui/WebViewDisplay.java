package com.glug.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class WebViewDisplay extends Activity {
	
	WebView webview;
	String URL="";
public static String WEB_LINK_STIRNG="weblink";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.glug_webview_display);
	    
	    URL=getIntent().getStringExtra(WEB_LINK_STIRNG);
	    
	    webview=(WebView) findViewById(R.id.webdisplay);
	    webview.getSettings().setJavaScriptEnabled(true);
	    webview.loadUrl(URL);
	    
	
	    // TODO Auto-generated method stub
	}

}
