package board.tiles;

import board.Board;
import board.Player;

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
	
	public int maxOccupants() {
		return 1;
	}
	
	public int currentOccupants(Board b) {
		int r = 0;
		for(Player p : b.getPlayers()) {
			if(p.getTile().equals(this)) r++;
		}
		return r;
	}
	
	@Override
	public boolean equals(Object o) {
		return (this == o);
	}
	
	
}
