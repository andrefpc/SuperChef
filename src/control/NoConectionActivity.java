package control;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sharedprefs.R;

public class NoConectionActivity extends BasicActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuOptions();
		setContentView(R.layout.no_conection);
		
		leftMenu();
		
	}
	
	protected void onResume(){
		super.onResume();
		
		TextView textMessage = (TextView) findViewById(R.id.text);
		textMessage.setText("Desculpe, mais precisamos de internet para essa operação!");

		
	}
}
