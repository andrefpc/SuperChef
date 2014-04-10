package control;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import service.JsonFromUrl;
import service.WebServiceUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.superChef.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domain.Ingredientes;
import domain.Recipy;

public class ResponseListActivity extends Activity{
	
	   // URL Address
	   ProgressDialog mProgressDialog;
	   Gson gson = new Gson();
	   Recipy recipy = new Recipy();
	   

	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.response_list);
	       
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

}
