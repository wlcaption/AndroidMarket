package com.app.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.shop.app.R;
import com.squareup.picasso.Picasso;
/**
 * 广告轮播适配器
 * @author admin
 *
 */
public class ViewPagerAdapter extends PagerAdapter {

	private Context mContext;
	List<String> imgurls;
	ImageClickListener imgClickListener;
	public ImageClickListener getImgClickListener() {
		return imgClickListener;
	}

	public void setImgClickListener(ImageClickListener imgClickListener) {
		this.imgClickListener = imgClickListener;
	}

	public ViewPagerAdapter(Context context, List<String> imgurls) {
		this.mContext = context;
		this.imgurls = imgurls;
	}

	// 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量

	@Override
	public int getCount() {
		return imgurls.size();

	}

	// 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;

	}

	// PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁

	@Override
	public void destroyItem(ViewGroup view, int position, Object object) {

		((ViewPager) view).removeView((View) object);

	}

	// 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可

	@Override
	public Object instantiateItem(ViewGroup view, final int position) {

		ImageView imageview = new ImageView(mContext);
		imageview.setScaleType(ScaleType.CENTER_CROP);
		Picasso.with(mContext).load(imgurls.get(position))
				.placeholder(R.color.ECECEC).error(R.color.ECECEC)
				.into(imageview);
		((ViewPager) view).addView(imageview);
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(imgClickListener!=null)
				{
					imgClickListener.imageClick(position);
				}
			}
		});
		return imageview;

	}
  public interface ImageClickListener 
  {
	 public void imageClick(int position);
  }
}