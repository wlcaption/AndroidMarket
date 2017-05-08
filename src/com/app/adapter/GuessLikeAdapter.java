package com.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
 * @date 2015-8-24 下午2:33:58
 */
public class GuessLikeAdapter extends BaseMyAdapter<GameDetailItem> {

	public GuessLikeAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.gamedetail_cai_item,
					null);
			holder = new ViewHolder();
			holder.icon = MyViewHolder.get(convertView,R.id.item_layout_imageview);
			holder.name = MyViewHolder.get(convertView, R.id.item_layout_title);
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
		holder.name.setText(item.getTitle());
		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub

	}

	static class ViewHolder {
		ImageView icon;
		TextView name;
	}
}
