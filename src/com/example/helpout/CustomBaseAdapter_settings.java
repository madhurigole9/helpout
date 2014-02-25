package com.example.helpout;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter_settings extends BaseAdapter{
	
		Context context;
	    List<rowitem_settings> rowItems;
	 
	    public CustomBaseAdapter_settings(Context context, List<rowitem_settings> items) {
	        this.context = context;
	        this.rowItems = items;
	    }
	 
	    /*private view holder class*/
	    private class ViewHolder {
	        ImageView imageView;
	        TextView txtTitle;
	    }
	 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder = null;
	 
	        LayoutInflater mInflater = (LayoutInflater)
	            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.listitem_settings, null);
	            holder = new ViewHolder();
	            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
	            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
	            convertView.setTag(holder);
	        }
	        else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	 
	        rowitem_settings rowItem = (rowitem_settings) getItem(position);

	        holder.txtTitle.setText(rowItem.getTitle());
	        holder.imageView.setImageResource(rowItem.getImageId());
	 
	        return convertView;
	    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return rowItems.indexOf(getItem(position));
	}


}
