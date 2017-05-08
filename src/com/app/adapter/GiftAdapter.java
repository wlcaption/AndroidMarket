package com.app.adapter;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.BaseMyAdapter;
import com.app.entity.GameDetailItem;
import com.app.utils.MyViewHolder;
import com.shop.app.R;
import com.squareup.picasso.Picasso;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-8-20 下午5:19:30
 */
public class GiftAdapter extends BaseMyAdapter<GameDetailItem> {
	public GiftAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.giftitem, null);
			holder = new ViewHolder();
			holder.icon = MyViewHolder.get(convertView, R.id.item_layout_imageview);
			holder.item_layout_title = MyViewHolder.get(convertView, R.id.item_layout_title);
			holder.gift_condition = MyViewHolder.get(convertView, R.id.gift_condition);
			holder.item_view_operation = MyViewHolder.get(convertView, R.id.item_view_operation);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GameDetailItem item = this.getListData().get(position);
		if (!TextUtils.isEmpty(item.getIcon())) {
			Picasso.with(context)
					.load("http://www.gamept.cn/" + item.getIcon())
					.placeholder(R.color.ECECEC).error(R.color.ECECEC)
					.into(holder.icon);
		}
		holder.item_layout_title.setText(item.getTitle()+"新手礼包");
		holder.gift_condition.setText("注册会员");
		holder.item_view_operation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				holder.item_view_operation.setText("已领取");
			}
		});
		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub

	}

	static class ViewHolder {
		ImageView icon;
		TextView item_layout_title;
		TextView gift_condition;
		Button item_view_operation;//领取
	}

}
