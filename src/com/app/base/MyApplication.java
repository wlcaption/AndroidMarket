package com.app.base;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


/**
 * @author wlcaption@qq.com
 * 2017-4-13 上午10:32:15
 */
public class MyApplication extends Application {
	private static MyApplication mInstance;
	/**
	 * 下载路径对应已下载大小，key=下载路径，value=已下载文件大小
	 */
	public static Map<String, Integer> DownloadLengthMap = new HashMap<String, Integer>();

	public static MyApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		DownloadLengthMap.clear();
	}
	/**
	 * 分享功能
	 * 
	 * @param context
	 *            上下文
	 * @param activityTitle
	 *            Activity的名字
	 * @param msgTitle
	 *            消息标题
	 * @param msgText
	 *            消息内容
	 * @param imgPath
	 *            图片路径，不分享图片则传null
	 */
	public static void shareMsg(Context cx, String activityTitle, String msgTitle,
			String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		cx.startActivity(Intent.createChooser(intent, activityTitle));
	}
}
