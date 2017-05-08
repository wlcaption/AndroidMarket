package com.app.base;

import com.lidroid.xutils.ViewUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;


/**
 * @author wlcaption@qq.com
 * 2017-4-13 上午10:32:50
 */
public abstract class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setMainLayout();
		ViewUtils.inject(this);
		initBeforeData();
		initEvents();
		initAfterData();

	}

	/**
	 * 初始化布局
	 */
	protected abstract void setMainLayout();

	/**
	 * 初始化先前数据
	 */
	protected abstract void initBeforeData();

	/**
	 * 初始化事件
	 */
	protected abstract void initEvents();

	/**
	 * 初始化之后数据
	 */
	protected abstract void initAfterData();

	/**
	 * 含有Bundle通过Action跳转界面 *
	 */
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过Class跳转界面 *
	 */
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/**
	 * 含有Bundle通过Class跳转界面 *
	 */
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtra("bundle", bundle);
		}
		startActivity(intent);
	}

	/**
	 * 短暂显示Toast提示(来自String) *
	 */
	public void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短暂显示Toast提示(来自res) *
	 */
	protected void showShortToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 初始化布局
	 */
	public abstract void back(View view);

}
