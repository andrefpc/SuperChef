package control;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.superchef.R;

import domain.CookTypeEnum;
import domain.DietEnum;
import domain.RecipeCousineEnum;

public class AddUserInfoActivity extends BasicActivity {

	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuOptions();
		setContentView(R.layout.add_user_info);
		
		leftMenu();
		
		prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
		
		String userNamePrefs = prefs.getString(USERNAME_KEY, null);
		String dietPrefs = prefs.getString(DIET_KEY, null);
		String cookTypePrefs = prefs.getString(COOKTYPE_KEY, null);
		String recipyCousinePrefs = prefs.getString(RECIPYCOUSINE_KEY, null);
		
		final EditText name = (EditText) findViewById(R.id.name_edit_text);
		name.setText(userNamePrefs);
		
		List<String> dietList = new ArrayList<String>();
		List<String> cookTypeList = new ArrayList<String>();
		List<String> recipyCousineList = new ArrayList<String>();
		for (DietEnum diet : DietEnum.values()) {
			dietList.add(diet.getName());
		}
		for (CookTypeEnum diet : CookTypeEnum.values()) {
			cookTypeList.add(diet.getName());
		}
		for (RecipeCousineEnum diet : RecipeCousineEnum.values()) {
			recipyCousineList.add(diet.getName());
		}
		
        Spinner dietSpinner = (Spinner) findViewById(R.id.dietSpinner);
        ArrayAdapter<String> dietAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, dietList);
        dietSpinner.setAdapter(dietAdapter);
        int indexDiet = DietEnum.getByName(dietPrefs);
        dietSpinner.setSelection(indexDiet);
        
        Spinner cookTypeSpinner = (Spinner) findViewById(R.id.cookTypeSpinner);
        ArrayAdapter<String> cookTypeAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, cookTypeList);
        cookTypeSpinner.setAdapter(cookTypeAdapter);
        int indexCookType = CookTypeEnum.getByName(cookTypePrefs);
        cookTypeSpinner.setSelection(indexCookType);
        
        Spinner recipyCousineSpinner = (Spinner) findViewById(R.id.recipyCousineSpinner);
        ArrayAdapter<String> recipyCousineAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, recipyCousineList);
        recipyCousineSpinner.setAdapter(recipyCousineAdapter);
        int indexCousine = RecipeCousineEnum.getByName(recipyCousinePrefs);
        recipyCousineSpinner.setSelection(indexCousine);
        
        
        Button saveButton = (Button) findViewById(R.id.add_name_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userName = name.getEditableText().toString();
				
				Spinner dietSp = (Spinner) findViewById(R.id.dietSpinner);
	            String dietSpinnerString = dietSp.getSelectedItem().toString();
	            
	            Spinner cookTypeSp = (Spinner) findViewById(R.id.cookTypeSpinner);
	            String cookTypeSpinnerString = cookTypeSp.getSelectedItem().toString();
	            
	            Spinner recipyCousineSp = (Spinner) findViewById(R.id.recipyCousineSpinner);
	            String recipyCousineSpinnerString = recipyCousineSp.getSelectedItem().toString();
	            
				
				Editor editor = prefs.edit();
				editor.putString(USERNAME_KEY, userName);
				editor.putString(DIET_KEY, dietSpinnerString);
				editor.putString(COOKTYPE_KEY, cookTypeSpinnerString);
				editor.putString(RECIPYCOUSINE_KEY, recipyCousineSpinnerString);
				editor.commit();
				Intent intent = new Intent(AddUserInfoActivity.this, RecomendationRecipesActivity.class);
				intent.putExtra("cookType", cookTypeSpinnerString);
				intent.putExtra("diet", dietSpinnerString);
				intent.putExtra("recipyCousine", recipyCousineSpinnerString);
				intent.putExtra("name", userName);
				startActivity(intent);
				finish();
			}
		});
	}
}
