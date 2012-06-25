package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;

import com.quaintsoft.imageviewer.BrightnessContrastDialog.OnBrightnessContrastSetListener;
import com.quaintsoft.imageviewer.color.BrightnessContrast;

public class ImageBrightnessContrastChanger implements
		OnBrightnessContrastSetListener {
	
	private Context context;
	private ImageViewModel imageViewModel;
	
	public ImageBrightnessContrastChanger(Context context, ImageViewModel imageViewModel) {
		this.context = context;
		this.imageViewModel = imageViewModel;
	}

	// TODO: Separate brightness contrast from gamma
	public void onBrightnessContrastSet(int brightness, int contrast) {
		Bitmap bmp = imageViewModel.getImageBitmap();
		BrightnessContrast bc = new BrightnessContrast();
		bc.setBrightness(brightness);
		bc.setContrast(contrast);
		bc.apply(bmp);
		imageViewModel.invalidate();
	}

}
