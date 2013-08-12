package board.tiles;

import board.Board;
import board.Player;

/**
 * Represents a tile on the board.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public abstract class Tile {
	private int x;
	private int y;
	private boolean secretTile = false;
	private int occupants = 0;

	/**
	 * Can a person visit this tile?
	 * @return true if yes, else false.
	 */
	public abstract boolean isPassable();


	/**
	 * Set the X coordinate of the {@link Board#getBoardTiles() board tiles}
	 * @param x X coordinate
	 */
	public void setX(int x){
		this.x=x;
	}

	/**
	 * Set the Y coordinate of the {@link Board#getBoardTiles() board tiles}
	 * @param y Y coordinate
	 */
	public void setY(int y){
		this.y=y;
	}

	/**
	 * Get the X coordinate of the {@link Board#getBoardTiles() board tiles}
	 */
	public int getX(){
		return x;
	}

	/**
	 * Get the Y coordinate of the {@link Board#getBoardTiles() board tiles}
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return Maximum number of people that can be in a tile
	 */
	public int maxOccupants() {
		return 1;
	}

	/**
	 * Returns the number of current occupants in a tile on a specific board.
	 * @param b Board
	 * @return Number of occupants
	 */
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

	/**
	 * Set whether the tile is a secret passage tile
	 * @param b True if secret passage tile
	 */
	public void setSecretTile(boolean b) {
		this.secretTile = b;
	}

	/**
	 * Returns whether the tile is a secret passage tile
	 * @return whether the tile is a secret passage tile
	 */
	public boolean isSecretTile() { return this.secretTile; }
	
	public void decrementOccupants(){
		this.occupants--;
	}
	
	public void incrementOccupants(){
		this.occupants++;
	}
	
	public int getOccupants(){
		return this.occupants;
	}


}
