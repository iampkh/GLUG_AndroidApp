package com.glug.pkh.viewscreens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.glug.gnu_linux.R;
import com.pkh.adapters.ViewPagerAdapter;
import com.pkh.db.DataBase;
import com.pkh.db.DatabaseCreator;
import com.pkh.db.Feed;
import com.pkh.service.BroadCastService;
import com.pkh.service.DataFetchService;

public class GlugHome extends FragmentActivity implements OnClickListener {

	private  ViewPager mViewPager;
	private ViewPagerAdapter mAdapter;
	private Button recent_leftbtn,aboutus_right_btn,home__btn,logout__btn;
	TextView glugHometext;
	DataBase sdb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.glug_viewpager_holder);
		glugHometext=(TextView) findViewById(R.id.glugHomeText);
		DatabaseCreator dbcreation=new DatabaseCreator(getApplicationContext()); //database is created while creating instance itself
		sdb=new DataBase(getApplicationContext());
		sdb.openDB();
		/*
		sdb.addFeed(DataBase.RECENT_TABLE_NAME, new Feed("007", "pkh has given recent", "www.com", "about glug infor"));
		sdb.addFeed(DataBase.RECENT_TABLE_NAME, new Feed("008", "2 pkh has given recent", "2 www.com", "2 about glug infor"));
		sdb.addFeed(DataBase.RECENT_TABLE_NAME, new Feed("009", "3 pkh has given recent", "3 www.com", "3 about glug infor"));
		sdb.addFeed("puduvaiglug", new Feed("007", "pkh test db", "www.google.com", "test glug data"));*/
		
		Cursor recentCursor=sdb.getCursor(DataBase.RECENT_TABLE_NAME);
		String scrollText1=null;
		//Log.d("pkhtag", "pkh cursor count="+sdb.getFeed(DataBase.RECENT_TABLE_NAME, recentCursor.getCount()-1).getId());
		if(recentCursor.getCount()>0){
		 scrollText1="Title-->["+sdb.getFeed(DataBase.RECENT_TABLE_NAME, recentCursor.getCount()-1).getTitle();
		scrollText1+="]-Date-->["+sdb.getFeed(DataBase.RECENT_TABLE_NAME, recentCursor.getCount()-1).getId();
		scrollText1+="]-Description-->["+sdb.getFeed(DataBase.RECENT_TABLE_NAME, recentCursor.getCount()-1).getDescription();
		scrollText1+="]-From-->["+sdb.getFeed(DataBase.RECENT_TABLE_NAME, recentCursor.getCount()-1).getLink()+"]";
		}
		sdb.closeDB();
	/*	String scrollText2=sdb.getFeed(DataBase.RECENT_TABLE_NAME, recentCursor.getCount()-2).getTitle();
		String scrollText3=sdb.getFeed(DataBase.RECENT_TABLE_NAME, recentCursor.getCount()-3).getTitle();*/
		
		TextView scrollTxtView=(TextView) findViewById(R.id.recent_scroll_text);
		
		if(scrollText1!=null && !scrollText1.replace(" ", "").equals("")){
			scrollTxtView.setText(""+scrollText1);
		}else{
			scrollTxtView.setText(R.string.no_recent_feed);
		}
	
		
		
		recent_leftbtn=(Button) findViewById(R.id.recent_btn);
		aboutus_right_btn=(Button) findViewById(R.id.aboutus_btn);
		home__btn=(Button) findViewById(R.id.home_btn);
		logout__btn=(Button) findViewById(R.id.logout);
		
		recent_leftbtn.setOnClickListener(this);
		aboutus_right_btn.setOnClickListener(this);
		home__btn.setOnClickListener(this);
		logout__btn.setOnClickListener(this);

		setUpView();
		setTab();
	}

	private void setTab() {
		// TODO Auto-generated method stub
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if(mViewPager.getCurrentItem()==0){
					recent_leftbtn.setVisibility(ViewGroup.GONE);
					
					glugHometext.setText("Recents");
				}else if(mViewPager.getCurrentItem()==2){
					/*Intent intent=new Intent(getApplicationContext(), BroadCastService.class);
					sendBroadcast(intent);*/
					glugHometext.setText("About Us");
					aboutus_right_btn.setVisibility(ViewGroup.GONE);
				}else{
					glugHometext.setText("GNU_Linux");
					recent_leftbtn.setVisibility(ViewGroup.VISIBLE);
					aboutus_right_btn.setVisibility(ViewGroup.VISIBLE);
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

	private void setUpView() {
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mAdapter = new ViewPagerAdapter(getApplicationContext(),
				getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		if(getIntent().getBooleanExtra(DataFetchService.FROM_NOTIFICATION, false))
		{
			mViewPager.setCurrentItem(0);
		}else{
			mViewPager.setCurrentItem(1);
		}

	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glug, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.recent_btn:
			if(mViewPager.getCurrentItem()==2){
				mViewPager.setCurrentItem(1);
			}else{
				mViewPager.setCurrentItem(0);
			}
			
			
			break;
		case R.id.aboutus_btn:
			if(mViewPager.getCurrentItem()==0){
				mViewPager.setCurrentItem(1);
			}else{
				mViewPager.setCurrentItem(2);
			}
			
			break;
		case R.id.home_btn:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.logout:
			this.finish();
		
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//sdb.closeDB();
		super.onDestroy();
	}

}
