package main;

import cards.*;
import java.util.*;
import board.*;
import board.tiles.*;
import cards.*;
import java.math.*;



public class Cluedo {
	private final CardTuple solution;

	public Cluedo() {
		Board b = new Board();
		List<Player> players = b.getPlayers();
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

		System.out.printf("Solution: %s\n", solution.toString());

	}

	public static void main (String[] args) {
		Cluedo game = new Cluedo();
	}

	private static int rand(int min, int max) {
		int diff = max-min;
		return (int)((Math.random()*diff)+min);
	}
}
