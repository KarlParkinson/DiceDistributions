package com.example.dicedistributions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private final Context context;
	private final int[] numbers;
	private final double[] expectedDistribution;
	private final double[] observedDistribution;
	
	public MyAdapter(Context ctx, int[] nums, double[] expected, double[] observed) {
		
		this.context = ctx;
		this.numbers = nums;
		this.expectedDistribution = expected;
		this.observedDistribution = observed;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return numbers.length;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.row_layout, parent, false);
		TextView textView1 = (TextView) row.findViewById(R.id.numDiceTextView);
		TextView textView2 = (TextView) row.findViewById(R.id.textView2);
		TextView textView3 = (TextView) row.findViewById(R.id.textView3);
		
		textView1.setText(String.valueOf(numbers[position]));
		try{
			textView2.setText(String.valueOf(expectedDistribution[position]));
		} catch(NullPointerException e) {
			textView2.setText(String.valueOf(0.1667));
		}
		textView3.setText(String.valueOf(observedDistribution[position]));
		
		return row;
	}

}
