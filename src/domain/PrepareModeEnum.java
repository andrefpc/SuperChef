package domain;

public enum PrepareModeEnum {

	UNKNOWN(0,"Modo de preparo"),ASSADO(1, "Assado"), COZIDO(2, "Cozido"), CRU(3, "Cru"), ENSOPADO(4,
			"Ensopado"), FRITO(5, "Frito"), GELADOECONGELADO(6,
			"Gelado e Congelado"), GRELHADO(7, "Grelhado"), MICROONDAS(8,
			"Microondas"), REFODADO(9, "Refofado"), VAPOR(10, "Vapor");

	private String name;

	private int id;

	PrepareModeEnum(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public static PrepareModeEnum getById(int id) {
		for (PrepareModeEnum e : values()) {
			if (e.id == id)
				return e;
		}
		return null;
	}

}
