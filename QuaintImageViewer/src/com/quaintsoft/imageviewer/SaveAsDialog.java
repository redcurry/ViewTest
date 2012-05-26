package com.quaintsoft.imageviewer;

import java.io.File;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SaveAsDialog extends AlertDialog implements OnClickListener,
		OnCheckedChangeListener, OnSeekBarChangeListener {

	public static final int DEFAULT_JPEG_QUALITY = 95;
	
	private Context context;
	private OnSaveListener callBack;
	private View view;
	
	public interface OnSaveListener {
		void onSave(File filePath, CompressFormat fileFormat, int jpegQuality);
	}
	
	public SaveAsDialog(Context context, OnSaveListener callBack) {
		super(context);
		
		this.context = context;
		this.callBack = callBack;
		
		setTitle(R.string.save_as_title);
		setView(createView());
		setButton(BUTTON_POSITIVE, "ok", this);
		setButton(BUTTON_NEGATIVE, "cancel", (OnClickListener)null);
		
		setupView();
	}
	
	private View createView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.save_as, null);
		return view;
	}
	
	private void setupView() {
		setupFormatRadioGroup();
		setupJpegQualitySeekBar();
	}
	
	private void setupFormatRadioGroup() {
		RadioGroup formatRadioGroup = getFileFormatRadioGroup();
		formatRadioGroup.setOnCheckedChangeListener(this);
	}
	
	private RadioGroup getFileFormatRadioGroup() {
		return (RadioGroup)view.findViewById(R.id.save_as_format_radio_group);
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton jpegRadioButton = getJpegRadioButton();
		if (jpegRadioButton.isChecked())
			setEnabledJpegQualityLayout(true);
		else
			setEnabledJpegQualityLayout(false);
	}
	
	private RadioButton getJpegRadioButton() {
		return (RadioButton)view.findViewById(R.id.save_as_jpeg_radio);
	}
	
	private void setEnabledJpegQualityLayout(boolean enabled) {
		LinearLayout jpegQualityLayout = getJpegQualityLayout();
		for (int i = 0; i < jpegQualityLayout.getChildCount(); i++)
			jpegQualityLayout.getChildAt(i).setEnabled(enabled);
	}

	private void setupJpegQualitySeekBar() {
		SeekBar jpegQualitySeekBar = getJpegQualitySeekBar();
		jpegQualitySeekBar.setOnSeekBarChangeListener(this);
	}
	
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		TextView qualityValueTextView = getJpegQualityTextView();
		qualityValueTextView.setText(String.valueOf(progress));
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}
	
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
	
	private LinearLayout getJpegQualityLayout() {
		return (LinearLayout)view.findViewById(R.id.save_as_quality_layout);
	}
	
	private SeekBar getJpegQualitySeekBar() {
		return (SeekBar)view.findViewById(R.id.save_as_quality_seek);
	}
	
	private TextView getJpegQualityTextView() {
		return (TextView)view.findViewById(R.id.save_as_quality_value);
	}

	public void onClick(DialogInterface dialog, int which) {
		if (callBack != null)
			callBack.onSave(getFilePath(), getFileFormat(), getJpegQuality());
	}
	
	private File getFilePath() {
		return new File(getPicturesDirectory(), getFileName());
	}
	
	private File getPicturesDirectory() {
		File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		picturesDir.mkdir();
		return picturesDir;
	}
	
	private String getFileName() {
		String fileName = getFileNameEdit().getText().toString();
		return appendExtensionIfNecessary(fileName);
	}
		
	private EditText getFileNameEdit() {
		return (EditText)view.findViewById(R.id.save_as_file_name_edit);
	}
	
	private String appendExtensionIfNecessary(String fileName) {
		String extension = getExtension();
		if (fileName.endsWith(extension))
			return fileName;
		else
			return fileName + extension;
	}
	
	private String getExtension() {
		if (getFileFormat() == CompressFormat.JPEG)
			return ".jpg";
		else
			return ".png";
	}
	
	private CompressFormat getFileFormat() {
		if (getJpegRadioButton().isChecked())
			return CompressFormat.JPEG;
		else
			return CompressFormat.PNG;
	}
	
	private int getJpegQuality() {
		return getJpegQualitySeekBar().getProgress();
	}
	
	public void reset() {
		getFileNameEdit().setText("");
		getJpegRadioButton().setChecked(true);
		getJpegQualitySeekBar().setProgress(DEFAULT_JPEG_QUALITY);
	}

}
