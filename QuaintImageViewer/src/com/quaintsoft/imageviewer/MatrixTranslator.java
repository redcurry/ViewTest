package com.quaintsoft.imageviewer;

import android.graphics.Matrix;

public class MatrixTranslator {
	
	public static Matrix translateBy(Matrix matrix, float dx, float dy) {
		Matrix newMatrix = new Matrix(matrix);
		newMatrix.postTranslate(dx, dy);
		return newMatrix;
	}
	
}
