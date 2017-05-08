package com.app.base;

import com.lidroid.xutils.ViewUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	public Context cx;
	int layout;
	public LayoutInflater inflater;

	public BaseFragment(Context cx, int layout) {
		this.cx = cx;
		this.layout = layout;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		View v = inflater.inflate(layout, null);
		ViewUtils.inject(this, v);
		initBeforeData();
		initEvents();
		initAfterData();
		return v;
	}

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
		intent.setClass(cx, cls);
		if (bundle != null) {
			intent.putExtra("bundle", bundle);
		}
		startActivity(intent);
	}

	/**
	 * 短暂显示Toast提示(来自String) *
	 */
	public void showShortToast(String text) {
		Toast.makeText(cx, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短暂显示Toast提示(来自res) *
	 */
	protected void showShortToast(int resId) {
		Toast.makeText(cx, resId, Toast.LENGTH_SHORT).show();
	}

}
