package com.glug.adapter;

import com.glug.fragments.About;
import com.glug.fragments.Contributors;
import com.glug.fragments.EventList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter{
	Context mContext;
	public static final int mTotalPage=3;
	public ViewPagerAdapter(Context context,FragmentManager fragmentManager) {
		// TODO Auto-generated constructor stub
		super(fragmentManager);
		mContext=context;
	}

	@Override
	public Fragment getItem(int pos) {
		// TODO Auto-generated method stub
		Fragment fragment=new Fragment();
		switch (pos) {
		case 0:
			fragment=EventList.getInstance(mContext);
			break;
		case 1:
			fragment=About.getInstance(mContext);
			break;
		case 2:
			fragment=Contributors.getInstance(mContext);
			break;

		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTotalPage;
	}
}
