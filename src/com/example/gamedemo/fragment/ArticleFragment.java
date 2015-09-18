package com.example.gamedemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.gamedemo.R;
import com.example.gamedemo.adapter.ViewPagerMyAdapter;

public class ArticleFragment extends BaseFragment {

	private HorizontalScrollView horizontalScrollView;
	private RadioGroup rgGroup;

	private ViewPager viewPager;

	private ViewPagerMyAdapter myAdapter;
	private List<Fragment> listFragments = new ArrayList<Fragment>();

	public ArticleFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_article, container, false);
		initView(view);
		setLisener();

		return view;
	}

	// 给控件设置监听事件
	private void setLisener() {
		// TODO Auto-generated method stub
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_fragmentarticle_articlehome:
					viewPager.setCurrentItem(0);
					break;
				case R.id.rb_fragmentarticle_gamenews:
					viewPager.setCurrentItem(1);
					break;
				case R.id.rb_fragmentarticle_gametalk:
					viewPager.setCurrentItem(2);
					break;
				case R.id.rb_fragmentarticle_harddeviceinfo:
					viewPager.setCurrentItem(3);
					break;
				case R.id.rb_fragmentarticle_gameforword:
					viewPager.setCurrentItem(4);
					break;
				case R.id.rb_fragmentarticle_gametest:
					viewPager.setCurrentItem(5);
					break;
				case R.id.rb_fragmentarticle_good:
					viewPager.setCurrentItem(6);
					break;
				case R.id.rb_fragmentarticle_gamerecorde:
					viewPager.setCurrentItem(7);
					break;
				case R.id.rb_fragmentarticle_eventfoucs:
					viewPager.setCurrentItem(8);
					break;
				case R.id.rb_fragmentarticle_methodcenter:
					viewPager.setCurrentItem(9);
					break;

				default:
					break;
				}

			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				// 得到对应的radiobutton设置为选中状态
				RadioButton rgbutton = (RadioButton) rgGroup.getChildAt(arg0);
				rgbutton.setChecked(true);
				// 设置scrollview滑动
				horizontalScrollView.scrollTo(rgbutton.getLeft(), 0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	// 初始化控件
	private void initView(View view) {
		// TODO Auto-generated method stub
		horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.scrollviewHorizontal);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_fragmentarticle_topmenus);
		viewPager = (ViewPager) view.findViewById(R.id.vp_fragmentarticle_show);
		/*
		 * 1 文章首页 2 新闻 文章特定 typeid:
		 * 
		 * 151 游戏杂谈 152 硬件信息 153 游戏前瞻 154 游戏评测 196 原创精品 197 游戏盘点 199 时事焦点 25
		 * 攻略中心
		 */
		listFragments.add(new ArticleBannerFragment());
		listFragments.add(new ArticleNoBannerFragment(2));
		listFragments.add(new ArticleNoBannerFragment(151));
		listFragments.add(new ArticleNoBannerFragment(152));
		listFragments.add(new ArticleNoBannerFragment(153));
		listFragments.add(new ArticleNoBannerFragment(154));
		listFragments.add(new ArticleNoBannerFragment(196));
		listFragments.add(new ArticleNoBannerFragment(197));
		listFragments.add(new ArticleNoBannerFragment(199));
		listFragments.add(new ArticleNoBannerFragment(25));
		myAdapter = new ViewPagerMyAdapter(getChildFragmentManager(), listFragments);

		viewPager.setAdapter(myAdapter);
	}

}
