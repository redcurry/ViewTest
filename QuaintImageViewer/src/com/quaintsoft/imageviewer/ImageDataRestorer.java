package com.quaintsoft.imageviewer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

public class ImageDataRestorer {
	
	private static final String IMAGE_MATRIX_BUNDLE_KEY = "IMAGE_MATRIX";
	
	private Activity activity;
	private ImageViewModel imageViewModel;
	
	public ImageDataRestorer(Activity activity, ImageViewModel imageViewModel) {
		this.activity = activity;
		this.imageViewModel = imageViewModel;
	}

	public Object getImageData() {
		return imageViewModel.getImageBitmap();
	}

	public void store(Bundle outState) {
		saveImageMatrix(outState);
	}

	private void saveImageMatrix(Bundle outState) {
		Matrix imageMatrix = imageViewModel.getImageMatrix();
		outState.putFloatArray(IMAGE_MATRIX_BUNDLE_KEY, getMatrixValues(imageMatrix));
	}

	private float[] getMatrixValues(Matrix matrix) {
		float[] values = new float[9];
		matrix.getValues(values);
		return values;
	}

	public void restore(Bundle savedInstanceState) {
		restoreImage();
		restoreImageMatrix(savedInstanceState);
	}
	
	private void restoreImage() {
		Bitmap imageBitmap = (Bitmap)activity.getLastNonConfigurationInstance();
		imageViewModel.setImageBitmap(imageBitmap);
	}
	
	private void restoreImageMatrix(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			float[] imageMatrixValues = savedInstanceState.getFloatArray(IMAGE_MATRIX_BUNDLE_KEY);
			imageViewModel.validateAndSetImageMatrix(getMatrixFromValues(imageMatrixValues));
		}
	}

	private Matrix getMatrixFromValues(float[] values) {
		Matrix matrix = new Matrix();
		matrix.setValues(values);
		return matrix;
	}

}
