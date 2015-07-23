package com.ducnd.property;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ContentProperty implements Serializable{
	private String name, path, parent, type, size, modify;

	public ContentProperty(String name, String path, String parent,
			String type, String size, String modify) {
		super();
		this.name = name;
		this.path = path;
		this.parent = parent;
		this.type = type;
		this.size = size;
		this.modify = modify;
	}
	@SuppressLint("SimpleDateFormat")
	public static String convetDate( long time ) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}
	
}
