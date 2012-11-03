package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class QuaintImageView extends ImageView {
	
	private boolean antialiasing = false;

	public QuaintImageView(Context context) {
		super(context);
	}

	public QuaintImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuaintImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (antialiasing)
			super.onDraw(canvas);
		else
			drawWithoutAntialiasing(canvas);
	}
	
	private void drawWithoutAntialiasing(Canvas canvas) {
		Bitmap bmp = getBitmap();
		Matrix imageMatrix = getImageMatrix();
		if (bmp != null && imageMatrix != null)
			canvas.drawBitmap(bmp, imageMatrix, null);
	}
	
	private Bitmap getBitmap() {
		Drawable drawable = getDrawable();
		if (drawable instanceof BitmapDrawable)
			return ((BitmapDrawable)drawable).getBitmap();
		return null;
	}
	
	public void setAntialiasing(boolean antialias) {
		antialiasing = antialias;
	}
	
	public boolean getAntialiasing() {
		return antialiasing;
	}

}
