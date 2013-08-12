package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import board.Weapon;
import board.tiles.Room;

public class WeaponTests {

	@Test
	public void weaponEquality() {
		Weapon one = new Weapon("Weapon"), two = new Weapon("Two"), three = new Weapon("Weapon");
		assertTrue(one.equals(three));
		assertFalse(two.equals(one));
	}

	@Test public void fieldTests() {
		Weapon a = new Weapon("Weap");
		assertTrue(a.getName().equals("Weap"));
		Room r = new Room("A room");
		a.setRoom(r);
		assertTrue(r == a.getRoom());
		assertTrue(a.getName().equals(a.toString()));
	}

}
