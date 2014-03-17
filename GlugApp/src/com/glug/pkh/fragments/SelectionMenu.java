package com.glug.pkh.fragments;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.glug.gnu_linux.R;
import com.glug.pkh.viewscreens.FeedRegionList;



public class SelectionMenu extends Fragment implements OnClickListener{
	
	Button feedlist_btn,aboutus_btn,recents_btn,webpage_btn;
	Context context;
	
	
	
	public static SelectionMenu getInstance(Context context){
		SelectionMenu about=new SelectionMenu();
		
		return about;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//getting the GlugHome application context 
		context=getActivity().getApplicationContext();
				
		//getting the view group, use selectionview to access xml file. (eg) selectingview.findviewbyid(R.id.buttonname);
		ViewGroup selectionview=(ViewGroup) inflater.inflate(R.layout.glug_selection_menu, null);
		//creating instance to the button in the "glug_selection_meu.xml"
		feedlist_btn=(Button) selectionview.findViewById(R.id.feedlist);
		webpage_btn=(Button) selectionview.findViewById(R.id.webpage);
		
		
		feedlist_btn.setOnClickListener(this);
		webpage_btn.setOnClickListener(this);
		
		
		
		
		
		
		return selectionview;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.feedlist:
			Intent feedlistintent=new Intent(context, FeedRegionList.class);
			startActivity(feedlistintent);
			
			break;
		case R.id.webpage:
			
			break;			
		

		default:
			break;
		}
		
	}

}
