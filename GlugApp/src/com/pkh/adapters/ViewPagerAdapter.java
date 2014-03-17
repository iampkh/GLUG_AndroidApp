package com.pkh.adapters;





import com.glug.pkh.fragments.About;
import com.glug.pkh.fragments.Recents;
import com.glug.pkh.fragments.SelectionMenu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.SeekBar;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private Context _context;
	public static int totalPage=3;
	public ViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);	
		_context=context;
		
		}
	@Override
	public Fragment getItem(int position) {
		Fragment f = new Fragment();
		switch(position){
		case 0:
			f=Recents.getInstance(_context);
				
			break;
		case 1:
			f=SelectionMenu.getInstance(_context);
			
			break;
		case 2:
			f=About.getInstance(_context);	
				
			break;
		}
		return f;
	}
	@Override
	public int getCount() {
		return totalPage;
	}

}
