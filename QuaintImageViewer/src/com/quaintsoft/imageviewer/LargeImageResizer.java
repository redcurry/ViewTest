package com.quaintsoft.imageviewer;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.quaintsoft.imageviewer.OpenLargeImageDialog.OnLargeImageResizeSetListener;

public class LargeImageResizer implements OnLargeImageResizeSetListener {

	Context context;
	ImageViewModel imageViewModel;
	
	public LargeImageResizer(Context context, ImageViewModel model) {
		this.context = context;
		this.imageViewModel = model;
	}
	
	public void onResizeSet(Uri imageUri, int resizeValue) {
		try {
			freeCurrentImage();
			openImage(imageUri, resizeValue);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("error", e.toString());
		}
	}
	
	public void freeCurrentImage() {
		Bitmap bmp = imageViewModel.getImageBitmap();
		if (bmp != null)
			bmp.recycle();
	}
	
	public void openImage(Uri uri, int resizeValue) throws Exception {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = (int)(100.0 / resizeValue);
		InputStream in = context.getContentResolver().openInputStream(uri);
		Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
		in.close();
		
		imageViewModel.setImageBitmap(bmp);
	}

}
