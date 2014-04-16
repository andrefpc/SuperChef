package domain;

public class ItemList {

	private String imageUrl;
	private String title;
	
	public ItemList(String imageUrl, String title) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
	}
	
	public ItemList() {
		super();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
