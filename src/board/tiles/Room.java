package board.tiles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.CluedoException;

import board.Weapon;

//TODO update class diagram to incorporate new Room - RoomTile relationship
public class Room {
	private final String name;
	private List<Weapon> weapons;
	private Set<Tile> tiles;

	public Room(String name) {
		this.name = name;
		weapons=new ArrayList<Weapon>();
		tiles = new HashSet<Tile>();
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
	
	public void addTile(RoomTile r) {
		if(r == null) throw new CluedoException("A room can't have a null exit/entry");
		this.tiles.add(r);
	}

	public boolean hasTile(RoomTile r) {
		return (this.tiles.contains(r));
	}
	
	public Set<Tile> getTiles() {
		return this.tiles;
	}

	
	

	
	

}
