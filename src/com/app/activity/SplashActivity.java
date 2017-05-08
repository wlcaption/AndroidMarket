package com.app.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.app.base.BaseActivity;
import com.app.base.Constant;
import com.app.utils.SpUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;



/**
 * @author wlcaption@qq.com
 * 2017-4-13 上午10:25:46
 */
public class SplashActivity extends BaseActivity {

	@ViewInject(R.id.imageview1)
	ImageView imageview1;
	Timer timer = new Timer();
	private int flag = 0;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (flag) {
			case 1:
				boolean isFirstRun = SpUtil.getBoolean(SplashActivity.this,
						Constant.IS_FIRST_RUN);
				if (!isFirstRun) {
					startActivity(GuideActivity.class);
					SpUtil.putBoolean(SplashActivity.this, Constant.IS_FIRST_RUN, true);
					SplashActivity.this.finish();
				}
				break;
			case 2:
				//imageview1.setImageResource(R.drawable.ad);
//				SpotManager.getInstance(SplashActivity.this).showSplashSpotAds(SplashActivity.this, MainActivity.class);
				break;
			case 3:
				gotoActivity();
				break;
			}

		}
	};

	public void gotoActivity() {
		boolean isFirstRun = SpUtil.getBoolean(SplashActivity.this,
				Constant.IS_FIRST_RUN);
		if (!isFirstRun) {
			startActivity(GuideActivity.class);
			SpUtil.putBoolean(SplashActivity.this, Constant.IS_FIRST_RUN, true);
		} else {
			startActivity(MainActivity.class);
		}
		SplashActivity.this.finish();
	}

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				flag++;
				Log.e("flag", flag + "");
				Message mesasge = new Message();
				mesasge.what = flag;
				handler.sendMessage(mesasge);
			}
		}, 600, 1000);

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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		timer.cancel();
		super.onDestroy();
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 10045) {
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
