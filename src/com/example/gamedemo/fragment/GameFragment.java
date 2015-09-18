package com.example.gamedemo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gamedemo.GameDetailActivity;
import com.example.gamedemo.R;
import com.example.gamedemo.adapter.MyGridViewAdapter;
import com.example.gamedemo.bean.Game;
import com.example.gamedemo.callback.DataStringCallBack;
import com.example.gamedemo.utils.HttpUtils;
import com.example.gamedemo.utils.MyGetStringAsyncTask;
import com.example.gamedemo.utils.PathUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

public class GameFragment extends BaseFragment {
	private static final String TAG = "FragmentGame111111";
	private Spinner spinner;
	private GridView gridView;
	private String[] gameNames;
	private String gamePicUrl;
	private int row = 30;
	private int typeid = 179;
	private Bitmap bitmap = null;
	private List<Map<String, Object>> listAdapter = new ArrayList<Map<String, Object>>();
	private Game game;
	private List<Game> listGame = new ArrayList<Game>();
	private int pagenum = 1;
	private String title;
	private MyGridViewAdapter adapter;
	private boolean isBottomGrideView;

	public GameFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_game, container, false);
		initView(view);
		initData();
		function();
		setListener();
		return view;
	}

	private void setListener() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Toast.makeText(getActivity(), position + "", 0).show();
			}
		});
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					typeid = 179;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();
					break;
				case 1:
					typeid = 181;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 2:
					typeid = 182;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 3:
					typeid = 183;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 4:
					typeid = 184;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 5:
					typeid = 185;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 6:
					typeid = 186;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 7:
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();
					typeid = 187;

					break;
				case 8:
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();
					typeid = 188;

					break;
				case 9:
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 10:
					typeid = 190;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 11:
					typeid = 191;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;
				case 12:
					typeid = 192;
					// Toast.makeText(getActivity(), "click" + position,
					// 0).show();

					break;

				default:
					break;
				}
				listGame.clear();
				function();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), GameDetailActivity.class);
				intent.putExtra("position", position);
				intent.putExtra("info", listGame.get(position));
				startActivity(intent);
			}
		});
		gridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (isBottomGrideView) {
					Toast.makeText(getActivity(), "loading..", 0).show();
					pagenum++;
					function();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				isBottomGrideView = firstVisibleItem + visibleItemCount == totalItemCount;
			}
		});

	}

	private void function() {

		// Toast.makeText(getActivity(), "" + PathUtils.getGamePath(row,
		// typeid, pagenum), 0).show();
		try {
			new MyGetStringAsyncTask(new DataStringCallBack() {

				@Override
				public void getStringData(String data) {
					try {
						String substring = data.substring(data.indexOf("{") - 1);
						// String replaceAll =
						// data.replaceAll(splitString, "");

						Log.d(TAG, "-->substring  " + substring);
						// Log.d(TAG, "-->data " + data);
						JSONObject jsonObject = new JSONObject(substring);
						JSONObject jsonObjectData = jsonObject.getJSONObject("data");
						for (int i = 0; i < row; i++) {
							JSONObject gameObject = jsonObjectData.getJSONObject("" + i);
							game = new Game();
							game.getJSONObject(gameObject);
							listGame.add(game);
							inflaterLayout(listGame, i);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.d(TAG, "-->" + "JSON fail1");
						e.printStackTrace();
					}

				}

			}).execute(PathUtils.getGamePath(row, typeid, pagenum));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Log.d(TAG, "-->" + "JSON fai2l");
			e1.printStackTrace();
		}

		///////////////////////
		/*
		 * try { // String httpGetGameContent = //
		 * HttpUtils.httpGet(PathUtils.getGamePath(row, typeid, // pagenum));
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		////////////////////////

	}

	private void inflaterLayout(List<Game> listGame, int position) {
		/*
		 * View view =
		 * LayoutInflater.from(getActivity()).inflate(R.layout.game_item, null);
		 * TextView textViewGameName = (TextView)
		 * view.findViewById(R.id.textView_game_name); ImageView
		 * imageViewGamePic = (ImageView)
		 * view.findViewById(R.id.imageView_game_pic);
		 * textViewGameName.setText(listGame.get(position).getShorttitle().trim(
		 * )); // imageViewGamePic.setImageBitmap(BitMapUtils.); gamePicUrl =
		 * "http://122.226.122.6" + listGame.get(position).getLitpic();
		 */

		/*
		 * SimpleAdapter adapter = new SimpleAdapter(getActivity(), listAdapter,
		 * R.layout.game_item, new String[] { "name", "bitmap" }, new int[] {
		 * R.id.textView_game_name, R.id.imageView_game_pic });
		 */
		// gridView.setAdapter(adapter);
		if (adapter == null) {
			adapter = new MyGridViewAdapter(listGame, getActivity());
			gridView.setAdapter(adapter);

		}
		adapter.notifyDataSetChanged();

	}

	private void initData() {
		gameNames = getResources().getStringArray(R.array.spinnerName);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
				gameNames);

		spinner.setAdapter(adapter);
		for (int i = 0; i < listGame.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", listGame.get(i).getShorttitle());
			Bitmap bitmapInMap = getBitmapFormUrl();
			if (bitmapInMap != null) {
				map.put("bitmap", bitmapInMap);
			} else {
				Log.d(TAG, "-_>" + "bitmap is null all the time");
			}
			listAdapter.add(map);
		}

	}

	private Bitmap getBitmapFormUrl() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.obj != null) {

					bitmap = (Bitmap) msg.obj;
				} else {
					Log.d(TAG, "bitmap null");
				}
			}
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d(TAG, "-->" + "Thread start !!!!!!");
				Message message = Message.obtain();
				message.setTarget(handler);
				message.obj = HttpUtils.getBitmapFormUrl(gamePicUrl);
				message.sendToTarget();
				Log.d(TAG, "-->" + "Thread stop !!!!!!");
			}
		}).start();
		return bitmap;

	}

	private void initView(View view) {
		spinner = (Spinner) view.findViewById(R.id.spinner_search);
		gridView = (GridView) view.findViewById(R.id.gridView_game);

	}

}
