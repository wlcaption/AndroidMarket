package com.app.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.app.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;
import com.squareup.picasso.Picasso;

/**
 * 类说明 查看聊天图片信息（本地的和别人发来的图片）
 * 
 * @author wangsheng
 * @date 2015-7-31 下午4:19:02
 */
public class BigPicActivity extends BaseActivity {

	String imgPath;
	@ViewInject(R.id.pic)
	ImageView pic;
	@ViewInject(R.id.loading_progress)
	private RelativeLayout loading_progress;
	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_bigpic);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		imgPath=this.getIntent().getBundleExtra("bundle").getString("data");
		if (!TextUtils.isEmpty(imgPath)) {
			Picasso.with(this)
					.load(imgPath)
					.placeholder(R.color.ECECEC).error(R.color.ECECEC)
					.into(pic);
		}
		pic.setVisibility(View.VISIBLE);
		loading_progress.setVisibility(View.GONE);
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
