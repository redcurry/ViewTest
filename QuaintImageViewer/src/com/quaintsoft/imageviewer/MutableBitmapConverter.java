package com.quaintsoft.imageviewer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class MutableBitmapConverter {

	private File tempFile;
	private RandomAccessFile tempRandomAccessFile;
	private FileChannel channel;
	
	public Bitmap convertToMutable(Bitmap bmp) {
		try {
			Buffer cache = createMappedBuffer(bmp.getWidth() * bmp.getHeight() * 4);
			bmp.copyPixelsToBuffer(cache);
			
			bmp = clearBitmap(bmp);
			
			cache.position(0);
			bmp.copyPixelsFromBuffer(cache);
			
			cleanUp();
		}
		catch (Exception e) {
			return null;
		}

		return bmp;
	}
	
	private Buffer createMappedBuffer(int size) throws IOException {
		tempFile = File.createTempFile(".bmp", null);
		tempRandomAccessFile = new RandomAccessFile(tempFile, "rw");
		channel = tempRandomAccessFile.getChannel();
		return channel.map(MapMode.READ_WRITE, 0, size);
	}
	
	private Bitmap clearBitmap(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Config config = bmp.getConfig();
		bmp.recycle();
		return Bitmap.createBitmap(width, height, config);
	}

	private void cleanUp() throws IOException {
		channel.close();
		tempRandomAccessFile.close();
		tempFile.delete();
	}
	
}
