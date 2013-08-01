package board.tiles;

import java.util.ArrayList;
import java.util.List;

import board.Weapon;


public class Room extends Tile {
	private final String name;
	private List<Weapon> weapons;

	public Room(String name) {
		this.name = name;
		weapons=new ArrayList<Weapon>();
	}

	public String getName() {
		return name;
	}
	

	public static enum RoomEnum {
		SPA,
		THEATRE,
		LIVING_ROOM,
		OBSERVATORY,
		HALL,
		GUEST_HOUSE,
		DINING_ROOM,
		KITCHEN,
		PATIO,
		POOL_ROOM
	}

	public void addWeapon(Weapon weapon) {
		if(weapon==null)throw new IllegalArgumentException("Weapon can not be null");
		weapons.add(weapon);
		
	}
	public List<Weapon> getWeapons(){
		return this.weapons;
	}

	@Override
	public boolean isPassable() {
		return true;
	}

	@Override
	public void onEnter() {
		// TODO write onEnter method
	}

	
	

}
