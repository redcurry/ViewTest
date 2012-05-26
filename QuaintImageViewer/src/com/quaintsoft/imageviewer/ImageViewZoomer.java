package com.quaintsoft.imageviewer;

import android.graphics.Matrix;

public class ImageViewZoomer {
	
	private ImageViewModel imageViewAdapter;
	private float defaultZoomFactor = 2.0f;
	
	public ImageViewZoomer(ImageViewModel adapter) {
		imageViewAdapter = adapter;
	}
	
	public void zoomImageTo(float zoomFactor) {
		Matrix imageMatrix = imageViewAdapter.getImageMatrix();
		float cx = getImageViewCenterX();
		float cy = getImageViewCenterY();
		Matrix newImageMatrix = MatrixScaler.scaleTo(imageMatrix, zoomFactor, cx, cy);
		imageViewAdapter.validateAndSetImageMatrix(newImageMatrix);
	}

	public void zoomImageBy(float zoomFactor) {
		Matrix imageMatrix = imageViewAdapter.getImageMatrix();
		float cx = getImageViewCenterX();
		float cy = getImageViewCenterY();
		Matrix newImageMatrix = MatrixScaler.scaleBy(imageMatrix, zoomFactor, cx, cy);
		imageViewAdapter.validateAndSetImageMatrix(newImageMatrix);
	}
	
	private float getImageViewCenterX() {
		return imageViewAdapter.getImageViewWidth() / 2.0f;
	}
	
	private float getImageViewCenterY() {
		return imageViewAdapter.getImageViewHeight() / 2.0f;
	}

	public void zoomImageToActualSize() {
		zoomImageTo(1.0f);
	}

	public void zoomInImage() {
		zoomImageBy(defaultZoomFactor);
	}
	
	public void zoomOutImage() {
		zoomImageBy(1.0f / defaultZoomFactor);
	}
	
	public void fitImageToView() {
		if (getImageAspectRatio() > getImageViewAspectRatio())
			fitImageWidthToView();
		else
			fitImageHeightToView();
	}
	
	private float getImageAspectRatio() {
		return (float)imageViewAdapter.getImageWidth() / imageViewAdapter.getImageHeight();
	}
	
	private float getImageViewAspectRatio() {
		return (float)imageViewAdapter.getImageViewWidth() / imageViewAdapter.getImageViewHeight();
	}
	
	public void fitImageToViewIfLarge() {
		if (imageViewAdapter.getImageWidth() > imageViewAdapter.getImageViewWidth()
				|| imageViewAdapter.getImageHeight() > imageViewAdapter.getImageViewHeight())
			fitImageToView();
	}
	
	public void fitImageWidthToView() {
		zoomImageTo(getFitWidthZoomFactor());
	}
	
	private float getFitWidthZoomFactor() {
		return (float)imageViewAdapter.getImageViewWidth() / imageViewAdapter.getImageWidth();
	}

	public void fitImageHeightToView() {
		zoomImageTo(getFitHeightZoomFactor());
	}
	
	private float getFitHeightZoomFactor() {
		return (float)imageViewAdapter.getImageViewHeight() / imageViewAdapter.getImageHeight();
	}

}
