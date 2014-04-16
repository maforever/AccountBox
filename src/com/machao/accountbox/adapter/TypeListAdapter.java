package com.machao.accountbox.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.machao.accountbox.R;
import com.machao.accountbox.db.service.model.Type;

public class TypeListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Type> types;
	private int resourceId;
	private int currentParentSelectedIndex = -1;
	private int currentChildSelectedIndex = -1;
	public TypeListAdapter(Context context, List<Type> types, int resouceId) {
		this.inflater = LayoutInflater.from(context);
		this.types = types;
		this.resourceId = resouceId;
	}
	
	@Override
	public int getCount() {
		return types.size();
	}

	@Override
	public Object getItem(int arg0) {
		return types.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	public void setParentSelected(int parentSelectedIndex) {
		this.currentParentSelectedIndex = parentSelectedIndex;
		this.notifyDataSetChanged();
	}
	
	public void setChildSelected(int childSelectedIndex) {
		this.currentChildSelectedIndex = childSelectedIndex;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView name;
		ImageView image;
		LinearLayout bg;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(resourceId, null);
			bg = (LinearLayout) convertView.findViewById(R.id.bg);
			name = (TextView) convertView.findViewById(R.id.name);
			image = (ImageView) convertView.findViewById(R.id.image);
			vh = new ViewHolder();
			vh.bg = bg;
			vh.name = name;
			vh.image = image;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			bg = vh.bg;
			name = vh.name;
			image = vh.image;
		}
		
		name.setText(types.get(position).getName());
		int imageId = types.get(position).getImgId();
		image.setImageResource(imageId);
		bg.setBackgroundResource(0);
		if(currentParentSelectedIndex == position || currentChildSelectedIndex == position) {
			bg.setBackgroundResource(R.drawable.type_list_select_bg);
		}
		return convertView;
	}

	static class ViewHolder {
		LinearLayout bg;
		TextView name;
		ImageView image;
	}
}



















