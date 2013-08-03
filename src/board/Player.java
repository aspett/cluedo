package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.CluedoException;

import cards.Card;

import board.tiles.*;

public class Player {
	private String character;
	private int playerNumber;
	private Tile currentTile;
	private static Player currentPlayer;
	private List<Card> cards;
	
	public String getName(){
		return this.character;
	}

	public Player(String c){
		if(c == null || c.length() < 1) throw new IllegalArgumentException("Player needs a name");
		this.character=c;
		cards = new ArrayList<Card>();
	}

	public String toString() {
		return String.format("%s", character);
	}
	
	public void setTile(Tile tile) {
		if(!tile.isPassable()) throw new CluedoException("Player can not be on an impassable tile.");
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
	
	public void addCard(Card c) {
		if(c == null) throw new CluedoException("Can not give a player a null card");
		this.cards.add(c);
	}
	
	public boolean hasCard(Card c) {
		return this.cards.contains(c);
	}
	
	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}
	
	


}
