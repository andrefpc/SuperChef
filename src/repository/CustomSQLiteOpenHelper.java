package repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_RECIPE = "receita";
	public static final String TABLE_INGREDIENTE = "ingrediente";
	public static final String DATABASE_NAME = "receitas.db";
	public static final int DATABASE_VERSION = 1;

	//Database create sql statement
	private static final String REDIPE_CREATE = "CREATE TABLE " + TABLE_RECIPE +
			"(id integer primary key autoincrement, " +
			"titulo text, " +
			"modo_preparo text, " +
			"porcoes integer, " +
			"min_duracao integer, " +
			"img_url character varying(500))";
	
	private static final String INGREDINTE_CREATE = "CREATE TABLE " + TABLE_INGREDIENTE +
			"(id integer primary key autoincrement , " +
			"nome text NOT NULL, " +
			"receita_id integer, " +
			"CONSTRAINT ingrediente_receita_id_fkey FOREIGN KEY (receita_id) " +
			"REFERENCES receita (id))";
	
	public CustomSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(REDIPE_CREATE);
		db.execSQL(INGREDINTE_CREATE);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTE);
		onCreate(db);	
	}

}
