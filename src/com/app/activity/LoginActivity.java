package com.app.activity;

import android.view.View;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;


/**
 * @author wlcaption@qq.com
 * 2017-4-13 上午11:13:41
 */
public class LoginActivity extends BaseActivity {
	@ViewInject(R.id.title)
    TextView title; 
	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		title.setText("会员登录");
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
