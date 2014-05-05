package br.ufrj.superchef.domain;

public enum DietEnum {

	UNKNOWN(0,"Todos"),BAIXOCOLESTEROL(1, "Baixo Colesterol"), BAIXOSODIO(2, "Baixo S�dio"), DIET(
			3, "Diet"), KOSHER(4, "Kosher"), LIGHT(5, "Light"), MACROBIOTICA(6,
			"Macrobi�tica"), SAUDAVEL(7, "Saud�vel"), SEMGLUTEN(8, "Sem Glut�n"), SEMLACTOSE(
			9, "Sem Lactose"), VEGETARIANA(10, "Vegetariana");

	private String name;

	private int id;

	private DietEnum() {
	}

	DietEnum(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public static DietEnum getById(int id) {
		for (DietEnum e : values()) {
			if (e.id == id)
				return e;
		}
		return null;
	}
	
	public static int getByName(String name) {
		for (DietEnum e : values()) {
			if (e.name.equals(name))
				return e.id;
		}
		return 0;
	}

}
