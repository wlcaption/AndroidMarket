package com.app.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.adapter.GameAdapter;
import com.app.adapter.ViewPagerAdapter;
import com.app.adapter.ViewPagerAdapter.ImageClickListener;
import com.app.base.BaseActivity;
import com.app.component.ErrorHintView;
import com.app.component.ErrorHintView.OperateListener;
import com.app.component.RefreshLayout;
import com.app.component.RefreshLayout.OnLoadListener;
import com.app.entity.GameDetailItem;
import com.app.utils.AsyncHttpUtil;
import com.app.utils.JsonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shop.app.R;

/**
 * 类说明 推荐
 * 
 * @author wangsheng
 * @date 2015-8-21 下午1:52:23
 */
public class Game1Activity extends BaseActivity implements ImageClickListener{

	@ViewInject(R.id.refreshLayout)
	RefreshLayout refreshLayout;
	@ViewInject(R.id.data_list)
	ListView mListView;
	@ViewInject(R.id.hintView)
	private ErrorHintView mErrorHintView;
	public static int VIEW_LIST = 1;
	/** 显示断网 **/
	public static int VIEW_WIFIFAILUER = 2;
	/** 显示加载数据失败 **/
	public static int VIEW_LOADFAILURE = 3;
	public static int VIEW_LOADING = 4;
	private static final String URL_PATH = "http://www.gamept.cn/yx_zt.php?ztid=";
	private List<GameDetailItem> mList = new ArrayList<GameDetailItem>();
	GameAdapter adapter;
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
	ViewPager viewPager;// 广告
	/** 首页轮播图地址 */
	private List<String> imgList = new ArrayList<String>();
	LinearLayout ly_pager_point;// 广告点
	private List<View> dots; // 图片标题正文的那些点
	private int currentItem = 0; // 当前图片的索引号
	private TextView title;
	private String[] titles; // 图片标题
	// 切换当前显示的图片
	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};
	private ScheduledExecutorService scheduledExecutorService;

	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每五秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5,
				TimeUnit.SECONDS);
		super.onStart();
	}

	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imgList.size();
				handler2.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}
	}

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main1);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		addHeadView();
		adapter = new GameAdapter(Game1Activity.this);
		mListView.setAdapter(adapter);
		showLoading(VIEW_LOADING);
		loadData(35);
	}

	private void addHeadView() {
		// TODO Auto-generated method stub
		View view = View.inflate(Game1Activity.this, R.layout.viewpage, null);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		title = (TextView) view.findViewById(R.id.title);
		imgList.add("http://wy.77l.com/d/file/2013-06/05/a32201b8e60c9b0f1fd147bc0e7f68ad.jpg");
		imgList.add("http://wy.77l.com/d/file/2014-05/05/fd5f6581fb901fd1fcd5d1f761449f69.jpg");
		imgList.add("http://images.banma.com/v0/banma-website/ios/bbs/data/attachment/forum/201211/13/163506ltlro7ttk7f7w7bl.jpg");
		imgList.add("http://i1.sinaimg.cn/gm/2014/0123/U1782P115DT20140123154105.jpg");
		imgList.add("http://img0.pcgames.com.cn/pcgames/1403/19/3662621_1.jpg");
		titles = new String[imgList.size()];
		titles[0] = "男神男神男神男神";
		titles[1] = "萝莉萝莉萝莉萝莉";
		titles[2] = "正太正太正太正太";
		titles[3] = "大叔大叔大叔大叔";
		titles[4] = "御姐御姐御姐御姐";
		ly_pager_point = (LinearLayout) view.findViewById(R.id.ly_pager_point);
		initViwepgaerDotView(view);
		ViewPagerAdapter vpAdapter = new ViewPagerAdapter(Game1Activity.this,
				imgList);
		vpAdapter.setImgClickListener(this);
		viewPager.setAdapter(vpAdapter);
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		mListView.addHeaderView(view);
	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}

	private void initViwepgaerDotView(View v) {
		dots = new ArrayList<View>();
		dots.add(v.findViewById(R.id.v_dot0));
		dots.add(v.findViewById(R.id.v_dot1));
		dots.add(v.findViewById(R.id.v_dot2));
		dots.add(v.findViewById(R.id.v_dot3));
		dots.add(v.findViewById(R.id.v_dot4));
	}

	/**
	 * 35 34 33 32 ... 27
	 */
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
							// mList.clear();
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

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		// 设置下拉刷新监听器
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				showShortToast("刷新");
				refreshLayout.postDelayed(new Runnable() {
					@Override
					public void run() {
						// 更新数据
						loadData(34);
						refreshLayout.setRefreshing(false);
					}
				}, 1500);
			}
		});

		// 加载监听器
		refreshLayout.setOnLoadListener(new OnLoadListener() {
			@Override
			public void onLoad() {
				showShortToast("加载更多");
				refreshLayout.postDelayed(new Runnable() {
					@Override
					public void run() {
						loadData(27);
						refreshLayout.setLoading(false);
					}
				}, 1500);
			}
		});
		//
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle b=new Bundle();
				b.putSerializable("data", adapter.getItem(arg2-1));
				startActivity(GameDetailActivity.class,b);
			}
		});
		//
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub

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
	public void imageClick(int position) {
		// TODO Auto-generated method stub
		Bundle b=new Bundle();
		b.putSerializable("data", adapter.getItem(position));
		startActivity(GameDetailActivity.class,b);
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		
	}
}
