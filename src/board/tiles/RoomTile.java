package board.tiles;

import main.CluedoException;
/**
 * A tile which is an entrance/exit to a {@link Room room}
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class RoomTile extends Tile {
	private Room room;
	@Override
	public boolean isPassable() {
		return true;
	}

	/**
	 * Sets the room to which this entrance/exit tile belongs to
	 * @param room Room
	 */
	public void setRoom(Room room) {
		if(room == null) throw new CluedoException("Can not give a room entry/exit a null room to belong to");
		this.room = room;
	}

	/**
	 * Gets the room to which this entrance/exit tile belongs to
	 * @return Room
	 */
	public Room getRoom() {
		return this.room;
	}

	@Override
	public int maxOccupants() {
		return 6;
	}
}
