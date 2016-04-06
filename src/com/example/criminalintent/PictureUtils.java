package com.example.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;

public class PictureUtils {
	
	
	@SuppressWarnings("deprecation")
	public static BitmapDrawable getSelectedDrawable(Activity act, String path, String orientation) {
		
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

		Bitmap bitmapInitial = BitmapFactory.decodeFile(path, options);
				
		if (orientation != null) {
			Matrix matrix = new Matrix();
			if (orientation.equals("portrait")) {
				matrix.postRotate(90);
			} else if (orientation.equals("reverse-landscape")) {
				matrix.postRotate(-180);
			} else if (orientation.equals("reverse-portrait")) {
				matrix.postRotate(-90);
			}
			Bitmap bitmapFinal = Bitmap.createBitmap(bitmapInitial, 0, 0, bitmapInitial.getWidth(), bitmapInitial.getHeight(), matrix, true);
			return new BitmapDrawable(act.getResources(), bitmapFinal);
		}

	
		return new BitmapDrawable(act.getResources(), bitmapInitial);
	}
	
	public static void cleanImageVIew(ImageView imageView) {
		if (!(imageView.getDrawable() instanceof BitmapDrawable)) return;
		
		// We are cleaning image to reduce the eating of memory
		BitmapDrawable b = (BitmapDrawable) imageView.getDrawable();
		b.getBitmap().recycle();
		imageView.setImageDrawable(null);
	}
}
