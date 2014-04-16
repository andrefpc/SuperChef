package domain;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Recipy implements Serializable{

	private static final long serialVersionUID = 5637595838300262403L;

	@SerializedName("id")
	private int id;
	
	@SerializedName("urlImagem")
	private String imageUrl;
	
	@SerializedName("titulo")
	private String titulo;
	
	@SerializedName("modoPreparo")
	private String modoPreparo;
	
	@SerializedName("porcoes")
	private int porcoes;
	
	@SerializedName("minDuracao")
	private int minDuracao;
	
	@SerializedName("ingredientes")
	private List<Ingredientes> ingredientes;
	
	@SerializedName("tags")
	private List<String> tags;

	public Recipy() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getModoPreparo() {
		return modoPreparo;
	}
	public void setModoPreparo(String modoPreparo) {
		this.modoPreparo = modoPreparo;
	}
	public int getPorcoes() {
		return porcoes;
	}
	public void setPorcoes(int porcoes) {
		this.porcoes = porcoes;
	}
	public int getMinDuracao() {
		return minDuracao;
	}
	public void setMinDuracao(int minDuracao) {
		this.minDuracao = minDuracao;
	}
	public List<Ingredientes> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(List<Ingredientes> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	
	
}
