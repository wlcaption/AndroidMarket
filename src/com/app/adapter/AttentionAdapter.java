package com.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
 * @date 2015-8-21 下午11:44:11
 */
public class AttentionAdapter extends BaseMyAdapter<GameDetailItem> {

	public AttentionAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_favor, null);
			holder = new ViewHolder();
			holder.hItemIcon = MyViewHolder.get(convertView, R.id.hItemIcon);
			holder.name = MyViewHolder.get(convertView, R.id.hItemTitle);
			holder.hItemBtn= MyViewHolder.get(convertView, R.id.hItemBtn);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		GameDetailItem item = this.getListData().get(position);
		if (!TextUtils.isEmpty(item.getIcon())) {
			Picasso.with(context)
					.load("http://www.gamept.cn/" + item.getIcon())
					.placeholder(R.color.ECECEC).error(R.color.ECECEC)
					.into(holder.hItemIcon );
		}
		holder.name.setText(item.getTitle());
		holder.hItemBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				holder.hItemBtn.setImageResource(R.drawable.his_item_btn_s);
			}
		});
		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub

	}
	static class ViewHolder {
		ImageView hItemIcon;
		TextView name;
		ImageButton hItemBtn;
	}
}
