package control;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.superchef.R;

public class MainActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuOptions();
		setContentView(R.layout.main);
		
		leftMenu();
		
	}
	
	protected void onResume(){
		super.onResume();
		
		SharedPreferences prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
		String userName = prefs.getString(USERNAME_KEY, null);
		String diet = prefs.getString(DIET_KEY, null);
		String cookType = prefs.getString(COOKTYPE_KEY, null);
		String recipyCousine = prefs.getString(RECIPYCOUSINE_KEY, null);
		
		TextView nameMessage = (TextView) findViewById(R.id.name);
		
		
		if (userName != null) {
			if(diet==null){
				diet = "";
			}
			if(cookType==null){
				cookType = "";
			}
			if(recipyCousine==null){
				recipyCousine = "";
			}
			if(!conectado(this)){
				Intent intent = new Intent(this, NoConectionActivity.class);
				startActivity(intent);
				finish();
			}else{
				Intent intent = new Intent(MainActivity.this, RecomendationRecipesActivity.class);
				intent.putExtra("name", userName);
				intent.putExtra("diet", diet);
				intent.putExtra("cookType", cookType);
				intent.putExtra("recipyCousine", recipyCousine);
				startActivity(intent);
			}
			   
		}else{
			Button addInfoButton = (Button) findViewById(R.id.add_info);
			
			nameMessage.setText("Vimos que você ainda se cadastrou suas informações...");
			addInfoButton.setText("Cadastrar");
			
			addInfoButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this, AddUserInfoActivity.class);
					startActivity(intent);
				}
			});
		}
		
	}
}
