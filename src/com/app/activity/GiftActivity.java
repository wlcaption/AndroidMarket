/**
 * Program  : ViewPagerActivity.java
 * Author   : qianj
 * Create   : 2012-5-31 下午2:02:15
 */
package com.app.activity;

import java.util.ArrayList;
import java.util.List;
import com.shop.app.R;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

/**
 * 
 * @author qianj
 * @version 1.0.0
 * @2012-5-31 下午2:02:15
 */
@SuppressWarnings("deprecation")
public class GiftActivity extends ActivityGroup {

	List<View> listViews;
	Context context = null;
	LocalActivityManager manager = null;
	TabHost tabHost = null;
	private ViewPager pager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		context = GiftActivity.this;
		pager = (ViewPager) findViewById(R.id.viewpager);

		// 定放一个放view的list，用于存放viewPager用到的view
		listViews = new ArrayList<View>();
		manager = this.getLocalActivityManager();
		manager.dispatchCreate(savedInstanceState);

		Intent i1 = new Intent(context, GiftTuijianActivity.class);
		listViews.add(getView("A", i1));
		Intent i2 = new Intent(context, GiftHotActivity.class);
		listViews.add(getView("B", i2));
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup(manager);

		// 这儿主要是自定义一下tabhost中的tab的样式
		RelativeLayout tabIndicator1 = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.layout_tab, null);
		TextView tvTab1 = (TextView) tabIndicator1.findViewById(R.id.tv_title);
		tvTab1.setText("推荐");

		RelativeLayout tabIndicator2 = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.layout_tab, null);
		TextView tvTab2 = (TextView) tabIndicator2.findViewById(R.id.tv_title);
		tvTab2.setText("热门");

		Intent intent = new Intent(context, EmptyActivity.class);
		// 注意这儿Intent中的activity不能是自身
		// 比如“A”对应的是T1Activity，后面intent就new的T3Activity的。
		tabHost.addTab(tabHost.newTabSpec("A").setIndicator(tabIndicator1)
				.setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("B").setIndicator(tabIndicator2)
				.setContent(intent));
		pager.setAdapter(new MyPageAdapter(listViews));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// 当viewPager发生改变时，同时改变tabhost上面的currentTab
				tabHost.setCurrentTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// 点击tabhost中的tab时，要切换下面的viewPager
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				if ("A".equals(tabId)) {
					pager.setCurrentItem(0);
				}
				if ("B".equals(tabId)) {
					pager.setCurrentItem(1);
				}
			}
		});

	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	private class MyPageAdapter extends PagerAdapter {

		private List<View> list;

		private MyPageAdapter(List<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(View view, int position, Object arg2) {
			ViewPager pViewPager = ((ViewPager) view);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(View view, int position) {
			ViewPager pViewPager = ((ViewPager) view);
			pViewPager.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}
	
	public void search(View view) {
		startActivity(new Intent(this, SearchActivity.class));
	}

}
