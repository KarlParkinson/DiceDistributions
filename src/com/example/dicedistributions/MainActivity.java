package com.example.dicedistributions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {

	private SeekBar numDiceSeekBar;
	private TextView numDiceTV;
	private Spinner numRollsSpinner;
	private Button RollDiceButton;
	
	private int numDice;
	private int numRolls;
	
	public static String NUM_DICE = "com.example.dicedistributions.numDice";
	public static String NUM_ROLLS = "com.example.dicedistibutions.numRolls";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
		//Initialize all UI elements
		numDiceSeekBar = (SeekBar) findViewById(R.id.numDiceSeekBar);
		numDiceTV = (TextView) findViewById(R.id.numDiceTextView);
		numRollsSpinner = (Spinner) findViewById(R.id.NumRollsSpinner);
		RollDiceButton = (Button) findViewById(R.id.RollDiceButton);
		
		
		//Methods to take care of initialization of Spinner (and SeekBar?)
		initSeekBar();
		initSpinner();
		initButton();
		
	}



	private void initButton() {
		
		RollDiceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				startHistogram();
				
			}
			
		});
		
	}



	protected void startHistogram() {
		
		Intent intent = new Intent(this, HistogramActivity.class);
		intent.putExtra(NUM_DICE, (double) numDice);
		intent.putExtra(NUM_ROLLS, (double) numRolls);
		startActivity(intent);
		
	}



	private void initSeekBar() {
		
		numDiceSeekBar.setOnSeekBarChangeListener(numDiceSeekBarListener);
		
	}



	private void initSpinner() {
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numRolls_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numRollsSpinner.setAdapter(adapter);
		numRollsSpinner.setOnItemSelectedListener(numRollsSpinnerListener);
		
	}
	
	private OnSeekBarChangeListener numDiceSeekBarListener = new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			
			numDice = (numDiceSeekBar.getProgress());
			numDiceTV.setText(String.format("%d", numDice));
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private OnItemSelectedListener numRollsSpinnerListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			numRolls = Integer.parseInt((String) numRollsSpinner.getSelectedItem());
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	


}
