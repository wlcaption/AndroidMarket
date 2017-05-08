package com.app.adapter;

import java.io.File;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.app.base.BaseMyAdapter;
import com.app.component.NumberProgressBar;
import com.app.download.DownloadManager;
import com.app.entity.GameDetailItem;
import com.app.utils.MyViewHolder;
import com.app.utils.ToolUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.shop.app.R;
import com.squareup.picasso.Picasso;

/**
 * 类说明
 * 
 * @author wangsheng
 * @date 2015-8-21 下午11:44:11
 */
public class DownAdapter extends BaseMyAdapter<GameDetailItem> {
	private DownloadManager manager;
	// 根目录
	private String ROOT_PATH = Environment.getExternalStorageDirectory()
			.getPath() + File.separator;

	public DownAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		manager = new DownloadManager(context);
	}

	@Override
	protected View getExView(final int position, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_down, null);
			holder = new ViewHolder();
			holder.hItemIcon = MyViewHolder.get(convertView, R.id.iconImg);
			holder.name = MyViewHolder.get(convertView, R.id.tv_name);
			holder.btn_canel = MyViewHolder.get(convertView, R.id.btn_canel);
			holder.startBtn = MyViewHolder.get(convertView, R.id.startBtn);
			holder.pauseBtn = MyViewHolder.get(convertView, R.id.pauseBtn);
			holder.finishBtn = MyViewHolder.get(convertView, R.id.finishBtn);
			holder.progressBar = MyViewHolder.get(convertView, R.id.progress);
			holder.current_result = MyViewHolder.get(convertView,
					R.id.current_result);
			holder.cur_state = MyViewHolder.get(convertView, R.id.cur_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.startBtn.setVisibility(View.VISIBLE);
		holder.pauseBtn.setVisibility(View.GONE);
		holder.finishBtn.setVisibility(View.GONE);
		final GameDetailItem item = this.getListData().get(position);
		if (!TextUtils.isEmpty(item.getIcon())) {
			Picasso.with(context)
					.load("http://www.gamept.cn/" + item.getIcon())
					.placeholder(R.color.ECECEC).error(R.color.ECECEC)
					.into(holder.hItemIcon);
		}
		holder.startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "下载" + position, 1000).show();
				download(holder, item.getUrl());
			}
		});
		holder.name.setText(item.getTitle());
		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub

	}

	static class ViewHolder {
		ImageView hItemIcon;
		TextView name;
		Button startBtn;// 开始下载
		Button pauseBtn;//
		Button finishBtn;//
		Button btn_canel;// 取消
		NumberProgressBar progressBar;// 进度条
		TextView current_result;// 暂停/继续/完成
		TextView cur_state;// 当前状态
	}

	class MyOnClickListener implements OnClickListener {

		private ViewHolder holder;
		private int position;
		GameDetailItem item;

		public MyOnClickListener(GameDetailItem item, ViewHolder holder,
				int position) {
			this.holder = holder;
			this.position = position;
			this.item = item;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_canel:// 删除
				String path = manager.getDownloadInfo(position)
						.getFileSavePath();
				try {
					manager.removeDownload(position);
					DownAdapter.this.getListData().remove(position);
					notifyDataSetChanged();
					// 删除文件
					File file = new File(path);
					if (file.exists())
						file.delete();
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case R.id.startBtn:
				// download(holder, item.getUrl());
				break;

			case R.id.pauseBtn:
				try {
					manager.stopDownload(position);
					holder.current_result.setText("继续");
					holder.pauseBtn.setVisibility(View.GONE);
					holder.startBtn.setVisibility(View.VISIBLE);
					holder.finishBtn.setVisibility(View.GONE);
				} catch (DbException e) {
					e.printStackTrace();
				}
				break;
			case R.id.finishBtn:
				ToolUtil.installApk(context,
						new File(manager.getDownloadInfo(position)
								.getFileSavePath()));
				break;
			}
		}
	};

	//
	public void download(final ViewHolder holder, String url) {
		String name = url.substring(url.lastIndexOf("/") + 1, url.length());
		String target = ROOT_PATH + name;
		HttpUtils http = new HttpUtils();
		http.download(url, target, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
				true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
				new RequestCallBack<File>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						holder.progressBar.setMax((int) total);
						holder.progressBar.setProgress((int) current);
						Log.e("onLoading", current + ":" + total);
					}

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {

					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});

	}
}
