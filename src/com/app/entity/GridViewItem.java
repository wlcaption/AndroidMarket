package com.app.entity;

import java.io.Serializable;

/**  
 * 类说明   
 *  
 * @author wangsheng 
 * @date 2015-8-21 下午3:43:47    
 */
public class GridViewItem implements Serializable {

	public GridViewItem(int iconId,String name)
	{
		this.iconId=iconId;
		this.name=name;
	}
	public int iconId;
	public String name;
}
