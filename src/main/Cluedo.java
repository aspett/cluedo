package main;

import cards.*;
import java.util.*;
import board.*;
import board.tiles.*;
import cards.*;
import java.math.*;

import userinterface.TextBasedInterface;
import userinterface.UserInterface;



public class Cluedo {
	private CardTuple solution;
	private Board b = new Board();
	private UserInterface ui;
	private State state;

	private static enum State {

	}

	public Cluedo() {
		ui = new TextBasedInterface(b);
		List<Card> deck = initializeCardsSolution();
		//List<Player> players = b.getPlayers();

		//Get players
		//TODO do this once we get matt's interface changes
		//List<Player> players = ui.initPlayers();

		//Choose a random player to start!




		System.out.println(deck);
		System.out.println(deck.size());
		Collections.shuffle(deck);
	}

	public static void main (String[] args) {
		Cluedo game = new Cluedo();
	}

	/**
	 * Makes a random solution, and returns a list of the remaining cards in the deck.
	 * @return remaining cards in deck after solution is taken out
	 */
	private List<Card> initializeCardsSolution() {
		List<Player> players = b.getAvailablePlayers();
		List<Weapon> weapons = b.getWeapons();
		List<Room> rooms = b.getRooms();

		List<CharacterCard> cc = new ArrayList<CharacterCard>();
		List<RoomCard> rc = new ArrayList<RoomCard>();
		List<WeaponCard> wc = new ArrayList<WeaponCard>();

		for(Player p : players) {
			cc.add(new CharacterCard(p));
		}
		for(Weapon w : weapons) {
			wc.add(new WeaponCard(w));
		}
		for(Room r : rooms) {
			rc.add(new RoomCard(r));
		}

		int rand = Cluedo.rand(0, wc.size());
		WeaponCard weaponSolution = wc.remove(rand);

		rand = Cluedo.rand(0, rc.size());
		RoomCard roomSolution = rc.remove(rand);

		rand = Cluedo.rand(0, cc.size());
		CharacterCard characterSolution = cc.remove(rand);

		solution = new CardTuple(characterSolution, roomSolution, weaponSolution);

		List<Card> deck = new ArrayList<Card>();
		deck.addAll(cc);
		deck.addAll(rc);
		deck.addAll(wc);

		return deck;
	}

	//UTILITY METHODS

	/**
	 * Generates a random integer between min and max, not including max.
	 * @param min Minimum number
	 * @param max maximum number
	 * @return a random integer
	 */
	private static int rand(int min, int max) {
		int diff = max-min;
		return (int)((Math.random()*diff)+min);
	}
}
