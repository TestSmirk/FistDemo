package com.example.gamedemo;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gamedemo.callback.DataStringCallBack;
import com.example.gamedemo.utils.HttpUtils;
import com.example.gamedemo.utils.MyGetStringAsyncTask;
import com.example.gamedemo.utils.PathUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class DetailActivity extends Activity {
	private ImageButton imageButtonBack;
	private WebView webView;
	private static final String TAG = "DetailActivity";
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		initView();
		initData();
		function();
	}

	private void function() {
		imageButtonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getString("id");

		String typeid = bundle.getString("typeid");
		/* arcurl */
		try {
			Log.d(TAG, "-->进入解析" + PathUtils.getDetailPath(id, typeid));
			new MyGetStringAsyncTask(new DataStringCallBack() {

				@Override
				public void getStringData(String data) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(data);

						String arcurl = jsonObject.optString("arcurl");
						Log.d(TAG, "-->" + arcurl);
						// http://www.3dmgame.com/news/201509/3521976.html
						String ipUrl = arcurl.replaceAll("http://www.3dmgame.com/news/", "http://122.226.122.6/news/");
						Log.d(TAG, "-->" + ipUrl);
						if (arcurl != null) {
							/* ipUrl.trim() */
							webView.loadUrl(ipUrl);

						} else {
							Toast.makeText(getApplicationContext(), "null :)", 0).show();
						}
						Log.d(TAG, "-->退出解析" + arcurl);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).execute(PathUtils.getDetailPath(id, typeid));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initView() {
		imageButtonBack = (ImageButton) findViewById(R.id.imageButton_back);

		webView = (WebView) findViewById(R.id.webview_detail);
		WebSettings settings = webView.getSettings();

		webViewSetting(settings);

	}

	private void webViewSetting(WebSettings settings) {
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		// settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		/*
		 * DisplayMetrics metrics = new DisplayMetrics();
		 * getWindowManager().getDefaultDisplay().getMetrics(metrics); int
		 * mDensity = metrics.densityDpi;
		 * 
		 * if (mDensity == 120) { settings.setDefaultZoom(ZoomDensity.CLOSE); }
		 * else if (mDensity == 160) {
		 * settings.setDefaultZoom(ZoomDensity.MEDIUM); } else if (mDensity ==
		 * 240) { settings.setDefaultZoom(ZoomDensity.FAR); }
		 */
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_comment:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), CommentActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("id", id);
			bundle.putString("pageNum", 1 + "");
			intent.putExtras(bundle);
			startActivity(intent);

			break;
		case R.id.button_refresh:
			webView.reload();
			initData();
			Toast.makeText(getApplicationContext(), "刷新成功 Im Sorry for no ProgressBar", 0).show();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	protected void onPause() {
		super.onPause();
		finish();
		
	};

}
