package tests;

import static org.junit.Assert.*;
import board.*;
import board.tiles.*;

import main.Cluedo;

import org.junit.Test;
import java.util.*;
public class GeneralTests {

	@Test
	public void checkRoomNames() {
		int min = 0, max = 10;
		for(int i = 0; i < 50; i++) {
			System.out.println(Cluedo.rand(0, 10));
		}
	}

}
