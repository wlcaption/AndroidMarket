package com.app.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder工具操作类<br />
 * 来源自互联网
 * 
 * @author join
 *
 */
public class MyViewHolder{
	// I added a generic return type to reduce the casting noise in client code
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}