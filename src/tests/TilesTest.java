package tests;

import static org.junit.Assert.*;

import main.CluedoException;

import org.junit.Test;

import board.Board;
import board.Player;
import board.Weapon;
import board.tiles.ImpassableTile;
import board.tiles.RegularTile;
import board.tiles.Room;
import board.tiles.RoomTile;
import board.tiles.Tile;

public class TilesTest {


	@Test
	public void EveryRoomHasOneWeaponAtStartExceptPoolRoom(){
		Board b = new Board();
		for(Room r : b.getRooms()){
			if(r.getName().equalsIgnoreCase("pool room")){
				assertTrue(r.getWeapons().size()==0);
			}else{
				assertTrue(r.getWeapons().size()==1);
			}
		}
	}

	@Test
	public void TestRoomEquals(){
		Room r1 = new Room("Dining Room");		
		r1.addWeapon(new Weapon("Rope"));

		Room r2 = new Room("Dining Room");
		r2.addWeapon(new Weapon("Rope"));

		assertTrue(r1.equals(r2));

		r2.addWeapon(new Weapon("Pistol"));
		assertFalse(r1.equals(r2));
	}


	@Test
	public void TestPlayerSetTiles(){
		Tile t1 = new ImpassableTile(true);
		Tile t2 = new RegularTile();
		Tile t3 = new RoomTile();
		Player p1 = new Player("Jack Mustard");
		Player p2 = new Player("Dianne White");

		try{
			p1.setTile(t1);
			fail("Should not have been able to set an Impassable Tile");
		}catch(CluedoException e){

		}

		try{
			p1.setTile(t2);
			p2.setTile(t2);
			fail("Two players can not occupy the same regular tile");
		}catch(CluedoException e){
			
		}
		
		try{
			p1.setTile(t3);
			p2.setTile(t3);
		}catch(CluedoException e){
			fail("Two players should be able to occupy the same room tile");
		}
		
		



	}



}

