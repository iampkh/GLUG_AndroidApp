package com.glug.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.glug.adapter.ViewPagerAdapter;
import com.glug.db.DataBase;
import com.glug.service.MultipleDataFetchService;

public class GlugNotifierActivity extends FragmentActivity implements
		OnClickListener {

	Button mLeftButton, mRightButton, mHomeButton, mInfoButton;
	TextView mTitleTextView, mScrollTextView;
	ViewPager mViewPager;
	ViewPagerAdapter mViewPagerAdapter;
	DataBase mDatabase;
	 public boolean isNetworkPresent=false;
	 
	 @Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			isNetworkPresent=isNetWorkAvailable();
			
		}
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		 boolean mDBConatainsData=false;
		 mDatabase.openDB();
		// Adding fragments to the view pager
			addFragments();
			// registering page change listener for viewpager
			pageChangeListener();
		 if((mDatabase.getFeedsCount(DataBase.MAIN_TABLE_NAME)>0)){
			 mDBConatainsData=true;
		 }else{
			 mDBConatainsData=false;
		 }
		 if(!mDBConatainsData ){
		 if(isNetworkPresent){
/*			Intent intent = new Intent(getApplicationContext(),
					DataFetchService.class);

			startService(intent);*/
			 MultipleDataFetchService fetchallData=new MultipleDataFetchService(this,mViewPagerAdapter);
				fetchallData.startFetchingData();
				}else{
					AlertDialog.Builder builder=new AlertDialog.Builder(this);
					builder.setTitle("Need to fetch data from server");
					builder.setMessage("1.'OK' will switch on the Mobile data \n2. 'Cancel' to close the alert Dialog");
					builder.create();
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub 
							startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
						}
					});
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
						}
					});
					
					
					builder.show();
				}}
		super.onResume();
	}
	 @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		 mDatabase.closeDB();
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// to avoid showing top action bar in the application
		// "requestWindowFeature();"
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.glug_notifier_fragmentactivity);
		// creating database;
		mDatabase = new DataBase(getApplicationContext());
		mDatabase.openDB();
		Cursor recentCursor = mDatabase.getCursor(DataBase.MAIN_TABLE_NAME);
		String scrollText1 = null; 
		// Log.d("pkhtag",
		// "pkh cursor count="+sdb.getFeed(DataBase.RECENT_TABLE_NAME,
		// recentCursor.getCount()-1).getId()); 
		if (recentCursor.getCount() > 0) {
			scrollText1 = "Title-->["
					+ mDatabase.getFeed(DataBase.MAIN_TABLE_NAME,
							recentCursor.getCount() - 1).getTitle();
			scrollText1 += "]-Date-->[" 
					+ mDatabase.getFeed(DataBase.MAIN_TABLE_NAME,
							recentCursor.getCount() - 1).getId();
			// scrollText1+="]-Description-->["+sdb.getFeed(DataBase.MAIN_TABLE_NAME,
			// recentCursor.getCount()-1).getDescription();
			scrollText1 += "]-From-->["
					+ mDatabase.getFeed(DataBase.MAIN_TABLE_NAME,
							recentCursor.getCount() - 1).getLink() + "]";
		}
		
		// ending database creation
		mScrollTextView = (TextView) findViewById(R.id.recent_scroll_text);
		if (scrollText1 != null && !scrollText1.replace(" ", "").equals("")) {
			mScrollTextView.setText("" + scrollText1);
		} else {
			mScrollTextView.setText(R.string.no_recent_feed);
		}
		// Button declarations:
		mLeftButton = (Button) findViewById(R.id.leftBtn);
		mRightButton = (Button) findViewById(R.id.rightBtn);
		mHomeButton = (Button) findViewById(R.id.home_btn);
		mInfoButton = (Button) findViewById(R.id.info);
		
		mLeftButton.setVisibility(ViewGroup.GONE);
		mRightButton.setVisibility(ViewGroup.GONE);

		// textview
		mTitleTextView = (TextView) findViewById(R.id.glugHomeText);
		// registering button in onClick listener:
		mLeftButton.setOnClickListener(this);
		mRightButton.setOnClickListener(this);
		mHomeButton.setOnClickListener(this);
		mInfoButton.setOnClickListener(this);
		
		mHomeButton.setBackgroundResource(R.drawable.camlogo);
		
		
		/*MultipleDataFetchService fetchallData=new MultipleDataFetchService(this);
		fetchallData.startFetchingData();*/
		
	}
	private boolean isNetWorkAvailable() {
		// TODO Auto-generated method stub

		
		
		ConnectivityManager conMan = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

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

	// Adding fragment to the fragmentActivity using ViewPager
	private void addFragments() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPagerAdapter = new ViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setCurrentItem(0);
	}

	// view pager page change listnere
	private void pageChangeListener() {
		// TODO Auto-generated method stub
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (mViewPager.getCurrentItem() == 0) {
					mLeftButton.setVisibility(ViewGroup.GONE);
					mRightButton.setVisibility(ViewGroup.GONE);
					mTitleTextView.setText("Glug Notifier");
					mHomeButton.setBackgroundResource(R.drawable.camlogo);

				} else if (mViewPager.getCurrentItem() == 1) {
					mLeftButton.setVisibility(ViewGroup.VISIBLE);
					mRightButton.setVisibility(ViewGroup.VISIBLE);
					mTitleTextView.setText("About Us");
					mHomeButton.setBackgroundResource(R.drawable.home_button);

				}else if(mViewPager.getCurrentItem()==2){
					mLeftButton.setVisibility(ViewGroup.VISIBLE);
					mRightButton.setVisibility(ViewGroup.GONE);
					mTitleTextView.setText("SnapShot");
					mHomeButton.setBackgroundResource(R.drawable.home_button);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glug_notifier, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.leftBtn:
			if(mViewPager.getCurrentItem()!=0){
				if(mViewPager.getCurrentItem()==1){
					mViewPager.setCurrentItem(0);
				}else if(mViewPager.getCurrentItem()==2){
					mViewPager.setCurrentItem(1);
				}
			}
			break;
		case R.id.home_btn:
			if(mViewPager.getCurrentItem()==0){
				mViewPager.setCurrentItem(2);
			}else{
			mViewPager.setCurrentItem(0);
			}
			break;
		case R.id.info:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.rightBtn:
			if(mViewPager.getCurrentItem()!=2){
				if(mViewPager.getCurrentItem()==1){
					mViewPager.setCurrentItem(2);
				}
			}
			break;
		

		default:
			break;
		}
	}

}
