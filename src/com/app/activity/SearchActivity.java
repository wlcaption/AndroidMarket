package com.app.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.adapter.GameAdapter;
import com.app.adapter.SearchAdapter;
import com.app.base.BaseActivity;
import com.app.component.ErrorHintView;
import com.app.component.KeywordsFlow;
import com.app.component.ErrorHintView.OperateListener;
import com.app.entity.GameDetailItem;
import com.app.utils.AsyncHttpUtil;
import com.app.utils.JsonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shop.app.R;

/**
 * 类说明 查询界面
 * 
 * @author wangsheng
 * @date 2015-8-21 下午6:17:10
 */
public class SearchActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.keywordsflow)
	KeywordsFlow keywordsFlow;
	@ViewInject(R.id.searchBtn)
	Button searchBtn;
	@ViewInject(R.id.searchText)
	EditText searchText;
	@ViewInject(R.id.mListView)
	ListView mListView;//匹配结果
	@ViewInject(R.id.hintView)
	ErrorHintView mErrorHintView;
	SearchAdapter searchAdapter;
	@ViewInject(R.id.searchClearBtn)
	ImageButton searchClearBtn;
	@ViewInject(R.id.searchcon)
	RelativeLayout searchcon;
	@ViewInject(R.id.dataList)
	ListView dataList;
	public static int VIEW_LIST = 1;
	/** 显示断网 **/
	public static int VIEW_WIFIFAILUER = 2;
	/** 显示加载数据失败 **/
	public static int VIEW_LOADFAILURE = 3;
	public static int VIEW_LOADING = 4;
	GameAdapter gameAdapter;
	private static final String URL_PATH = "http://www.gamept.cn/yx_zt.php?ztid=";
	private List<GameDetailItem> mList = new ArrayList<GameDetailItem>();
	private static final int FEEDKEY_START = 1;
	private int STATE = 1;
	private static String[] keywords = new String[] { "花千骨", "刀塔传奇", "卧虎藏龙",
			"自由之战", "战舰少女", "全民斗西游", "世界2", "超神战记", "梦幻神域", "热血街霸", "龙血战神",
			"暗黑黎明", "龙纹三国", "剑魂之刃", "神魔", "乱斗西游", "黑暗光年", " 秦时明月", " 无双剑姬",
			"征途口袋版", "COS大乱斗", "坦克大战", "天下HD", "秦时明月2", "太极熊猫", "影之刃" };
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FEEDKEY_START:
				keywordsFlow.rubKeywords();
				feedKeywordsFlow(keywordsFlow, keywords);
				keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
				sendEmptyMessageDelayed(FEEDKEY_START, 5000);
				break;
			case 0:
				gameAdapter.appendToList(mList);
				break;
			}
		};
	};
	Runnable eChanged = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<Object>data=new ArrayList<Object>();
			for(int i=0;i<10;i++)
			{
				data.add(new Object());
			}
			searchcon.setVisibility(View.GONE);//那些飞入字体view隐藏
			searchAdapter.appendToList(data);
			mListView.setVisibility(View.VISIBLE);
		}
	};

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_search);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		keywordsFlow.setDuration(1000l);
		keywordsFlow.setOnItemClickListener(this);
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		handler.sendEmptyMessageDelayed(FEEDKEY_START, 5000);
		searchAdapter=new SearchAdapter(this);
		mListView.setAdapter(searchAdapter);
		gameAdapter=new GameAdapter(this);
		dataList.setAdapter(gameAdapter);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		//消除
		searchClearBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				searchText.setText("");
				searchClearBtn.setVisibility(View.GONE);
				mListView.setVisibility(View.GONE);
				dataList.setVisibility(View.GONE);
				searchAdapter.clear();
				searchcon.setVisibility(View.VISIBLE);
				gameAdapter.clear();
			}
		});
		//搜索按钮点击
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(TextUtils.isEmpty(searchText.getText()))
				{
					showShortToast("请输入搜索关键字");
					return;
				}
				gameAdapter.clear();//原先结果清空
				searchAdapter.clear();
				searchClearBtn.setVisibility(View.VISIBLE);// 当文本框为空时，则叉叉消失
				//开启线程去后台获取数据
				getData(searchText.getText().toString());
			}
		});
		//
		searchText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() == 0) {
					searchcon.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.GONE);
					searchAdapter.clear();
					searchClearBtn.setVisibility(View.GONE);
					dataList.setVisibility(View.GONE);
					searchcon.setVisibility(View.VISIBLE);
					gameAdapter.clear();
				} else {
					searchClearBtn.setVisibility(View.VISIBLE);// 当文本框不为空时，出现叉叉
					mListView.setVisibility(View.VISIBLE);
					handler.post(eChanged);
				}
			}
		});
		//查询结果item点击
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				searchText.setText("全民斗西游");
				searchText.setSelection(searchText.getText().length());
				searchAdapter.clear();
				gameAdapter.clear();//原先结果清空
				searchClearBtn.setVisibility(View.VISIBLE);// 当文本框为空时，则叉叉消失
				//开启线程去后台获取数据
				getData("全民斗西游");
			}
		});
		//
		dataList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putSerializable("data", gameAdapter.getItem(arg2));
				startActivity(GameDetailActivity.class, b);
			}
		});
	}
    //开启线程去后台获取数据
	protected void getData(String keyWord) {
		// TODO Auto-generated method stub
		dataList.setVisibility(View.VISIBLE);
		searchcon.setVisibility(View.GONE);//那些飞入字体view隐藏
		mListView.setVisibility(View.GONE);
		showLoading(VIEW_LOADING);
		loadData(35);
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
						showShortToast("获取数据失败");
					}
				} catch (Exception e) {
					e.printStackTrace();
					showLoading(VIEW_LOADFAILURE);
					showShortToast("获取数据异常");
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				showShortToast("获取数据失败");
				showLoading(VIEW_WIFIFAILUER);
			}
		});
	}
	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		if (v instanceof TextView) {
			String keyword = ((TextView) v).getText().toString().trim();
			searchText.setText(keyword);
			searchText.setSelection(keyword.length());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeMessages(FEEDKEY_START);
		STATE = 0;
	}

	@Override
	protected void onStop() {
		super.onStop();
		handler.removeMessages(FEEDKEY_START);
		STATE = 0;
	}

	@Override
	public void onPause() {
		super.onPause();
		handler.removeMessages(FEEDKEY_START);
		STATE = 0;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (STATE == 0) {
			keywordsFlow.rubKeywords();
			handler.sendEmptyMessageDelayed(FEEDKEY_START, 3000);
		}

	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		this.finish();
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
}
