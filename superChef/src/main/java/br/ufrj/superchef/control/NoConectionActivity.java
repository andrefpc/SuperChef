package br.ufrj.superchef.control;

import android.os.Bundle;
import android.widget.TextView;

import br.ufrj.superchef.R;

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
		textMessage.setText("Ops, vericamos um problema na conex�o, sua internet pode estar inst�vel " +
				"ou voc� est� desconectado. \n" +
				"Por favor, verifique a conex�o e tente novamente!");

		
	}
}
