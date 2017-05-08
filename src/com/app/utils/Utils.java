package com.app.utils;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Utils {
    public static final int AVATAR_IMAGE_SIZE = 128;

    public static ObjectAnimator GenerateColorAnimator(Context context, int animatorID, Object target) {
        ObjectAnimator colorAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(context, animatorID);
        colorAnimation.setTarget(target);
        colorAnimation.setEvaluator(new ArgbEvaluator());
        return colorAnimation;
    }

    public static ObjectAnimator GenerateSlideAnimator(Context context, int animatorID, Object target) {
        ObjectAnimator alphaAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(context, animatorID);
        alphaAnimation.setTarget(target);
        alphaAnimation.setEvaluator(new FloatEvaluator());
        return alphaAnimation;
    }

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, int stringID) {
        Toast.makeText(context, context.getString(stringID), Toast.LENGTH_SHORT).show();
    }

    public static Bitmap getBitMap(String imgPath) {
        return BitmapFactory.decodeFile(imgPath);
    }

    public static Bitmap getScaledBitMap(String imgPath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight)
                inSampleSize = Math.round(srcHeight / height);
            else
                inSampleSize = Math.round(srcWidth / width);
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(imgPath, options);
    }

    public static Bitmap getScaledBitMap(byte[] data, int width, int height) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight)
                inSampleSize = Math.round(srcHeight / height);
            else
                inSampleSize = Math.round(srcWidth / width);
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }


    public static byte[] ConvertBitMapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }
    public static int dip2px(Context context, float dpValue) {  
		final float scale = context.getResources().getDisplayMetrics().density;  
		return (int) (dpValue * scale + 0.5f);  
	}

	/**获取屏幕分辨率宽*/
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		//((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);  
		Display display = wm.getDefaultDisplay();  
		display.getMetrics(dm);

		return dm.widthPixels;
	}
}