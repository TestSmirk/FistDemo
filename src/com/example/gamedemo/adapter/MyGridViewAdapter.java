package com.example.gamedemo.adapter;

import java.util.List;
import java.util.Map;

import com.example.gamedemo.R;
import com.example.gamedemo.bean.Game;
import com.example.gamedemo.utils.AsyncImageLoader;
import com.example.gamedemo.utils.AsyncImageLoader.ImageLoaderlistener;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * nothing TODO Egg pain
 * 
 * @author Smirk
 *
 */
public class MyGridViewAdapter extends MyBaseAdapter<Game> {
	private final static String TAG = "MyGridViewAdapter";

	public MyGridViewAdapter(List<Game> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
		this.list = null;
		this.list = list;
		this.context = context;
	}

	private Context context;
	private List<Game> list;
	private String gamePicUrl = null;

	@Override
	public View getConvertView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.game_item, parent, false);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView_game_pic);
			viewHolder.textView = (TextView) convertView.findViewById(R.id.textView_game_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(list.get(position).getShorttitle().trim());
		gamePicUrl = "http://122.226.122.6" + list.get(position).getLitpic();
		viewHolder.imageView.setTag(gamePicUrl);
		viewHolder.imageView.setImageResource(R.drawable.ic_launcher);

		AsyncImageLoader imageLoader = new AsyncImageLoader(context);
		Bitmap bitmap = imageLoader.downLoader(viewHolder.imageView, new ImageLoaderlistener() {

			@Override
			public void onImageLoader(Bitmap bitmap, ImageView imageView) {
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
				}
			}
		});

		if (bitmap != null) {

			viewHolder.imageView.setImageBitmap(bitmap);
		} else {
			Log.d(TAG, "-->" + bitmap);

		}
		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;
	}

}
