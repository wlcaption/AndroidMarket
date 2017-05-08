package com.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.app.base.BaseMyAdapter;
import com.app.utils.MyViewHolder;
import com.shop.app.R;
import com.squareup.picasso.Picasso;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-8-24 下午2:33:58
 */
public class PicAdapter extends BaseMyAdapter<String> {

	public PicAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_pic, null);
			holder = new ViewHolder();
			holder.item_layout_imageview = MyViewHolder.get(convertView,
					R.id.item_layout_imageview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String imgUrl = this.getListData().get(position);
		if (!TextUtils.isEmpty(imgUrl)) {
			Picasso.with(context).load(imgUrl).placeholder(R.color.ECECEC)
					.error(R.color.ECECEC).into(holder.item_layout_imageview);
		}
		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub

	}

	static class ViewHolder {
		ImageView item_layout_imageview;
	}
}
