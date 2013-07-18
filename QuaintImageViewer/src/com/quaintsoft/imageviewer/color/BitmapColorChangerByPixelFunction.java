package com.quaintsoft.imageviewer.color;

import android.graphics.Bitmap;
import android.graphics.Color;

public abstract class BitmapColorChangerByPixelFunction
	implements BitmapColorChanger {
	
	protected int[] values;
	
	public void apply(Bitmap bmp) {
		if (bmp == null || !bmp.isMutable())
			return;
		
		applyTo256Values();
		
		try {
			applyToAllPixels(bmp);
		} catch (OutOfMemoryError e) {
			try {
				applyToPixelsByPieces(bmp, 2);
			} catch (OutOfMemoryError e2) {
				try {
					applyToPixelsByPieces(bmp, 4);
				} catch (OutOfMemoryError e3) {
					applyPixelByPixel(bmp);
				}
			}
		}
	}
	
	protected void applyTo256Values() {
		values = new int[256];
		for (int i = 0; i < values.length; i++)
			values[i] = pixelFunction(i);
	}
	
	protected abstract int pixelFunction(int i);

	protected void applyToAllPixels(Bitmap bmp) {
		int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
		bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
		
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = applyToPixel(pixels[i]);
		
		bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
	}
	
	/* Where I was:
	 * I was trying to make this gamma operation faster on large image
	 * by splitting up the image into pieces (and it IS faster), but sometimes
	 * I run out of memory.
	 * 
	 */
	
	protected void applyToPixelsByPieces(Bitmap bmp, int pieceSize) {
		int width = bmp.getWidth() / pieceSize;
		int height = bmp.getHeight() / pieceSize;
		
		for (int i = 0; i < pieceSize; i++) {
			for (int j = 0; j < pieceSize; j++) {
				int[] pixels = new int[width * height];
				bmp.getPixels(pixels, 0, width, i * width, j * height, width, height);
				for (int k = 0; k < pixels.length; k++)
					pixels[k] = applyToPixel(pixels[k]);
				bmp.setPixels(pixels, 0, width, i * width, j * height, width, height);
			}
		}
	}
	
	protected void applyPixelByPixel(Bitmap bmp) {
		for (int x = 0; x < bmp.getWidth(); x++)
			for (int y = 0; y < bmp.getHeight(); y++)
				bmp.setPixel(x, y, applyToPixel(bmp.getPixel(x, y)));
	}
	
	protected int applyToPixel(int pixel) {
		int r = Color.red(pixel);
		int g = Color.green(pixel);
		int b = Color.blue(pixel);
		return Color.rgb(values[r], values[g], values[b]);
	}

}
