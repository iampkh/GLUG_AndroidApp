package com.glug.fragments;



import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.glug.ui.R;



public class About extends Fragment{
	
	public static About getInstance(Context context){
		About about=new About();
		
		return about;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.glug_about_us_fragment, container, false);

        WebView webView = (WebView) mainView.findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true); 
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/About/About.htm");

    return mainView;
	}

}
