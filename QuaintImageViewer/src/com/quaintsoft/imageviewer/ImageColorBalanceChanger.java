package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;

import com.quaintsoft.imageviewer.ColorBalanceDialog.OnColorBalanceSetListener;
import com.quaintsoft.imageviewer.color.BrightnessContrast;
import com.quaintsoft.imageviewer.color.ColorBalance;

public class ImageColorBalanceChanger implements OnColorBalanceSetListener {
	
	private Context context;
	private ImageViewModel imageViewModel;
	
	public ImageColorBalanceChanger(Context context, ImageViewModel imageViewModel) {
		this.context = context;
		this.imageViewModel = imageViewModel;
	}

	public void onColorBalanceSet(int red, int green, int blue) {
		Bitmap bmp = imageViewModel.getImageBitmap();
		ColorBalance color_balance = new ColorBalance();
		color_balance.setRed(red);
		color_balance.setGreen(green);
		color_balance.setBlue(blue);
		color_balance.apply(bmp);
		imageViewModel.invalidate();
	}

}
