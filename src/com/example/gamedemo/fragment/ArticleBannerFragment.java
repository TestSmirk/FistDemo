package com.example.gamedemo.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gamedemo.DetailActivity;
import com.example.gamedemo.R;
import com.example.gamedemo.adapter.MyBannerAdapter;
import com.example.gamedemo.adapter.MyPullListviewAdapter;
import com.example.gamedemo.bean.Article;
import com.example.gamedemo.callback.DataStringCallBack;
import com.example.gamedemo.utils.MyGetStringAsyncTask;
import com.example.gamedemo.utils.PathUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class ArticleBannerFragment extends BaseFragment {

	//
	/*
	 * 1 文章首页 2 新闻 文章特定 typeid:
	 * 
	 * 151 游戏杂谈 152 硬件信息 153 游戏前瞻 154 游戏评测 196 原创精品 197 游戏盘点 199 时事焦点 25 攻略中心
	 */
	private RadioGroup radioGroup;
	private MyViewPager viewPager;
	private PullToRefreshListView listRefreshListView;
	private ListView listview;
	private List<ImageView> listBanner = new ArrayList<ImageView>();
	private RadioButton[] arrRadioButton = null;
	private List<Article> alllist = new ArrayList<Article>();
	private LayoutInflater inflater;
	private MyBannerAdapter adapterBanner = null;
	private MyPullListviewAdapter adpter;
	private Article article;
	private ArrayList<Article> list;

	private MyGetStringAsyncTask task;

	private int num = 1;
	private final static String TAG = "ArticleBannerFragment";

	public ArticleBannerFragment() {
		Log.d(TAG, "-->" + "start");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_articlebanner, container, false);
		initView(view);
		getData(1);
		getVewPagerView(view);
		setListener();
		return view;
	}

	private void setListener() {
		listRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

			// 设置下拉的监听回调
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				getData(num);

			}

			// 上啦的监听回调
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				num++;
				getData(num);

			}
		});
		listRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				Bundle bundle = new Bundle();
				//Toast.makeText(getActivity(), "--> article" + alllist.get(position - 2), 0).show();

				bundle.putString("id", alllist.get(position - 2).getId());
				bundle.putString("typeid", alllist.get(position - 2).getTypeid());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}

	private void getVewPagerView(View view) {
		viewPager = (MyViewPager) view.findViewById(R.id.viewpager_head);
		radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_head);
		TypedArray img = getResources().obtainTypedArray(R.array.arrImg);

		for (int i = 0; i < img.length(); i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setImageDrawable(img.getDrawable(i));
			Log.d(TAG, "-->" + imageView);
			if (listBanner.size() < 3) {
				listBanner.add(imageView);
			}
		}
		initDots(listBanner);
		MyBannerAdapter myBannerAdapter = new MyBannerAdapter(listBanner);
		viewPager.setAdapter(myBannerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				arrRadioButton[arg0].setChecked(true);
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

	private void initDots(List<ImageView> listBanner2) {
		arrRadioButton = new RadioButton[listBanner2.size()];
		for (int i = 0; i < arrRadioButton.length; i++) {
			RadioButton radioButton = new RadioButton(getActivity());
			radioButton.setButtonDrawable(R.drawable.dot_1);
			radioButton.setPadding(0, 0, 10, 0);
			radioGroup.addView(radioButton);
			arrRadioButton[i] = radioButton;
		}
		arrRadioButton[0].setChecked(true);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				for (int i = 0; i < arrRadioButton.length; i++) {
					if (arrRadioButton[i].getId() == checkedId) {
						viewPager.setCurrentItem(i);
					}
				}
			}
		});
	}

	// 获取数据
	private void getData(int num) {
		// TODO Auto-generated method stub
		task = new MyGetStringAsyncTask(new DataStringCallBack() {

			@Override
			public void getStringData(String data) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(data)) {
					// Log.i(">>>>>>>>", ">>>>>>>" + data);
					try {
						JSONObject jsonObject = new JSONObject(data);
						JSONObject jsonData = jsonObject.getJSONObject("data");
						ArrayList<Article> list = new ArrayList<Article>();
						for (int i = 0; i < 20; i++) {
							JSONObject articleObject = jsonData.getJSONObject("" + i);
							article = new Article();
							article.getJSONObject(articleObject);
							list.add(article);
						}
						alllist.addAll(list);
						adpter.notifyDataSetChanged();
						listRefreshListView.onRefreshComplete();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

				}
			}
		});
		// 执行异步任务
		task.execute(PathUtils.getArticlePath(20, 1, num));

	}

	private void initView(View view) {
		Log.d(TAG, "-->" + view);
		listRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pulltofreshlistview_fragmentarticlebanner);
		Log.d(TAG, "-->" + listRefreshListView);
		listRefreshListView.setMode(Mode.BOTH);
		adpter = new MyPullListviewAdapter(alllist, getActivity());
		// 3.添加数据和适配器
		ListView listviewHead = listRefreshListView.getRefreshableView();
		View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.listview_headview, null);
		listviewHead.addHeaderView(inflate);
		listRefreshListView.setAdapter(adpter);

		// 4.设置监听事件，做分页加载数据
	}

}
