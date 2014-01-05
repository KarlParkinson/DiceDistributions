package com.example.dicedistributions;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	
	private SurfaceHolder sh;
	private Context ctx;
	private final Paint Expectedpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint Observedpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint Scalepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	private Dice dice;
	
	private int diceUpperBound;
	private int diceLowerBound;
	
	public double[] ExpectedDistribution;
	public double[] observedDistribution;
	
	private double maxProb; // initialize in constructor
	private double probUnits; // initialize in constructor
	
	private boolean run;

	public DrawThread(SurfaceHolder surfaceHolder, Context context, Dice d) {
		
		sh = surfaceHolder;
		dice = d;
		ctx = context;
		Expectedpaint.setColor(Color.BLUE);
		Expectedpaint.setStyle(Style.STROKE);
		Observedpaint.setColor(Color.RED);
		Observedpaint.setStyle(Style.FILL);
		Scalepaint.setStyle(Style.STROKE);
		Scalepaint.setColor(Color.WHITE);
		
		diceLowerBound = dice.numDice; // dice.numDice * 6
		diceUpperBound = dice.numDice*6; // dice.numDice
		
		ExpectedDistribution = computeExpected();
		observedDistribution = new double[(diceUpperBound - diceLowerBound)+1];
		maxProb = max(ExpectedDistribution);
		maxProb = maxProb + (maxProb/10);
		probUnits = maxProb/10;
		
		
	}
	
	private double max(double[] array) {
		double max = array[0];
		for (int i = 1; i < array.length; i++) {
			if(array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}

	private double[] computeExpected() {

		switch(dice.numDice) {
		case(1) :
			double[] expected1 = {0.16667, 0.16667, 0.16667, 0.16667, 0.16667, 0.16667};
			return expected1;
		case(2):
			double[] expected2 = {0.02778, 0.05556, 0.08333, 0.11111, 0.13889, 0.16667, 0.13889, 0.11111, 0.08333, 0.05556, 0.02778};
			return expected2;
		case(3):
			double[] expected3 = {0.00463, 0.01389, 0.02778, 0.0463, 0.06944, 0.09722, 0.11574, 0.125, 0.125, 0.11574, 0.09722, 0.06944, 0.0463, 0.02778, 0.01389, 0.00463};
			return expected3;
		case(4):
			double[] expected4 = {0.00077, 0.00309, 0.00772, 0.01543, 0.02701, 0.04321, 0.06173, 0.08025, 0.09645, 0.10802, 0.11265, 0.10802, 0.09645, 0.08025, 0.06173, 0.04321, 0.02701, 0.01543, 0.00772, 0.00309, 0.00077};
			return expected4;
		case(5):
			double[] expected5 = {0.00013, 0.00064, 0.00193, 0.0045, 0.009, 0.0162, 0.02636, 0.03922, 0.05401, 0.06944, 0.08372, 0.09452, 0.10031, 0.10031, 0.09452, 0.08372, 0.06944, 0.05401, 0.03922, 0.02636, 0.0162, 0.009, 0.0045, 0.00193, 0.00064, 0.00013};
			return expected5;
		case(6):
			double[] expected6 = {0.00002, 0.00013, 0.00045, 0.0012, 0.0027, 0.0054, 0.00977, 0.0162, 0.02488, 0.03571, 0.04816, 0.06121, 0.07354, 0.08372, 0.09047, 0.09285, 0.09047, 0.08372, 0.07354, 0.06121, 0.04816, 0.03571, 0.02488, 0.0162, 0.00977, 0.0054, 0.0027, 0.0012, 0.00045, 0.00013, 0.00002};
			return expected6;
		default:
			double[] expectedDefault = {0.0};
			return expectedDefault;
		}
	}
	
	private void computeObserved() {
		
		for (Integer i = 0; i < ((diceUpperBound-diceLowerBound)+1); i++) {
			Integer z = i + diceLowerBound;
			Integer numRolled = dice.rolls.get(z.toString());
			if (numRolled == null) {
				numRolled = 0;
			}
			observedDistribution[i] = numRolled;
		}
		
	}
	
	public void setRun(boolean b) {
		run = b;
	}

	public void run() {
		
		while(run) {
			synchronized(dice) {
				while (dice.rollsLeft > 0) {
					try {
						dice.wait();
					} catch (InterruptedException e) {
						
					}
				}
				computeObserved();
				draw();
				dice.notify();
				this.run = false;
			}
		}
	}
	


	private void draw(){
		
		
		Canvas canvas = sh.lockCanvas();
		//canvas.drawColor(Color.BLACK);
		
		double bottomBuffer = canvas.getHeight()/12;
		double pixelMax = canvas.getHeight()-bottomBuffer;
		double leftBuffer = canvas.getWidth()/12;
		double pixelUnits = pixelMax/10;
		
		for (int i=0; i<observedDistribution.length; i++) {
			float bottom = (float) pixelMax;
			float width = (float) ((canvas.getWidth()-leftBuffer)/observedDistribution.length);
			float left = (float) ((i*width)+leftBuffer);
			float top = (float) ((bottom - (((observedDistribution[i]/dice.numRolls)/probUnits)*pixelUnits)));
			float right = left + width;
			canvas.drawRect(left, top, right, bottom, Observedpaint);
		}
		
		for (int i = 0; i<ExpectedDistribution.length; i++) {
			float width = (float) ((canvas.getWidth()-leftBuffer)/ExpectedDistribution.length);
			float left = (float) ((i*width)+leftBuffer);
			float top = (float) (pixelMax-((ExpectedDistribution[i]/probUnits)*pixelUnits));
			float right = left+width;
			float bottom = (float) pixelMax;
			canvas.drawRect(left, top, right, bottom, Expectedpaint);
		}
		
		// Draw x-axis of graph
		canvas.drawLine((float) (0+leftBuffer), (float) pixelMax, canvas.getWidth(), (float) pixelMax, Scalepaint);
		// Draw y-axis of graph
		float yAxisTop = (float) ((maxProb/probUnits)*pixelUnits);
		float startMeasuring = (float) (pixelMax + ((canvas.getHeight()-pixelMax)/2));
		canvas.drawLine((float) (0+leftBuffer), (float) pixelMax, (float) (0+leftBuffer), startMeasuring-yAxisTop, Scalepaint);
		
		sh.unlockCanvasAndPost(canvas);
		
	}
}
