package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.CluedoException;

import cards.Card;

import board.tiles.*;

public class Player {
	private String character;
	private int playerNumber;
	private Tile currentTile;
	private static Player currentPlayer;
	private List<Card> cards;
	private Set<Tile> disallowed;
	private boolean mustmove;
	private Room currentRoom;


	public Player(String c){
		if(c == null || c.length() < 1) throw new IllegalArgumentException("Player needs a name");
		this.character=c;
		cards = new ArrayList<Card>();
		disallowed = new HashSet<Tile>();
		mustmove = false;
		currentRoom=null;
	}

	public String getName(){
		return this.character;
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
		Collections.sort(this.cards);
	}

	public boolean hasCard(Card c) {
		return this.cards.contains(c);
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}

	public void setDisallowedTiles(Set<Tile> tiles) {
		if(tiles == null) this.disallowed = new HashSet<Tile>();
		else
			this.disallowed = tiles;
	}
	public Set<Tile> getDisallowedTiles() {
		return this.disallowed;
	}

	public void addDisallowedTile(Tile t) {
		this.disallowed.add(t);
	}

	public boolean isTileDisallowed(Tile t) {
		return this.disallowed.contains(t);
	}

	public boolean mustMove() {
		return this.mustmove;
	}

	public void setMustMove(boolean m) {
		this.mustmove = m;
	}

	public boolean canStayInTile(Tile t) {
		return !(mustmove && disallowed.contains(t));
	}



}
