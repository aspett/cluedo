package board.tiles;

/**
 * Represents a tile on the board in which a player starts in.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class StartTile extends Tile {
	public boolean isPassable() { return true; }

	@Override
	public void onEnter() {
		// TODO write onEnter method
	}

}
