/**
 * Represents the goal/target sequence that the player is trying to guess in Alien Mastermind
 */
package alienmastermind;

import java.util.Random;

public class Goal {
	private Peg[] sequence; // the actual target guess sequence
	private int[] beenCounted; // keeps track of how each peg was counted (blue rocket, white rocket, or nothing)
	private int level; // current level of the game
	private Random rand = new Random();
	public static final int NUM_PEGS = 4;
	public static final int COUNTEDBLUE = 1;
	public static final int COUNTEDWHITE = 2;
	public static final int NOTCOUNTED = 3;
	

	/**
	 * Creates a new Goal, based on the given level
	 * 
	 * @param currentLevel	current level of the game
	 */
	public Goal(int currentLevel) {
		level = currentLevel;
		sequence = new Peg[NUM_PEGS];
		beenCounted = new int[NUM_PEGS];
		for (int i : beenCounted) {
			beenCounted[i] = NOTCOUNTED;
		}

		switch (level) {
		case 1: // level 1, choose randomly from 4 colors
			for (int i = 0; i < NUM_PEGS; i++) {
				double randDouble = rand.nextDouble();
				if (randDouble < 0.25) {
					sequence[i] = new Peg(Peg.RED);
				} else if ((randDouble >= 0.25) && (randDouble < 0.5)) {
					sequence[i] = new Peg(Peg.YELLOW);
				} else if ((randDouble >= 0.5) && (randDouble < 0.75)) {
					sequence[i] = new Peg(Peg.GREEN);
				} else { // (randDouble >= 0.75)
					sequence[i] = new Peg(Peg.PURPLE);
				}
			}
			break;
		case 2: // level 2, choose randomly from 5 colors
			for (int i = 0; i < NUM_PEGS; i++) {
				double randDouble = rand.nextDouble();
				if (randDouble < 0.2) {
					sequence[i] = new Peg(Peg.RED);
				} else if ((randDouble >= 0.2) && (randDouble < 0.4)) {
					sequence[i] = new Peg(Peg.YELLOW);
				} else if ((randDouble >= 0.4) && (randDouble < 0.6)) {
					sequence[i] = new Peg(Peg.GREEN);
				} else if ((randDouble >= 0.6) && (randDouble < 0.8)) {
					sequence[i] = new Peg(Peg.PURPLE);
				} else { // (randDouble >= 0.8)
					sequence[i] = new Peg(Peg.BLUE);
				}
			}
			break;
		case 3: // level 3, choose randomly from 6 colors
			for (int i = 0; i < NUM_PEGS; i++) {
				double randDouble = rand.nextDouble();
				if (randDouble < 0.167) {
					sequence[i] = new Peg(Peg.RED);
				} else if ((randDouble >= 0.167) && (randDouble < 0.333)) {
					sequence[i] = new Peg(Peg.YELLOW);
				} else if ((randDouble >= 0.333) && (randDouble < 0.5)) {
					sequence[i] = new Peg(Peg.GREEN);
				} else if ((randDouble >= 0.5) && (randDouble < 0.667)) {
					sequence[i] = new Peg(Peg.PURPLE);
				} else if ((randDouble >= 0.667) && (randDouble < 0.833)) {
					sequence[i] = new Peg(Peg.BLUE);
				} else { // (randDouble >= 0.833)
					sequence[i] = new Peg(Peg.ORANGE);
				}
			}
			break;
		case 4: // level 4, choose randomly from 7 colors
			for (int i = 0; i < NUM_PEGS; i++) {
				double randDouble = rand.nextDouble();
				if (randDouble < 0.143) {
					sequence[i] = new Peg(Peg.RED);
				} else if ((randDouble >= 0.143) && (randDouble < 0.286)) {
					sequence[i] = new Peg(Peg.YELLOW);
				} else if ((randDouble >= 0.286) && (randDouble < 0.429)) {
					sequence[i] = new Peg(Peg.GREEN);
				} else if ((randDouble >= 0.429) && (randDouble < 0.572)) {
					sequence[i] = new Peg(Peg.PURPLE);
				} else if ((randDouble >= 0.572) && (randDouble < 0.715)) {
					sequence[i] = new Peg(Peg.BLUE);
				} else if ((randDouble >= 0.715) && (randDouble < 0.858)) {
					sequence[i] = new Peg(Peg.ORANGE);
				} else { // (randDouble >= 0.858)
					sequence[i] = new Peg(Peg.PINK);
				}
			}
			break;
		default: // case 5: // level 5, choose randomly from 8 colors
			for (int i = 0; i < NUM_PEGS; i++) {
				double randDouble = rand.nextDouble();
				if (randDouble < 0.125) {
					sequence[i] = new Peg(Peg.RED);
				} else if ((randDouble >= 0.125) && (randDouble < 0.25)) {
					sequence[i] = new Peg(Peg.YELLOW);
				} else if ((randDouble >= 0.25) && (randDouble < 0.375)) {
					sequence[i] = new Peg(Peg.GREEN);
				} else if ((randDouble >= 0.375) && (randDouble < 0.5)) {
					sequence[i] = new Peg(Peg.PURPLE);
				} else if ((randDouble >= 0.5) && (randDouble < 0.625)) {
					sequence[i] = new Peg(Peg.BLUE);
				} else if ((randDouble >= 0.625) && (randDouble < 0.75)) {
					sequence[i] = new Peg(Peg.ORANGE);
				} else if ((randDouble >= 0.75) && (randDouble < 0.875)) {
					sequence[i] = new Peg(Peg.PINK);
				} else { // (randDouble >= 0.875)
					sequence[i] = new Peg(Peg.AQUA);
				}
			}
			break;
		}

	}


	/**
	 * Returns the peg in the given position of the goal
	 * 
	 * @param position	
	 * @return	the peg in the given position of the goal
	 */
	public Peg getSequence(int position) {
		return sequence[position];
	}


	/**
	 * Returns the sequence of pegs in the goal
	 * 
	 * @return	the sequence of pegs in the goal
	 */
	public Peg[] getSequence() {
		return sequence;
	}

	/**
	 * Counts the peg at the given index as counted as the given rocket color
	 * 
	 * @param index	the index of the peg in the goal
	 * @param color	the rocket color that peg was counted
	 */
	public void setPegCount(int index, int color) {
		beenCounted[index] = color;
	}


	/**
	 * Clears all counts for the goal pegs
	 */
	public void clearAllCounts() {
		for (int i = 0; i < NUM_PEGS; i++) {
			beenCounted[i] = NOTCOUNTED;
		}
	}

	/**
	 * Returns the rocket count for the peg at the given index in the goal
	 * 
	 * @param index	the index of the peg 
	 * 
	 * @return	the rocket count for the peg
	 */
	public int getPegCount(int index) {
		return beenCounted[index];
	}


	/**
	 * Returns the current game level
	 * 
	 * @return	the current game level
	 */
	public int getLevel() {
		return level;
	}
}