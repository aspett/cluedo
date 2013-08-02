package tests;

import static org.junit.Assert.*;
import board.*;
import board.tiles.*;

import main.Cluedo;
import main.Dice;

import org.junit.Test;
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
	public void runSomeDice() {
		Dice d = new Dice();
		for(int i = 0; i < 1000; i++) {
			int roll = d.roll();
			assertTrue(roll > 0 && roll < 7);
		}
	}

}
