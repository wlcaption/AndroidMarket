package com.app.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-8-22 下午6:25:40
 */
public class LogRegActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.sureBtn)
	Button sureBtn;
	@ViewInject(R.id.regBtn)
	Button regBtn;
	@ViewInject(R.id.email)
	EditText email;
	@ViewInject(R.id.title)
    TextView title; 
	@ViewInject(R.id.terms_text)
	TextView terms_text;
	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_logreg);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		title.setText("会员登录");
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		sureBtn.setOnClickListener(this);
		regBtn.setOnClickListener(this);
		terms_text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(DeclarActivity.class);
			}
		});
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sureBtn:
			if (regBtn.getText().equals("会员注册")) {
				//登录
			}else
			{
				//进入登录界面
				startActivity(LoginActivity.class);
			}
			break;
		case R.id.regBtn:

			if (regBtn.getText().equals("会员注册")) {
				email.setVisibility(View.VISIBLE);
				regBtn.setText("会员登录");
				title.setText("会员注册");
			}else
			{
				email.setVisibility(View.GONE);
				regBtn.setText("会员注册");
				title.setText("会员登录");
			}
			break;
		}
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		this.finish();
	}

}
