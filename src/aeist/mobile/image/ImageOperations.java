package aeist.mobile.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @since version 0.1
 * 
 * TODO: description
 * 
 * @author joaovasques
 *
 */
public class ImageOperations {
	
	/**
	 * Sets round corners on an image
	 * @param bitmap image bitmap
	 * @return image's bitmap with rounded corners
	 */
	 public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		        bitmap.getHeight(), Config.ARGB_8888);
		    Canvas canvas = new Canvas(output);

		    final int color = 0xff424242;
		    final Paint paint = new Paint();
		    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		    final RectF rectF = new RectF(rect);
		    final float roundPx = 20;

		    paint.setAntiAlias(true);
		    canvas.drawARGB(0, 0, 0, 0);
		    paint.setColor(color);
		    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		    canvas.drawBitmap(bitmap, rect, rect, paint);

		    return output;
		  }
	 
	 /**
	  * Scales the given image's bitmap 
	  * @param bitmap image's bitmap
	  * @param width bitmap's final width
	  * @param height bitmap's final height
	  * @return
	  */
	 public static Bitmap scaleImage(Bitmap bitmap, int width, int height)
	 {
		 Bitmap scaled_image =Bitmap.createScaledBitmap(bitmap, width, height, true);
		 return scaled_image;
	 }
	  
}
