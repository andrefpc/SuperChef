package br.ufrj.superchef.domain;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Serializable{

	private static final long serialVersionUID = 8523114962932950812L;

	@SerializedName("id")
	private int id;
	
	@SerializedName("nome")
	private String nome;

	public Ingredient() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
