package control;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.superChef.R;

import domain.CookTypeEnum;
import domain.DietEnum;
import domain.RecipyCousineEnum;

public class AddUserInfoActivity extends Activity {

	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
}
