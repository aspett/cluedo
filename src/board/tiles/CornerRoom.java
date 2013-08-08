package board.tiles;
/**
 * Represents a room that has a secret passage from one corner to the corner room
 * diagonally across the board.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class CornerRoom extends Room {

	private RoomTile secretPassage;
	
	public CornerRoom(String name) {
		super(name);
	}
	
	/**
	 * Sets the tile which will be used as the 'secret passage'
	 * @param tile Tile
	 */
	public void setSecretPassage(RoomTile tile){
		this.secretPassage=tile;
	}
	
	/**
	 * Gets the tile which is the secret passage tile for the corner room.
	 * @return
	 */
	public Tile getSecretPassage(){
		return this.secretPassage;
	}
	
	

}
