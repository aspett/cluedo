package board.tiles;
/**
 * Represents a tile on the board which can not be entered.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class ImpassableTile extends Tile{
	private boolean isWall;
	
	
	public ImpassableTile(boolean wall){
		isWall=wall;
	}
	
	@Override
	public boolean isPassable() {
		return false;
	}
	
	/**
	 * Returns whether this tile is a wall or, simply impassable (for drawing purposes)
	 * @return True if a wall
	 */
	public boolean isWall(){
		return isWall;
	}
	

	@Override
	public void onEnter() {
		throw new RuntimeException("You should never be able to enter an impassable tile");
	}
	
	@Override
	public int maxOccupants() {
		return 0;
	}


}
