package com.glug.pkh.fragments;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glug.gnu_linux.R;



public class About extends Fragment{
	
	public static About getInstance(Context context){
		About about=new About();
		
		return about;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		ViewGroup aboutView=(ViewGroup) inflater.inflate(R.layout.glug_about_us, null);
		
		return aboutView;
	}

}
