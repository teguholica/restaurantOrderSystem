package com.waiki.pesanmakan.menu;

import java.util.ArrayList;

import com.waiki.pesanmakan.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<MenuObj> menuObjs;
	
	public MenuAdapter(Context context,ArrayList<MenuObj> menuObjs) {
		this.context = context;
		this.menuObjs = menuObjs;
	}

	@Override
	public int getCount() {
		return menuObjs.size();
	}

	@Override
	public MenuObj getItem(int arg0) {
		return menuObjs.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null){
			convertView = inflater.inflate(R.layout.menu_item, null);
		}
		
		MenuObj menuObj = menuObjs.get(position);
		
		TextView txtName = (TextView) convertView.findViewById(R.id.menu_item_txt_name);
		txtName.setText(menuObj.getName());
		
		TextView txtPrice = (TextView) convertView.findViewById(R.id.menu_item_txt_price);
		txtPrice.setText(menuObj.getPrice());
		
		return convertView;
	}

}
