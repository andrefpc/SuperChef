package control;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sharedprefs.R;


public class RecipePageActivity extends Activity{

		ProgressDialog mProgressDialog;
		String title;
		String imageUrl;
		String minDuracao;
		String porcoes;
		List<String> ingredientes;
		String modoPreparo;		
		Bitmap image;
	
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
		   super.onCreate(savedInstanceState);
		   getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Add this line
		   ActionBar actionBar = getActionBar();
		   actionBar.show();
		   getActionBar().setIcon(R.drawable.chef);     
		   getActionBar().setTitle("Super Chef");
		   getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_background)); 
		   setContentView(R.layout.recipe_page);
		   
		   Intent intent = getIntent();
		   
		   title = intent.getStringExtra("title");
		   imageUrl = intent.getStringExtra("imageUrl");
		   minDuracao = intent.getStringExtra("minDuracao");
		   porcoes = intent.getStringExtra("porcoes");
		   ingredientes = intent.getStringArrayListExtra("ingredientes");
		   modoPreparo = intent.getStringExtra("modoPreparo");
		   
		   new ImageLoad().execute();
	   }
		
		
		
	   private class ImageLoad extends AsyncTask<Void, Void, Void> {

	       @Override
	       protected void onPreExecute() {
	           super.onPreExecute();
	           mProgressDialog = new ProgressDialog(RecipePageActivity.this);
	           mProgressDialog.setMessage("Carregando receita...");
	           mProgressDialog.setIndeterminate(false);
	           mProgressDialog.show();
	       }

	       @Override
	       protected Void doInBackground(Void... params) {
	    	   
	    	   try {
					URL url = new URL(imageUrl);
			        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			        connection.setDoInput(true);
			        connection.connect();
			        InputStream input = connection.getInputStream();
			        image = BitmapFactory.decodeStream(input);
				} catch (Exception e) {
				    return null;
				}

	    	   return null;
	       }
		       

	       @Override
	       protected void onPostExecute(Void result) {
	    	   
	    	   TextView titleView = (TextView) findViewById(R.id.title);
	    	   TextView prepareTimeView = (TextView) findViewById(R.id.prepareTime);
	    	   TextView portionView = (TextView) findViewById(R.id.portion);
	    	   TextView prepareModeView = (TextView) findViewById(R.id.prepareMode);
			   ImageView imageView = (ImageView) findViewById(R.id.image);	
			   ListView ingredientesView = (ListView) findViewById(R.id.ingredientsList);
   
	    	   imageView.setImageBitmap(image);
	    	   
	    	   titleView.setText(title);
	    	   prepareTimeView.setText(minDuracao);
	    	   portionView.setText(porcoes);
	    	   prepareModeView.setText(modoPreparo);

	    	   ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecipePageActivity.this, android.R.layout.simple_list_item_1, ingredientes);
	    	   ingredientesView.setAdapter(adapter);
	    	   
	    	   
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
