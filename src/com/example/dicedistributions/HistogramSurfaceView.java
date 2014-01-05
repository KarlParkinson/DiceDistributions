/*
 * Decide on how to calculate probabilities for dice. Hard code in or algorithm?
 * Want separate thread for rolling dice and drawing observed distribution.
 * Fix the bug when numDice = 0.
 */

package com.example.dicedistributions;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HistogramSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	private SurfaceHolder sh;
	private Dice d;
	private Context c;
	
	public DrawThread drawThread;
	public DiceThread diceThread;
	
	
	public HistogramSurfaceView(Context context, int numDice, int numRolls, Dice dice) {
		
		super(context);
		sh = getHolder();
		sh.addCallback(this);
		d = dice;
		c = context;
		
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//surfaceCreated(holder);
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		diceThread = new DiceThread(d);
		diceThread.setRun(true);
		drawThread = new DrawThread(sh, c, d);
		drawThread.setRun(true);
		diceThread.start();
		drawThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		boolean retry = true;
		drawThread.setRun(false);
		diceThread.setRun(false);
		while (retry) {
			try {
				drawThread.join();
				diceThread.join();
				retry = false;
			} catch (InterruptedException e) {

			}

		}
	}

}
	

