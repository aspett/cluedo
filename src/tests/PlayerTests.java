package tests;

import static org.junit.Assert.*;

import java.util.*;

import main.CluedoException;

import org.junit.Test;

import cards.Card;
import cards.CharacterCard;

import board.Board;
import board.Player;

public class PlayerTests {

	@Test
	public void playerEquality() {
		Player one = new Player("Hi"), two = new Player("HiThere");
		assertFalse(one.equals(two));
		Player three = new Player("Hi");
		Player four = new Player("hi");
		assertTrue(one.equals(three));
		assertTrue(three.equals(four));
	}

	@Test
	public void uniquePlayers() {
		Board b = new Board();
		List<Player> players = b.getAvailablePlayers();
		for(Player p : players) {
			for(Player o : players) {
				if(p.getName().equals(o.getName())) {
					assertTrue(p == o);
				}
				else assertFalse(p == o);
			}
		}
	}

	@Test public void givingCards() {
		Player p = new Player("Bob");
		Card c = new CharacterCard(new Player("John"));
		assertFalse(p.hasCard(c));
		p.addCard(c);
		assertTrue(p.hasCard(c));
	}

	@Test public void modifyingCards() {
		Player p = new Player("Bob");
		Card c = new CharacterCard(new Player("John"));
		List<Card> cards = p.getCards();
		try {
			cards.remove(0);
			fail("Should not be able to modify cards");
		} catch (UnsupportedOperationException e) {

		}
		try {
			cards.add(c);
			fail("Should not be able to modify cards");
		} catch (UnsupportedOperationException e) {

		}
		try {
			p.addCard(null);
			fail("should not be able to add null card");
		} catch(CluedoException e) {

		}
	}

	@Test public void toStringTest() {
		Player p = new Player("Player Name");
		assertTrue(p.toString().equals("Player Name"));
	}

}
