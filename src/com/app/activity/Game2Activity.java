package com.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.app.adapter.GridViewAdapter;
import com.app.base.BaseActivity;
import com.app.entity.GridViewItem;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-8-21 下午3:35:55
 */
public class Game2Activity extends BaseActivity {
	@ViewInject(R.id.gridView)
	GridView gridView;
	GridViewAdapter adapter;

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_gamecate);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		adapter = new GridViewAdapter(this);
		List<GridViewItem> data = new ArrayList<GridViewItem>();
		data.add(new GridViewItem(R.drawable.icon1, "角色扮演"));
		data.add(new GridViewItem(R.drawable.icon2, "射击枪战"));
		data.add(new GridViewItem(R.drawable.icon3, "策略战棋"));
		data.add(new GridViewItem(R.drawable.icon4, "模拟经营"));
		data.add(new GridViewItem(R.drawable.icon5, "休闲益智"));
		data.add(new GridViewItem(R.drawable.icon6, "体育运动"));
		data.add(new GridViewItem(R.drawable.icon7, "动作闯关"));
		data.add(new GridViewItem(R.drawable.icon8, "赛车竞速"));
		data.add(new GridViewItem(R.drawable.icon9, "棋牌卡牌"));
		data.add(new GridViewItem(R.drawable.icon10, "音乐舞蹈"));
		adapter.appendToList(data);
		gridView.setAdapter(adapter);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle b=new Bundle();
				GridViewItem item=adapter.getListData().get(arg2);
				b.putSerializable("data", item);
				startActivity(CategoryActivity.class,b);
			}
		});
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
