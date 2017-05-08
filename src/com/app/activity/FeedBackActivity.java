package com.app.activity;

import android.view.View;

import com.app.base.BaseActivity;
import com.shop.app.R;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-8-22 下午1:55:15
 */
public class FeedBackActivity extends BaseActivity {

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_feedback);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
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
		this.finish();
	}

}
