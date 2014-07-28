package com.glug.fragments;

import java.io.File;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.glug.service.ExportBmpService;
import com.glug.ui.R;

public class Contributors extends Fragment implements OnClickListener {

	Button activities, gallery;
	ImageButton camera;
	static Context mContext;

	public static Contributors getInstance(Context context) {
		Contributors mEventList = new Contributors();
		// mContext=context;
		return mEventList;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext = getActivity().getApplicationContext();
		ViewGroup eventView = (ViewGroup) inflater.inflate(
				R.layout.glug_contributors_fragment, null);
		
		gallery = (Button) eventView.findViewById(R.id.gallery);
		camera = (ImageButton) eventView.findViewById(R.id.camBttn);

		gallery.setOnClickListener(this);
		camera.setOnClickListener(this);
		

		return eventView;
	}

	private double getScreenSizeInch() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		int dens = dm.densityDpi;
		double wi = (double) width / (double) dens;
		double hi = (double) height / (double) dens;
		double x = Math.pow(wi, 2);
		double y = Math.pow(hi, 2);
		double screenInches = Math.sqrt(x + y);
		Log.e("pkhtag", "screen inches double=" + screenInches);
		Log.e("pkhtag", "screen inches integer=" + (int) screenInches);
		return screenInches;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("pkhtag", "onactivity result="+requestCode);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 1:
				Intent serviceIntent = new Intent(getActivity(), ExportBmpService.class);
				serviceIntent.putExtra("inch", getScreenSizeInch());
				getActivity().startService(serviceIntent);
				break;
			}
		}
	}

	private File getTempFile(Context context) {
		// it will return /sdcard/image.tmp
		final File path = new File(Environment.getExternalStorageDirectory(),
				ExportBmpService.GlugName);
		if (!path.exists()) {
			path.mkdir();
		}
		return new File(path, "image.tmp");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.gallery:
			Intent galleryintent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("content://media/internal/images/media"));
			galleryintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(galleryintent);
			break;
		case R.id.camBttn:
			Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			camIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(getTempFile(mContext)));
			startActivityForResult(camIntent, 1);

			break;
		default:
			break;
		}
	}

}
