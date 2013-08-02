package userinterface;

import java.util.List;
import cards.CardTuple;


import board.Player;
import board.tiles.Tile;
import board.tiles.Room;

public interface UserInterface {
	/**
	 * Sets up players.
	 */
	public List<Player> initPlayers();
	public void draw();
	/**
	 * Tell the interface to prompt the user whether they want to make a guess.
	 * @param currentPlayer Current player
	 * @param currentRoom The room that they're in.
	 * @param isGuessOrAccusation TRUE IF GUESS, FALSE IF ACCUSATION
	 * @return
	 */
	public CardTuple promptGuess(Player currentPlayer, Room currentRoom, boolean isGuessOrAccusation);
	/**
	 * Ask the interface to get the tile of the next move (1 movement)
	 * @param currentPlayer The player that is allowed to move
	 * @return The tile the player moved to, or null if the player does not move.
	 */
	public Tile promptMove(Player currentPlayer);
	/**
	 * Tell the current player that refutePlayer can refute the accusation that they made.
	 * If refutePlayer is null, no one can refute the claim.
	 * @param refutePlayer
	 */
	public void playerCanRefute(Player refutePlayer);
	
	/**
	 * Tell the next player that it's their turn, and how many moves they can make.
	 * @param currentPlayer The player
	 */
	public void alertPlayerTurn(Player currentPlayer);
	
	/**
	 * Alert the player of the number of moves they have left.
	 * @param moves Number of moves left.
	 */
	public void alertNumMoves(int moves) ;
}
