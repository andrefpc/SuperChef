package domain;

public enum RecipyCousineEnum {

	UNKNOWN(0,"Cozinha da receita"),BRASILEIRA(1, "Brasileira"), BAINA(2, "Baiana"), GAUCHA(3, "Gaúcha"), MINEIRA(
			4, "Mineira"), NORDESTINA(5, "Nordestina"), ALEMA(6, "Alemã"), AMERICANA(
			7, "Americana"), ARABE(8, "Árabe"), CHINESA(9, "Chinesa"), ESPANHOLA(
			10, "Espanhola"), FRANCESA(11, "Francesa"), GREGA(12, "Grega"), INDIANA(
			13, "Indiana"), ITALIANA(14, "Italiana"), JAPONESA(15, "Japonesa"), MEXICANA(
			16, "Mexicana"), PORTUGUESA(17, "Portuguesa"), TAILANDESA(18,
			"Tailandesa");

	private String name;

	private int id;

	RecipyCousineEnum(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public static RecipyCousineEnum getById(int id) {
		for (RecipyCousineEnum e : values()) {
			if (e.id == id)
				return e;
		}
		return null;
	}

}
