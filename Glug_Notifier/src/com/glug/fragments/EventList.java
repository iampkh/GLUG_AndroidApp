package com.glug.fragments;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.glug.db.DataBase;
import com.glug.db.Feed;
import com.glug.ui.WebViewDisplay;
import com.glug.ui.R;



public class EventList extends Fragment{
	private ViewGroup mContainerViewGroup;
	private ViewGroup mEventView;
	private DataBase mDatabase;
	
	public static EventList getInstance(Context context){
		EventList mEventList=new EventList();
		
		return mEventList;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//creating instance to database;
	 	mDatabase=new DataBase(getActivity().getApplicationContext());
	 	mDatabase.openDB();
		 mEventView=(ViewGroup) inflater.inflate(R.layout.glug_event_list_fragment, null);
		 mContainerViewGroup=(ViewGroup) mEventView.findViewById(R.id.container);
		 
		 //looping to get all the data from database 
		 	if(mDatabase.getFeedsCount(DataBase.MAIN_TABLE_NAME)!=0){
		 		mEventView.findViewById(android.R.id.empty).setVisibility(ViewGroup.GONE);
		 		for(int i=0;i<mDatabase.getFeedsCount(DataBase.MAIN_TABLE_NAME);i++){
		 			addCustomItem(i, mDatabase.getFeed(DataBase.MAIN_TABLE_NAME, mDatabase.getFeedsCount(DataBase.MAIN_TABLE_NAME)-1-i));
		 			//addItem(i,sdb.getFeed(DataBase.RECENT_TABLE_NAME, sdb.getFeedsCount(DataBase.RECENT_TABLE_NAME)-1-i));
		 		}
		 		
		 	}
		 	
		 mDatabase.closeDB();
		return mEventView;
	}
	
	private void addCustomItem(int id,final Feed feed) {
		// TODO Auto-generated method stub
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity().getApplicationContext()).inflate(
                R.layout.glug_custom_list_view, mContainerViewGroup, false);
		 final TextView tv= ((TextView) newView.findViewById(android.R.id.text1));
	      tv.setId(id);
	      tv.setText(feed.getTitle());
	      tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent webviewIntent=new Intent(getActivity().getApplicationContext(), WebViewDisplay.class);
				webviewIntent.putExtra(WebViewDisplay.WEB_LINK_STIRNG, feed.getLink());
				startActivity(webviewIntent);
				
			}
		});
	      
	      //custom view button click listener
	      newView.findViewById(R.id.delete_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDatabase.openDB();
        		mDatabase.deleteFeed(DataBase.MAIN_TABLE_NAME, feed);
        		mDatabase.closeDB();
        //	Toast.makeText(getActivity().getApplicationContext(), "item no ="+tv.getId(), 0).show();
            mContainerViewGroup.removeView(newView);

            // If there are no rows remaining, show the empty view.
            if (mContainerViewGroup.getChildCount() == 0) {
                mEventView.findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
            }
				
			}
		});
	      
	      mContainerViewGroup.addView(newView,0);

	}

}
