package service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharedprefs.R;

import domain.ItemList;

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
//
//			ImageView image = (ImageView) view.findViewById(R.id.imageRecipe);
			TextView title = (TextView) view.findViewById(R.id.titleRecipe);
//			
//			Bitmap imageBmp = getBitmapFromURL(item.getImageUrl());
			
			title.setText(item.getTitle());
//			image.setImageBitmap(imageBmp);
		}

		return view;

	}
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}