package domain;

public enum CookTypeEnum {

	UNKNOWN(0,"Todos"),ACOMPANHAMENTOS(1, "Acompanhamentos"), AVES(2, "Aves"), BEBIDAS(3,
			"Bebidas"), CARNES(4, "Carnes"), DOCESESOBREMESAS(5,
			"Doces e Sobremesas"), ENTRADAS(6, "Entradas"), LANCHES(7,
			"Lanches"), MASSAS(8, "Massas"), MOLHOS(9, "Molhos"), PAESESALGADOS(
			10, "Pães e Salgados"), PEIXESEFRUTOSDOMAR(11,
			"Peixes e Frutos do Mar"), SALADAS(12, "Saladas"), SOPAS(13,
			"Sopas"), TORTASEBOLOS(14, "Tortas e Bolos");

	private String name;

	private int id;

	CookTypeEnum(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public static CookTypeEnum getById(int id) {
		for (CookTypeEnum e : values()) {
			if (e.id == id)
				return e;
		}
		return null;
	}

}
