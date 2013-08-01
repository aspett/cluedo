package board;

import board.tiles.*;

public class Player {
	private String character;
	private int playerNumber;
	private Tile currentTile;
	private static Player currentPlayer;
	public String getName(){
		return this.character;
	}

	public Player(String c){
		if(c == null || c.length() < 1) throw new IllegalArgumentException("Player needs a name");
		this.character=c;
	}

	public String toString() {
		return String.format("%s", character);
	}
	
	public void setTile(Tile tile) {
		this.currentTile = tile;
	}
	
	public Tile getTile() {
		return this.currentTile;
	}
	
	public static void setCurrentPlayer(Player p ) {
		currentPlayer = p;
	}
	public static Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	


}
