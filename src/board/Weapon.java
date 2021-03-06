package board;

import board.tiles.Room;
/**
 * Class used to represent a weapon in the game of Cluedo
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class Weapon {

	private Room room;
	private String name;

	public Weapon(String w){
		name=w;
	}

	public String getName(){
		return this.name;
	}
	public String toString(){
		return String.format("%s", name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weapon other = (Weapon) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Set the room that the weapon is in
	 * @param r Room
	 */
	public void setRoom(Room r){
		this.room=r;
	}

	/**
	 * Get the room that the weapon is currently contained in.
	 * @return
	 */
	public Room getRoom(){
		return room;
	}



}
