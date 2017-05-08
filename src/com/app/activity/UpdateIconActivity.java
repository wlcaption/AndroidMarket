package com.app.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.app.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 类说明 修改头像界面
 * 
 * @author wangsheng
 * @date 2015-8-22 下午1:17:06
 */
public class UpdateIconActivity extends BaseActivity {

	@ViewInject(R.id.imageview)
	CircleImageView imageview;
	@ViewInject(R.id.btn)
	Button updateBtn;
	@ViewInject(R.id.btn_upload)
	Button btn_upload;
	
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	public static Uri photoUri;// 照相之后的数据

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_photo);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		updateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new PopupWindows(UpdateIconActivity.this, imageview);
			}
		});
		btn_upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showShortToast("上传头像");
			}
		});
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub

	}

	public class PopupWindows extends PopupWindow {

		@SuppressWarnings("deprecation")
		public PopupWindows(Context mContext, View parent) {
			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.pop_up_in));
			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.MATCH_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();
			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					takePhoto();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					getPhoto();
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	/**
	 * 
	 * 系统相机拍照
	 * 
	 * @return
	 */
	public void takePhoto() {
		// TODO Auto-generated method stub
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (!SDState.equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "内存卡不存在", 1000).show();
			return;
		}
		try {
			photoUri = this.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					new ContentValues());
			if (photoUri != null) {
				Intent i = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(i, CAMERA_REQUEST_CODE);
				Log.e("takePhoto", "takePhoto");
			} else {
				Toast.makeText(this, "发生意外，无法写入相册", 1000).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(this, "发生意外，无法写入相册", 1000).show();
		}
	}

	/**
	 * 从相册取照片
	 */
	public void getPhoto() {
		Intent intentFromGallery = new Intent();
		intentFromGallery.setType("image/*"); // 设置文件类型
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
	}

	/**
	 * 回调函数处理
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// getActivity();
		// 结果码不等于取消时候
		if (resultCode != FragmentActivity.RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				Log.e("CAMERA_REQUEST_CODE", "CAMERA_REQUEST_CODE");
				startPhotoZoom(photoUri);
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
	};

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	@SuppressWarnings("deprecation")
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			imageview.setImageDrawable(drawable);
			// 上传头像到服务器上去
			//String imageStrData = "";
			// bitmap 转换 String
			//ByteArrayOutputStream stream = new ByteArrayOutputStream();
			//photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			//byte[] b = stream.toByteArray();
			// 将图片流以字符串形式存储下来
			//imageStrData = new String(Base64Coder.encodeLines(b));
			//uploadImage(imageStrData);
		}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Log.e("startPhotoZoom", "startPhotoZoom");
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		this.finish();
	}

}
