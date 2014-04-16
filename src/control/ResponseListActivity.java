package control;

import java.util.ArrayList;
import java.util.List;

import service.JsonFromUrl;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sharedprefs.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domain.Ingredientes;
import domain.Recipy;
//import service.ItemAdapter;
//import domain.ItemList;

public class ResponseListActivity extends Activity {
	
	   // URL Address
	   ProgressDialog mProgressDialog;
	   Gson gson = new Gson();
	   Recipy recipy = new Recipy();
	   List<Recipy> recipyList = new ArrayList<Recipy>();
	   String keyword = "";
	   String prepareTime = "";
	   String portions = "";
	   String cookType = "";
	   String prepareMode = "";
	   String diet = "";
	   String recipeCousine = "";
	   String ocasion = "";

	   private ListView listView;
	   private ArrayList<String> list = new ArrayList<String>();
//	   private ItemAdapter m_adapter;
	   private ArrayAdapter<String> adapter;
	   

	   @Override
	   public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Add this line
		   ActionBar actionBar = getActionBar();
		   actionBar.show();
		   getActionBar().setIcon(R.drawable.chef);     
		   getActionBar().setTitle("Super Chef");
		   getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_background)); 
		   setContentView(R.layout.response_list);
	       
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
	    	   if(!cookType.equals("Tipo de comida")){
	    		   url += "tipo=" + cookType.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!prepareMode.equals("Modo de preparo")){
	    		   url += "modoPreparo=" + prepareMode.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!diet.equals("Dieta")){
	    		   url += "dieta=" + diet.replaceAll("[ ]", "%20") + "&";
	    	   }
	    	   if(!recipeCousine.equals("Cozinha da receita")){
	    		   url += "cozinha=" + recipeCousine + "&";
	    	   }
	    	   if(!ocasion.equals("Ocasião")){
	    		   url += "ocasiao=" + ocasion.replaceAll("[ ]", "%20");
	    	   }
	    	   java.lang.reflect.Type arrayListType = new TypeToken<ArrayList<Recipy>>(){}.getType();
	    	   
	    	   recipyList = gson.fromJson(JsonFromUrl.getJson(url), arrayListType);

	    	   return null;
	       }
	       

       @Override
       protected void onPostExecute(Void result) {
    	   
    	   for (Recipy recipy : recipyList) {
//			   ItemList item = new ItemList(recipy.getImageUrl(), recipy.getTitulo());
			   list.add(recipy.getTitulo());
		   }
    	   
    	   listView = (ListView) findViewById(R.id.RecipesList);
 
    	   adapter = new ArrayAdapter<String>(ResponseListActivity.this, android.R.layout.simple_list_item_1, list);
    	   
//    	   m_adapter = new ItemAdapter(ResponseListActivity.this, R.layout.row_layout, m_parts);
           
    	   listView.setAdapter(adapter);
    	   listView.setOnItemClickListener(new OnItemClickListener() {
    		   public void onItemClick(AdapterView<?> parent, View view,int position, long id){
    			   String selectedFromList = (listView.getItemAtPosition(position).toString());
    			   Recipy recipeToIntent = null;
    			   for (Recipy recipy : recipyList) {
    		        	 if(recipy.getTitulo().equals(selectedFromList)){
    		        		 recipeToIntent = recipy;
    		        		 break;
    		        	 }
    			   }
    			   ArrayList<String> ingredientsNameList = new ArrayList<String>();
    			   for (Ingredientes ingredientes : recipeToIntent.getIngredientes()) {
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
	   
	   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.changeInfo){
			Intent intent = new Intent(this, AddUserInfoActivity.class);
			startActivity(intent);
		}
		if(item.getItemId() == R.id.search){
			Intent intent = new Intent(this, SearchRecipyActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
