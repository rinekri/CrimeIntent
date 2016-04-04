package com.example.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;

public class PictureUtils {
	
	
	@SuppressWarnings("deprecation")
	public static BitmapDrawable getSelectedDrawable(Activity act, String path) {
		
		Display display = act.getWindowManager().getDefaultDisplay();
		float destWidth = display.getWidth();
		float destHeight = display.getHeight();
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		
		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;
		
		int inSampleSize = 1;
		
		if (srcHeight > destHeight || srcWidth > destWidth) {
			if (srcWidth > srcHeight) {
				inSampleSize = Math.round(srcHeight / destHeight);

			} else {
				inSampleSize = Math.round(srcWidth / destWidth);
			}
		}
		
		options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;
		
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		return new BitmapDrawable(act.getResources(), bitmap);
	}
	
	public static void cleanImageVIew(ImageView imageView) {
		if (!(imageView.getDrawable() instanceof BitmapDrawable)) return;
		
		// We are cleaning image to reduce the eating of memory
		BitmapDrawable b = (BitmapDrawable) imageView.getDrawable();
		b.getBitmap().recycle();
		imageView.setImageDrawable(null);
	}
}
