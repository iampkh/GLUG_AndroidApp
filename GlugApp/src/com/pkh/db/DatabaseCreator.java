package com.pkh.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

import com.pkh.service.Location_Details;

public class DatabaseCreator {
	ArrayList<String> databaseList=new ArrayList<String>();

	//InputStream filepath; 
	public DatabaseCreator(Context context) {
		// TODO Auto-generated constructor stub
		try {
			parseXmlData(context.getAssets().open("lug_details.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataBase db=new DataBase(context,databaseList); //creating databse with table present in databaseList
		Log.d("pkhtag","pkh database creator  database ----------");
		
	}
	public DatabaseCreator(Context context,String getData) {
		// TODO Auto-generated constructor stub
		try {
			parseXmlData(context.getAssets().open("lug_details.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void parseXmlData(InputStream is) {
		// TODO Auto-generated method stub

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
	                  
	                   
	                   break;

	               case XmlPullParser.TEXT:
	                   text = parser.getText();
	                   break;

	               case XmlPullParser.END_TAG:
	                    if (tagname.equalsIgnoreCase("dbname")){ 
	                		  databaseList.add(text);
		                	  
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
}
