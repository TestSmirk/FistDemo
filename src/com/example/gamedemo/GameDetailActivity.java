package com.example.gamedemo;

import java.io.Serializable;

import com.example.gamedemo.bean.Game;
import com.example.gamedemo.utils.BitMapUtils;
import com.example.gamedemo.utils.HttpUtils;
import com.example.gamedemo.utils.PathUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameDetailActivity extends Activity {
	private TextView textViewTitle, textViewType, textViewmadeCompany, textViewReleaseDate, textViewReleaseCompany,
			textViewTerrace, textViewWeb;
	private ImageView imageViewIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_detail);
		initView();

		initData();

	}

	private void initData() {
		Intent intent = getIntent();
		Game game = (Game) intent.getSerializableExtra("info");
		textViewmadeCompany.setText("开发厂商: " + game.getRelease_company());
		textViewReleaseDate.setText("发售日期: " + game.getRelease_date());
		textViewTerrace.setText("游戏平台: " + game.getTerrace());
		textViewTitle.setText("游戏名称: " + game.getShorttitle());
		textViewWeb.setText("官方网站: " + game.getWebsit());
		textViewType.setText("游戏类型: " + game.getTypename());
		final String url = PathUtils.getPic() + game.getLitpic();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Bitmap bm = (Bitmap) msg.obj;
				if (bm != null) {
					imageViewIcon.setImageBitmap(bm);
				} else {
					Toast.makeText(getApplicationContext(), "图片获取失败", 0).show();
					imageViewIcon.setBackgroundResource(R.drawable.ic_launcher);

				}
			}
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = Message.obtain();
				message.setTarget(handler);
				message.obj = HttpUtils.getBitmapFormUrl(url);
				message.sendToTarget();
			}
		}).start();

	}

	private void initView() {
		textViewmadeCompany = (TextView) findViewById(R.id.textView_game_factory);
		textViewReleaseDate = (TextView) findViewById(R.id.textView_release_date);
		textViewTerrace = (TextView) findViewById(R.id.textView_tentrace);
		textViewTitle = (TextView) findViewById(R.id.textView_game_name);
		textViewType = (TextView) findViewById(R.id.textView_game_type);
		textViewWeb = (TextView) findViewById(R.id.textView_game_web);
		imageViewIcon = (ImageView) findViewById(R.id.imageView_gameicon);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
