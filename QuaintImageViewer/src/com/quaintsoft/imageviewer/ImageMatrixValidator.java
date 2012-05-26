package com.quaintsoft.imageviewer;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

public class ImageMatrixValidator {
	
	private ImageViewModel imageViewAdapter;
	private boolean alignedCenter = true;
	
	public ImageMatrixValidator(ImageViewModel adapter) {
		imageViewAdapter = adapter;
	}
	
	public void alignTopLeft() {
		alignedCenter = false;
	}
	
	public void alignCenter() {
		alignedCenter = true;
	}
	
	public Matrix getValidImageMatrix(Matrix imageMatrix) {
		Matrix newImageMatrix = new Matrix(imageMatrix);
		RectF imageRect = getImageRect(imageMatrix);
		validateAxisX(newImageMatrix, imageRect);
		validateAxisY(newImageMatrix, imageRect);
		return newImageMatrix;
	}
	
	private RectF getImageRect(Matrix imageMatrix) {
		float imageWidth = imageViewAdapter.getImageWidth();
		float imageHeight = imageViewAdapter.getImageHeight();
		RectF imageRect = new RectF(0, 0, imageWidth, imageHeight);
		imageMatrix.mapRect(imageRect);
		return imageRect;
	}

	private void validateAxisX(Matrix imageMatrix, RectF imageRect) {
		float imageViewWidth = imageViewAdapter.getImageViewWidth();
		float dx = getDelta(imageRect.left, imageRect.width(), imageViewWidth);
		imageMatrix.postTranslate(dx, 0);
	}
	
	private void validateAxisY(Matrix imageMatrix, RectF imageRect) {
		float imageViewHeight = imageViewAdapter.getImageViewHeight();
		float dy = getDelta(imageRect.top, imageRect.height(), imageViewHeight);
		imageMatrix.postTranslate(0, dy);
	}
	
	private float getDelta(float imagePos, float imageLength, float viewLength) {
		if (imageLength < viewLength)
			return getDeltaForSmallImage(imagePos, imageLength, viewLength);
		else
			return getDeltaForLargeImage(imagePos, imageLength, viewLength);
	}
	
	private float getDeltaForSmallImage(float imagePos, float imageLength, float viewLength) {
		float alignedPos = alignedCenter ? getCenteredPos(imageLength, viewLength) : 0f;
		
		if (imagePos != alignedPos)
			return alignedPos - imagePos;

		return 0f;
	}
	
	private float getDeltaForLargeImage(float imagePos, float imageLength, float viewLength) {
		if (imagePos > 0)
			return -imagePos;

		float imageRight = imagePos + imageLength;
		if (imageRight < viewLength)
			return (viewLength - imageRight);

		return 0f;
	}
	
	private float getCenteredPos(float imageLength, float viewLength) {
		return (viewLength - imageLength) / 2f;
	}
	
}
