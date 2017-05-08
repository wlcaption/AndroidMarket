package com.app.activity;

import android.view.View;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;

/**
 * 类说明  关于我们
 * 
 * @author wangsheng
 * @date 2015-8-22 下午1:55:15
 */
public class AboutActivity extends BaseActivity {

	@ViewInject(R.id.aboutUs)
	TextView aboutUs;
	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_about);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		aboutUs.setText(getResources().getString(R.string.aboutUs));
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		
	}

}
