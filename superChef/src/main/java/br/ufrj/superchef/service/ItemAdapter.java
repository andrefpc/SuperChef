package br.ufrj.superchef.service;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufrj.superchef.R;

import br.ufrj.superchef.domain.ItemList;

public class ItemAdapter extends ArrayAdapter<ItemList> {

	private ArrayList<ItemList> objects;

	public ItemAdapter(Context context, int textViewResourceId, ArrayList<ItemList> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){

		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_layout, null);
		}
		
		ItemList item = objects.get(position);

		if (item != null) {

			ImageView image = (ImageView) view.findViewById(R.id.imageRecipe);
			TextView title = (TextView) view.findViewById(R.id.titleRecipe);

			
			title.setText(item.getTitle());
			image.setImageBitmap(item.getImageUrl());
		}

		return view;

	}

}