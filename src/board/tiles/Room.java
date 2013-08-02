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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((weapons == null) ? 0 : weapons.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (weapons == null) {
			if (other.weapons != null)
				return false;
		} else if (!weapons.equals(other.weapons))
			return false;
		return true;
	}
	
	

	
	

}
