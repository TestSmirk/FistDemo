package com.example.gamedemo.utils;



import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LightingColorFilter;
import android.util.Log;

public class HttpUtils {
	/**
	 * Post 请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(String url, Map<String, String> params)
			throws Exception {
		HttpClient client = new DefaultHttpClient();
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			NameValuePair nameValuePair = new BasicNameValuePair(
					entry.getKey(), entry.getValue());
			nameValuePairs.add(nameValuePair);
		}

		UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(
				nameValuePairs, HTTP.UTF_8);
		HttpPost post = new HttpPost(url);
		post.setEntity(encodedFormEntity);
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		}
		return null;

	}
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String httpGet(String url) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 20000);
		HttpConnectionParams.setSoTimeout(params, 30000);
		HttpGet get = new HttpGet(url);
		HttpResponse response=null;
		try {
			 response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);
			}
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
		    Log.i("tag","wangluochashi");
		}
		return null;

	}
	/**
	 * 网络获取图片
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmapFormUrl(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		// 设置超时时间
		HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(),
				6 * 1000);
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				// Bitmap bitmap =
				// BitmapFactory.decodeStream(entity.getContent());
				Bitmap bitmap = BitMapUtils.decodeBitmap(
						EntityUtils.toByteArray(entity), 200, 200);
				return bitmap;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
