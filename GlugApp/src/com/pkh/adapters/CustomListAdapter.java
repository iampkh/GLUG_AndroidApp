package com.pkh.adapters;

import java.util.ArrayList;

import com.glug.gnu_linux.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter{
	Context context;
	ArrayList<String> arraylist;
	
	public CustomListAdapter(Context context,ArrayList<String> arraylist) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.arraylist=arraylist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arraylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arraylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=convertView.inflate(context, R.layout.glug_custom_list_view, null);
		TextView textView=(TextView) view.findViewById(R.id.list_text);
		textView.setText(arraylist.get(position));
		return view;
	}

}
