package br.ufrj.superchef.repository;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RatedDao {
	
	private SQLiteDatabase database;
	private CustomSQLiteOpenHelper sqliteOpenHelper;
	
	public RatedDao(Context context){
		sqliteOpenHelper = new CustomSQLiteOpenHelper(context);
	}
	
	public void open() throws SQLException{
		database = sqliteOpenHelper.getWritableDatabase();
	}
	
	public void close(){
		sqliteOpenHelper.close();
	}
	
	public void create(String recipeTitle, String rate){
		
		int idRated = 0;
		String sqlIdRated = "SELECT id from rated order by id DESC limit 1";
		Cursor cursorReceita = database.rawQuery(sqlIdRated, new String[]{});
		if (cursorReceita != null && cursorReceita.moveToFirst()) {
			idRated = cursorReceita.getInt(0)+1; //The 0 is the column index, we only have 1 column, so the index is 0
		}
		cursorReceita.close();
		
		String sqlRated = "insert into rated(id, recipe_title, rating) values(?, ?, ?);";
		database.execSQL(sqlRated, new String[]{String.valueOf(idRated), recipeTitle, rate});
		

	}
	
	public boolean isRated(String recipeTitle){
		String sqlId = "SELECT id from rated where recipe_title like ? order by id";
		Cursor cursorReceita = database.rawQuery(sqlId, new String[]{recipeTitle});
		if (cursorReceita != null && cursorReceita.moveToFirst()) {
			return true;
		}
		return false;
		
	}
	
	public String getRate(String recipeTitle){
		String sqlId = "SELECT rating from rated where recipe_title like ? order by id";
		Cursor cursorReceita = database.rawQuery(sqlId, new String[]{recipeTitle});
		if (cursorReceita != null && cursorReceita.moveToFirst()) {
			return cursorReceita.getString(0);
		}
		return "0.0";
		
	}

}
