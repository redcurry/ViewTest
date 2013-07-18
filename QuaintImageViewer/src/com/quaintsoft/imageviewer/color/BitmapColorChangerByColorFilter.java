package com.quaintsoft.imageviewer.color;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public abstract class BitmapColorChangerByColorFilter
	implements BitmapColorChanger {
	
	public void apply(Bitmap bmp) {
		if (bmp != null && bmp.isMutable()) {
			Canvas canvas = new Canvas(bmp);
			canvas.drawBitmap(bmp, 0, 0, createPaint());
		}
	}
	
	protected Paint createPaint() {
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(createColorMatrix()));
		return paint;
	}
	
	protected abstract ColorMatrix createColorMatrix();

}
