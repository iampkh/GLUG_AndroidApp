package com.glug.pkh.viewscreens;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.glug.gnu_linux.R;
import com.glug.pkh.utils.GlugUtility;
import com.pkh.adapters.CustomListAdapter;
import com.pkh.service.Location_Details;

public class FeedList extends Activity implements OnClickListener{
	
	ListView feedlistview;
	ArrayList<Location_Details> locDetailsArray;
	ArrayList<String> temp;
	Location_Details locDetail;
	InputStream filepath;
	ArrayList<String> databaseList=new ArrayList<String>();
	boolean isFetchdone=false;
	String LOCATION_TAG="";
	TextView feedlistTextView;
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
		feedlistTextView=(TextView) findViewById(R.id.feedlistText);
		feedlistTextView.setText("Select Group");
		temp=new ArrayList<String>();
		
		LOCATION_TAG=getIntent().getStringExtra("lugLoc");
		
		Log.e("pkhtag", "pkh location selected="+LOCATION_TAG);
		locDetailsArray=new ArrayList<Location_Details>();
		try {
			filepath=this.getAssets().open("lug_details.xml");
			parsingLocation(filepath);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CustomListAdapter adapter=new CustomListAdapter(getApplicationContext(), temp);
		feedlistview.setAdapter(adapter);
		
		feedlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Intent openLuginfo=new Intent(getApplicationContext(), DetailView.class);
				openLuginfo.putExtra(GlugUtility.LUG_NAME_KEY, locDetailsArray.get(pos).getLugName());
				openLuginfo.putExtra(GlugUtility.LUG_LINK_KEY, locDetailsArray.get(pos).getLugLink());
				openLuginfo.putExtra(GlugUtility.LUG_FEED_KEY, locDetailsArray.get(pos).getLug_rssfeed());
				openLuginfo.putExtra("dbname", databaseList.get(pos));	
				startActivity(openLuginfo);
				
				
				
			}
		});
		
	}
	
	private void createDatabase() {
		// TODO Auto-generated method stub
		

	}
	
	private void parsingLocation(InputStream is) {
		// TODO Auto-generated method stub

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
	                   if (tagname.equalsIgnoreCase(LOCATION_TAG)) {
	                       // create a new instance of employee
	                	  Log.e("pkhtag", "tagname loctag=="+LOCATION_TAG);
	                	  if(!isFetchdone){
	                		  isFetchdone=true;
	                	  }
	                	  
	                   }
	                   else if(tagname.equalsIgnoreCase("lug")){
	                	   locDetail=new Location_Details();
	                	   Log.e("pkhtag", "tagname lug=="+tagname);
	                   }
	                   break;

	               case XmlPullParser.TEXT:
	                   text = parser.getText();
	                   break;

	               case XmlPullParser.END_TAG:
	                   if (tagname.equalsIgnoreCase(LOCATION_TAG)){ 
	                       // add employee object to list
	                	   /*Log.e("pkhTAG", "--getting next elem--"+parser.nextTag());*/
	                	   Log.e("pkhtag", "tagname end loc=="+LOCATION_TAG);
	                	   isFetchdone=false;
	                	   return;
	                	  
	                      
	                   } else if(tagname.equalsIgnoreCase("name")){
	                	  if(isFetchdone){
	                	   locDetail.setLugName(text);
	                	   Log.e("pkhtag", "name end loc=="+text);
	                	   temp.add(text);
	                	  }
	                   }else if(tagname.equalsIgnoreCase("link")){
	                	  if(isFetchdone){
	                	   locDetail.setLugLink(text);
	                	  Log.e("pkhtag", "link end loc=="+text);
	                	  }
	                   } else if(tagname.equalsIgnoreCase("feed")){
	                	  if(isFetchdone){
	                		  Log.e("pkhtag", "feed end loc=="+text);
	                	   locDetail.setLug_rssfeed(text);
	                	  }
	                   } else if(tagname.equalsIgnoreCase("lug")){
	                	  if(isFetchdone){
	                		  Log.e("pkhtag", "feed end loc added");
	                		  locDetailsArray.add(locDetail);
	                	  }
	                   }  else if (tagname.equalsIgnoreCase("dbname")){ 
	                	   if(isFetchdone){
	                		  databaseList.add(text);
	                	   }
		                	  
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