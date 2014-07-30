package com.glug.service;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.glug.adapter.ViewPagerAdapter;
import com.glug.db.DataBase;
import com.glug.db.Feed;
import com.glug.pkh.utils.GlugUtility;
import com.glug.ui.GlugNotifierActivity;
import com.glug.ui.R;



public class MultipleDataFetchService{
	String xml;
	String parseddata=null;
	String url_default="http://puduvaiglug.wordpress.com/feed/?paged=",url="";
	String finalstring=null;
	Notification notification;
	Intent intent;
	SharedPreferences mPref=null;
	SharedPreferences.Editor mEd=null;
	public static final String IS_FROM_ACTIVITY_STRING="isFromActivity";
	boolean isFromActivity=false;
	String id=null,title=null,desc=null,link=null;
	DataBase dataBase;
	String TABLE_NAME;
	String Sitelink;
	int mPrefPos=0;
	int urlPageNo=0;
	ArrayList<String> recentChangelist=new ArrayList<String>();
	public static final String FROM_NOTIFICATION="FROMNOTIFICATION";
	Context mContext;
	public ProgressDialog mProgressDialog;
	
	public boolean isDataModified=false;
	public ViewPagerAdapter mViewpageAdapter;
	


// TODO Auto-generated method stub

//Toast.makeText(this, "MyAlarmService.onCreate()", //Toast.LENGTH_LONG).show();
	
	
	
public MultipleDataFetchService(Context mContext,ViewPagerAdapter mViewpageAdapter){
	dataBase=new DataBase(mContext);
	this.mContext=mContext;
	this.mViewpageAdapter=mViewpageAdapter;
	dataBase.openDB();
	mPref=mContext.getSharedPreferences(GlugUtility.GLUG_SHARED_PREF_NAME, Context.MODE_WORLD_READABLE);
}


public void startFetchingData() {
	// TODO Auto-generated method stub

	mPref=mContext.getSharedPreferences(GlugUtility.GLUG_SHARED_PREF_NAME,Context.MODE_WORLD_WRITEABLE );
	mProgressDialog=new ProgressDialog(mContext);
	mProgressDialog.setMessage("Data is being fetched from server , Please wait  (The app will fetch the last 10 updates)");
	mProgressDialog.setTitle("Downloading...");
	mProgressDialog.setCancelable(false);
	MyFetching mf=new MyFetching();
	mf.execute(url_default+urlPageNo);

}



/*private int getPrefStartPosition(int pos) {
	// TODO Auto-generated method stub
	
	for(int i=pos;i<tablenameLIst.size();i++){
		if(mPref.getBoolean(tablenameLIst.get(i), false)){
			Log.e("pkhtag", "pkh current pref position is= "+i);
			url=mPref.getString(tablenameLIst.get(i)+"_feed", url_default);
			return i;
			
		}
		
	}
	return tablenameLIst.size()-1;

}*/


public void setFeedData(String title,String id,String desc,String link,String tablename) {
	// TODO Auto-generated method stub
	//Log.d("pkhtagnotifier", "pkh---set title="+title+"--id="+id+"--desc="+desc+"--link="+link);
	 /*mEd = mPref.edit();
     mEd.putString(Glug_Home_Page.TITLE,title);
     mEd.putString(Glug_Home_Page.ID,id);
     mEd.putString(Glug_Home_Page.LINK,link);
     mEd.putString(Glug_Home_Page.DESC,desc);
     mEd.commit();*/
     
     //inserting in database
	if(title!=null && id!=null && desc!=null && link!=null)
	{ 
		isDataModified=true;
		Feed feedData=new Feed(id, title, link, desc);
		dataBase.addFeed(tablename,feedData);
	}
     

}
public String getFeedData(String tablename) {
	// TODO Auto-generated method stub
	
	String getdbstring;
	Cursor cursor=dataBase.getCursor(tablename);
//	//Log.d("pkhtagnotifier", "pkh -- id fromserver="+id+"- id from dtabse"+cursor.getShort(0));
	
	if(cursor!=null && cursor.getCount()>0){
		cursor.moveToLast();
		getdbstring=cursor.getString(0);
		return getdbstring;
	}
	else
		return "";
	//return mPref.getString(Glug_Home_Page.TITLE, "").toString();

}

public boolean isSame(String tableName) {
	// TODO Auto-generated method stub
	boolean isanychangpresents=false;
	//try{
	if(isFromActivity){
		
	
//	id="pkh";
		if(id!=null)
		{	if(id.equals(getFeedData(DataBase.MAIN_TABLE_NAME))){
			
			return true; 
		}else{
			setFeedData(title, id, desc, link,DataBase.MAIN_TABLE_NAME);
			
			setFeedData(title, id, desc, link,DataBase.BACKUP_TABLE_NAME);
			
			return false;
		}
		}
	}else{
	
		if(id!=null){			
			if(id.equals(getFeedData(DataBase.MAIN_TABLE_NAME))){
				//Log.d("pkhtagnotifier", "pkh--its calling true");
				//return true;
			}else{
				//Log.d("pkhtagnotifier", "pkh--its is same setFeedData() true");
				setFeedData(title, id, desc, link,DataBase.MAIN_TABLE_NAME);
				setFeedData(title, id, desc, link,DataBase.BACKUP_TABLE_NAME);
				//recentChangelist.add(tableName);
				//return false;
				isanychangpresents=true;
			
		}
		}
		
	}
	return isanychangpresents;
	/*}catch(Exception e){
		//setSharedPref(title, id, desc, link,TABLE_NAME);
		e.printStackTrace();
		return false;
	}*/
}


private void SendData(String tableName) {
	//try{
	if(!isSame(tableName)){
		//can do thing if data is not same
	}

	}

	
@SuppressWarnings("deprecation")
private void ShowNotification(String title) {/*
	// TODO Auto-generated method stub
	if(!isFromActivity){
		
		Intent openWebviewIntent=new Intent(this, GlugNotifierActivity.class);
		openWebviewIntent.putExtra(FROM_NOTIFICATION, true);
		
		
		
		NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, openWebviewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		notification = new Notification(R.drawable.glug_icon, "New Post from PuduvaiGLUG", System.currentTimeMillis());

		notification.setLatestEventInfo(this, title, id, pendingIntent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(1, notification);
	}
*/}

public String getXmlFromUrl(String params) {
	 xml = null;

	try {
		////Log.d("pkh error", "--try");
		// defaultHttpClient
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(params);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		xml = EntityUtils.toString(httpEntity);

	} catch (UnsupportedEncodingException e) {
		//Log.d("pkhtagnotifier error", "error on catch1");
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		//Log.d("pkhtagnotifier error", "error on catch2");
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
		//Log.d("pkhtagnotifier error", "error on catch3");
	}
	// return XML
	//if(xml!=null)
	//Log.d("pkhtagnotifier", "pkh-- fetch="+xml);
	return xml;
}
class MyFetching extends AsyncTask<String, Void, Void>{
	//final ProgressDialog pr=new ProgressDialog(this);
	String parseddata=null;

	
	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
//		//Log.d("pkh data received", "data received="+getXmlFromUrl(params[0]));
		try{
			if(!params[0].replace(" ", "").equalsIgnoreCase(""))
			{
				parseddata=getXmlFromUrl(params[0]).replaceAll("&#8211;","-");
				//Log.e("pkhtag", "pkh database on do in bg execute  is=");
			}
		}catch(Exception e){
			
		}
		
		//Log.d("pkhtagnotifier", "pkh datafetched="+xml);
		return null;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//pr.setMessage("loading");
		//pr.show();
		

		
		
		if(!mProgressDialog.isShowing()){
		mProgressDialog.show();
		}

	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		try{
			dataBase.deleteAllFeed(DataBase.MAIN_TABLE_NAME);
		FeedParser parser=new FeedParser(parseddata);
		
		finalstring=parser.getParsedData();
		//Log.d("TAGPKH", "pkh- finalstring="+title);
		for(int i=parser.getFeedDataList().size();i>0;i--){
			Feed feed=parser.getFeedDataList().get(i-1);
			id=feed.getId();
			title=feed.getTitle();
			desc=feed.getDescription();
			link=feed.getLink();
			SendData(DataBase.MAIN_TABLE_NAME);
		}
		//-----reading all the weblink values
	//	
		Log.e("TAGPKH", "pkh- stoped service="+urlPageNo);
		/*if(!(urlPageNo>10)){
			urlPageNo=urlPageNo+1;
		 MyFetching mf=new MyFetching();
			mf.execute(url_default+urlPageNo);
		//----end of reading all the weblink values
			
		 	if(isDataModified)	
			   {
		 	//	ShowNotification(title);
			   }
		 	 id=null;title=null;desc=null;link=null;
			  
		}else{*/
		mViewpageAdapter.notifyDataSetChanged();
		   
			if(mProgressDialog.isShowing()){
				mProgressDialog.cancel();
				}
			dataBase.closeDB();
		//}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}

class FeedParser extends DefaultHandler{
	
	boolean isitemvisited=false,isfetchedOnce=false;
	String tempval;
	StringBuilder tempstring=new StringBuilder();
	ArrayList<Feed> parserFeed;
	public FeedParser(String xmlString) {
		parserFeed=new ArrayList<Feed>();
		// TODO Auto-generated constructor stub
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			parser.parse(new InputSource(new StringReader(xmlString)), this);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			
		}

	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if(qName=="item") {
			isitemvisited=true;
			////Toast.makeText(FeedView.this, "item", 0).show();
		}
		
		
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(qName=="item"){
			Log.e("pkhtag", "xml valie id="+id+"==title="+title);
			Feed mFeed=new Feed();
			mFeed.setId(id);
			mFeed.setTitle(title);
			mFeed.setDescription(desc);
			mFeed.setLink(link);
			parserFeed.add(mFeed);
			isitemvisited=false;
			isfetchedOnce=false;
		}
		if(isitemvisited && !isfetchedOnce){
			if(qName=="title"){
				tempstring.append("Title="+tempval.toString()+"\n");
				title=tempval.toString();
				//Log.d("TAG", "pkh Title="+tempval.toString());
			}
			if(qName=="link"){
				//Log.d("TAG", "pkh Link="+tempval.toString());
				tempstring.append("Link="+tempval.toString()+"\n");
				link=tempval.toString();
			}
			if(qName=="description"){
				//Log.d("TAG", "pkh Description="+tempval.toString());
				tempstring.append("Description="+tempval.toString()+"\n");
				desc=tempval.toString();
			}
			if(qName=="pubDate"){
				tempstring.append("ID="+tempval.toString()+"\n");
				id=tempval.toString();
				//Log.d("TAG", "pkh Id="+tempval.toString());
				//getSharedPref(TABLE_NAME);
			}
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		tempval=new String(ch, start, length);
	}
	public String getParsedData() {
		// TODO Auto-generated method stub
		
		return tempstring.toString();
		
	}
	private ArrayList<Feed> getFeedDataList() {
		// TODO Auto-generated method stub

		return parserFeed;
	}
}

}
