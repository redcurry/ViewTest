package com.quaintsoft.imageviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.quaintsoft.imageviewer.color.BitmapColorChangerByColorFilter;
import com.quaintsoft.imageviewer.color.Grayscale;
import com.quaintsoft.imageviewer.color.InvertColors;
import com.quaintsoft.imageviewer.preference.FitPreferenceApplier;
import com.quaintsoft.imageviewer.preference.PreferenceListener;
import com.quaintsoft.imageviewer.preference.Preferences;

public class QuaintImageViewerActivity extends Activity {
	private static final int OPEN_REQUEST_CODE  = 1;
	
	private static final String IMAGE_MATRIX_BUNDLE_KEY = "IMAGE_MATRIX";
	
	private static final int DIALOG_SAVE = 1;
	private static final int DIALOG_BRIGHTNESS_CONTRAST = 2;
	private static final int DIALOG_GAMMA = 3;
	private static final int DIALOG_HUE_SATURATION = 4;
	private static final int DIALOG_COLOR_BALANCE = 5;
	
	private ImageViewModel imageViewModel;
	private ImageViewOnTouchListener imageViewOnTouchListener;
	private ImageViewZoomer imageViewZoomer;
	
	private PreferenceListener prefListener;
	private FitPreferenceApplier fitPrefApplier;
	
	private Uri imageUri;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		setupImageView();
		setupPreferences();
		Log.d("blah", System.getProperty("java.io.tmpdir"));
    }
    
    private void setupImageView() {
		QuaintImageView imageView = (QuaintImageView)findViewById(R.id.image_view);
		imageViewModel = new ImageViewModel(imageView);
		imageViewOnTouchListener = new ImageViewOnTouchListener(this, imageViewModel);
		imageView.setOnTouchListener(imageViewOnTouchListener);
		imageViewZoomer = new ImageViewZoomer(imageViewModel);
    }
    
    private void setupPreferences() {
    	prefListener = new PreferenceListener(this, imageViewModel);
    	fitPrefApplier = new FitPreferenceApplier(this, imageViewZoomer);
    	
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }
    
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		restoreImage();
		restoreImageMatrix(savedInstanceState);
	}
	
	private void restoreImage() {
		Bitmap imageBitmap = (Bitmap)getLastNonConfigurationInstance();
		imageViewModel.setImageBitmap(imageBitmap);
	}
	
	private void restoreImageMatrix(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			float[] imageMatrixValues = savedInstanceState.getFloatArray(IMAGE_MATRIX_BUNDLE_KEY);
			imageViewModel.validateAndSetImageMatrix(getMatrixFromValues(imageMatrixValues));
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return imageViewModel.getImageBitmap();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveImageMatrix(outState);
	}
	
	private void saveImageMatrix(Bundle outState) {
		Matrix imageMatrix = imageViewModel.getImageMatrix();
		outState.putFloatArray(IMAGE_MATRIX_BUNDLE_KEY, getMatrixValues(imageMatrix));
	}
	
	private float[] getMatrixValues(Matrix matrix) {
		float[] values = new float[9];
		matrix.getValues(values);
		return values;
	}

	private Matrix getMatrixFromValues(float[] values) {
		Matrix matrix = new Matrix();
		matrix.setValues(values);
		return matrix;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
			case DIALOG_SAVE:
				return createSaveAsDialog();
			case DIALOG_BRIGHTNESS_CONTRAST:
				return createBrightnessContrastDialog();
			case DIALOG_GAMMA:
				return createGammaDialog();
			default:
				return super.onCreateDialog(id);
		}
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		
		switch(id) {
			case DIALOG_SAVE:
				((SaveAsDialog)dialog).reset();
				break;
			case DIALOG_BRIGHTNESS_CONTRAST:
				((BrightnessContrastDialog)dialog).reset();
				((BrightnessContrastDialog)dialog).setBitmapForPreview(imageViewModel.getImageBitmap());
				break;
			case DIALOG_GAMMA:
				((GammaDialog)dialog).reset();
				((GammaDialog)dialog).setBitmapForPreview(imageViewModel.getImageBitmap());
				break;
		}
	}

	private Dialog createSaveAsDialog() {
		return new SaveAsDialog(this, new ImageSaver(this, imageViewModel));
	}
	
	private Dialog createBrightnessContrastDialog() {
		return new BrightnessContrastDialog(this,
				new ImageBrightnessContrastChanger(this, imageViewModel));
	}
	
	private Dialog createGammaDialog() {
		return new GammaDialog(this, new ImageGammaChanger(this, imageViewModel));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menu_open:
				chooseImageToOpen();
				return true;
			case R.id.menu_save_as:
				showDialog(DIALOG_SAVE);
				return true;
			case R.id.menu_grayscale:
				changeColors(new Grayscale());
				return true;
			case R.id.menu_invert:
				changeColors(new InvertColors());
				return true;
			case R.id.menu_brightness_contrast:
				showDialog(DIALOG_BRIGHTNESS_CONTRAST);
				return true;
			case R.id.menu_gamma:
				showDialog(DIALOG_GAMMA);
				return true;
			case R.id.menu_settings:
				showSettings();
				return true;
			case R.id.menu_actual_size:
				imageViewZoomer.zoomImageToActualSize();
				return true;
			case R.id.menu_zoom_in:
				imageViewZoomer.zoomInImage();
				return true;
			case R.id.menu_zoom_out:
				imageViewZoomer.zoomOutImage();
				return true;
			case R.id.menu_fit_to_window:
				imageViewZoomer.fitImageToView();
				return true;
			case R.id.menu_fit_width_to_window:
				imageViewZoomer.fitImageWidthToView();
				return true;
			case R.id.menu_fit_height_to_window:
				imageViewZoomer.fitImageHeightToView();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void chooseImageToOpen() {
		//Intent openImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
		//openImageIntent.setType("image/*");
		//startActivityForResult(openImageIntent, OPEN_REQUEST_CODE);
		Intent openImageIntent = new Intent(Intent.ACTION_PICK);
		openImageIntent.setType("vnd.android.cursor.dir/image");
		startActivityForResult(openImageIntent, OPEN_REQUEST_CODE);
	}
	
	private void changeColors(BitmapColorChangerByColorFilter colorChanger) {
		Bitmap bmp = imageViewModel.getImageBitmap();
		colorChanger.apply(bmp);
		imageViewModel.invalidate();
	}
	
	private void showSettings() {
		Intent showSettingsIntent = new Intent(this, Preferences.class);
		startActivity(showSettingsIntent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case OPEN_REQUEST_CODE:
				if (resultCode == RESULT_OK)
					openImage(data.getData());
				break;
			}
	}

	private void openImage(Uri data) {
		try {
			imageViewModel.setImageURI(data);
		} catch (OutOfMemoryError e) {
			openLargeImage(data);
		}

		fitPrefApplier.apply();
	}
	
	private void openLargeImage(Uri data) {
		// TODO: Tell user that image was resized
		LargeImageOpener largeImageOpener = new LargeImageOpener(this);
		Bitmap bmp = largeImageOpener.openImage(data);
		imageViewModel.setImageBitmap(bmp);
	}
	
	private void printMemoryInfo() {
		try {
			File memfile = new File("/proc/meminfo");
			BufferedReader in = new BufferedReader(new FileReader(memfile));
			String lines = "";
			String line = in.readLine();
			while (line != null) {
				lines += line;
				line = in.readLine();
			}
			Log.d("memory", lines);
		} catch (IOException e) {
			
		}
	}
	
}
