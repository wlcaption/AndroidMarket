package com.app.activity;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import com.app.adapter.DownAdapter;
import com.app.base.BaseActivity;
import com.app.component.ErrorHintView;
import com.app.component.ErrorHintView.OperateListener;
import com.app.component.NumberProgressBar;
import com.app.entity.GameDetailItem;
import com.app.utils.AsyncHttpUtil;
import com.app.utils.JsonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shop.app.R;

/**
 * 类说明 下载
 * 
 * @author wangsheng
 * @date 2015-8-21 下午11:30:42
 */
public class Download1Activity extends BaseActivity {

	@ViewInject(R.id.down_list)
	ListView mListView;
	DownAdapter adapter;
	public static int VIEW_LIST = 1;
	/** 显示断网 **/
	public static int VIEW_WIFIFAILUER = 2;
	/** 显示加载数据失败 **/
	public static int VIEW_LOADFAILURE = 3;
	public static int VIEW_LOADING = 4;
	private static final String URL_PATH = "http://www.gamept.cn/yx_zt.php?ztid=";
	private List<GameDetailItem> mList = new ArrayList<GameDetailItem>();
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				for (int i = 0; i < mList.size(); i++) {
					GameDetailItem item = mList.get(i);
					item.setUrl(urls[i]);
				}
				adapter.appendToList(mList);
				mHandler.post(myRunnable);
				break;
			}
		}
	};
	private String[] urls = {
			"http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk",
			"http://dl.m.cc.youku.com/android/phone/Youku_Phone_youkuweb.apk",
			"http://dldir1.qq.com/qqmi/TencentVideo_V4.1.0.8897_51.apk",
			"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V10.6.0.620_android_pf145_(Build150721222435).apk",
			"http://msoftdl.360.cn/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk",
			"http://www.51job.com/client/51job_51JOB_1_AND2.9.3.apk",
			"http://upgrade.m.tv.sohu.com/channels/hdv/5.0.0/SohuTV_5.0.0_47_201506112011.apk",
			"http://dldir1.qq.com/qqcontacts/100001_phonebook_4.0.0_3148.apk",
			"http://download.alicdn.com/wireless/taobao4android/latest/702757.apk",
			"http://download.3g.fang.com/soufun_android_30001_7.9.0.apk" };
	@ViewInject(R.id.hintView)
	private ErrorHintView mErrorHintView;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				View view = mListView.getChildAt(msg.arg1);
				if (view != null) {
					NumberProgressBar numPbar = (NumberProgressBar) view
							.findViewById(R.id.progress);
					int progress = numPbar.getProgress() + 5;
					if (progress > 100) {
						progress = 0;
					}
					numPbar.setProgress(progress);
				}
				break;
			}
		}
	};

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_downlist);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		adapter = new DownAdapter(this);
		mListView.setAdapter(adapter);
		showLoading(VIEW_LOADING);
		loadData(34);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub

	}

	Runnable myRunnable = new Runnable() {
		public void run() {
			Message msg = mHandler.obtainMessage();
			msg.what = 0;
			mHandler.sendMessage(msg);
			mHandler.postDelayed(this, 1000);
		}
	};

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
						showLoading(VIEW_LIST);
						List<GameDetailItem> list = JsonUtils.getInstance(
								GameDetailItem.class, array);
						if (list != null && list.size() > 0) {
							mList.addAll(list);
							handler.sendEmptyMessage(0);
						}
					} else {
						showLoading(VIEW_LOADFAILURE);
					}
				} catch (Exception e) {
					e.printStackTrace();
					showLoading(VIEW_LOADFAILURE);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				showLoading(VIEW_WIFIFAILUER);
			}
		});
	}

	/**
	 * 等待
	 * 
	 * @param i
	 */
	private void showLoading(int i) {
		mErrorHintView.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);
		switch (i) {
		case 1:
			mErrorHintView.hideLoading();
			mListView.setVisibility(View.VISIBLE);
			break;
		case 2:
			mErrorHintView.hideLoading();
			mErrorHintView.netError(new OperateListener() {
				@Override
				public void operate() {
					showLoading(VIEW_LOADING);
					loadData(35);
				}
			});
			break;
		case 3:
			mErrorHintView.hideLoading();
			mErrorHintView.loadFailure(new OperateListener() {
				@Override
				public void operate() {
					showLoading(VIEW_LOADING);
					loadData(35);
				}
			});
			break;
		case 4:
			mErrorHintView.loadingData();
			break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (myRunnable != null)
			mHandler.removeCallbacks(myRunnable);
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		
	}
}
