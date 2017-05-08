package com.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;

/**
 * 类说明 引导页
 * 
 * @author wangsheng
 * @date 2015-8-22 上午12:41:45
 */
public class GuideActivity extends BaseActivity {

	@ViewInject(R.id.viewpager)
	ViewPager viewPager;
	List<View> viewList = new ArrayList<View>();
	ImageView startImg;
	// 底部小点图片
	private ImageView[] dots;
	// 记录当前选中位置
	private int currentIndex;

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_guide);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		View v1 = View.inflate(this, R.layout.what_new_one, null);
		View v2 = View.inflate(this, R.layout.what_new_two, null);
		View v3 = View.inflate(this, R.layout.what_new_three, null);
		View v4 = View.inflate(this, R.layout.what_new_four, null);
		viewList.add(v1);
		viewList.add(v2);
		viewList.add(v3);
		viewList.add(v4);
		viewPager.setAdapter(pagerAdapter);
		// 初始化底部小点
		initDots();
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[viewList.size()];
		// 循环取得小点图片
		for (int i = 0; i < viewList.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				setCurrentDot(arg0);
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

	private void setCurrentDot(int position) {
		if (position < 0 || position > viewList.size() - 1
				|| currentIndex == position) {
			return;
		}
		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);
		currentIndex = position;
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub

	}

	PagerAdapter pagerAdapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public int getCount() {

			return viewList.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewList.get(position));

		}

		@Override
		public int getItemPosition(Object object) {

			return super.getItemPosition(object);
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return null;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position));
			if (position == viewList.size() - 1) {
				startImg = (ImageView) findViewById(R.id.iv_start_weibo);
				startImg.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						startActivity(MainActivity.class);
						GuideActivity.this.finish();
					}
				});
			}
			return viewList.get(position);
		}

	};

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		this.finish();
	}
}
