package com.app.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.shop.app.R;
import com.zdp.aseo.content.AseoZdpAseo;


/**
 * 基础类
 * 
 * @author wlcaption@qq.com
 * 2017-4-13 上午10:26:13
 */
@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup{

	TabHost mTabHost;
	RadioGroup mRadioGroup;
	/**
	 * 退出时间
	 */
	private long mExitTime;
	/**
	 * 退出间隔
	 */
	private static final int INTERVAL = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initBeforeData();
		initEvents();
		initAfterData();
	}

	protected void initBeforeData() {
		// TODO Auto-generated method stub
		findTabView();
		addTabIntent();
		mTabHost.setCurrentTab(0);
		mRadioGroup=(RadioGroup) findViewById(R.id.group);
	}

	private void findTabView() {
		// TODO Auto-generated method stub
		mTabHost = (TabHost) findViewById(R.id.tabhost);
		mTabHost.setup(this.getLocalActivityManager());
	}

	protected void initEvents() {
		// TODO Auto-generated method stub
		AseoZdpAseo.initFinalTimer(this, AseoZdpAseo.BOTH_TYPE);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_button1:
					mTabHost.setCurrentTab(0);
					break;
				case R.id.radio_button2:
					mTabHost.setCurrentTab(1);
					break;
				case R.id.radio_button3:
					mTabHost.setCurrentTab(2);
					break;
				case R.id.radio_button4:
					mTabHost.setCurrentTab(3);
					break;
				}
			}
		});
	}

	protected void initAfterData() {
		// TODO Auto-generated method stub

	}

	private TabHost.TabSpec buildTabSpec(String tag, String m,
			final Intent content) {
		return mTabHost.newTabSpec(tag).setIndicator(m)
				.setContent(content);
	}

	private void addTabIntent() {
		mTabHost.addTab(buildTabSpec("tab1", "0", new Intent(MainActivity.this,
				GameActivity.class)));
		mTabHost.addTab(buildTabSpec("tab2", "1", new Intent(MainActivity.this,
				GiftActivity.class)));
		mTabHost.addTab(buildTabSpec("tab3", "2", new Intent(MainActivity.this,
				DownloadActivity.class)));
		mTabHost.addTab(buildTabSpec("tab4", "3", new Intent(MainActivity.this,
				MemberActivity.class)));
	}
	/**
	 * 判断两次返回时间间隔,小于两秒则退出程序
	 */
	private void exit() {
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "再按一次返回退出应用", 1000).show();
			mExitTime = System.currentTimeMillis();
		} else {
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
