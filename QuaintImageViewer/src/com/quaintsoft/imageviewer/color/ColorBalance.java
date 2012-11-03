package com.quaintsoft.imageviewer.color;

import android.graphics.Color;

public class ColorBalance extends BitmapColorChangerByPixelFunction {
	
	protected int[] red_values;
	protected int[] green_values;
	protected int[] blue_values;
	
	private int red = 0;
	private int green = 0;
	private int blue = 0;
	
	private int color = 0;
	
	@Override
	protected void applyTo256Values() {
		red_values = getAppliedTo256Values(red);
		green_values = getAppliedTo256Values(green);
		blue_values = getAppliedTo256Values(blue);
	}
	
	protected int[] getAppliedTo256Values(int c) {
		color = c;
		int[] values = new int[256];
		for (int i = 0; i < values.length; i++)
			values[i] = pixelFunction(i);
		return values;
	}

	public int pixelFunction(int value) {
		return clamp(value + color);
	}
	
	private int clamp(float x) {
		if (x > 255)
			return 255;
		else if (x < 0)
			return 0;
		else
			return (int)x;
	}
	
	@Override
	protected int applyToPixel(int pixel) {
		int r = Color.red(pixel);
		int g = Color.green(pixel);
		int b = Color.blue(pixel);
		int newR = red_values[r];
		int newG = green_values[g];
		int newB = blue_values[b];
		return Color.rgb(newR, newG, newB);
	}

	public void setRed(int r) {
		red = r;
	}
	
	public void setGreen(int g)	{
		green = g;
	}
	
	public void setBlue(int b) {
		blue = b;
	}
	
}
