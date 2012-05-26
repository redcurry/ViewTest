package com.quaintsoft.imageviewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class BitmapFileWriter {
	
	private FileOutputStream out;
	
	public BitmapFileWriter(File file) throws FileNotFoundException {
		out = new FileOutputStream(file);
	}
	
	public void write(Bitmap bmp, CompressFormat format, int quality) throws IOException {
		if (!bmp.compress(format, quality, out))
			throw new IOException();
	}
	
	public void close() throws IOException {
		out.flush();
		out.close();
	}
	
}
