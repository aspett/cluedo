package board.tiles;
//TODO change CD for interface to abstract
public abstract class Tile {
	private int x;
	private int y;
	
	/**
	 * Can a person visit this tile?
	 * @return true if yes, else false.
	 */
	public abstract boolean isPassable();
	
	/**
	 * Called whenever a player enters a tile.
	 */
	public abstract void onEnter();
	
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	
	public int getX(){
		return x;
	}

	public int getY() {
		return y;
	}
	
	
}
