package com.example.gamedemo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamedemo.R;
import com.example.gamedemo.bean.Article;
import com.example.gamedemo.utils.AsyncImageLoader.ImageLoaderlistener;
import com.example.gamedemo.utils.PathUtils;

public class MyPullListviewAdapter extends MyBaseAdapter<Article> {

	public MyPullListviewAdapter(List<Article> list, Context context) {
		super(list, context);
	}

	@Override
	public View getConvertView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_listview, parent, false);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.img_itemlayout);
			holder.txt = (TextView) convertView.findViewById(R.id.txt_itemlayout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 获取传进来的数据进行显示
		Article article = list.get(position);
		if (article != null) {
			holder.txt.setText(article.getShorttitle());
		}
		final String picPath = PathUtils.getPic() + article.getLitpic();
		// 给imageview设置标记
		holder.img.setTag(picPath);
		// 设置预设图片
		holder.img.setImageResource(R.drawable.product_default);

		// 图片下载
		Bitmap bitmap = loader.downLoader(holder.img, new ImageLoaderlistener() {
			// 走网络下载图片
			@Override
			public void onImageLoader(Bitmap bitmap, ImageView imageView) {
				// TODO Auto-generated method stub
				if (imageView.getTag().equals(picPath) && bitmap != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		});
		// 从文件或者内存中找到图片直接显示出来
		if (bitmap != null) {
			holder.img.setImageBitmap(bitmap);
		}

		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView txt;
	}

}
