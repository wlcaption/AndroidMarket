package com.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.base.BaseMyAdapter;
import com.app.entity.GridViewItem;
import com.app.utils.MyViewHolder;
import com.shop.app.R;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-8-21 下午3:37:57
 */
public class GridViewAdapter extends BaseMyAdapter<GridViewItem> {

	public GridViewAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.game_cate_item, null);
			holder = new ViewHolder();
			holder.icon = MyViewHolder.get(convertView, R.id.ItemImage);
			holder.name = MyViewHolder.get(convertView, R.id.ItemText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GridViewItem item = this.getListData().get(position);
		holder.icon.setImageResource(item.iconId);
		holder.name.setText(item.name);
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
