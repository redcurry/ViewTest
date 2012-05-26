package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Matrix;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;

public class ImageViewOnTouchListener implements OnTouchListener {
	
	private ImageViewModel imageViewAdapter;
	
	private GestureDetector scrollGestureDetector;
	private ScaleGestureDetector scaleGestureDetector;
	
	public ImageViewOnTouchListener(Context context, ImageViewModel adapter) {
		imageViewAdapter = adapter;
		setupScrollGestureDetector(context);
		setupScaleGestureDetector(context);
	}

	private void setupScrollGestureDetector(Context context) {
		scrollGestureDetector = new GestureDetector(context,
			new GestureDetector.SimpleOnGestureListener() {

				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
					float dx = -distanceX;
					float dy = -distanceY;
					Matrix imageMatrix = imageViewAdapter.getImageMatrix();
					Matrix newImageMatrix = MatrixTranslator.translateBy(imageMatrix, dx, dy);
					imageViewAdapter.validateAndSetImageMatrix(newImageMatrix);
					return true;
				}
		});
	}

	private void setupScaleGestureDetector(Context context) {
		scaleGestureDetector = new ScaleGestureDetector(context,
			new ScaleGestureDetector.SimpleOnScaleGestureListener() {

				@Override
				public boolean onScale(ScaleGestureDetector detector) {
					float scale = detector.getScaleFactor();
					float px = detector.getFocusX();
					float py = detector.getFocusY();
					Matrix imageMatrix = imageViewAdapter.getImageMatrix();
					Matrix newImageMatrix = MatrixScaler.scaleBy(imageMatrix, scale, px, py);
					imageViewAdapter.validateAndSetImageMatrix(newImageMatrix);
					return true;
				}
		});
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getPointerCount() == 1)
			scrollGestureDetector.onTouchEvent(event);
		else
			scaleGestureDetector.onTouchEvent(event);
		return true;
	}

}
