package com.app.component;

import com.app.utils.MyViewHolder;
import com.shop.app.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-7-16 下午11:14:42
 */
public class TitleLinearLayout extends LinearLayout {

	ImageView titleRightImageview;
    RightImageViewOnclikListener listener;
    TextView title_bar_middle;
    
	public RightImageViewOnclikListener getListener() {
		return listener;
	}

	public void setListener(RightImageViewOnclikListener listener) {
		this.listener = listener;
	}

	public TitleLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view = LayoutInflater.from(context).inflate(
				R.layout.include_back_title_item_layout, this);
		title_bar_middle=MyViewHolder.get(view, R.id.barTxt);
		View title_bar_left = MyViewHolder.get(view, R.id.backButton);
		title_bar_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Activity) getContext()).finish();
			}
		});
		/*titleRightImageview = MyViewHolder
				.get(view, R.id.titleRightImageview);
		titleRightImageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(listener!=null)
				{
					listener.rightViewOnclick();
				}
			}
		});*/
	}

	public void setRightImageResource(int resId) {
		if (resId > 0) {
			titleRightImageview.setImageResource(resId);
		}
		titleRightImageview.setVisibility(View.VISIBLE);
	}
	public interface RightImageViewOnclikListener
	{
		public void rightViewOnclick();
	}

	public void setMiddleText(String text)
	{
		this.title_bar_middle.setText(text);
	}
}
