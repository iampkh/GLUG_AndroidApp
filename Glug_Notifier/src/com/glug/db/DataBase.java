package com.glug.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase {
	HelperDB helpDB;
	SQLiteDatabase sdb;
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "GLUG";
	public static final String MAIN_TABLE_NAME = "MAIN_DB";
	public static final String BACKUP_TABLE_NAME="BACKUP_DB";

	// Contacts table name
	//private static final String TABLE_FEED = "feed";

	// Contacts Table Columns names
	private static final String KEY_TITLE = "title";
	private static final String KEY_LINK = "link";
	private static final String KEY_DESC = "description";
	private static final String KEY_ID = "id";

	public ArrayList<String> tableLIst;

	/*public DataBase(Context context) {
		// TODO Auto-generated constructor stub
		helpDB=new HelperDB(context);
		
		//openDB();
		// closeDB();
		
	}*/
	public DataBase(Context context) {
		// TODO Auto-generated constructor stub
		helpDB=new HelperDB(context); 
	}
	
	//MAKING DATABASE READ AND WRITABLE
	public void openDB() {
		// TODO Auto-generated method stub
		sdb=helpDB.getWritableDatabase();
		//Log.d("pkhtag","pkh open db called");
	}

	public void addFeed(String TABLE_NAME,Feed feed) {

		//openDB();
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, feed.getTitle()); // feet title
		values.put(KEY_DESC, feed.getDescription()); // feed description
		values.put(KEY_LINK, feed.getLink());// feed link
		values.put(KEY_ID, feed.getId());

		// Inserting Row
		sdb.insert(TABLE_NAME, null, values);
		//Log.d("pkhtag", "pkh add feed ======"+values);
		
		
		// closeDB();
		////Log.d("pkhtag", "pkh add feed ======"+getFeedsCount(TABLE_NAME));

	}
/*	public ArrayList<String> getTableNames(){
		final ArrayList<String> dirArray = new ArrayList<String>();

		SqlHelper sqlHelper = new SqlHelper(this, "TK.db", null, 1);
		SQLiteDatabase DB = sqlHelper.getWritableDatabase();
		Cursor c = sdb.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
		while(c.moveToNext()){
		   String s=c.getString(0);
		   if(s.equals("android_metadata"))
		   {
		     //System.out.println("Get Metadata");
		     continue;
		   }
		   else
		   {
		      dirArray.add(s);
		   }
		 }
		return dirArray;
	}*/
	public Feed getFeed(String TABLE_NAME,int pos) {
		
		
		// openDB();
		 Cursor allcursor=getCursor(TABLE_NAME);
		
		 
		 ArrayList<String> idlist=new ArrayList<String>();
		 int index=0;
		 if(allcursor!=null){
			 allcursor.moveToFirst();
		do{
			 idlist.add(allcursor.getString(index));
			 //Log.d("pkhtag","pkh all id value is="+allcursor.getString(index)); 
			// index++;
		 }	 while(allcursor.moveToNext());	
		
		
	
		Cursor cursor = sdb.query(TABLE_NAME, new String[] {KEY_ID, KEY_TITLE,
				KEY_LINK, KEY_DESC}, KEY_ID + "=?",
				new String[] { String.valueOf(idlist.get(pos)) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		/*//Log.d("pkhtag", "pkh id="+cursor.getString(0));
		//Log.d("pkhtag", "pkh tit="+cursor.getString(1));
		//Log.d("pkhtag", "pkh lin="+cursor.getString(2));
		//Log.d("pkhtag", "pkh des="+cursor.getString(3));*/

		Feed feed = new Feed(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3));
		// return contact
		// closeDB();
		return feed;
		 }else{
			 // closeDB();
			 return null;
		 }
	}
	 public List<Feed> getAllFeed(String TABLE_NAME) {
		// openDB();
	        List<Feed> feedlist = new ArrayList<Feed>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
	 
	        
	        Cursor cursor = sdb.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	Feed feed= new Feed();
	                feed.setId(cursor.getString(0));
	                feed.setTitle(cursor.getString(1));
	                feed.setLink(cursor.getString(2));
	                feed.setDescription(cursor.getString(3));
	                // Adding contact to list
	                feedlist.add(feed);
	            } while (cursor.moveToNext());
	        }
	 
	        // return contact list
	        // closeDB();
	        return feedlist;
	    }
	 public void  deleteAllFeed(String TABLE_NAME){
		 sdb.rawQuery("delete from "+ TABLE_NAME, null);
	 }
	 public List<Feed> getAllFeedReversed(String TABLE_NAME){
		// openDB();
		 List<Feed> reverFeedLIst=new ArrayList<Feed>();
		 List<Feed> totList=getAllFeed(TABLE_NAME);
		 
		 int index=0;
		 while(index<totList.size()){
			 reverFeedLIst.add(totList.get(totList.size()-1-index));
			 index++;
		 }
		 
		 // closeDB();
		return reverFeedLIst;
		 
	 }
/*	 public Cursor getLastRow(String TABLE_NAME){
		// openDB();
		 //Log.d("pkhtag", "pkh last row 1st");
		 Cursor allcursor=getCursor(TABLE_NAME);
		
		 //Log.d("pkhtag", "pkh last row 2st");
		 ArrayList<String> idlist=new ArrayList<String>();
		 int index=0;
		 if(allcursor.getCount()<1){
			 //Log.d("pkhtag", "pkh cursor is empty=+"+allcursor.getCount());
			 return null;
		 }
		 while(allcursor.moveToNext()){
			 //Log.d("pkhtag", "pkh cursor is empty while loop=+"+allcursor.getString(index));
			 idlist.add(allcursor.getString(index));
			 index++;
		 }
	
		 Cursor cursor;
		 
			 //Log.d("pkhtag", "pkh get last row not null");
		  cursor = sdb.query(TABLE_NAME, new String[] { KEY_TITLE,KEY_LINK, KEY_DESC}, KEY_ID + "=?",	new String[] { String.valueOf(idlist.get(idlist.size()-1)) }, null, null, null, null);
		
		 		    
		 // closeDB();
	     return cursor;
	 
	 }*/
	 public int getFeedsCount(String TABLE_NAME) {
		// openDB();
	        String countQuery = "SELECT  * FROM " + TABLE_NAME;
	        
	        Cursor cursor = sdb.rawQuery(countQuery, null);
	        int count=cursor.getCount();
	        // closeDB();
	        if(count>0){
	        	return count;
	        }else{
	        	return 0;
	        }
	        // return count
	      
	       
	    }
	 @SuppressWarnings("unused")
	public Cursor getCursor(String TABLE_NAME){
		//// openDB();
		 String countQuery = "SELECT  * FROM " + TABLE_NAME;
	     //Cursor cursor=   sdb.rawQuery(countQuery, null);
		 Cursor cursor=sdb.query(TABLE_NAME, null, null, null, null, null, null);
	//	 // closeDB();
		 //Log.d("pkhtag", "pkh inside db get cursor ="+cursor);
	     return cursor;
	 
	 }
	 public void deleteFeed(String TABLE_NAME,Feed feed) {
		// openDB();
	        
	        sdb.delete(TABLE_NAME, KEY_ID + " = ?",
	                new String[] { String.valueOf(feed.getId()) });
	       // closeDB();
	    }
	public void closeDB() {
		// TODO Auto-generated method stub
		helpDB.close();
	}

	class HelperDB extends SQLiteOpenHelper{
		//ArrayList<String> list;

		/*public HelperDB(Context context,ArrayList<String> list) {
			
			super(context, DATABASE_NAME, null, DATABASE_VERSION);	
			//Log.d("pkhtag", "pkh create helper db feed ======"+list.size());
			this.list=list;
			
			// TODO Auto-generated constructor stub
		}*/

		public HelperDB(Context context) {
			
			super(context, DATABASE_NAME, null, DATABASE_VERSION);		
			//Log.d("pkhtag", "pkh constructor context only called ======");
			
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			////Log.d("pkhtag", "pkh create table feed ======");
			//creating Table for Main List
				String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS "+MAIN_TABLE_NAME+"("+KEY_ID+" TEXT,"+KEY_TITLE+" TEXT,"+KEY_LINK+" TEXT,"+KEY_DESC+" TEXT"+")";
				db.execSQL(CREATE_CONTACTS_TABLE);
				
			//Creating table for backup for later use;
			
			 CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS "+BACKUP_TABLE_NAME+"("+KEY_ID+" TEXT,"+KEY_TITLE+" TEXT,"+KEY_LINK+" TEXT,"+KEY_DESC+" TEXT"+")";
			 db.execSQL(CREATE_CONTACTS_TABLE);
			

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			//db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
		}
	}

}
