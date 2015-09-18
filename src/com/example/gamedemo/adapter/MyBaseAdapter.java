package com.example.gamedemo.adapter;

import java.util.List;

import com.example.gamedemo.utils.AsyncImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	public List<T> list;
	public Context context;
	public LayoutInflater inflater;
	public AsyncImageLoader loader;

	public MyBaseAdapter(List<T> list, Context context) {
		this.context = context;
		this.list = list;
		inflater=LayoutInflater.from(context);
		loader=new AsyncImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		return getConvertView(position, convertView, parent);
	}

	public abstract View getConvertView(int position, View convertView,
			ViewGroup parent);

}
