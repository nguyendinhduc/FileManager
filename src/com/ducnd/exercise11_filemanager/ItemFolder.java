package com.ducnd.exercise11_filemanager;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;
import android.widget.CheckBox;

public class ItemFolder{
	String name, size, path;
	Bitmap imageIcon;
	private int id = -1; 
	boolean isChecked = false;
	public ItemFolder(Bitmap imageIcon, String name,String path, String size, boolean isChecked) {
		this.imageIcon = imageIcon;
		this.name = name;
		this.size = size;
		this.path = path;
		this.isChecked = isChecked;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
}
