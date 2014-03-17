package com.glug.pkh.viewscreens;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.glug.gnu_linux.R;
import com.pkh.adapters.CustomListAdapter;
import com.pkh.db.DatabaseCreator;

public class FeedRegionList extends Activity implements OnClickListener{
	
	InputStream filepath;
	Context context;
	ArrayList<String> locationList;
	ArrayList<String> taglist;
	String currentLoc="";
	ListView feedlistview;
	TextView feedRegionTExt;
	private Button home__btn,logout__btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.glug_feedlist);
		feedlistview=(ListView) findViewById(R.id.feedlist);
		
		home__btn=(Button) findViewById(R.id.home_btn);
		logout__btn=(Button) findViewById(R.id.logout);
		home__btn.setOnClickListener(this);
		logout__btn.setOnClickListener(this);
		feedRegionTExt=(TextView) findViewById(R.id.feedlistText);
		feedRegionTExt.setText("Select Region");
		
		try {
			filepath=this.getAssets().open("location.xml");
			parsingLocation(filepath);
			
			Log.d("pkhtag","pkh oncreate after database ----------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CustomListAdapter adapter=new CustomListAdapter(getApplicationContext(), locationList);
		feedlistview.setAdapter(adapter);
		
		feedlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent openluglistActivity=new Intent(getApplicationContext(), FeedList.class);
				openluglistActivity.putExtra("lugLoc", taglist.get(pos));
				startActivity(openluglistActivity);
				
			}
		});
		
	}
	
	private void createDatabase() {
		// TODO Auto-generated method stub
		

	}
	private void parsingLocation(InputStream is) {
		String text = "";
		// TODO Auto-generated method stub
		 XmlPullParserFactory factory = null;
	       XmlPullParser parser = null;
	       try {
	           factory = XmlPullParserFactory.newInstance();
	           factory.setNamespaceAware(true);
	           parser = factory.newPullParser();

	           parser.setInput(is, null);

	           int eventType = parser.getEventType();
	           while (eventType != XmlPullParser.END_DOCUMENT) {
	               String tagname = parser.getName();
	               switch (eventType) {
	               case XmlPullParser.START_TAG:
	                   if (tagname.equalsIgnoreCase("Location")) {
	                       // create a new instance of employee
	                	   
	                	   locationList = new ArrayList<String>();
	                	   taglist=new ArrayList<String>();
	                   }else{
	                	   
	                	   taglist.add(tagname);
	                   }
	                   break;

	               case XmlPullParser.TEXT:
	                   text = parser.getText();
	                   break;

	               case XmlPullParser.END_TAG:
	                   if (tagname.equalsIgnoreCase("Location")) {
	                       // add employee object to list
	                	   /*Log.e("pkhTAG", "--getting next elem--"+parser.nextTag());*/
	                      
	                   } else{
	                	   locationList.add(text);
	                   } 
	                   break;

	               default:
	                   break;
	               }
	               eventType = parser.next();
	           }

	       } catch (XmlPullParserException e) {
	           e.printStackTrace();
	       } catch (IOException e) {
	           e.printStackTrace();
	       }

	      
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.home_btn:
			Intent i = new Intent(getBaseContext(), GlugHome.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
			
			break;
		case R.id.logout:
			this.finish();
			break;

		default:
			break;
		}
		
	}
}