package com.quaintsoft.imageviewer.color;

import android.graphics.Bitmap;
import android.graphics.Color;

public abstract class BitmapColorChangerByPixelFunction
	implements BitmapColorChanger, PixelFunction {

	public void apply(Bitmap bmp) {
		if (bmp == null || !bmp.isMutable())
			return;
		
		int[] values = new int[256];
		for (int i = 0; i < values.length; i++)
			values[i] = pixelFunction(i);
		
		int r, g, b, newR, newG, newB;
		for (int x = 0; x < bmp.getWidth(); x++) {
			for (int y = 0; y < bmp.getHeight(); y++) {
				int pixel = bmp.getPixel(x, y);
				r = Color.red(pixel);
				g = Color.green(pixel);
				b = Color.blue(pixel);
				newR = values[r];
				newG = values[g];
				newB = values[b];
				bmp.setPixel(x, y, Color.rgb(newR, newG, newB));
			}
		}
	}

}
