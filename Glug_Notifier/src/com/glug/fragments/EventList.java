package com.glug.fragments;



import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.glug.db.DataBase;
import com.glug.db.Feed;
import com.glug.ui.WebViewDisplay;
import com.glug.ui.R;



public class EventList extends Fragment implements Updateable{
	
	private ViewGroup mEventView;
	private DataBase mDatabase;
	ListView feedListView;
	CustomAdapter mCustomAdapter;
	LayoutInflater inflater;
	public static EventList getInstance(Context context){
		EventList mEventList=new EventList();
		
		return mEventList;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//creating instance to database;
		this.inflater=inflater;
		addViewToFragment(inflater);
		return mEventView;
	}
	private void addViewToFragment(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		mDatabase=new DataBase(getActivity().getApplicationContext());
	 	mDatabase.openDB();
		 mEventView=(ViewGroup) inflater.inflate(R.layout.glug_event_list_fragment, null);
		 feedListView=(ListView) mEventView.findViewById(R.id.feedList);
		 
		 final ArrayList<Feed> mFeedArayList=new ArrayList<Feed>();
		 		 
		 //looping to get all the data from database 
			if(mDatabase.getFeedsCount(DataBase.MAIN_TABLE_NAME)!=0){
/*		 		mEventView.findViewById(android.R.id.empty).setVisibility(ViewGroup.GONE);*/
		 		for(int i=0;i<mDatabase.getFeedsCount(DataBase.MAIN_TABLE_NAME);i++){
		 		//	addCustomItem(i,mDatabase.getFeed(DataBase.MAIN_TABLE_NAME, mDatabase.getFeedsCount(DataBase.MAIN_TABLE_NAME)-1-i));
		 			mFeedArayList.add(mDatabase.getFeed(DataBase.MAIN_TABLE_NAME, mDatabase.getFeedsCount(DataBase.MAIN_TABLE_NAME)-1-i));
		 			//addItem(i,sdb.getFeed(DataBase.RECENT_TABLE_NAME, sdb.getFeedsCount(DataBase.RECENT_TABLE_NAME)-1-i));
		 		}
		 		
		 	}
			
			mCustomAdapter=new CustomAdapter(getActivity().getApplicationContext(),mFeedArayList);
			
			
			this.feedListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int pos, long arg3) {
					// TODO Auto-generated method stub
					//Toast.makeText(getActivity(), "link-="+mFeedArayList.get(pos).getLink(), 0).show();
					Intent webviewIntent=new Intent(getActivity().getApplicationContext(), WebViewDisplay.class);
					webviewIntent.putExtra(WebViewDisplay.WEB_LINK_STIRNG, mFeedArayList.get(pos).getLink());
					startActivity(webviewIntent);
				}
			});
			feedListView.setAdapter(mCustomAdapter);
			mCustomAdapter.notifyDataSetChanged();
		 mDatabase.closeDB();

	}
	
	/*private void addCustomItem(int id,final Feed feed) {
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

	}*/

	@Override
	public void update() {
		// TODO Auto-generated method stub
		mCustomAdapter.notifyDataSetChanged();
		
	}
	class CustomAdapter extends BaseAdapter{
		
		Context mContext;
		ArrayList<Feed> mFeedList;
		public CustomAdapter(Context mContext,ArrayList<Feed> mfeedList) {
			// TODO Auto-generated constructor stub
			this.mContext=mContext;
			this.mFeedList=mfeedList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFeedList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.glug_custom_list_view,  null);
			TextView mFeedTextView=(TextView) convertView.findViewById(R.id.feedText);
			ImageView mImageIconBtn=(ImageView) convertView.findViewById(R.id.iconBtn);
			mFeedTextView.setText(mFeedList.get(position).getTitle());
			
			return convertView;
		}
		
	}
	

}
 interface Updateable {
	   public void update();
	}
