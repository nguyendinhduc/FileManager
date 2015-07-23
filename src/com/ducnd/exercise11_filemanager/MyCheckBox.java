package com.ducnd.exercise11_filemanager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class MyCheckBox extends CheckBox{
	private int posistion = -1;
	public MyCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyCheckBox(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	

	public int getPosistion() {
		return posistion;
	}

	public void setPosistion(int posistion) {
		this.posistion = posistion;
	}
	

}
