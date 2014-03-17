package com.glug.pkh.viewscreens;

import com.glug.gnu_linux.R;
import com.glug.pkh.utils.GlugUtility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DetailView extends Activity implements OnClickListener{
	
	ImageView checkBox;
	Button getRecentbtn,goToPagebtn,homebtn,logout_btn;
	TextView title,datemodified,aboutLug;
	LinearLayout recentContainerLayout;
	TableRow followContainer_layout;
	SharedPreferences mPref=null;
	SharedPreferences.Editor editor=null;
	TextView glugDetailText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.glug_detail_view);
		getRecentbtn=(Button) findViewById(R.id.getRecentBtn);
		goToPagebtn=(Button) findViewById(R.id.gotoPage);
		homebtn=(Button) findViewById(R.id.home_btn);
		logout_btn=(Button) findViewById(R.id.logout);
		glugDetailText=(TextView) findViewById(R.id.glugDetText);
		
		
		createSharedPreference();
		
		glugDetailText.setText(getIntent().getStringExtra(GlugUtility.LUG_NAME_KEY));
		title=(TextView) findViewById(R.id.titleText);
		datemodified=(TextView) findViewById(R.id.timeStamp);
		aboutLug=(TextView) findViewById(R.id.aboutGlugText);
		
		checkBox=(ImageView) findViewById(R.id.followCheckbox);
		
		recentContainerLayout=(LinearLayout) findViewById(R.id.recentContainer);
		followContainer_layout=(TableRow) findViewById(R.id.followCheckboxContainer);
		
		recentContainerLayout.setVisibility(ViewGroup.GONE);
		
		getRecentbtn.setOnClickListener(this);
		goToPagebtn.setOnClickListener(this);
		homebtn.setOnClickListener(this);
		logout_btn.setOnClickListener(this);
		followContainer_layout.setOnClickListener(this);
		
		Log.d("TAGPKH", "pkh--its is detailview dbname=="+getIntent().getStringExtra("dbname"));
		if(mPref.getBoolean(getIntent().getStringExtra("dbname"), false)){
			
			checkBox.setImageResource(R.drawable.check_box_click);
			//Toast.makeText(getApplicationContext(), "true pref", 0).show();
			WriteSharedPreference(getIntent().getStringExtra(GlugUtility.LUG_FEED_KEY), true);
			
		}else{
			checkBox.setImageResource(R.drawable.check_box);
			WriteSharedPreference(getIntent().getStringExtra(GlugUtility.LUG_FEED_KEY), false);
			//Toast.makeText(getApplicationContext(), "false pref", 0).show();
		}
		
		String feedlink=getIntent().getStringExtra(GlugUtility.LUG_FEED_KEY);
		if(feedlink.replace(" ","").equals("")){
			followContainer_layout.setVisibility(ViewGroup.GONE);
		}
		
		Log.e("pkhtag", "pkh  intent data=="+getIntent().getStringExtra("dbname"));
		
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void getDataFromService() {
		// TODO Auto-generated method stub

	}
	private void createSharedPreference() {
		// TODO Auto-generated method stub
		mPref=getSharedPreferences(GlugUtility.GLUG_SHARED_PREF_NAME,Context.MODE_WORLD_WRITEABLE );

	}
	private void WriteSharedPreference(String FeedLink,boolean isFollowable){
		 editor = mPref.edit();
		 editor.putBoolean(getIntent().getStringExtra("dbname"),isFollowable);
		 editor.putString(getIntent().getStringExtra("dbname")+"_feed",FeedLink);
	     editor.commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.getRecentBtn:
			
			Intent intent=new Intent(getApplicationContext(), Glug_Recent.class);
			intent.putExtra(Glug_Recent.DATABASE_NAME, getIntent().getStringExtra("dbname"));
			startActivity(intent);
			
			break;
		case R.id.gotoPage:
			Intent webviewintent=new Intent(getApplicationContext(), WebViewDisplay.class);
			
			webviewintent.putExtra(WebViewDisplay.WEB_LINK_STIRNG, getIntent().getStringExtra(GlugUtility.LUG_LINK_KEY));
			startActivity(webviewintent);
					
			break;
		case R.id.home_btn:
			Intent i = new Intent(getBaseContext(), GlugHome.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
			break;
		case R.id.logout:
			this.finish();
			break;
		case R.id.followCheckboxContainer:
			if(mPref.getBoolean(getIntent().getStringExtra("dbname"), false)){ // if true it makes check box not follow and set false
				checkBox.setImageResource(R.drawable.check_box);
				WriteSharedPreference(getIntent().getStringExtra(GlugUtility.LUG_FEED_KEY), false);
				
			}else{
				checkBox.setImageResource(R.drawable.check_box_click);
				WriteSharedPreference(getIntent().getStringExtra(GlugUtility.LUG_FEED_KEY), true);
			}
			
			break;

		default:
			break;
		}
		
	}

}
