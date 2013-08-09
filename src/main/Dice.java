package main;

/**
 * Represents a number of die. Used for generating a random number of moves for a player to take.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class Dice {
	private static final int numDice = Cluedo.DEBUG_MODE ? 100 : 2;
	public static int roll() {
		return (int)(Math.random()*(6*numDice)+numDice);
	}
}
