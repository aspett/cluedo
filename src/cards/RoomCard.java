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

}
