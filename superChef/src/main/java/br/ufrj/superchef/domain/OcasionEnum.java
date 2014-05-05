package br.ufrj.superchef.domain;

public enum OcasionEnum {

	UNKNOWN(0,"Todos"),ANIVERSARIO(1, "Anivers�rio"), CASAMENTO(2, "Casamento"), FESTAINFANTIL(3,
			"Festa Infantil"), FESTAJUNINA(4, "Festa Junina"), INVERNO(5,
			"Inverno"), NATAL(6, "Natal"), PASCOA(7, "P�scoa"), RECEPCAO(8,
			"Recep��o"), REVEILLON(9, "R�veillon"), VERAO(10, "Ver�o");

	private String name;

	private int id;

	OcasionEnum(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public static OcasionEnum getById(int id) {
		for (OcasionEnum e : values()) {
			if (e.id == id)
				return e;
		}
		return null;
	}
	
	public static int getByName(String name) {
		for (OcasionEnum e : values()) {
			if (e.name.equals(name))
				return e.id;
		}
		return 0;
	}

}
