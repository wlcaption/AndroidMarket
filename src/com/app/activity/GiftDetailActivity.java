package com.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.app.adapter.GiftAdapter;
import com.app.base.BaseActivity;
import com.app.base.MyApplication;
import com.app.entity.GameDetailItem;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;
import com.squareup.picasso.Picasso;

/**
 * 类说明 礼包详情
 * 
 * @author wangsheng
 * @date 2015-8-21 下午7:59:46
 */
public class GiftDetailActivity extends BaseActivity {

	@ViewInject(R.id.rgp_gift_option)
	RadioGroup rgp_gift_option;
	@ViewInject(R.id.gift_content_view)
	LinearLayout gift_content_view;
	@ViewInject(R.id.gift_content_view2)
	LinearLayout gift_content_view2;
	@ViewInject(R.id.gift_content)
	TextView gift_content;
	@ViewInject(R.id.lblist)
	ListView mListView;
	GiftAdapter adapter;
	@ViewInject(R.id.item_layout_imageview)
    ImageView icon;
	@ViewInject(R.id.item_layout_title)
	TextView name;
	@ViewInject(R.id.barTxt)
	TextView barTxt;
	@ViewInject(R.id.bt_share)
	ImageView bt_share;
	@ViewInject(R.id.item_view_operation)
	Button item_view_operation;//关注
	@ViewInject(R.id.btn_status)
	Button btn_status;//下边领取
	@ViewInject(R.id.detail_msg)
	ImageView detail_msg;//游戏图标
	GameDetailItem item;
	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_giftdetail);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		barTxt.setText("礼包详情");
		// 获取传过来的数据
		item = (GameDetailItem) this.getIntent()
				.getBundleExtra("bundle").getSerializable("data");
		name.setText(item.getTitle());
		if (!TextUtils.isEmpty(item.getIcon())) {
			Picasso.with(this)
					.load("http://www.gamept.cn/" + item.getIcon())
					.placeholder(R.color.ECECEC).error(R.color.ECECEC)
					.into(icon );
		}
		
		rgp_gift_option.check(R.id.gift_option_content);
		gift_content.setText("200钻石，万能碎片*10，初级符文*3，金币*200个");
		//
		adapter=new GiftAdapter(this);
		List<GameDetailItem>dataList=new ArrayList<GameDetailItem>();
		GameDetailItem item1=new GameDetailItem();
		item1.setIcon(item.getIcon());
		item1.setTitle(item.getTitle());
		GameDetailItem item2=new GameDetailItem();
		item2.setTitle(item.getTitle());
		item2.setIcon(item.getIcon());
		GameDetailItem item3=new GameDetailItem();
		item3.setTitle(item.getTitle());
		item3.setIcon(item.getIcon());
		dataList.add(item1);
		dataList.add(item2);
		dataList.add(item3);
		adapter.appendToList(dataList);
		mListView.setAdapter(adapter);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		rgp_gift_option.setOnCheckedChangeListener(listen);
		bt_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String imgFilePath ="";
				MyApplication.shareMsg(GiftDetailActivity.this,"手游助手", "今天心情", "一天不看书，智商输给猪",
						imgFilePath);
			}
		});
		item_view_operation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				item_view_operation.setText("已关注");
			}
		});
		btn_status.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				btn_status.setText("已领取");
			}
		});
		detail_msg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle b=new Bundle();
				b.putSerializable("data", item);
				startActivity(GameDetailActivity.class,b);
			}
		});
	}

	private OnCheckedChangeListener listen = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (group.getCheckedRadioButtonId()) {
			case R.id.gift_option_content:
				gift_content_view.setVisibility(View.VISIBLE);
				gift_content_view2.setVisibility(View.GONE);
				break;
			case R.id.gift_option_about:
				gift_content_view.setVisibility(View.GONE);
				gift_content_view2.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}

		}
	};

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
