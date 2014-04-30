package repository;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import domain.Ingrediente;
import domain.Recipe;

public class FavoriteDao {

	private SQLiteDatabase database;
	private String[] columnsReceita = { "id", "titulo", "modo_preparo", "porcoes", "min_duracao", "img_url" };
	private String[] columnsIngredientes = { "id", "nome", "receita_id" };
	private CustomSQLiteOpenHelper sqliteOpenHelper;
	
	public FavoriteDao(Context context){
		sqliteOpenHelper = new CustomSQLiteOpenHelper(context);
	}
	
	public void open() throws SQLException{
		database = sqliteOpenHelper.getWritableDatabase();
	}
	
	public void close(){
		sqliteOpenHelper.close();
	}
	
	public void create(Recipe recipe){
		
		int idReceita = 0;
		String sqlIdReceita = "SELECT id from receita order by id DESC limit 1";
		Cursor cursorReceita = database.rawQuery(sqlIdReceita, new String[]{});
		if (cursorReceita != null && cursorReceita.moveToFirst()) {
			idReceita = cursorReceita.getInt(0)+1; //The 0 is the column index, we only have 1 column, so the index is 0
		}
		cursorReceita.close();
		
		String sqlReceita  = "insert into receita(id, titulo, modo_preparo, porcoes, min_duracao,img_url) values(?, ?, ?, ?, ?, ?);";
		String sqlIngrediente = "insert into ingrediente(id, nome, receita_id) values(?, ?, ?);";
		database.execSQL(sqlReceita, new String[]{String.valueOf(idReceita), recipe.getTitulo(), recipe.getModoPreparo(), 
				String.valueOf(recipe.getPorcoes()), String.valueOf(recipe.getMinDuracao()), 
				recipe.getImageUrl()});
		
		int idIngrediente = 0;
		String sqlIdIngrediente = "SELECT id from ingrediente order by id DESC limit 1";
		Cursor cursorIngrediente = database.rawQuery(sqlIdIngrediente, new String[]{});
		if (cursorIngrediente != null && cursorIngrediente.moveToFirst()) {
			idIngrediente = cursorIngrediente.getInt(0)+1; //The 0 is the column index, we only have 1 column, so the index is 0
		}
		cursorIngrediente.close();

		
		for (Ingrediente ingrediente : recipe.getIngredientes()) {
			database.execSQL(sqlIngrediente, new String[]{String.valueOf(idIngrediente), ingrediente.getNome(), String.valueOf(idReceita)});
			idIngrediente++;
		}

	}
	
	public void delete(Recipe receita){
		long id = receita.getId();
		database.delete(CustomSQLiteOpenHelper.TABLE_RECIPE, "id = " + id, null);
		database.delete(CustomSQLiteOpenHelper.TABLE_INGREDIENTE, "receita_id = " + id, null);
	}
	
	public boolean isFavorite(String name){
		String sqlId = "SELECT id from receita where titulo like ? order by id";
		Cursor cursorReceita = database.rawQuery(sqlId, new String[]{name});
		if (cursorReceita != null && cursorReceita.moveToFirst()) {
			return true;
		}
		return false;
		
	}
	
	public List<Recipe> getAll(){
		List<Recipe> receitas = new ArrayList<Recipe>();
		
		Cursor cursorReceita = database.query(CustomSQLiteOpenHelper.TABLE_RECIPE, columnsReceita, null, null, null, null, null);
		cursorReceita.moveToFirst();
		
		while(!cursorReceita.isAfterLast()){
			Recipe receita = new Recipe();
			receita.setId(cursorReceita.getInt(0));
			receita.setTitulo(cursorReceita.getString(1));
			receita.setModoPreparo(cursorReceita.getString(2));
			receita.setPorcoes(cursorReceita.getInt(3));
			receita.setMinDuracao(cursorReceita.getInt(4));
			receita.setImageUrl(cursorReceita.getString(5));
			
			Cursor cursorIngredientes = database.query(CustomSQLiteOpenHelper.TABLE_INGREDIENTE, columnsIngredientes, "receita_id = " + cursorReceita.getInt(0), null, null, null, null);
			cursorIngredientes.moveToFirst();

			List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
			while(!cursorIngredientes.isAfterLast()){
				Ingrediente ingrediente = new Ingrediente();
				ingrediente.setId(cursorIngredientes.getInt(0));
				ingrediente.setNome(cursorIngredientes.getString(1));
				ingredientes.add(ingrediente);
				cursorIngredientes.moveToNext();
			}
			cursorIngredientes.close();
			receita.setIngredientes(ingredientes);
			receitas.add(receita);
			cursorReceita.moveToNext();
		}
		cursorReceita.close();
		
		return receitas;
	}

}
