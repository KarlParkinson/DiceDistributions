package com.example.dicedistributions;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class StatsActivity extends ListActivity {
	
	private double[] observed;
	private double[] expected;
	private int[] numbers;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statslayout);
		Intent intent = getIntent();
		expected = intent.getDoubleArrayExtra(HistogramActivity.EXPECTED_ARRAY);
		observed = intent.getDoubleArrayExtra(HistogramActivity.OBSERVED_ARRAY);
		numbers = intent.getIntArrayExtra(HistogramActivity.NUMBERS);
		
		MyAdapter adapter = new MyAdapter(this, numbers, expected, observed);
		setListAdapter(adapter);
		
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}


}
