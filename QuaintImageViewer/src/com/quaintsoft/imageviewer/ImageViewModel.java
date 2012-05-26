package com.quaintsoft.imageviewer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView.ScaleType;

public class ImageViewModel {

	private QuaintImageView imageView;
	private ImageMatrixValidator imageMatrixValidator;
	private boolean imageIsBound = true;
	Bitmap originalBmp = null;
	
	public ImageViewModel(QuaintImageView iv) {
		imageView = iv;
		imageView.setScaleType(ScaleType.MATRIX);
		imageMatrixValidator = new ImageMatrixValidator(this);
		
		// If the user changes the screen orientation,
		// the ImageView won't know its dimensions until later,
		// so the ImageMatrixValidator won't perform right;
		// this "trick" waits until the layout has been finalized
		// to call the ImageMatrixInvalidator 
		imageView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			public void onGlobalLayout() {
				invalidate();
			}
		});
	}
	
	public void setAlignCenter(boolean alignCenter) {
		if (alignCenter)
			imageMatrixValidator.alignCenter();
		else
			imageMatrixValidator.alignTopLeft();
		invalidate();
	}
	
	public void setAntialiasing(boolean antialias) {
		imageView.setAntialiasing(antialias);
		invalidate();
	}
	
	public Bitmap getImageBitmap() {
		if (imageView.getDrawable() instanceof BitmapDrawable)
			return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
		else
			return null;
	}
	
	public void setImageBitmap(Bitmap imageBitmap) {
		imageView.setImageBitmap(imageBitmap);
		invalidate();
	}
	
	public void setImageURI(Uri data) {
		imageView.setImageURI(data);
//		setImageBitmap(copyBitmap(getImageBitmap()));  // Fixes weird density-independent problems
//		invalidate();
	}
	
	private Bitmap copyBitmap(Bitmap bmp) {
		Bitmap newBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(newBmp);
		canvas.drawBitmap(bmp, new Matrix(), null);
		return newBmp;
	}

	public int getImageWidth() {
		Drawable drawable = imageView.getDrawable();
		if (drawable != null)
			return imageView.getDrawable().getIntrinsicWidth();
		else
			return 0;
	}
	
	public int getImageHeight() {
		Drawable drawable = imageView.getDrawable();
		if(drawable != null)
			return imageView.getDrawable().getIntrinsicHeight();
		else
			return 0;
	}
	
	public int getImageViewWidth() {
		return imageView.getWidth();
	}
	
	public int getImageViewHeight() {
		return imageView.getHeight();
	}
	
	public Matrix getImageMatrix() {
		return imageView.getImageMatrix();
	}
	
	public void validateAndSetImageMatrix(Matrix imageMatrix) {
		if (imageIsBound)
			imageMatrix = imageMatrixValidator.getValidImageMatrix(imageMatrix);
		
		imageView.setImageMatrix(imageMatrix);
	}
	
	public void invalidate() {
		validateAndSetImageMatrix(getImageMatrix());
	}
	
}
