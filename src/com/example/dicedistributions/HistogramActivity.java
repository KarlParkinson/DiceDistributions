package com.example.dicedistributions;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class HistogramActivity extends Activity {
	
	private int numDice;
	private int numRolls;
	private Dice dice;
	private HistogramSurfaceView histogram;
	
	public static String EXPECTED_ARRAY = "com.example.dicedistributions.expectedArray";
	public static String OBSERVED_ARRAY = "com.example.dicedistributions.observedArray";
	public static String NUMBERS = "com.example.dicedistributions.numbers";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		numDice = (int) intent.getDoubleExtra(MainActivity.NUM_DICE, 0.0);
		numRolls = (int) intent.getDoubleExtra(MainActivity.NUM_ROLLS, 0.0);
		dice = new Dice(numDice, numRolls);
		histogram = new HistogramSurfaceView(this, numDice, numRolls, dice);
		setContentView(histogram);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.histogram, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		case(R.id.statistics_option):
			launchStatsActivity();
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	private void launchStatsActivity() {
		
		double[] expected = histogram.drawThread.ExpectedDistribution;
		double[] observed = histogram.drawThread.observedDistribution;
		changeObservedToDecimal(observed);
		int[] numbers = computeNumbers();
		Intent intent = new Intent(this, StatsActivity.class);
		intent.putExtra(EXPECTED_ARRAY, expected);
		intent.putExtra(OBSERVED_ARRAY, observed);
		intent.putExtra(NUMBERS, numbers);
		startActivity(intent);
		
		
	}

	private void changeObservedToDecimal(double[] observed) {
		
		for (int i = 0; i < observed.length; i++) {
			observed[i] = observed[i]/numRolls;
		}
		
	}

	private int[] computeNumbers() {
		
		int upper = numDice*6;
		int lower = numDice;
		int[] nums = new int[(upper-lower)+1];
		for(int i = 0; i < ((upper-lower)+1); i++) {
			int z = i + lower;
			nums[i] = z;
		}
		return nums;
	}

}
