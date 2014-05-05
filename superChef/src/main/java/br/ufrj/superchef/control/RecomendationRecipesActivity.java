package br.ufrj.superchef.control;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.superchef.service.ItemAdapter;
import br.ufrj.superchef.service.JsonFromUrl;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import br.ufrj.superchef.R;

import br.ufrj.superchef.domain.Ingredient;
import br.ufrj.superchef.domain.ItemList;
import br.ufrj.superchef.domain.Recipe;

public class RecomendationRecipesActivity extends BasicActivity{

	   // URL Address
	   ProgressDialog mProgressDialog;
	   Gson gson = new Gson();
	   Recipe recipy = new Recipe();
	   List<Recipe> recipyList = new ArrayList<Recipe>();
	   String cookType = "";
	   String diet = "";
	   String recipeCousine = "";
	   String name = "";

	   private ListView listView;
	   private TextView title;
	   private ArrayList<ItemList> listRecipeItens = new ArrayList<ItemList>();
	   final List<Recipe> responseList = new ArrayList<Recipe>();
	   private ArrayAdapter<ItemList> adapter;
	   Bitmap image;
	   ItemList itemList;
	   

	   @Override
	   public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   menuOptions();
		   setContentView(R.layout.recomandation_page);
		   
		   leftMenu();
	       
	       Intent intent = getIntent();
	       
	       cookType = intent.getStringExtra("cookType");
	       diet = intent.getStringExtra("diet");
	       recipeCousine = intent.getStringExtra("recipyCousine");
	       name = intent.getStringExtra("name");
	       
	       new JsonRecipyResult().execute();

	   }

	   private class JsonRecipyResult extends AsyncTask<Void, Void, Void> {
		  
		   String url = "http://superchef.herokuapp.com/receitas/recomendacao?";

	       @Override
	       protected void onPreExecute() {
	           super.onPreExecute();
	           mProgressDialog = new ProgressDialog(RecomendationRecipesActivity.this);
	           mProgressDialog.setMessage("Carregando receitas...");
	           mProgressDialog.setIndeterminate(false);
	           mProgressDialog.show();
	       }

	       @Override
	       protected Void doInBackground(Void... params) {
	    	   if(!cookType.equals("Todos")){
	    		   url += "tipo=" + cookType.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!diet.equals("Todos")){
	    		   url += "dieta=" + diet.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!recipeCousine.equals("Todos")){
	    		   url += "cozinha=" + recipeCousine;
	    	   }

	    	   java.lang.reflect.Type arrayListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
	    	   
	    	   String json = JsonFromUrl.getJson(url);
	    	   
	    	   if(json == null){
	    		   startActivity(new Intent(RecomendationRecipesActivity.this, NoConectionActivity.class));
	    	   }else{
	    		   recipyList = gson.fromJson(json, arrayListType);
	    	   }
	    	   
	    	   try {
	    		   for (Recipe recipe : recipyList) {
	    			   URL url = new URL(recipe.getImageUrl());
	    			   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    			   connection.setDoInput(true);
	    			   connection.connect();
	    			   InputStream input = connection.getInputStream();
	    			   image = BitmapFactory.decodeStream(input);
	    			   itemList = new ItemList();
	    			   itemList.setTitle(recipe.getTitulo());
	    			   itemList.setImageUrl(image);
	    			   listRecipeItens.add(itemList);
	    			   responseList.add(recipe);
	    		   }
				} catch (Exception e) {
				    return null;
				}

	    	   return null;
	       }
	       

	    @Override
	    protected void onPostExecute(Void result) {
	    	
	    	title = (TextView) findViewById(R.id.title);
	    	title.setText("Ol� "+name+ ", encontramos receitas perfeitas para voc�!");
	 	   
	    	listView = (ListView) findViewById(R.id.recomendationList);
	
	    	adapter = new ItemAdapter(RecomendationRecipesActivity.this, android.R.layout.simple_list_item_1, listRecipeItens);
	        
	    	listView.setAdapter(adapter);
	 	   
	    	listView.setOnItemClickListener(new OnItemClickListener() {
	    		public void onItemClick(AdapterView<?> parent, View view,int position, long id){
	    			Recipe recipeToIntent = responseList.get(position);
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
	
	 			  
	 			   
	 			   Intent intent = new Intent(RecomendationRecipesActivity.this, RecipePageActivity.class);
	 			   intent.putExtra("title", recipeToIntent.getTitulo());
	 			   intent.putExtra("imageUrl", recipeToIntent.getImageUrl());
	 			   intent.putExtra("minDuracao", minutos);
	 			   intent.putExtra("porcoes", porcoes);
	 			   intent.putStringArrayListExtra("ingredientes", ingredientsNameList);
	 			   intent.putExtra("modoPreparo", recipeToIntent.getModoPreparo());
	 			   startActivity(intent);
	 			   
	 			   
	 		   }});
 	   
			mProgressDialog.dismiss();           
	    }
	}

}
