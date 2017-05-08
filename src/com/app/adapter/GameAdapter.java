package com.app.adapter;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
 * @date 2015-8-20 下午5:19:30
 */
public class GameAdapter extends BaseMyAdapter<GameDetailItem> {
	public GameAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_game, null);
			holder = new ViewHolder();
			holder.icon = MyViewHolder.get(convertView, R.id.icon);
			holder.name = MyViewHolder.get(convertView, R.id.name);
			holder.star = MyViewHolder.get(convertView, R.id.star);
			holder.people = MyViewHolder.get(convertView, R.id.people);
			holder.download = MyViewHolder.get(convertView, R.id.download);
			holder.size = MyViewHolder.get(convertView, R.id.size);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GameDetailItem item = this.getListData().get(position);
		if (!TextUtils.isEmpty(item.getIcon())) {
			Picasso.with(context)
					.load("http://www.gamept.cn/" + item.getIcon())
					.placeholder(R.color.ECECEC).error(R.color.ECECEC)
					.into(holder.icon);
		}
		holder.name.setText(item.getTitle());
		if (!TextUtils.isEmpty(item.getStar())) {
			setPraiseRate(holder.star, item.getStar());
		} else {
			holder.star.setImageResource(R.drawable.start5);
		}
		holder.people.setText(new Random().nextInt(100000) + "次");
		holder.size.setText(item.getFilesize());
		holder.download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();        
		        intent.setAction("android.intent.action.VIEW");    
		        Uri content_url = Uri.parse("http://www.gamept.cn/" + item.getFlashurl());  
		        Log.e("url", content_url.toString());
		        intent.setData(content_url);  
		        context.startActivity(intent);
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
		TextView name;
		ImageView star;
		TextView people;
		TextView download;
		TextView size;
	}

	/**
	 * 评分星级
	 * 
	 * @param img
	 * @param score
	 */
	private void setPraiseRate(ImageView img, String score) {
		if (score.endsWith("0")) {
			img.setImageResource(R.drawable.start0);
		} else if (score.endsWith("0.5")) {
			img.setImageResource(R.drawable.start0_5);
		} else if (score.endsWith("1")) {
			img.setImageResource(R.drawable.start1);
		} else if (score.endsWith("1.5")) {
			img.setImageResource(R.drawable.start1_5);
		} else if (score.endsWith("2")) {
			img.setImageResource(R.drawable.start2);
		} else if (score.endsWith("2.5")) {
			img.setImageResource(R.drawable.start2_5);
		} else if (score.endsWith("3")) {
			img.setImageResource(R.drawable.start3);
		} else if (score.endsWith("3.5")) {
			img.setImageResource(R.drawable.start3_5);
		} else if (score.endsWith("4")) {
			img.setImageResource(R.drawable.start4);
		} else if (score.endsWith("4.5")) {
			img.setImageResource(R.drawable.start4_5);
		} else if (score.endsWith("5")) {
			img.setImageResource(R.drawable.start5);
		} else {
			img.setImageResource(R.drawable.start5);
		}
	}
}
