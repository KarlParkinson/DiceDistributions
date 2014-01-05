package com.example.dicedistributions;

public class DiceThread extends Thread {
	
	private Dice dice;
	private boolean run;
	
	public DiceThread(Dice d) {
		
		dice = d;
		
	}
	
	public void setRun(boolean b) {
		run = b;
	}
	
	// Method to simulate rolling dice
	private void rollDice() {
		
		Integer roll = 0;
		int numDice = dice.numDice;
		
		while(numDice > 0) {
			roll += (1 + (int)(Math.random()*((6-1)+1)));
			numDice--;
		}
		Integer result = dice.rolls.get(roll.toString());
		dice.rolls.put(roll.toString(), (result==null) ? 1: result+1);
		dice.rollsLeft --;
		dice.numRolls++;
		
	}
	
	public void run() {

		while (run) {
			synchronized (dice) {
				while(dice.rollsLeft > 0) {
					rollDice();
				}
				dice.notify();
				this.run = false;
			}
		}
	}
}