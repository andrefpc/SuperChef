package control;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.superChef.R;

import domain.CookTypeEnum;
import domain.DietEnum;
import domain.OcasionEnum;
import domain.PrepareModeEnum;
import domain.RecipyCousineEnum;

public class SearchRecipyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		final EditText keyword = (EditText) findViewById(R.id.keyword_text);
		final EditText prepareTime = (EditText) findViewById(R.id.prepareTime_text);
		final EditText portions = (EditText) findViewById(R.id.portions_text);
		
		
		List<String> cookTypeList = new ArrayList<String>();
		List<String> prepareModeList = new ArrayList<String>();
		List<String> dietList = new ArrayList<String>();
		List<String> recipyCousineList = new ArrayList<String>();
		List<String> ocasionList = new ArrayList<String>();
		for (CookTypeEnum cookType : CookTypeEnum.values()) {
			cookTypeList.add(cookType.getName());
		}
		for (PrepareModeEnum prepareMode : PrepareModeEnum.values()) {
			prepareModeList.add(prepareMode.getName());
		}
		for (DietEnum diet : DietEnum.values()) {
			dietList.add(diet.getName());
		}
		for (RecipyCousineEnum recipyCousine : RecipyCousineEnum.values()) {
			recipyCousineList.add(recipyCousine.getName());
		}
		for (OcasionEnum ocasion : OcasionEnum.values()) {
			ocasionList.add(ocasion.getName());
		}
		
		Spinner cookTypeSpinner = (Spinner) findViewById(R.id.cookTypeSpinner);
        ArrayAdapter<String> cookTypeAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, cookTypeList);
        cookTypeSpinner.setAdapter(cookTypeAdapter);
        
        Spinner prepareModeSpinner = (Spinner) findViewById(R.id.prepareModeSpinner);
        ArrayAdapter<String> prepareModeAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, prepareModeList);
        prepareModeSpinner.setAdapter(prepareModeAdapter);
		
        Spinner dietSpinner = (Spinner) findViewById(R.id.dietSpinner);
        ArrayAdapter<String> dietAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, dietList);
        dietSpinner.setAdapter(dietAdapter);
        
        Spinner recipyCousineSpinner = (Spinner) findViewById(R.id.recipyCousineSpinner);
        ArrayAdapter<String> recipyCousineAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, recipyCousineList);
        recipyCousineSpinner.setAdapter(recipyCousineAdapter);
        
        Spinner ocasionSpinner = (Spinner) findViewById(R.id.ocasionSpinner);
        ArrayAdapter<String> ocasionAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, ocasionList);
        ocasionSpinner.setAdapter(ocasionAdapter);
        
        
        Button saveButton = (Button) findViewById(R.id.search);
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keywordString = keyword.getEditableText().toString();
				String prepareTimeString = prepareTime.getEditableText().toString();
				String portionsTimeString = portions.getEditableText().toString();
				
				Spinner cookTypeSp = (Spinner) findViewById(R.id.cookTypeSpinner);
				String cookTypeSpinnerString = cookTypeSp.getSelectedItem().toString();
				
				Spinner prepareModeSp = (Spinner) findViewById(R.id.prepareModeSpinner);
				String prepareModepinnerString = cookTypeSp.getSelectedItem().toString();
		            
				Spinner dietSp = (Spinner) findViewById(R.id.dietSpinner);
	            String dietSpinnerString = dietSp.getSelectedItem().toString();
	            
	            Spinner recipyCousineSp = (Spinner) findViewById(R.id.recipyCousineSpinner);
	            String recipyCousineSpinnerString = recipyCousineSp.getSelectedItem().toString();

				Spinner ocasionSp = (Spinner) findViewById(R.id.ocasionSpinner);
				String ocasionSpinnerString = cookTypeSp.getSelectedItem().toString();
				
				Intent intent = new Intent(SearchRecipyActivity.this, ResponseListActivity.class);
				startActivity(intent);
	            
			}
		});
	}
}


