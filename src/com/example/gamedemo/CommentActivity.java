package com.example.gamedemo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gamedemo.callback.DataStringCallBack;
import com.example.gamedemo.utils.HttpUtils;
import com.example.gamedemo.utils.MyGetStringAsyncTask;
import com.example.gamedemo.utils.PathUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends Activity {
	private ImageButton imageButtonBack;
	private EditText editTextComment, editTextCommentName;
	private Button buttonSubmit;
	private static final String TAG = "CommentActivity";
	private LinearLayout layout = null;
	private String id;
	private Handler handler;
	private static final int WHAT0 = 0;
	private HashMap<String, String> map;
	private static final int WHAT1 = 1;

	private static final int WHAT2 = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		initView();
		handleToast();
		setListener();
		function();
	}

	private void handleToast() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case WHAT0:
					Toast.makeText(getApplicationContext(), map.get("username") + "评论成功!", 0).show();
					break;
				case WHAT1:
					Toast.makeText(getApplicationContext(), map.get("username") + "评论失败!", 0).show();
					break;
				case WHAT2:
					Toast.makeText(getApplicationContext(), map.get("username") + "请求次数过多,请5秒后重试!!", 0).show();
					break;

				default:
					break;
				}
			}
		};
	}

	private void setListener() {
		imageButtonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		buttonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String commentContent = editTextComment.getText().toString().trim();
				if (!TextUtils.isEmpty(commentContent)) {
					Toast.makeText(getApplicationContext(), "提交评论中..", 0).show();
					map = new HashMap<String, String>();
					map.put("aid", id);
					map.put("msg", editTextComment.getText().toString().trim());
					String username = editTextCommentName.getText().toString().trim();
					if (!TextUtils.isEmpty(username)) {
						map.put("username", username);
					} else {
						map.put("username", "Anonymous");

					}
					try {
						new Thread(new Runnable() {

							@Override
							public void run() {
								Message message = Message.obtain();
								message.setTarget(handler);
								// TODO Auto-generated method stub
								try {
									String response = HttpUtils
											.httpPost("http://122.226.122.6/sitemap/api.php?id=" + id + "type=2", map);
									Log.d(TAG, "-->post response" + response);
									if (response == null) {
										message.what = WHAT2;
										message.sendToTarget();
									}
									JSONObject jsonObject = new JSONObject(response);
									int code = jsonObject.optInt("code");

									if (1 == code) {
										message.what = WHAT0;
										message.sendToTarget();
										Log.d(TAG, "--> send message success" + message);
										// message.setTarget(handler);
										// message.sendToTarget();
										/*
										 * Toast.makeText(getApplicationContext(
										 * ), map.get("username") + "评论成功!", 0)
										 * .show();
										 */
									} else {
										message.what = WHAT1;

										message.sendToTarget();
										Log.d(TAG, "--> send message success" + message);
										// message.setTarget(handler);
										// message.sendToTarget();

										/*
										 * Toast.makeText(getApplicationContext(
										 * ), map.get("username") + "评论失败!", 0)
										 * .show();
										 */
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Log.d(TAG, "--> error in my Thread!!" + "!!!!!!");
								}
							}
						}).start();
						layout.removeAllViews();
						function();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d(TAG, "-->" + "post error");
					}
				} else {
					Toast.makeText(getApplicationContext(), "Can not be empty,dont kidding me I am serious", 0).show();
				}

				editTextComment.setText("");
				editTextCommentName.setText("");
			}
		});
	}

	private void function() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getString("id");
		new MyGetStringAsyncTask(new DataStringCallBack() {

			@Override
			public void getStringData(String data) {
				Log.d(TAG, "-->解析出的内容" + data);
				try {
					JSONObject jsonObject = new JSONObject(data);
					int code = jsonObject.optInt("code");
					if (1 == code) {
						JSONObject description = jsonObject.getJSONObject("description");
						JSONArray dataJsonArray = description.getJSONArray("data");
						for (int i = 0; i < dataJsonArray.length(); i++) {
							JSONObject optJSONObject = dataJsonArray.optJSONObject(i);
							String username = optJSONObject.optString("username");
							String floor = optJSONObject.optString("floor");
							String msg = optJSONObject.optString("msg");
							Log.d(TAG, "--> kind of info " + username + "    " + floor + "    " + msg);
							addViewtothisActivity(username, msg, floor, dataJsonArray.length());
						}
					} else {
						Log.d(TAG, "--> json form comment  " + "no data ");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).execute(PathUtils.getComment(id, 1 + ""));

	}

	private void addViewtothisActivity(String username, String msg, String floor, int length) {
		View view = LayoutInflater.from(this).inflate(R.layout.comment_users, null);
		TextView textViewUsername = (TextView) view.findViewById(R.id.textView_username);
		TextView textViewfloor = (TextView) view.findViewById(R.id.textView_floor);
		TextView textViewmsg = (TextView) view.findViewById(R.id.textView_comment_content);
		textViewfloor.setText("第" + floor + "层");
		textViewmsg.setText("评论内容" + msg);
		textViewUsername.setText("用户名: " + username);
		layout.addView(view);

	}

	private void initView() {
		editTextCommentName = (EditText) findViewById(R.id.textView_recivecomment_username);
		layout = (LinearLayout) findViewById(R.id.container_comment);
		imageButtonBack = (ImageButton) findViewById(R.id.imageButton_back_back);
		editTextComment = (EditText) findViewById(R.id.textView_recivecomment);
		buttonSubmit = (Button) findViewById(R.id.button_comment_submit);

	}

}
