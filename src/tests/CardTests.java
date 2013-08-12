package tests;

import static org.junit.Assert.*;
import cards.*;
import board.*;
import board.tiles.Room;
import main.*;
import java.util.*;
import org.junit.Test;

public class CardTests {

	@Test
	public void characterCardEqualTest() {
		Board b = new Board();
		List<Player> players = b.getAvailablePlayers();
		List<CharacterCard> cards = new ArrayList<CharacterCard>();
		for(Player p : players) {
			cards.add(new CharacterCard(p));
		}

		for(CharacterCard c : cards) {
			for(CharacterCard k : cards) {
				if(c.equals(k)) {
					assertTrue(c.getName().equals(k.getName()));
				}
				if(!c.equals(k)) {
					assertFalse(c.getName().equals(k.getName()));
				}
			}
		}

		assertFalse(cards.get(0).equals(null));
		assertFalse(cards.get(0).equals("hello"));
		try {
			assertFalse(cards.get(0).equals(new CharacterCard(null)));
			fail("Should not be able to make a null card");
		} catch(NullPointerException e) {

		}
	}

	@Test
	public void weaponCardEqualTests() {
		Board b = new Board();
		List<Weapon> weapons = b.getWeapons();
		List<WeaponCard> cards = new ArrayList<WeaponCard>();
		for(Weapon w : weapons) {
			cards.add(new WeaponCard(w));
		}

		for(WeaponCard w : cards) {
			for(WeaponCard o : cards) {
				if(w.equals(o)) {
					assertTrue(w.getName().equalsIgnoreCase(o.getName()));
				} else {
					assertFalse(w.getName().equalsIgnoreCase(o.getName()));
				}
			}
		}

		assertFalse(cards.get(0).equals(null));
		assertFalse(cards.get(0).equals("hello"));
		try {
			assertFalse(cards.get(0).equals(new WeaponCard(null)));
		} catch(NullPointerException e) {

		}
	}

	@Test
	public void roomCardEqualsTest() {
		Board b = new Board();
		List<Room> rooms = b.getRooms();
		List<RoomCard> cards = new ArrayList<RoomCard>();
		for(Room w : rooms) {
			cards.add(new RoomCard(w));
		}

		for(RoomCard w : cards) {
			for(RoomCard o : cards) {
				if(w.equals(o)) {
					assertTrue(w.getName().equalsIgnoreCase(o.getName()));
				} else {
					assertFalse(w.getName().equalsIgnoreCase(o.getName()));
				}
			}
		}

		assertFalse(cards.get(0).equals(null));
		assertFalse(cards.get(0).equals("hello"));
		try {
			assertFalse(cards.get(0).equals(new RoomCard(null)));
		} catch(NullPointerException e) {

		}
	}

	@Test
	public void comparisonTests() {
		CharacterCard ca = new CharacterCard(new Player("Bob")), cb = new CharacterCard(new Player("Bobby"));
		WeaponCard wa = new WeaponCard(new Weapon("Weapon A")), wb = new WeaponCard(new Weapon("Weapon B"));
		RoomCard ra = new RoomCard(new Room("Room A")), rb = new RoomCard(new Room("Room B"));

		assertTrue(ca.compareTo(cb) < 0);
		assertTrue(ca.compareTo(wa) < 0);
		assertTrue(ca.compareTo(ra) < 0);
		assertTrue(wa.compareTo(wb) < 0);
		assertTrue(wa.compareTo(ra) > 0);
		assertTrue(ra.compareTo(rb) < 0);
		assertTrue(ra.compareTo(ca) > 0);
		assertTrue(ra.compareTo(wa) < 0);
		assertTrue(ca.compareTo(ca) == 0);
		assertTrue(wa.compareTo(wa) == 0);
		assertTrue(ra.compareTo(ra) == 0);
		assertTrue(ca.compareTo(null) < 0);
	}



}
