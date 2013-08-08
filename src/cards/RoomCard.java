package cards;

import board.tiles.Room;

public class RoomCard extends Card {

	private Room room;

	public RoomCard(Room r){
		room=r;
	}

	public String toString(){
		return room.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((room == null) ? 0 : room.hashCode());
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
		RoomCard other = (RoomCard) obj;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}

	@Override
	public String getName() {
		return room.getName();
	}


}
