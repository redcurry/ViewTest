package com.quaintsoft.imageviewer.color;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public abstract class BitmapColorChangerByColorFilter
	implements BitmapColorChanger, ColorFilter {
	
	public void apply(Bitmap bmp) {
		if (bmp == null || !bmp.isMutable())
			return;
		Canvas canvas = new Canvas(bmp);
		canvas.drawBitmap(bmp, new Matrix(), createPaint());
	}
	
	private Paint createPaint() {
		Paint paint = new Paint();
		paint.setColorFilter(colorFilter());
		return paint;
	}

}
