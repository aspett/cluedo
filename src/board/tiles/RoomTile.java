package board.tiles;

import main.CluedoException;

public class RoomTile extends Tile {
	private Room room;
	@Override
	public boolean isPassable() {
		return true;
	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}

	public void setRoom(Room room) {
		if(room == null) throw new CluedoException("Can not give a room entry/exit a null room to belong to");
		this.room = room;
	}
	
	public Room getRoom() {
		return this.room;
	}

}
