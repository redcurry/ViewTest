package com.quaintsoft.imageviewer;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;

public class LargeImageOpener {

	private Context context;
	private int maxSampleSize = 16;
	
	public LargeImageOpener(Context context) {
		this.context = context;
	}
	
	public int getMaxSampleSize() {
		return maxSampleSize;
	}
	
	public void setMaxSampleSize(int size) {
		maxSampleSize = size;
	}
	
	public Bitmap openImage(Uri uri) {
		Bitmap bmp = null;
		boolean outOfMemory = true;
		int sampleSize = 2;		

		while (outOfMemory && sampleSize <= maxSampleSize) {
			try {
				bmp = openSampledImage(uri, sampleSize);
				outOfMemory = false;
			} catch (OutOfMemoryError e) {
				sampleSize++;
			} catch (Exception e) {
				break;
			}
		}
		
		return bmp;
	}
	
	private Bitmap openSampledImage(Uri uri, int sampleSize) throws Exception {
		Options options = new BitmapFactory.Options();
		options.inSampleSize = sampleSize;
		return openImageWithOptions(uri, options);
	}
	
	private Bitmap openImageWithOptions(Uri uri, Options options) throws Exception {
		InputStream in = context.getContentResolver().openInputStream(uri);
		Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
		in.close();
		return bmp;
	}

}