package com.pkh.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;


public class XmlPullParserHandler {
   List<Location_Details> location_Det_list;
   private Location_Details location_det;
   private String text;
   String locName;

   public XmlPullParserHandler(String locationName) {
	   location_Det_list = new ArrayList<Location_Details>();
	   locName=locationName;
   }

   public List<Location_Details> getEmployees() {
       return location_Det_list;
   }

   public List<Location_Details> parse(InputStream is) {
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
                   if (tagname.equalsIgnoreCase(locName)) {
                       // create a new instance of employee
                	   
                       location_det = new Location_Details();
                   }
                   break;

               case XmlPullParser.TEXT:
                   text = parser.getText();
                   break;

               case XmlPullParser.END_TAG:
                   if (tagname.equalsIgnoreCase(locName)) {
                       // add employee object to list
                	   /*Log.e("pkhTAG", "--getting next elem--"+parser.nextTag());*/
                       location_Det_list.add(location_det);
                   } else if (tagname.equalsIgnoreCase("name")) {
                	   location_det.setLugName(text);
                   } else if (tagname.equalsIgnoreCase("link")) {
                	   location_det.setLugLink(text);
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

       return location_Det_list;
   }
}