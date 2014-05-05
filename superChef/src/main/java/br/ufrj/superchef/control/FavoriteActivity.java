package br.ufrj.superchef.control;

import java.util.ArrayList;
import java.util.List;

import br.ufrj.superchef.domain.Ingredient;
import br.ufrj.superchef.repository.FavoriteDao;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.ufrj.superchef.R;

import br.ufrj.superchef.domain.Recipe;

public class FavoriteActivity extends BasicActivity {

	private FavoriteDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		menuOptions();	
		setContentView(R.layout.favorites);
		
		leftMenu();
		
		dao = new FavoriteDao(this);
		dao.open();
	}
	
	@Override
	protected void onResume(){
		dao.open();
		super.onResume();
		
		final List<Recipe> receitas = dao.getAll();
		List<String> listRecipeName = new ArrayList<String>();
		
		for (Recipe recipe : receitas) {
			listRecipeName.add(recipe.getTitulo());
		}
		
		
		ListView listView = (ListView) findViewById(R.id.favoriteList);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(FavoriteActivity.this, android.R.layout.simple_list_item_1, listRecipeName);
        
    	listView.setAdapter(adapter);
 	   
    	listView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view,int position, long id){
    			Recipe recipeToIntent = receitas.get(position);
    			ArrayList<String> ingredientsNameList = new ArrayList<String>();
 			   
 			   for (Ingredient ingredientes : recipeToIntent.getIngredients()) {
 				   ingredientsNameList.add(ingredientes.getNome());
 			   }
 			   
 			   String minutos = "";
 			   String porcoes = "";
 			   if(recipeToIntent.getMinDuracao() != 0){
 				   minutos = String.valueOf(recipeToIntent.getMinDuracao()) + " minutos";
 			   }else{
 				   minutos = "Tempo indispon�vel";
 			   }
 			   
 			   if(recipeToIntent.getPorcoes() != 0){
 				   porcoes = String.valueOf(recipeToIntent.getPorcoes() ) + " por��es";
 			   }else{
 				   porcoes = "Por��es indispon�vel";
 			   }

 			  
 			   
 			   Intent intent = new Intent(FavoriteActivity.this, RecipePageActivity.class);
 			   intent.putExtra("title", recipeToIntent.getTitulo());
 			   intent.putExtra("imageUrl", recipeToIntent.getImageUrl());
 			   intent.putExtra("minDuracao", minutos);
 			   intent.putExtra("porcoes", porcoes);
 			   intent.putStringArrayListExtra("ingredientes", ingredientsNameList);
 			   intent.putExtra("modoPreparo", recipeToIntent.getModoPreparo());
 			   startActivity(intent);
 			   
 			   
 		   }});
	}
	
	@Override
	protected void onPause(){
		dao.close();
		super.onPause();
	}
	
}
