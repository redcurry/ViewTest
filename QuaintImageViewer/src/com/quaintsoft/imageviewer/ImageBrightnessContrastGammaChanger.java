package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.quaintsoft.imageviewer.BrightnessContrastGammaDialog.OnBrightnessContrastGammaSetListener;
import com.quaintsoft.imageviewer.color.BrightnessContrast;
import com.quaintsoft.imageviewer.color.Gamma;

public class ImageBrightnessContrastGammaChanger implements
		OnBrightnessContrastGammaSetListener {
	
	private Context context;
	private ImageViewModel imageViewModel;
	
	public ImageBrightnessContrastGammaChanger(Context context, ImageViewModel imageViewModel) {
		this.context = context;
		this.imageViewModel = imageViewModel;
	}

	public void onBrightnessContrastGammaSet(int brightness, int contrast, float gamma) {
		Bitmap bmp = imageViewModel.getImageBitmap();
		Bitmap newBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Config.ARGB_8888);
		BrightnessContrast bc = new BrightnessContrast();
		bc.setBrightness(brightness);
		bc.setContrast(contrast);
		bc.apply(bmp, newBmp);
		Gamma g = new Gamma();
		g.setGamma(gamma);
		g.apply(newBmp, newBmp);
		imageViewModel.setImageBitmap(newBmp);
	}

}
