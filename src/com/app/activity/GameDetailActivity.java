package com.app.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.adapter.GuessLikeAdapter;
import com.app.adapter.PicAdapter;
import com.app.base.BaseActivity;
import com.app.base.MyApplication;
import com.app.component.NumberProgressBar;
import com.app.entity.GameDetailItem;
import com.app.utils.AsyncHttpUtil;
import com.app.utils.JsonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shop.app.R;
import com.squareup.picasso.Picasso;

/**
 * 类说明 游戏详情界面
 * 
 * @author wangsheng
 * @date 2015-8-21 下午6:17:34
 */
public class GameDetailActivity extends BaseActivity {

	@ViewInject(R.id.game_pic_gridview)
	GridView game_pic_gridview;
	PicAdapter adapter;
	@ViewInject(R.id.game_unfole)
	TextView game_unfole;
	@ViewInject(R.id.game_brief)
	TextView game_brief;
	@ViewInject(R.id.game_about_gridview)
	GridView game_about_gridview;
	GuessLikeAdapter guessLikeAdapter;
	private boolean hasMesure = false;
	int maxLines = 8;// 最大显示行数
	int defaultLines = 3;// 默认显示行数
	private static final String URL_PATH = "http://www.gamept.cn/yx_zt.php?ztid=";
	//
	Handler handler1 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int lines = msg.what;
			// 这里接受到消息，让后更新TextView设置他的maxLine就行了
			game_brief.setMaxLines(lines);
			game_brief.postInvalidate();
		}
	};
	private List<GameDetailItem> mList = new ArrayList<GameDetailItem>();
	private Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				guessLikeAdapter.appendToList(mList);
				break;
			}
		}
	};
	//
	@ViewInject(R.id.game_interesting)
	TextView game_interesting;
	@ViewInject(R.id.game_uninteresting)
	TextView game_uninteresting;
	@ViewInject(R.id.item_layout_imageview)
	ImageView icon;
	@ViewInject(R.id.item_layout_title)
	TextView item_layout_title;
	@ViewInject(R.id.barTxt)
	TextView barTxt;//
	@ViewInject(R.id.img_share)
	ImageView img_share;
	@ViewInject(R.id.detail_msg)
	ImageView detail_msg;
	@ViewInject(R.id.guanzhuBtn)
	Button guanzhuBtn;// 关注
	List<String> urlList;
	@ViewInject(R.id.progress)
	NumberProgressBar numPbar;
	@ViewInject(R.id.btn)
	Button btn;//点击下载
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
					int progress = numPbar.getProgress() + 5;
					if (progress > 100) {
						progress = 0;
					}
					numPbar.setProgress(progress);
				break;
			}
		}
	};
	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_gamedetail);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		barTxt.setText("游戏详情");
		game_brief.setText(getResources().getString(R.string.about));
		// 获取传过来的数据
		GameDetailItem item = (GameDetailItem) this.getIntent()
				.getBundleExtra("bundle").getSerializable("data");
		item_layout_title.setText(item.getTitle());
		if (!TextUtils.isEmpty(item.getIcon())) {
			Picasso.with(this).load("http://www.gamept.cn/" + item.getIcon())
					.placeholder(R.color.ECECEC).error(R.color.ECECEC)
					.into(icon);
		}

		ViewTreeObserver viewTreeObserver = game_brief.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				// 只需要获取一次就可以了
				if (!hasMesure) {
					game_brief.setMaxLines(defaultLines);
					hasMesure = true;
				}
				return true;
			}
		});
		adapter = new PicAdapter(this);
		urlList = new ArrayList<String>();
		urlList.add("http://wy.77l.com/d/file/2013-06/05/a32201b8e60c9b0f1fd147bc0e7f68ad.jpg");
		urlList.add("http://i1.sinaimg.cn/gm/2014/0123/U1782P115DT20140123154105.jpg");
		urlList.add("http://img0.pcgames.com.cn/pcgames/1403/19/3662621_1.jpg");
		urlList.add("http://wy.77l.com/d/file/2014-05/05/fd5f6581fb901fd1fcd5d1f761449f69.jpg");
		adapter.appendToList(urlList);
		game_pic_gridview.setAdapter(adapter);
		guessLikeAdapter = new GuessLikeAdapter(this);
		game_about_gridview.setAdapter(guessLikeAdapter);
		loadData(29);
		//
		String format = getResources().getString(R.string.game_uninteresting);
		String result = String.format(format, 289);
		game_interesting.setText(result);
		game_uninteresting.setText(String.format(
				getResources().getString(R.string.game_uninteresting), 10));
		//
	}
	Runnable myRunnable = new Runnable() {
		public void run() {
			Message msg = mHandler.obtainMessage();
			msg.what = 0;
			mHandler.sendMessage(msg);
			mHandler.postDelayed(this, 1000);
		}
	};
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (myRunnable != null)
			mHandler.removeCallbacks(myRunnable);
	}
	public void loadData(int page) {
		String url = URL_PATH + page;
		AsyncHttpUtil.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int code, Header[] headers,
					byte[] responseBody) {
				try {
					if (responseBody != null && responseBody.length > 0) {
						JSONObject obj = new JSONObject(
								new String(responseBody));
						JSONArray array = obj.getJSONArray("items");
						List<GameDetailItem> list = JsonUtils.getInstance(
								GameDetailItem.class, array);
						if (list != null && list.size() > 0) {
							mList.addAll(list);
							handler2.sendEmptyMessage(0);
						}
					} else {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
			}
		});
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		game_unfole.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (game_unfole.getText().equals("展开")) {
					game_unfole.setText("收起");
					Message message = new Message();
					message.what = maxLines;
					handler1.sendMessage(message);
				} else {
					game_unfole.setText("展开");
					Message message = new Message();
					message.what = defaultLines;
					handler1.sendMessage(message);
				}
			}
		});
		//
		img_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String imgFilePath = "";
				MyApplication.shareMsg(GameDetailActivity.this, "手游助手", "今天心情",
						"一天不看书，智商输给猪", imgFilePath);
			}
		});
		//
		detail_msg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(GameGiftActivity.class);
			}
		});
		//
		guanzhuBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				guanzhuBtn.setText("已关注");
			}
		});
		//
		game_interesting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				game_interesting.setText(String.format(getResources()
						.getString(R.string.game_interesting), 290));
			}
		});
		game_uninteresting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				game_uninteresting.setText(String.format(getResources()
						.getString(R.string.game_uninteresting), 11));
			}
		});
		// 猜你喜欢
		game_about_gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Bundle b = new Bundle();
				b.putSerializable("data",
						guessLikeAdapter.getListData().get(arg2));
				startActivity(GameDetailActivity.class, b);
			}
		});
		// 查看图片
		game_pic_gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putString("data", urlList.get(arg2));
				startActivity(BigPicActivity.class,b);
			}
		});
		//点击下载
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mHandler.post(myRunnable);
			}
		});
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		this.finish();
	}

}
