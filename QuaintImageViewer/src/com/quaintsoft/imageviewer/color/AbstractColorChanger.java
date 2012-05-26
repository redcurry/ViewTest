package com.quaintsoft.imageviewer.color;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public abstract class AbstractColorChanger {
	
	public void apply(Bitmap src, Bitmap dst) {
		if (src == null || dst == null)
			return;
		Canvas canvas = new Canvas(dst);
		canvas.drawBitmap(src, new Matrix(), createPaint());
	}
	
	private Paint createPaint() {
		Paint paint = new Paint();
		paint.setColorFilter(createColorFilter());
		return paint;
	}
	
	protected abstract ColorMatrixColorFilter createColorFilter();

}
