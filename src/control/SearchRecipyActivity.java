package control;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
import domain.OcasionEnum;
import domain.PrepareModeEnum;
import domain.RecipyCousineEnum;

public class SearchRecipyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Add this line
	    ActionBar actionBar = getActionBar();
	    actionBar.show();
	    getActionBar().setIcon(R.drawable.chef);     
	    getActionBar().setTitle("Super Chef");
	    getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_background)); 
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
				String portionsString = portions.getEditableText().toString();
				
				Spinner cookTypeSp = (Spinner) findViewById(R.id.cookTypeSpinner);
				String cookTypeSpinnerString = cookTypeSp.getSelectedItem().toString();
				
				Spinner prepareModeSp = (Spinner) findViewById(R.id.prepareModeSpinner);
				String prepareModepinnerString = prepareModeSp.getSelectedItem().toString();
		            
				Spinner dietSp = (Spinner) findViewById(R.id.dietSpinner);
	            String dietSpinnerString = dietSp.getSelectedItem().toString();
	            
	            Spinner recipyCousineSp = (Spinner) findViewById(R.id.recipyCousineSpinner);
	            String recipyCousineSpinnerString = recipyCousineSp.getSelectedItem().toString();

				Spinner ocasionSp = (Spinner) findViewById(R.id.ocasionSpinner);
				String ocasionSpinnerString = ocasionSp.getSelectedItem().toString();
				
				Intent intent = new Intent(SearchRecipyActivity.this, ResponseListActivity.class);
				intent.putExtra("keyword",keywordString);
				intent.putExtra("prepareTime",prepareTimeString);
				intent.putExtra("portions",portionsString);
				intent.putExtra("cookType",cookTypeSpinnerString);
				intent.putExtra("prepareMode",prepareModepinnerString);
				intent.putExtra("diet",dietSpinnerString);
				intent.putExtra("recipeCousine",recipyCousineSpinnerString);
				intent.putExtra("ocasion",ocasionSpinnerString);
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


