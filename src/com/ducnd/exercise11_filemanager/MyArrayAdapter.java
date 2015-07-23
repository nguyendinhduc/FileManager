package com.ducnd.exercise11_filemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyArrayAdapter extends ArrayAdapter<ItemFolder> implements CompoundButton.OnCheckedChangeListener {
	private int layoutId;
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<ItemFolder> folderList;
	private ViewGroup parent;
	public static int TYPE_LIST_SEARCH = 9829;
	private int type = -1;
	
	
	public MyArrayAdapter(Context context, int layoutId,
			ArrayList<ItemFolder> folderList) {
		super(context, layoutId, folderList);
		this.folderList = new ArrayList<ItemFolder>();
		this.folderList.addAll(folderList);
		this.mContext = context;
		this.inflater = LayoutInflater.from(mContext);
		this.layoutId = layoutId;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ArrayList<ItemFolder> getFolderList() {
		return folderList;
	}

	public void setFolderList(ArrayList<ItemFolder> folderList) {
		this.folderList = folderList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		HolderFolder holder = new HolderFolder();
//		View row = convertView;
//		this.parent = parent;
//		if (convertView == null) {
//			LayoutInflater inflater = ((Activity) this.mContext)
//					.getLayoutInflater();
//			row = inflater.inflate(layoutId, parent, false);
//
//			holder = new HolderFolder();
//			holder.imageIcon = ((ImageView) row.findViewById(R.id.imageIcon));
//			holder.name = ((TextView) row.findViewById(R.id.textName));
//			holder.name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Kingthing.ttf"));
//			holder.name.setTextSize(30);
//			TextView size =((TextView) row.findViewById(R.id.textSize));
//			holder.size = size;
//			holder.size.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/EveryTime.ttf"));
//			
//			MyCheckBox check = ((MyCheckBox) row.findViewById(R.id.checkBox));
//			
//			if ( folderList.get(position).isChecked ) {
//				check.setChecked(true);
//			}
//			else {
//				check.setChecked(false);
//			}
//			holder.check = check;
//			check.setPosistion(position);
//			check.setOnCheckedChangeListener(this);
//			
//			if ( type == TYPE_LIST_SEARCH ) {
//				check.setVisibility(View.GONE);
//				holder.size.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Stag_Sans.ttf"));
//			}
//			row.setTag(holder);

//		} else {
//			holder = (HolderFolder) convertView.getTag();
//		}
//		
//		ItemFolder itemFolder = getItem(position);
//		itemFolder = folderList.get(getPosistionListFolder(itemFolder));
//		holder.imageIcon.setImageBitmap(itemFolder.imageIcon);
//		holder.name.setText(itemFolder.name);
//		holder.size.setText(itemFolder.size);
//		holder.check.setChecked(itemFolder.isChecked);
//
//		row.setBackgroundResource(R.drawable.bg_state);
//		
//		return row;
		if ( convertView == null ) {
			convertView = inflater.inflate(layoutId, null);
		}
		HolderFolder holder = new HolderFolder();
		holder = new HolderFolder();
		holder.imageIcon = ((ImageView)convertView.findViewById(R.id.imageIcon));
		holder.name = ((TextView)convertView.findViewById(R.id.textName));
		holder.name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Kingthing.ttf"));
		holder.name.setTextSize(30);	
		holder.size = ((TextView)convertView.findViewById(R.id.textSize));
		holder.size.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/EveryTime.ttf"));
		holder.check = ((MyCheckBox)convertView.findViewById(R.id.checkBox));
		holder.check.setPosistion(position);
		holder.check.setOnCheckedChangeListener(this);
		
		ItemFolder itemFolder = getItem(position);
		holder.check.setChecked(itemFolder.isChecked);
		holder.imageIcon.setImageBitmap(itemFolder.imageIcon);
		holder.name.setText(itemFolder.name);
		holder.size.setText(itemFolder.size);
		if ( type == TYPE_LIST_SEARCH ) {
			holder.check.setVisibility(View.GONE);
			holder.size.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Stag_Sans.ttf"));
		}
		
		return convertView;
		
	}

	public View getView(int position) {
		View row = null;
		LayoutInflater inflater = ((Activity) this.mContext)
				.getLayoutInflater();
		row = inflater.inflate(layoutId, this.parent, false);

		HolderFolder holder = new HolderFolder();
		holder.imageIcon = ((ImageView) row.findViewById(R.id.imageIcon));
		holder.name = ((TextView) row.findViewById(R.id.textName));

		holder.size = ((TextView) row.findViewById(R.id.textSize));
		holder.check = ((MyCheckBox)row.findViewById(R.id.checkBox));

		row.setTag(holder);

		ItemFolder itemFolder = folderList.get(position);
		holder.imageIcon.setImageBitmap(itemFolder.imageIcon);
		holder.name.setText(itemFolder.name);
		holder.size.setText(itemFolder.size);
		holder.check.setChecked(itemFolder.isChecked);

		return row;
	}
	private class HolderFolder {
		ImageView imageIcon;
		TextView name, size;
		MyCheckBox check;
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		MyCheckBox check = (MyCheckBox)buttonView;
//		check.setChecked(isChecked);
//		int tem = check.getPosistion();
//		ItemFolder temItem = getItem(tem);
//		int po = getPosistionListFolder(temItem);
//		folderList.get(po).isChecked = isChecked;
		folderList.get(check.getPosistion()).isChecked = isChecked;
		
	}
	private int getPosistionListFolder(ItemFolder item) {
		int posision = -1;
		int count = 0;
		for ( ItemFolder i:folderList ) {
			if (i.equals(item) ) {
				posision = count;
				break;
			}
			count++;
		}
		return posision;
	}

}
