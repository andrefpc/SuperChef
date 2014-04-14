package control;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedprefs.R;

public class MainActivity extends Activity {
	final static String APP_PREFS = "app_prefs";
	final static String USERNAME_KEY = "username";
	final static String NICKNAME_KEY = "nickname";
	final static String DIET_KEY = "diet";
	final static String COOKTYPE_KEY = "cookType";
	final static String RECIPYCOUSINE_KEY = "recipyCousine";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Add this line
	    ActionBar actionBar = getActionBar();
	    actionBar.show();
	    getActionBar().setIcon(R.drawable.chef);     
	    getActionBar().setTitle("Super Chef");
	    getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_background)); 
		setContentView(R.layout.main);
	}
	
	protected void onResume(){
		super.onResume();
		
		SharedPreferences prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
		String userName = prefs.getString(USERNAME_KEY, null);
		String nickName = prefs.getString(NICKNAME_KEY, null);
		String diet = prefs.getString(DIET_KEY, null);
		String cookType = prefs.getString(COOKTYPE_KEY, null);
		String recipyCousine = prefs.getString(RECIPYCOUSINE_KEY, null);
		
		TextView nameMessage = (TextView) findViewById(R.id.name);
		TextView nickNameMessage = (TextView) findViewById(R.id.nickname);
		TextView dietMessage = (TextView) findViewById(R.id.diet);
		TextView cookTypeMessage = (TextView) findViewById(R.id.cookType);
		TextView recipyCousineMessage = (TextView) findViewById(R.id.recipyCousine);
		Button addInfoButton = (Button) findViewById(R.id.add_info);
		
		if (userName != null) {
			nameMessage.setText(userName);
			nickNameMessage.setText(nickName);
			dietMessage.setText(diet);
			cookTypeMessage.setText(cookType);
			recipyCousineMessage.setText(recipyCousine);
			addInfoButton.setText("Trocar Info");
		}else{
			nameMessage.setText("Você ainda se cadastrou...");
			addInfoButton.setText("Adicionar info");
		}
		
		addInfoButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddUserInfoActivity.class);
				startActivity(intent);
			}
		});
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
