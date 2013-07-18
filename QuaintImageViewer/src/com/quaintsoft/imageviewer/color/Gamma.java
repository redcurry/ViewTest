package com.quaintsoft.imageviewer.color;

public class Gamma extends BitmapColorChangerByPixelFunction {

	private float gamma = 1f;
	
	public void setGamma(float gamma) {
		this.gamma = gamma;
	}
	
	@Override
	protected int pixelFunction(int value) {
		return (int)(Math.pow(value / 255f, 1f / gamma) * 255);
	}
	
	

}
