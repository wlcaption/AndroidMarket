package com.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.base.BaseActivity;
import com.app.component.PromptDialog;
import com.app.component.PromptDialog.Builder;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 类说明 会员模块
 * 
 * @author wangsheng
 * @date 2015-8-22 上午12:47:36
 */
public class MemberActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.btn_mygift)
	TextView btn_mygift;
	@ViewInject(R.id.btn_about)
	TextView btn_about;
	@ViewInject(R.id.btn_fankui)
	TextView btn_fankui;
	@ViewInject(R.id.icon)
	CircleImageView icon;
	@ViewInject(R.id.bt_login)
	Button bt_login;
	@ViewInject(R.id.bt_logout)
	Button bt_logout;
	@ViewInject(R.id.btn_version)
	TextView btn_version;
	@ViewInject(R.id.btn_qiandao)
	TextView btn_qiandao;
	@ViewInject(R.id.btn_clear)
	TextView btn_clear;
	@ViewInject(R.id.btn_account)
	TextView btn_account;
	@ViewInject(R.id.btn_app)
	TextView btn_app;
	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_member);
	}

	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		btn_mygift.setOnClickListener(this);
		btn_about.setOnClickListener(this);
		btn_fankui.setOnClickListener(this);
		icon.setOnClickListener(this);
		bt_login.setOnClickListener(this);
		bt_logout.setOnClickListener(this);
		btn_version.setOnClickListener(this);
		btn_qiandao.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_account.setOnClickListener(this);
		btn_app.setOnClickListener(this);
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_mygift:
			startActivity(MyGiftActivity.class);
			break;
		case R.id.btn_about:
			startActivity(AboutActivity.class);
			break;
		case R.id.btn_fankui:
			startActivity(FeedBackActivity.class);
			break;
		case R.id.icon:
			startActivity(UpdateIconActivity.class);
			break;
		case R.id.bt_login:
			startActivity(LogRegActivity.class);
			break;
		case R.id.bt_logout:
			break;
		case R.id.btn_version:
			showUpdatDialog();
			break;
		case R.id.btn_qiandao:
			startActivity(SignInActivity.class);
			break;
		case R.id.btn_clear:
			createLoadingDialog(MemberActivity.this, "安装包正在清理中").show();
			break;
		case R.id.btn_account:
			startActivity(UserInfoActivity.class);
			break;
		case R.id.btn_app:
			break;
			
		}
	}

	public void search(View view) {
		startActivity(new Intent(this, SearchActivity.class));
	}

	/**
	 * 更新对话框
	 */
	public void showUpdatDialog() {
		new Builder(MemberActivity.this)
				.setMessage("最新版本：1.1\n最新版本已下载，是否安装？\n更新内容\n这只是一个演示\n学习一下也不错",
						null).setTitle("发现新版本")
				.setButton1("立即更新", new PromptDialog.OnClickListener() {
					@Override
					public void onClick(Dialog dialog, int which) {
						// TODO Auto-generated method stub
						/**
						 * 开始下载更新呗
						 */
						showShortToast("只是一个演示而已");
						dialog.dismiss();
					}
				}).setButton2("以后再说", new PromptDialog.OnClickListener() {
					@Override
					public void onClick(Dialog dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {

		AnimationDrawable animationDrawable;
		View v = View.inflate(context, R.layout.progressdialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.linear);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.loading_iv);
		animationDrawable = (AnimationDrawable) spaceshipImage.getBackground();
		animationDrawable.start();
		TextView tipTextView = (TextView) v.findViewById(R.id.message);// 提示文字
		tipTextView.setText(msg);// 设置加载信息
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		//loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

		return loadingDialog;
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		this.finish();
	}
}
