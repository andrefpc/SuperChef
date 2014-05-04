package control;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import repository.FavoriteDao;
import repository.RatedDao;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.superchef.R;

import domain.Ingrediente;
import domain.Recipe;


public class RecipePageActivity extends BasicActivity{

	ProgressDialog mProgressDialog;
	String title;
	String imageUrl;
	String minDuracao;
	String porcoes;
	List<String> ingredientes;
	String modoPreparo;		
	Bitmap image;
	
	private FavoriteDao favoritDao;
	private RatedDao ratedDao;
	TextToSpeech ttobj;
	
	String ratingUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   menuOptions();
	   setContentView(R.layout.recipe_page);
		
		leftMenu();
	   
	   favoritDao = new FavoriteDao(this);
	   favoritDao.open();
	   
	   ratedDao = new RatedDao(this);
	   ratedDao.open();
	   
	   Intent intent = getIntent();
	   
	   title = intent.getStringExtra("title");
	   imageUrl = intent.getStringExtra("imageUrl");
	   minDuracao = intent.getStringExtra("minDuracao");
	   porcoes = intent.getStringExtra("porcoes");
	   ingredientes = intent.getStringArrayListExtra("ingredientes");
	   modoPreparo = intent.getStringExtra("modoPreparo");
	   
		ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			
			@Override
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR){
					ttobj.setLanguage(new Locale("pt-br", "brazil"));
				}				
			}
		});
	   
		new ImageLoad().execute();
   }
		
		
		
   private class ImageLoad extends AsyncTask<Void, Void, Void> implements OnRatingBarChangeListener {

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
		   RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
		   
		   if(ratedDao.isRated(title)){
			   ratingBar.setEnabled(false);
			   ratingBar.setRating(Float.parseFloat(ratedDao.getRate(title)));
		   }else{
			   ratingBar.setEnabled(true);
			   ratingBar.setOnRatingBarChangeListener(this);
		   }
		   
		  
		   
		   Button favoriteButton = (Button) findViewById(R.id.favoriteButton);
		   if(favoritDao.isFavorite(title)){
			   favoriteButton.setEnabled(false);
		   }else{
			   favoriteButton.setEnabled(true);
		   }
		   
		   favoriteButton.setOnClickListener(new View.OnClickListener() {
				
			   @Override
			   public void onClick(View v) {
				   List<Ingrediente> ingredientesList = new ArrayList<Ingrediente>();
				   for ( String ingredientName : ingredientes) {
					   Ingrediente ingrediente = new Ingrediente();
					   ingrediente.setNome(ingredientName);
					   ingredientesList.add(ingrediente);
				   }
				   
				   
				   Recipe receita = new Recipe();
				   receita.setTitulo(title);
				   receita.setImageUrl(imageUrl);
				   if(!minDuracao.equals("Tempo indisponível")){
					   int min = Integer.parseInt(minDuracao.replaceAll("[^0-9]", ""));
					   receita.setMinDuracao(min);
				   }else{
					   receita.setMinDuracao(0);
				   }
				   if(!porcoes.equals("Porções indisponível")){
					   int portions = Integer.parseInt(porcoes.replaceAll("[^0-9]", ""));
						   receita.setMinDuracao(portions);
					   }else{
						   receita.setMinDuracao(0);
					   }
					   receita.setIngredientes(ingredientesList);
					   receita.setModoPreparo(modoPreparo);
					   
					   favoritDao.open();
					   favoritDao.create(receita);
					   
					   finish();
					   startActivity(getIntent());
				   }
			   });
   
	    	   imageView.setImageBitmap(image);
	    	   
	    	   titleView.setText(title);
	    	   prepareTimeView.setText(minDuracao);
	    	   portionView.setText(porcoes);
	    	   prepareModeView.setText(modoPreparo);

	    	   ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecipePageActivity.this, android.R.layout.simple_list_item_1, ingredientes);
	    	   ingredientesView.setAdapter(adapter);
	    	   
	    	   
	    	   mProgressDialog.dismiss();           
	       }

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				
				ratingUrl = "http://superchef.herokuapp.com/receitas/registro/rating/";
				ratingUrl += title.replaceAll("[ ]", "%20");
				ratingUrl += "/" + String.valueOf(rating*2);
				
				new SaveRating().execute();
				
				System.out.println("é esse rating: " + rating);
				ratedDao.create(title, String.valueOf(rating));
				finish();
				startActivity(getIntent());
			
		}
   }
   
   
   
   
   
   private class SaveRating extends AsyncTask<Void, Void, Void> {

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
//           mProgressDialog = new ProgressDialog(RecipePageActivity.this);
//           mProgressDialog.setMessage("Salvando rating...");
//           mProgressDialog.setIndeterminate(false);
//           mProgressDialog.show();
       }

       @Override
       protected Void doInBackground(Void... params) {
    	   try{
				URL url = new URL(ratingUrl);
				HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
				httpCon.setDoOutput(true);
				httpCon.setRequestMethod("PUT");
				OutputStreamWriter out = new OutputStreamWriter(
				    httpCon.getOutputStream());
				out.write("Resource content");
				out.close();
				httpCon.getInputStream();}
			catch(Exception e){
				e.printStackTrace();
			}
    	   
    	   return null;
	
       }
	       
	}
   
   
   
   
   
   
   
   
   
   
   
   
	public void speakPrepareModeText(View view){
	      
		String toSpeak = modoPreparo;
		Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
		ttobj.setSpeechRate((float) 0.7);
		ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
	
	}
	
	public void speakIngredientesText(View view){
		String ingredientesList="";
	    for (String ingrediente : ingredientes) {
	    	ingredientesList += ingrediente + "\n \n";
	    }
		String toSpeak = ingredientesList;
		Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
		ttobj.setSpeechRate((float) 0.7);
		ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
		
	}
   
	@Override
	public void onPause(){
		if(ttobj !=null){
			ttobj.stop();
			ttobj.shutdown();
		}
	    super.onPause();
	}

}
