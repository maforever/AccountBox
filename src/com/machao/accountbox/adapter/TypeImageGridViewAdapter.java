package com.machao.accountbox.adapter;

import com.machao.accountbox.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class TypeImageGridViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private int[] imgIds;
	private int resourceId;
	private int currentSelectedIndex = -1;
	public TypeImageGridViewAdapter(Context context, int[] imgIds, int resourceId) {
		this.inflater = LayoutInflater.from(context);
		this.imgIds = imgIds;
		this.resourceId = resourceId;
	}
	
	@Override
	public int getCount() {
		return imgIds.length;
	}

	@Override
	public Object getItem(int position) {
		return imgIds[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setSelected(int selectedIndex) {
		this.currentSelectedIndex = selectedIndex;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView image, selectedTag;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(resourceId, null);
			image = (ImageView) convertView.findViewById(R.id.image);
			selectedTag = (ImageView) convertView.findViewById(R.id.selectedTag);
			vh = new ViewHolder();
			vh.image = image;
			vh.selectedTag = selectedTag;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			image = vh.image;
			selectedTag = vh.selectedTag;
		}
		
		image.setImageResource(imgIds[position]);
		selectedTag.setVisibility(View.GONE);
		if(position == currentSelectedIndex) {
			selectedTag.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}

	static class ViewHolder {
		ImageView image, selectedTag;
	}
	
}



















