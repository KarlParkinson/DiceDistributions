package com.example.dicedistributions;

import java.util.HashMap;
import java.util.Map;

// This object will consist of a map to store results of rolls and number of rolls made and left
public class Dice {

	public int numRolls;
	public int rollsLeft;
	public int numDice;
	public Map<String, Integer> rolls;
	public boolean draw = false;
	
	public Dice(int numdice, int rollsWanted) {
		numDice = numdice;
		numRolls = 0;
		rollsLeft = rollsWanted;
		rolls = new HashMap<String, Integer>();
	}
	
}
