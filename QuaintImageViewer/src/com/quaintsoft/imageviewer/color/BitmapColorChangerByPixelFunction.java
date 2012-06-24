package com.quaintsoft.imageviewer.color;

import android.graphics.Bitmap;
import android.graphics.Color;

public abstract class BitmapColorChangerByPixelFunction
	implements BitmapColorChanger, PixelFunction {
	
	private int[] values;
	
	public void apply(Bitmap bmp) {
		if (bmp == null || !bmp.isMutable())
			return;
		
		applyTo256Values();
		
		try {
			applyToAllPixels(bmp);
		} catch (OutOfMemoryError e) {
			applyPixelByPixel(bmp);
		}
	}
	
	private void applyTo256Values() {
		values = new int[256];
		for (int i = 0; i < values.length; i++)
			values[i] = pixelFunction(i);
	}

	private void applyToAllPixels(Bitmap bmp) {
		int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
		bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
		
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = applyToPixel(pixels[i]);
		
		bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
	}
	
	private void applyPixelByPixel(Bitmap bmp) {
		for (int x = 0; x < bmp.getWidth(); x++)
			for (int y = 0; y < bmp.getHeight(); y++)
				bmp.setPixel(x, y, applyToPixel(bmp.getPixel(x, y)));
	}
	
	private int applyToPixel(int pixel) {
		int r = Color.red(pixel);
		int g = Color.green(pixel);
		int b = Color.blue(pixel);
		int newR = values[r];
		int newG = values[g];
		int newB = values[b];
		return Color.rgb(newR, newG, newB);
	}

}
