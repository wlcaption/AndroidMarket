package com.app.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.app.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;

/**
 * 类说明 免责声明
 * 
 * @author wangsheng
 * @date 2015-8-8 下午10:19:57
 */
public class DeclarActivity extends BaseActivity {

	@ViewInject(R.id.content)
	TextView content;
	@ViewInject(R.id.barTxt)
	TextView barTxt;
	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_declar);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
         String con=getFromAssets("xieyi.txt");
         content.setText( Html.fromHtml(con));
         barTxt.setText("注册用户协议");
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub

	}

	public String getFromAssets(String fileName) {
		String Result = "";
		try {
			InputStreamReader inputReader = new InputStreamReader(
					getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			
			while ((line = bufReader.readLine()) != null)
				Result += line;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result;
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		this.finish();
	}
}
