package com.app.activity;

import android.view.View;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-8-26 下午6:15:16
 */
public class UserInfoActivity extends BaseActivity {

	@ViewInject(R.id.barTxt)
	TextView barTxt;
	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_userinfo);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		barTxt.setText("用户信息");
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
