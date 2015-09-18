package com.example.gamedemo;

import com.example.gamedemo.fragment.ArticleFragment;
import com.example.gamedemo.fragment.ForumFragment;
import com.example.gamedemo.fragment.GameFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private TextView textViewTitleText;
	private RadioGroup rgMenus;

	private FragmentManager fragmentManager;
	private FragmentTransaction ftFragmentTransaction;

	private ArticleFragment articleFragment;
	private ForumFragment forumFragment;
	private GameFragment gameFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setListener();
		showFragment(0);

	}

	// 隐藏fragment的方法
	public void hideFragment(FragmentTransaction ftFragmentTransaction) {
		if (articleFragment != null) {
			ftFragmentTransaction.hide(articleFragment);
		}
		if (forumFragment != null) {
			ftFragmentTransaction.hide(forumFragment);
		}
		if (gameFragment != null) {
			ftFragmentTransaction.hide(gameFragment);
		}

	}

	// 显示fragment的方法
	public void showFragment(int num) {

		ftFragmentTransaction = fragmentManager.beginTransaction();
		// 显示之前先调用隐藏的方法避免显示重叠的情况
		hideFragment(ftFragmentTransaction);
		switch (num) {
		case 0:
			if (articleFragment != null) {
				ftFragmentTransaction.show(articleFragment);
			} else {
				articleFragment = new ArticleFragment();
				ftFragmentTransaction.add(R.id.container_activitymain, articleFragment);
			}

			break;
		case 1:
			if (forumFragment != null) {
				ftFragmentTransaction.show(forumFragment);
			} else {
				forumFragment = new ForumFragment();
				ftFragmentTransaction.add(R.id.container_activitymain, forumFragment);
			}

			break;
		case 2:
			if (gameFragment != null) {
				ftFragmentTransaction.show(gameFragment);
			} else {
				gameFragment = new GameFragment();
				ftFragmentTransaction.add(R.id.container_activitymain, gameFragment);
			}

			break;

		default:
			break;
		}
		ftFragmentTransaction.commit();
	}

	// 给个控件设置监听
	private void setListener() {
		// TODO Auto-generated method stub
		rgMenus.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_mainactivity_article:
					textViewTitleText.setText("文章");
					showFragment(0);
					break;
				case R.id.rb_mainactivity_forum:
					textViewTitleText.setText("论坛");
					showFragment(1);
					break;
				case R.id.rb_mainactivity_game:
					textViewTitleText.setText("游戏");
					showFragment(2);
					break;

				default:
					break;
				}
			}
		});
	}

	// 初始化控件
	private void initView() {
		// TODO Auto-generated method stub

		fragmentManager = getSupportFragmentManager();
		rgMenus = (RadioGroup) findViewById(R.id.rg_mainactivity_menus);
		textViewTitleText = (TextView) findViewById(R.id.titleBarText);

		textViewTitleText.setText("文章");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// Log.d(this.getClass().getName(), "back button pressed");
			Toast.makeText(getApplicationContext(), "back button pressed", 0).show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
