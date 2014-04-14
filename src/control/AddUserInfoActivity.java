package control;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sharedprefs.R;

import domain.CookTypeEnum;
import domain.DietEnum;
import domain.RecipyCousineEnum;

public class AddUserInfoActivity extends Activity {

	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Add this line
	    ActionBar actionBar = getActionBar();
	    actionBar.show();
	    getActionBar().setIcon(R.drawable.chef);     
	    getActionBar().setTitle("Super Chef");
	    getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_background)); 
		setContentView(R.layout.add_user_info);
		
		prefs = getSharedPreferences(MainActivity.APP_PREFS, MODE_PRIVATE);
		
		final EditText name = (EditText) findViewById(R.id.name_edit_text);
		final EditText nick = (EditText) findViewById(R.id.nick_edit_text);
		
		List<String> dietList = new ArrayList<String>();
		List<String> cookTypeList = new ArrayList<String>();
		List<String> recipyCousineList = new ArrayList<String>();
		for (DietEnum diet : DietEnum.values()) {
			dietList.add(diet.getName());
		}
		for (CookTypeEnum diet : CookTypeEnum.values()) {
			cookTypeList.add(diet.getName());
		}
		for (RecipyCousineEnum diet : RecipyCousineEnum.values()) {
			recipyCousineList.add(diet.getName());
		}
		
        Spinner dietSpinner = (Spinner) findViewById(R.id.dietSpinner);
        ArrayAdapter<String> dietAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, dietList);
        dietSpinner.setAdapter(dietAdapter);
        
        Spinner cookTypeSpinner = (Spinner) findViewById(R.id.cookTypeSpinner);
        ArrayAdapter<String> cookTypeAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, cookTypeList);
        cookTypeSpinner.setAdapter(cookTypeAdapter);
        
        Spinner recipyCousineSpinner = (Spinner) findViewById(R.id.recipyCousineSpinner);
        ArrayAdapter<String> recipyCousineAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, recipyCousineList);
        recipyCousineSpinner.setAdapter(recipyCousineAdapter);
        
        
        Button saveButton = (Button) findViewById(R.id.add_name_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userName = name.getEditableText().toString();
				String nickName = nick.getEditableText().toString();
				
				Spinner dietSp = (Spinner) findViewById(R.id.dietSpinner);
	            String dietSpinnerString = dietSp.getSelectedItem().toString();
	            
	            Spinner cookTypeSp = (Spinner) findViewById(R.id.cookTypeSpinner);
	            String cookTypeSpinnerString = cookTypeSp.getSelectedItem().toString();
	            
	            Spinner recipyCousineSp = (Spinner) findViewById(R.id.recipyCousineSpinner);
	            String recipyCousineSpinnerString = recipyCousineSp.getSelectedItem().toString();
	            
				
				Editor editor = prefs.edit();
				editor.putString(MainActivity.USERNAME_KEY, userName);
				editor.putString(MainActivity.NICKNAME_KEY, nickName);
				editor.putString(MainActivity.DIET_KEY, dietSpinnerString);
				editor.putString(MainActivity.COOKTYPE_KEY, cookTypeSpinnerString);
				editor.putString(MainActivity.RECIPYCOUSINE_KEY, recipyCousineSpinnerString);
				editor.commit();
				finish();
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
