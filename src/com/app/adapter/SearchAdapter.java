package com.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.base.BaseMyAdapter;
import com.app.utils.MyViewHolder;
import com.shop.app.R;

/**  
 * 类说明   
 *  
 * @author wangsheng 
 * @date 2015-8-24 下午1:27:41    
 */
public class SearchAdapter extends BaseMyAdapter<Object> {

	public SearchAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_search, null);
			TextView name=MyViewHolder.get(convertView, R.id.name);
			name.setText("全民斗西游");
		}
		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub
		
	}

}
