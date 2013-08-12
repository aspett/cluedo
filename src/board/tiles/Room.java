package board.tiles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.CluedoException;

import board.Weapon;

/**
 * Represents a room on the board with a collection of entrances/exits.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class Room {
	//private final String name;

	/**
	 * Weapons currently contained in the room
	 */
	//private List<Weapon> weapons;

	/**
	 * Entrances/Exits to the room
	 */
	//private Set<Tile> tiles;

	public Room(String name) {
		this.name = name;
		weapons=new ArrayList<Weapon>();
		tiles = new HashSet<Tile>();
	}

	public String getName() {
		return name;
	}

	/**
	 * Add a weapon to the currently tracked list of {@link #weapons weapons}
	 * @param weapon {@link Weapon Weapon} to add
	 */
	public void addWeapon(Weapon weapon) {
		if(weapon==null)throw new IllegalArgumentException("Weapon can not be null");
		weapons.add(weapon);
	}

	/**
	 * Removes a weapon from currently tracked list of {@link #weapons weapons}
	 * @param weapon {@link Weapon Weapon} to remove
	 */
	public void removeWeapon(Weapon weapon){
		weapons.remove(weapon);
	}

	/**
	 * Returns a list of weapons currently in the room
	 * @return list of weapons currently in the room
	 */
	public List<Weapon> getWeapons(){
		return this.weapons;
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

	/**
	 * Add a tile to be used as an entrance/exit to the room.
	 * @param r Tile to be used as entrance/exit
	 */
	public void addTile(RoomTile r) {
		if(r == null) throw new CluedoException("A room can't have a null exit/entry");
		this.tiles.add(r);
	}

	/**
	 * Returns whether this room has the specified entrance/exit tile
	 * @param r Tile to check
 	 * @return True if the tile is in part of this room
	 */
	public boolean hasTile(RoomTile r) {
		return (this.tiles.contains(r));
	}

	/**
	 * Returns all the entrance/exit {@link #tiles tiles} in this room
	 * @return
	 */
	public Set<Tile> getTiles() {
		return this.tiles;
	}
}
