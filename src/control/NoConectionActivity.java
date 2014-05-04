package control;

import android.os.Bundle;
import android.widget.TextView;

import com.superchef.R;

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
		textMessage.setText("Ops, vericamos um problema na conexão, sua internet pode estar instável " +
				"ou você está desconectado. \n" +
				"Por favor, verifique a conexão e tente novamente!");

		
	}
}
