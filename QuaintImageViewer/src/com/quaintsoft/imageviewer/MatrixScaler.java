package com.quaintsoft.imageviewer;

import android.graphics.Matrix;

public class MatrixScaler {
	
	public static Matrix scaleTo(Matrix matrix, float scale) {
		return scaleTo(matrix, scale, 0.0f, 0.0f);
	}
	
	public static Matrix scaleTo(Matrix matrix, float scale, float px, float py) {
		Matrix newMatrix = new Matrix(matrix);
		newMatrix.setScale(scale, scale, px, py);
		return newMatrix;
	}
	
	public static Matrix scaleBy(Matrix matrix, float scale) {
		return scaleBy(matrix, scale, 0.0f, 0.0f);
	}
	
	public static Matrix scaleBy(Matrix matrix, float scale, float px, float py) {
		Matrix newMatrix = new Matrix(matrix);
		newMatrix.postScale(scale, scale, px, py);
		return newMatrix;
	}
	
}
