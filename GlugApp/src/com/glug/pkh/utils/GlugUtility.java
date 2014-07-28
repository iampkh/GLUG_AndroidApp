package com.glug.pkh.utils;

import android.widget.Button;

public class GlugUtility {
	public static String GLUG_SHARED_PREF_NAME="glug";
	public static final String FEED_TITLE="title";
    public static final String FEED_ID="id";
    public static final String FEED_DESC="description";
    public static final String FEED_LINK="link";
   /* public static final String LUG_NAME_KEY="lugname";
	public static final String LUG_LINK_KEY="luglink";
	public static final String LUG_FEED_KEY="lugfeed";*/
     
    public static final String CALL_COUNT="CallCount";
    
    
    public void viewOnClickSizeChange(Button view,boolean isonclickstart) {
		// TODO Auto-generated method stub
    	int width=view.getWidth();
    	int height=view.getHeight();
    	if(isonclickstart){    	
    	view.setWidth(width-5);
    	view.setHeight(height-5);
    	}
    	else{
    		view.setWidth(width+5);
        	view.setHeight(height+5);
    	}
    	
	}

}
