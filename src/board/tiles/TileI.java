package board.tiles;

public interface TileI {
	/**
	 * Can a person visit this tile?
	 * @return true if yes, else false.
	 */
	public boolean isPassable();
	
	/**
	 * Called whenever a player enters a tile.
	 */
	public void onEnter();
}
