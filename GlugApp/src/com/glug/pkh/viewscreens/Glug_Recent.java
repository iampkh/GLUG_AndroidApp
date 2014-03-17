package com.glug.pkh.viewscreens;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.glug.gnu_linux.R;
import com.glug.pkh.viewscreens.WebViewDisplay;
import com.pkh.db.DataBase;
import com.pkh.db.Feed;



public class Glug_Recent extends Activity{
	 private ViewGroup mContainerView;
	 private ViewGroup aboutView;
	 private DataBase sdb;
	 public static final String DATABASE_NAME="datbaseName";
	public String databaseName="";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.glug_recent);
		FrameLayout frame=(FrameLayout) findViewById(R.id.frameContainer);
		frame.setBackgroundResource(R.drawable.welcome_screen_bg);
		
		mContainerView=(ViewGroup)findViewById(R.id.container);
		databaseName=getIntent().getStringExtra(DATABASE_NAME);
		
		sdb=new DataBase(getApplicationContext());
		//opened =======================
		sdb.openDB();
			if(sdb.getFeedsCount(databaseName)!=0){
				findViewById(android.R.id.empty).setVisibility(View.GONE);
				for(int i=0;i<sdb.getFeedsCount(databaseName);i++){
					//String title=sdb.getFeed(DataBase.RECENT_TABLE_NAME, sdb.getFeedsCount(DataBase.RECENT_TABLE_NAME)-1-i).getTitle();
					if(i<13){
						addItem(i,sdb.getFeed(databaseName, sdb.getFeedsCount(databaseName)-1-i));
					}
					
				}
				
                
			}
		sdb.closeDB();
		//db closed here ==========================
		//return aboutView;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*  switch (item.getItemId()) {
           
            case R.id.action_add_item:
                // Hide the "empty" view since there is now at least one item in the list.
            	
                return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void addItem(int id,final Feed feed) {
        // Instantiate a new "row" view.
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.glug_recent_frag_list, mContainerView, false);

        
        // Set the text in the new row to a random country.
      final TextView tv= ((TextView) newView.findViewById(android.R.id.text1));
      tv.setId(id);
      tv.setText(feed.getTitle());
      tv.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent webviewIntent=new Intent(getApplicationContext(), WebViewDisplay.class);
			webviewIntent.putExtra(WebViewDisplay.WEB_LINK_STIRNG, feed.getLink());
			startActivity(webviewIntent);
			
		}
	});

        // Set a click listener for the "X" button in the row that will remove the row.
        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the row from its parent (the container view).
                // Because mContainerView has android:animateLayoutChanges set to true,
                // this removal is automatically animated.
            	sdb.openDB();
            		sdb.deleteFeed(databaseName, feed);
            	sdb.closeDB();
            //	Toast.makeText(getActivity().getApplicationContext(), "item no ="+tv.getId(), 0).show();
                mContainerView.removeView(newView);

                // If there are no rows remaining, show the empty view.
                if (mContainerView.getChildCount() == 0) {
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                }
            }
        });

        // Because mContainerView has android:animateLayoutChanges set to true,
        // adding this view is automatically animated.
        mContainerView.addView(newView, 0);
    }

    /**
     * A static list of country names.
     */
   
    
}