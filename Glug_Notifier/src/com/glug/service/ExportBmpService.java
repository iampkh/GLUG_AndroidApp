package com.glug.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class ExportBmpService extends Service {
	File f;
	Context mContext;
	Intent mIntent;
	double mScreenInch=0;
	public static String GlugName="PuduvaiGlug";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);;
		mContext=this;
		mIntent=intent;
		mScreenInch=intent.getExtras().getDouble("inch");
		Log.e("pkhtag", "service started");
        final File file = getTempFile(this);
        //Bitmap captureBmp = Media.getBitmap(getContentResolver(), Uri.fromFile(file) );
          //captureBmp=drawTextToBitmap(getApplicationContext(), captureBmp, "test data");
         Bitmap captureBmp=addTextToBitmap("", getSavedBitmap());
          createFile(captureBmp);
         
          // do whatever you want with the bitmap (Resize, Rename, Add To Gallery, etc)
          //imgFavorite.setImageBitmap(captureBmp);
        //  sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
          refreshGallery(f);
          
		   // Toast.makeText(getApplicationContext(), "Photo ondestroy ", 0).show();
		    Toast.makeText(getApplicationContext(), "Photo saved ", 0).show();
		return Service.START_NOT_STICKY;
		
	}
	private void refreshGallery(File f) {
		// TODO Auto-generated method stub
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    getApplicationContext().sendBroadcast(mediaScanIntent);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		// sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
		// sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
		/* Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		    Uri contentUri = Uri.fromFile(f);
		    mediaScanIntent.setData(contentUri);
		    getApplicationContext().sendBroadcast(mediaScanIntent);
		    Toast.makeText(getApplicationContext(), "Photo ondestroy ", 0).show();*/
		    super.onDestroy();
		// scanSavedImage() ;
	}
	
	private void scanSavedImage() {
		// TODO Auto-generated method stub
		 MediaScannerConnection.scanFile(this, new String[] { Environment.getExternalStorageDirectory().toString() }, null, new MediaScannerConnection.OnScanCompletedListener() {
	            /*
	             *   (non-Javadoc)
	             * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
	             */
	            public void onScanCompleted(String path, Uri uri) 
	              {
	                  Log.e("pkhtag", "Scanned " + path + ":");
	                  Log.e("pkhtag", "-> uri=" + uri);
	              }
	            });

	}
	private Bitmap getSavedBitmap() {
		// TODO Auto-generated method stub
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(getTempFile(getApplicationContext()).getAbsolutePath(),options);
		//selected_photo.setImageBitmap(bitmap);
		return bitmap;

	}
	
	private File getTempFile(Context context){
		  //it will return /sdcard/image.tmp
		  final File path = new File( Environment.getExternalStorageDirectory(),GlugName );
		  if(!path.exists()){
		    path.mkdir();
		  }
		  return new File(path, "image.tmp");
		}
	
	//to create a image file
	private void createFile(Bitmap bmp) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		//Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

		
		Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime()).replace("-", "_").replace(":", "-");
        
        
		//you can create a new file name "test.jpg" in sdcard folder.
		 f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+GlugName+"/FSMK_"+formattedDate+".jpg");
		try {
			f.createNewFile();
			//write the bytes in file
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
			fo.flush();
			// remember close de FileOutput
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("pkhtag", "eerooor="+e);
		}
	}
	private Bitmap addTextToBitmap(String text,Bitmap bmp) {
		// TODO Auto-generated method stub
		  Bitmap alteredBitmap = bmp;//Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
		  alteredBitmap=convertToMutable(alteredBitmap);
		  Canvas canvas = new Canvas(alteredBitmap);
		  Paint paint = new Paint();
		  canvas.drawBitmap(alteredBitmap, 0, 0, paint);
		  paint.setColor(Color.RED); 
		  int textSize=getTextSize();
		  paint.setTextSize(textSize);
		 
		  canvas.drawText(GlugName, 50,canvas.getHeight()-175, paint); 

		  return alteredBitmap;
		  
	}
	private int getTextSize() {
		// TODO Auto-generated method stub
		if(mScreenInch<3){ 
			return 50;
		}else if(mScreenInch>3 && mScreenInch <4.5){
			return 65;
		}else if(mScreenInch>4.5 && mScreenInch <5.3){
			return 90;
		}else{
			return 110;
		}

	}
	/*private int getTextSizeAptForScreenSize() {
		// TODO Auto-generated method stub

		
		// TODO Auto-generated method stub
		 if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
		        //Toast.makeText(this, "Large screen",Toast.LENGTH_LONG).show();
		        return 75;
		    }
		    else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
		        //Toast.makeText(this, "Normal sized screen" , Toast.LENGTH_LONG).show();
		        return 65;
		    } 
		    else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
		        //Toast.makeText(this, "Small sized screen" , Toast.LENGTH_LONG).show();
		        return 40;
		    }
		    else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {     
		        //Toast.makeText(this, "Xlarge sized screen" , Toast.LENGTH_LONG).show();
		        return 85;
		    }
		    else {
		        Toast.makeText(this, "Screen size is neither large, normal or small" , Toast.LENGTH_LONG).show();
		        return 50;
		    }


	}*/
	
	public Bitmap textAsBitmap(String text, float textSize, int textColor) {
	    Paint paint = new Paint();
	    paint.setTextSize(textSize);
	    paint.setColor(textColor);
	    paint.setTextAlign(Paint.Align.LEFT);
	    int width = (int) (paint.measureText(text) + 0.5f); // round
	    float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
	    int height = (int) (baseline + paint.descent() + 0.5f);
	    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(image);
	    canvas.drawText(text, 0, baseline, paint);
	    return image;
	}
	public static Bitmap convertToMutable(Bitmap imgIn) {
	    try {
	        //this is the file going to use temporally to save the bytes. 
	        // This file will not be a image, it will store the raw image data.
	        File file = new File(Environment.getExternalStorageDirectory() + File.separator + ""+GlugName+"/temp.tmp");

	        //Open an RandomAccessFile
	        //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
	        //into AndroidManifest.xml file
	        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

	        // get the width and height of the source bitmap.
	        int width = imgIn.getWidth();
	        int height = imgIn.getHeight();
	        Config type = imgIn.getConfig();

	        //Copy the byte to the file
	        //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
	        FileChannel channel = randomAccessFile.getChannel();
	        MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
	        imgIn.copyPixelsToBuffer(map);
	        //recycle the source bitmap, this will be no longer used.
	        imgIn.recycle();
	        System.gc();// try to force the bytes from the imgIn to be released

	        //Create a new bitmap to load the bitmap again. Probably the memory will be available. 
	        imgIn = Bitmap.createBitmap(width, height, type);
	        map.position(0);
	        //load it back from temporary 
	        imgIn.copyPixelsFromBuffer(map);
	        //close the temporary file and channel , then delete that also
	        channel.close();
	        randomAccessFile.close();

	        // delete the temp file
	        file.delete();

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } 

	    return imgIn;
	}

}
