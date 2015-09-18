package com.example.gamedemo.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gamedemo.DetailActivity;
import com.example.gamedemo.R;
import com.example.gamedemo.adapter.MyPullListviewAdapter;
import com.example.gamedemo.bean.Article;
import com.example.gamedemo.callback.DataStringCallBack;
import com.example.gamedemo.utils.MyGetStringAsyncTask;
import com.example.gamedemo.utils.PathUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ArticleNoBannerFragment extends BaseFragment {
	private ListView listView;
	private MyPullListviewAdapter adapter;
	private List<Article> totalList = new ArrayList<Article>();
	private int typeId;
	private int pageNum = 1;
	private Article article;
	private MyGetStringAsyncTask task;
	private boolean isBottom;

	public ArticleNoBannerFragment(int typeId) {
		this.typeId = typeId;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_articlenobanner, container, false);
		initView(view);
		initData();
		setListener();

		return view;
	}

	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(getActivity(), "-->i" + totalList.get(position), 0).show();
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("id", totalList.get(position).getId());
				bundle.putString("typeid", totalList.get(position).getTypeid());
				intent.setClass(getActivity(), DetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (isBottom) {
					pageNum++;
					initData();
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
			}
		});
	}

	private void initData() {
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
						totalList.addAll(list);
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

				}
			}
		});
		// 执行异步任务
		task.execute(PathUtils.getArticlePath(20, typeId, pageNum));

		adapter = new MyPullListviewAdapter(totalList, getActivity());
		listView.setAdapter(adapter);

	}

	private void initView(View view) {
		listView = (ListView) view.findViewById(R.id.listView_nobanner);

	}
}
