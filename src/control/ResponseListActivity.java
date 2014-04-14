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
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sharedprefs.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domain.Ingredientes;
import domain.Recipy;

public class ResponseListActivity extends Activity{
	
	   // URL Address
	   ProgressDialog mProgressDialog;
	   Gson gson = new Gson();
	   Recipy recipy = new Recipy();
	   String keyword = "";
	   String prepareTime = "";
	   String portions = "";
	   String cookType = "";
	   String prepareMode = "";
	   String diet = "";
	   String recipeCousine = "";
	   String ocasion = "";
	   

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
	    	   String url = "http://superchef.herokuapp.com/receitas";
	    	   if(!keyword.equals("")){
	    		   url += "titulo=" + keyword;
	    	   }else if(!prepareTime.equals("")){
	    		   url += "tempoPreparo=" + prepareTime;
	    	   }else if(!portions.equals("")){
	    		   url += "porcoes=" + portions;
	    	   }else if(!cookType.equals("Tipo de comida")){
	    		   url += "tipo=" + cookType;
	    	   }else if(!prepareMode.equals("Tipo de comida")){
	    		   url += "modoPreparo=" + prepareMode;
	    	   }else if(!diet.equals("Dieta")){
	    		   url += "dieta=" + diet;
	    	   }else if(!recipeCousine.equals("Cozinha da receita")){
	    		   url += "cozinha=" + recipeCousine;
	    	   }else if(!ocasion.equals("Ocasião")){
	    		   url += "ocasiao=" + ocasion;
	    	   }
	    	   java.lang.reflect.Type arrayListType = new TypeToken<ArrayList<Recipy>>(){}.getType();
	    	   List<Recipy> recipyList = new ArrayList<Recipy>();
	    	   
	    	   
	    	   recipyList = gson.fromJson(JsonFromUrl.getJson(url), arrayListType);
	    	    
	    	   recipy = recipyList.get(0);

	    	   return null;
	       }
	       

       @Override
       protected void onPostExecute(Void result) {

    	    TextView recipyName = (TextView) findViewById(R.id.recipy_name);
			TextView prepareMode = (TextView) findViewById(R.id.prepare_mode);
			TextView portions = (TextView) findViewById(R.id.portions);
			TextView time = (TextView) findViewById(R.id.time);
			ListView ingredients = (ListView) findViewById(R.id.ingredients);
			ListView tags = (ListView) findViewById(R.id.tags);
		   
			recipyName.setText(recipy.getTitulo());
			prepareMode.setText(recipy.getModoPreparo());
			portions.setText(((Integer)(recipy.getPorcoes())).toString());
			time.setText(((Integer)(recipy.getMinDuracao())).toString());
			List<String> ingredientsList = new ArrayList<String>();
		   
			for (Ingredientes ingredientes : recipy.getIngredientes()) {
			   ingredientsList.add(ingredientes.getNome());
			}
			ingredients.setAdapter(new ArrayAdapter<String>(ResponseListActivity.this, android.R.layout.simple_list_item_1, ingredientsList));
			tags.setAdapter(new ArrayAdapter<String>(ResponseListActivity.this, android.R.layout.simple_list_item_1, recipy.getTags()));
		   
		
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
