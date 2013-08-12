package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.CluedoException;

import cards.Card;

import board.tiles.*;
/**
 * A class that represents a character in the game, and also a real player of the game.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class Player {
	private String character;
	private int playerNumber;
	private Tile currentTile;
	private static Player currentPlayer;
	private List<Card> cards;
	private Set<Tile> disallowed;
	private boolean mustmove;
	private Room currentRoom;
	private boolean blocked;
	private boolean alive;


	public Player(String c){
		if(c == null || c.length() < 1) throw new IllegalArgumentException("Player needs a name");
		this.character=c;
		cards = new ArrayList<Card>();
		disallowed = new HashSet<Tile>();
		mustmove = false;
		currentRoom=null;
		alive = false;
	}

	public String getName(){
		return this.character;
	}

	public String toString() {
		return String.format("%s", character);
	}

	/**
	 * Set the current tile occupied by the player to the given tile
	 * @param tile The tile to be occupied
	 */
	public void setTile(Tile tile) {
		setTile(tile, false);
	}

	/**
	 * Set the current tile occupied by the player to the given tile.
	 * Ignore invariant checks if bypassDisallowed is true.
	 * @param tile Tile to be occupied
	 * @param bypassDisallowed True if wishing to bypass invariant checks.
	 */
	public void setTile(Tile tile, boolean bypassDisallowed) {
		if(!tile.isPassable() || (disallowed.contains(tile) && !bypassDisallowed)) throw new CluedoException("Player can not be on an impassable tile, or moved in to a disallowed tile");
		if(currentTile != null)
			this.currentTile.decrementOccupants();
		if(tile.getOccupants() == tile.maxOccupants())throw new CluedoException("Players can not share the same tile.");
		this.currentTile = tile;
		this.currentTile.incrementOccupants(); 
	}

	public Tile getTile() {
		return this.currentTile;
	}

	/**
	 * @deprecated Shouldn't use this anymore
	 * @param p
	 */
	public static void setCurrentPlayer(Player p ) {
		currentPlayer = p;
	}

	/**
	 * @deprecated Shouldn't use this anymore
	 * @return
	 */
	public static Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Give the player a card
	 * @param c The card to give to the player
	 */
	public void addCard(Card c) {
		if(c == null) throw new CluedoException("Can not give a player a null card");
		this.cards.add(c);
		Collections.sort(this.cards);
	}

	/**
	 * Check whether a user has a card
	 * @param c The card
	 * @return true if have the card, else false.
	 */
	public boolean hasCard(Card c) {
		return this.cards.contains(c);
	}

	/**
	 * Gets all the cards the player has
	 * @return UNMODIFIABLE list of cards the player has.
	 */
	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}

	/**
	 * Sets the tiles that a player is not allowed to move in to.
	 * Used for invariant checks in {@link #setTile(Tile, boolean) setTile} method.
	 * @param tiles Disallowed tiles
	 */
	public void setDisallowedTiles(Set<Tile> tiles) {
		if(tiles == null) this.disallowed = new HashSet<Tile>();
		else
			this.disallowed = tiles;
	}

	/**
	 * Gets the tiles that a player is not allowed to move in to.
	 * Used for invariant checks in {@link #setTile(Tile, boolean) setTile} method.
	 * @return tiles Disallowed tiles
	 */
	public Set<Tile> getDisallowedTiles() {
		return this.disallowed;
	}

	/**
	 * Adds a disallowed tile.
	 * Used for invariant checks in {@link #setTile(Tile, boolean) setTile} method.
	 * @param t Tile to add
	 */
	public void addDisallowedTile(Tile t) {
		this.disallowed.add(t);
	}

	/**
	 * Checks if a tile is disallowed for a player
	 * Used for invariant checks in {@link #setTile(Tile, boolean) setTile} method/movement
	 * @param t Tile
	 * @return True if tile is disallowed
	 */
	public boolean isTileDisallowed(Tile t) {
		return this.disallowed.contains(t);
	}

	/**
	 * Used to indicate whether the player was in a room at the start of their turn,
	 * and therefore must move. (Option of 'staying put' not available)
	 * @return True if the player must move
	 */
	public boolean mustMove() {
		return this.mustmove;
	}

	/**
	 * Sets whether the player must move. See {@link #mustMove() mustMove} doc
	 * @param m True if player must move
	 */
	public void setMustMove(boolean m) {
		this.mustmove = m;
	}

	/**
	 * Returns whether a player is allowed to stay in a specific tile.
	 * @param t Tile
	 * @return True if the player may stay in the specific tile.
	 */
	public boolean canStayInTile(Tile t) {
		return !(mustmove && disallowed.contains(t));
	}

	/**
	 * Set whether this player is blocked in by other players.
	 * This is used to ensure that we can still use the invariants on setTile
	 * @param blocked
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * Get whether this player is blocked
	 * @see {@link #setBlocked setBlocked}
	 * @return whether the player is blocked
	 */
	public boolean isBlocked() {
		return this.blocked;
	}
	/**
	 * Get whether this player is active and alowed to move about the board and be drawn
	 * @return whether the player is active
	 */
	public boolean isAlive(){
		return this.alive;
	}

	public void setAlive(boolean alive){
		this.alive = alive;
	}



}
