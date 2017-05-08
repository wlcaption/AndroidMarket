package com.app.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.adapter.AttentionAdapter;
import com.app.base.BaseActivity;
import com.app.component.ErrorHintView;
import com.app.component.ErrorHintView.OperateListener;
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
public class Download2Activity extends BaseActivity {

	@ViewInject(R.id.down_list)
	ListView mListView;
	AttentionAdapter adapter;
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
				adapter.appendToList(mList);
				break;
			}
		}
	};
	@ViewInject(R.id.hintView)
	private ErrorHintView mErrorHintView;
	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_downlist);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		adapter = new AttentionAdapter(this);
		/*
		 * List<Object> data=new ArrayList<Object>(); data.add(new Object());
		 * data.add(new Object()); data.add(new Object()); data.add(new
		 * Object()); data.add(new Object()); data.add(new Object());
		 * data.add(new Object()); data.add(new Object()); data.add(new
		 * Object()); adapter.appendToList(data);
		 */
		mListView.setAdapter(adapter);
		showLoading(VIEW_LOADING);
		loadData(31);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle b=new Bundle();
				b.putSerializable("data", adapter.getItem(arg2));
				startActivity(GameDetailActivity.class,b);
			}
		});
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub

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
	public void back(View view) {
		// TODO Auto-generated method stub
		
	}
}
