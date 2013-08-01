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
	private Dice dice;

	private static enum State {
		PLAYER_NEW_TURN,
		GAME_END
	}

	public Cluedo() {
		ui = new TextBasedInterface(b);
		dice = new Dice();
		List<Card> deck = initializeCardsSolution();
		//List<Player> players = b.getPlayers();

		//Get players
		List<Player> players = ui.initPlayers();
		b.setPlayers(players);

		//Choose a random player to start!
		//TODO rules say scarlett is to start. Not sure what happens if no one is her
		int randPlayer = Cluedo.rand(0, players.size());
		Player startingPlayer = b.getPlayers().get(randPlayer);
		System.out.printf("Start player = %d/%s\n", randPlayer, startingPlayer);

		//Testing the board drawing
				ui.draw();

		while(state != State.GAME_END) {
			if(state == State.PLAYER_NEW_TURN) {
				int moves = dice.roll();
				while(moves>0){
					//keep getting moves
				}
			}
		}

		

		System.out.println(deck);
		System.out.println(deck.size());
		Collections.shuffle(deck);
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
		Collections.shuffle(deck);
		return deck;
	}


	public static void main (String[] args) {
		Cluedo game = new Cluedo();
	}

	public static int rand(int min, int max) {
		int diff = max-min;
		return (int)((Math.random()*diff)+min);
	}
}
