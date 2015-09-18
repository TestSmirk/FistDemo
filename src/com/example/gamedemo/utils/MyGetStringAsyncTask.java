package com.example.gamedemo.utils;

import com.example.gamedemo.callback.DataStringCallBack;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

//创建一个异步任务获取网络字符串数据
public class MyGetStringAsyncTask extends AsyncTask<String, Void, String> {
	private Context context;
	DataStringCallBack callBack;
	private ProgressDialog pDialog;

	public MyGetStringAsyncTask(DataStringCallBack callBack) {
		this.callBack = callBack;
		this.context = context;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result;
		try {
			result = HttpUtils.httpGet(params[0]);
			// 网络数据为空与否的判断
			if (!TextUtils.isEmpty(result)) {
				return result;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (!TextUtils.isEmpty(result)) {
			// 接口回調将数据传递出去
			callBack.getStringData(result);
		}
	}

}
