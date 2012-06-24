package com.quaintsoft.imageviewer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class OpenLargeImageDialog extends AlertDialog 
		implements OnClickListener, OnSeekBarChangeListener {

	private Context context;
	private OnLargeImageResizeSetListener callBack;
	private View view;
	
	private Uri imageUri;
	
	public interface OnLargeImageResizeSetListener {
		void onResizeSet(Uri imageUri, int resizeValue);
	}
	
	protected OpenLargeImageDialog(Context context,
			OnLargeImageResizeSetListener callBack) {
		super(context);
		
		this.context = context;
		this.callBack = callBack;

		setTitle(R.string.open_large_image_title);
		setView(createView());
		setButton(BUTTON_POSITIVE, "ok", this);
		setButton(BUTTON_NEGATIVE, "cancel", (OnClickListener)null);
		
		setupView();
	}
	
	private View createView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.open_large_image, null);
		return view;
	}
	
	private void setupView() {
		getResizeSeekBar().setOnSeekBarChangeListener(this);
	}
	
	private SeekBar getResizeSeekBar() {
		return (SeekBar)view.findViewById(R.id.open_large_image_resize_seekbar);
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		getResizeTextView().setText(String.valueOf(getResizeValue()) + "%");
	}
	
	private TextView getResizeTextView() {
		return (TextView)view.findViewById(R.id.open_large_image_resize_value);
	}
	
	private int getResizeValue() {
		return getResizeSeekBar().getProgress();
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case BUTTON_POSITIVE:
			if (callBack != null)
				callBack.onResizeSet(imageUri, getResizeValue());
			break;
		}
	}
	
	public void setImageUri(Uri uri) {
		imageUri = uri;
	}

}
