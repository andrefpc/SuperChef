package domain;

import android.graphics.Bitmap;

public class ItemList {

	private Bitmap imageUrl;
	private String title;
	
	public ItemList(Bitmap imageUrl, String title) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
	}
	
	public ItemList() {
		super();
	}

	public Bitmap getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(Bitmap imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
