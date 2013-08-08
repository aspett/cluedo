package board;

import board.tiles.Room;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Weapon other = (Weapon) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setRoom(Room r){
		this.room=r;
	}

	public Room getRoom(){
		return room;
	}



}
