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
import android.widget.TextView;

import com.example.sharedprefs.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domain.Ingrediente;
import domain.Recipe;

public class RecomendationRecipesActivity extends BasicActivity{

	   // URL Address
	   ProgressDialog mProgressDialog;
	   Gson gson = new Gson();
	   Recipe recipy = new Recipe();
	   List<Recipe> recipyList = new ArrayList<Recipe>();
	   List<Recipe> listDCT = new ArrayList<Recipe>();
	   List<Recipe> listDC = new ArrayList<Recipe>();
	   List<Recipe> listDT = new ArrayList<Recipe>();
	   List<Recipe> listD = new ArrayList<Recipe>();
	   String cookType = "";
	   String diet = "";
	   String recipeCousine = "";
	   String name = "";

	   private ListView listView;
	   private TextView title;
	   private ArrayList<String> listRecipeName = new ArrayList<String>();
//	   private ItemAdapter m_adapter;
	   private ArrayAdapter<String> adapter;
	   

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
		  
		   String url = "http://superchef.herokuapp.com/receitas/busca?";

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
	    	   String urlDCT = getUrlDCT(url);
	    	   String urlDC = getUrlDC(url);
	    	   String urlDT = getUrlDT(url);
	    	   String urlD = getUrlD(url);

	    	   java.lang.reflect.Type arrayListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
	    	   
	    	   listDCT = gson.fromJson(JsonFromUrl.getJson(urlDCT), arrayListType);
	    	   listDC = gson.fromJson(JsonFromUrl.getJson(urlDC), arrayListType);
	    	   listDT = gson.fromJson(JsonFromUrl.getJson(urlDT), arrayListType);
	    	   listD = gson.fromJson(JsonFromUrl.getJson(urlD), arrayListType);
	    	   
//	    	   List<Recipy> tempList1 = new ArrayList<Recipy>();
	    	   recipyList.addAll(listDCT);
	    	   recipyList.addAll(listDC);
	    	   recipyList.addAll(listDT);
	    	   recipyList.addAll(listD);

//	    	   List<Recipy> tempList2 = new ArrayList<Recipy>(tempList1);
//	    	   recipyList = new ArrayList<Recipy>(tempList1);
//	    	   int count;
//	    	   int id=0;
//		   		for (Recipy recipy1 : tempList1) {
//		   			count = 0;
//		   			for (Recipy recipy2 : tempList2) {
//						if(recipy2.getId() == recipy1.getId()){
//							count++;
//						}
//						if(count > 1){
//							recipyList.remove(id);
//						}
//						id++;
//					}
//					
//				}

	    	   return null;
	       }
	       
	       private String getUrlDCT(String url){
	    	   String newUrl = url;
	    	   if(!cookType.equals("Tipo de comida")){
	    		   newUrl += "tipo=" + cookType.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!diet.equals("Dieta")){
	    		   newUrl += "dieta=" + diet.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!recipeCousine.equals("Cozinha da receita")){
	    		   newUrl += "cozinha=" + recipeCousine;
	    	   }
	    	   return newUrl;
	       }
	       private String getUrlDC(String url){
	    	   String newUrl = url;
	    	   if(!diet.equals("Dieta")){
	    		   newUrl += "dieta=" + diet.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!recipeCousine.equals("Cozinha da receita")){
	    		   newUrl += "cozinha=" + recipeCousine;
	    	   }
	    	   return newUrl;
	       }
	       private String getUrlDT(String url){
	    	   String newUrl = url;
	    	   if(!cookType.equals("Tipo de comida")){
	    		   newUrl += "tipo=" + cookType.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!diet.equals("Dieta")){
	    		   newUrl += "dieta=" + diet.replaceAll("[ ]", "%20");
	    	   }
	    	   return newUrl;
	       }
	       private String getUrlD(String url){
	    	   String newUrl = url;
	    	   if(!diet.equals("Dieta")){
	    		   newUrl += "dieta=" + diet.replaceAll("[ ]", "%20");
	    	   }
	    	   return newUrl;
	       }
	       

    @Override
    protected void onPostExecute(Void result) {
    	
    	final List<Recipe> responseList = new ArrayList<Recipe>();
    	for (Recipe recipy : recipyList) {
    		listRecipeName.add(recipy.getTitulo());
    		responseList.add(recipy);
    	}
    	title = (TextView) findViewById(R.id.title);
    	title.setText("Olá "+name+ ", encontramos receitas perfeitas para você!");
 	   
    	listView = (ListView) findViewById(R.id.recomendationList);

    	adapter = new ArrayAdapter<String>(RecomendationRecipesActivity.this, android.R.layout.simple_list_item_1, listRecipeName);
        
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
