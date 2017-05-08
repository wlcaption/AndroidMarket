package com.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.activity.Download3Activity;
import com.app.base.BaseMyAdapter;
import com.app.component.PromptDialog;
import com.app.component.PromptDialog.Builder;
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
public class HistoryAdapter extends BaseMyAdapter<GameDetailItem> {
	Download3Activity activity;  
	public Download3Activity getActivity() {
		return activity;
	}

	public void setActivity(Download3Activity activity) {
		this.activity = activity;
	}

	public HistoryAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
   
	@Override
	protected View getExView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_history, null);
			holder = new ViewHolder();
			holder.hItemIcon = MyViewHolder.get(convertView, R.id.hItemIcon);
			holder.name = MyViewHolder.get(convertView, R.id.hItemTitle);
			holder.hItemBtn=MyViewHolder.get(convertView, R.id.hItemBtn);
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
				showExitDialog(position);
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
		Button hItemBtn;
	}
	public void showExitDialog(final int position) {
		new Builder(activity.getParent()).setMessage("确定删除查看记录吗?", null)
				.setTitle("提示")
				.setButton1("确定", new PromptDialog.OnClickListener() {
					@Override
					public void onClick(Dialog dialog, int which) {
						dialog.dismiss();
						getListData().remove(position);
						notifyDataSetChanged();
					}
				}).setButton2("取消", new PromptDialog.OnClickListener() {
					@Override
					public void onClick(Dialog dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}
}
