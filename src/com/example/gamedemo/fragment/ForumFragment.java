package com.example.gamedemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.example.gamedemo.R;

public class ForumFragment extends BaseFragment {
	private WebView webView;
	private final static String url = "http://bbs.3dmgame.com/forum.php";

	public ForumFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_forum, container, false);
		initView(view);
		initData();
		return view;
	}

	private void initData() {
		webView.loadUrl(url);
	}

	private void initView(View view) {

		webView = (WebView) view.findViewById(R.id.webView_forum);
		WebSettings settings = webView.getSettings();

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

}
