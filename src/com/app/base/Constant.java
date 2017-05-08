package com.app.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Environment;

/**
 * 类说明 一些常量都放在这里
 * 
 * @author wangsheng
 * @date 2015-7-17 下午10:54:07
 */
public class Constant {

	public static final String NO_NETWORK_ACTION = "com.yuema.nonetwork.action";
	public static final String FORCE_OFFLINE_ACTION = "com.yuema.force.offline.action";
	public static final String SHOW_COMMENT_LAYOUT_ACTION = "com.yuema.show.commentlayout.action";
	public static final String IS_FIRST_RUN = "isFirstRun";
	public static final String SHOW_DETAIL_COMMENT_LAYOUT_ACTION = "com.yuema.show.detail.commentlayout.action";
	public static final int SELECT_PHOTO_REQUEST_CODE = 0x1000;
	public static final int DELETE_PHOTO_REQUEST_CODE = 0x1001;
	public static final int MAX_SELECTED_IMAGE = 9;// 最大图片数
	/******* down APP name ******/
	public static final String appName = "updateAppTest";
	/******* down APP address *******/
	public static final String downUrl = "http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk";
	/* 请求码 */
	public static final int IMAGE_REQUEST_CODE = 0x0000;
	public static final int CAMERA_REQUEST_CODE = 0x0001;
	public static final int RESULT_REQUEST_CODE = 0x0002;
	public static final int VIDEO_REQUEST_CODE = 0x0003;
	public static final int LOCATION_REQUEST_CODE = 0x0004;
	public static final int PHONE_LENGTH = 11;// 手机长度
	// JPUSH
	public final static String KEY_MESSAGE = "message";
	public final static String MESSAGE_RECEIVED_ACTION = "com.shengge.client.MESSAGE_RECEIVED_ACTION";
	// upload image
	// 去上传文件
	public static final int TO_UPLOAD_FILE = 0x01;
	// 上传文件响应
	public static final int UPLOAD_FILE_DONE = 0x02;
	// 选择文件
	public static final int TO_SELECT_PHOTO = 0x03;
	// 上传初始化
	public static final int UPLOAD_INIT_PROCESS = 0x04;
	// 上传中
	public static final int UPLOAD_IN_PROCESS = 0x05;
	//获取数据成功
	public static final int DATA_OK = 0x06;
	//获取不到数据
	public static final int NO_DATA = 0x07;
	// 请求服务器uri
	public static final String requestURL = "http://10.0.0.147:8888/MyTest/p/file!upload";
	//
	public static final String BACK_ACTION = "com.shengge.client.backaction";
	// 静态地图API
	public static final String LOCATION_URL_S = "http://api.map.baidu.com/staticimage?width=320&height=240&zoom=17&center=";
	public static final String LOCATION_URL_L = "http://api.map.baidu.com/staticimage?width=480&height=800&zoom=17&center=";
	//壁纸
	public static final String WALLPAPER="wallpaper";
	//下载常量
	/**
	 * 下载文件保存的本地路径
	 */
	public static final String LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MyDownloader" + File.separator;
	/**
	 * 下载地址列表
	 */
	public static final List<String> URL_LIST = new ArrayList<String>();
	/**
	 * key=下载地址，value=文件名
	 */
	public static final Map<String, String> URL_MAP = new HashMap<String, String>();
	/**
	 * 获取到文件大小
	 */
	public static final int MSG_FILELENGTH = 1;
	/**
	 * 获取文件大小失败
	 */
	public static final int ERROR_FILELENGTH = 2;
	/**
	 * 更新进度条
	 */
	public static final int MSG_UPDATE = 3;
	/**
	 * 下载完成
	 */
	public static final int MSG_FINISH = 4;
	/**
	 * 下载完成
	 */
	public static final int ERROR_TEMP = 5;

	static {
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"小苹果.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"平凡之路.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"后会无期.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"愿得一人心.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"夜空中最亮的星.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"Let It Go.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"存在.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"我的歌声里.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"存在.mp3");
		saveData(
				"http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk",
				"我的歌声里.mp3");
	}

	/**
	 * 保存数据
	 * 
	 * @param url
	 *            下载路径
	 * @param name
	 *            文件名
	 */
	private static void saveData(String url, String name) {
		URL_LIST.add(url);
		URL_MAP.put(url, name);
	}

	/**
	 * 获取文件路径
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFilePath(String filename) {
		return LOCAL_PATH + filename;
	}

	/**
	 * 获取临时文件路径
	 * 
	 * @param filename
	 * @return
	 */
	public static String getTempPath(String filename) {
		String tempName = filename + ".temp";
		return LOCAL_PATH + tempName;
	}
}
