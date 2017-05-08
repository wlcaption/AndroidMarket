package com.app.utils;

import java.io.File;

import com.shop.app.R;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**  
 * 类说明   程序安装与卸载
 *  
 * @author wangsheng 
 * @date 2015-8-25 下午2:45:56    
 */
public class ToolUtil {

	 
    /**
     * Android 安装应用
     * 
     * @param context Application Context
     * @param filePath APK文件路径
     */
    public static void installApk(Context context, File file) {
        if (file.exists()) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            ((ContextWrapper) context).startActivity(i);
        } else {
          Toast.makeText(context, context.getString(R.string.install_fail_file_not_exist), 1000).show();
        }
    }
    
    /**
     * 卸载应用
     * 
     * @param context
     *            应用上下文
     * @param pkgName
     *            包名
     */
    public static void uninstallApk(Context context, String pkgName) {
        Uri packageURI = Uri.parse("package:" + pkgName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
    }
}
