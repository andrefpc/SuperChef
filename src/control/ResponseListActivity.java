package control;

import java.util.ArrayList;
import java.util.List;

import service.JsonFromUrl;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sharedprefs.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domain.Ingrediente;
import domain.Recipe;
//import service.ItemAdapter;
//import domain.ItemList;

public class ResponseListActivity extends BasicActivity {
	
	   // URL Address
	   ProgressDialog mProgressDialog;
	   Gson gson = new Gson();
	   Recipe recipy = new Recipe();
	   List<Recipe> recipyList = new ArrayList<Recipe>();
	   String keyword = "";
	   String prepareTime = "";
	   String portions = "";
	   String cookType = "";
	   String prepareMode = "";
	   String diet = "";
	   String recipeCousine = "";
	   String ocasion = "";

	   private ListView listView;
	   private ArrayList<String> listRecipeName = new ArrayList<String>();
//	   private ItemAdapter m_adapter;
	   private ArrayAdapter<String> adapter;
	   

	   @Override
	   public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);		   
		   menuOptions();
		   setContentView(R.layout.response_list);

		   leftMenu();
	       
	       Intent intent = getIntent();
	       
	       keyword = intent.getStringExtra("keyword");
	       prepareTime = intent.getStringExtra("prepareTime");
	       portions = intent.getStringExtra("portions");
	       cookType = intent.getStringExtra("cookType");
	       prepareMode = intent.getStringExtra("prepareMode");
	       diet = intent.getStringExtra("diet");
	       recipeCousine = intent.getStringExtra("recipeCousine");
	       ocasion = intent.getStringExtra("ocasion");
	       
	       new JsonRecipyResult().execute();

	   }

	   private class JsonRecipyResult extends AsyncTask<Void, Void, Void> {
		  
		   String url = "http://superchef.herokuapp.com/receitas/busca?";

	       @Override
	       protected void onPreExecute() {
	           super.onPreExecute();
	           mProgressDialog = new ProgressDialog(ResponseListActivity.this);
	           mProgressDialog.setMessage("Carregando receitas...");
	           mProgressDialog.setIndeterminate(false);
	           mProgressDialog.show();
	       }

	       @Override
	       protected Void doInBackground(Void... params) {
	    	   if(!keyword.equals("")){
	    		   url += "titulo=" + keyword.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!prepareTime.equals("")){
	    		   url += "tempoPreparo=" + prepareTime + "&";
	    	   }
	    	   if(!portions.equals("")){
	    		   url += "porcoes=" + portions + "&";
	    	   }
	    	   if(!cookType.equals("Todos")){
	    		   url += "tipo=" + cookType.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!prepareMode.equals("Todos")){
	    		   url += "modoPreparo=" + prepareMode.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!diet.equals("Todos")){
	    		   url += "dieta=" + diet.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!recipeCousine.equals("Todos")){
	    		   url += "cozinha=" + recipeCousine + "&";
	    	   }
	    	   if(!ocasion.equals("Todos")){
	    		   url += "ocasiao=" + ocasion.replaceAll("[ ]", "%20");
	    	   }
	    	   java.lang.reflect.Type arrayListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
	    	   
	    	   recipyList = gson.fromJson(JsonFromUrl.getJson(url), arrayListType);

	    	   return null;
	       }
	       

       @Override
       protected void onPostExecute(Void result) {
    	   final List<Recipe> responseList = new ArrayList<Recipe>();
    	   for (Recipe recipy : recipyList) {
			   listRecipeName.add(recipy.getTitulo());
			   responseList.add(recipy);
		   }
    	   
    	   listView = (ListView) findViewById(R.id.RecipesList);
 
    	   adapter = new ArrayAdapter<String>(ResponseListActivity.this, android.R.layout.simple_list_item_1, listRecipeName);
           
    	   listView.setAdapter(adapter);
    	   
    	   listView.setOnItemClickListener(new OnItemClickListener() {
    		   public void onItemClick(AdapterView<?> parent, View view,int position, long id){
    			   Recipe recipeToIntent = responseList.get(position);
    			   ArrayList<String> ingredientsNameList = new ArrayList<String>();
    			   
    			   for (Ingrediente ingredientes : recipeToIntent.getIngredientes()) {
    				   ingredientsNameList.add(ingredientes.getNome());
    			   }
    			   
    			   String minutos = "";
    			   String porcoes = "";
    			   if(recipeToIntent.getMinDuracao() != 0){
    				   minutos = String.valueOf(recipeToIntent.getMinDuracao()) + " minutos";
    			   }else{
    				   minutos = "Tempo indisponível";
    			   }
    			   
    			   if(recipeToIntent.getPorcoes() != 0){
    				   porcoes = String.valueOf(recipeToIntent.getPorcoes() ) + " porções";
    			   }else{
    				   porcoes = "Porções indisponível";
    			   }

    			  
    			   
    			   Intent intent = new Intent(ResponseListActivity.this, RecipePageActivity.class);
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
