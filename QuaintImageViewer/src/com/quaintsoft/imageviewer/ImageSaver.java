package com.quaintsoft.imageviewer;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.widget.Toast;

import com.quaintsoft.imageviewer.SaveAsDialog.OnSaveListener;

public class ImageSaver implements OnSaveListener {
	
	private Context context;
	private ImageViewModel imageViewModel;
	
	public ImageSaver(Context context, ImageViewModel imageViewModel) {
		this.context = context;
		this.imageViewModel = imageViewModel;
	}

	public void onSave(File filePath, CompressFormat fileFormat, int jpegQuality) {
		try {
			BitmapFileWriter writer = new BitmapFileWriter(filePath);
			writer.write(imageViewModel.getImageBitmap(), fileFormat, jpegQuality);
			writer.close();
		}
		catch (Exception e) {
			// TODO: Another type of error when bmp == null
			Toast alert = Toast.makeText(context,
					"Unable to save file to " + filePath.getPath(),
					Toast.LENGTH_SHORT);
			alert.show();
		}
	}

}
