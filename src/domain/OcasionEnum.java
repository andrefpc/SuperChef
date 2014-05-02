package domain;

public enum OcasionEnum {

	UNKNOWN(0,"Todos"),ANIVERSARIO(1, "Aniversário"), CASAMENTO(2, "Casamento"), FESTAINFANTIL(3,
			"Festa Infantil"), FESTAJUNINA(4, "Festa Junina"), INVERNO(5,
			"Inverno"), NATAL(6, "Natal"), PASCOA(7, "Páscoa"), RECEPCAO(8,
			"Recepção"), REVEILLON(9, "Réveillon"), VERAO(10, "Verão");

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

}
