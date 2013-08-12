package tests;

import static org.junit.Assert.*;
import board.*;
import board.tiles.*;

import main.Cluedo;
import main.CluedoException;
import main.Dice;

import org.junit.Test;

import userinterface.TextBasedInterface;
import userinterface.UserInterface;

import java.util.*;
public class GeneralTests {

	@Test
	public void checkRoomNames() {
		/*int min = 0, max = 10;
		for(int i = 0; i < 50; i++) {
			System.out.println(Cluedo.rand(0, 10));
		}*/
	}
	@Test
	public void rollSomeDice() {
		Dice d = new Dice();
		for(int i = 0; i < 1000; i++) {
			int roll = d.roll();
			assertTrue(roll > 1 && roll < 13);
		}
	}

	@Test public void cluedoExceptions() {
		try {
			throw new CluedoException();
		} catch(CluedoException e) {
			assertTrue(e.getMessage() == null);
		}

		try {
			throw new CluedoException("hi");
		} catch (CluedoException e) {
			assertFalse(e.getMessage() == null);
		}
	}

}
