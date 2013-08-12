package userinterface;
import java.util.List;

import cards.Card;
import cards.CardTuple;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;


import board.Player;
import board.tiles.Tile;
import board.tiles.Room;

/**
 * Interface for displaying the game state/prompting user interaction for the game.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public interface UserInterface {
	/**
	 * Sets up players.
	 */
	public List<Player> initPlayers();


	public void draw(List<Tile> numberedTiles);
	/**
	 * Tell the interface to prompt the user whether they want to make a guess.
	 * @param currentPlayer Current player
	 * @param currentRoom The room that they're in.
	 * @param isGuessOrAccusation TRUE IF GUESS, FALSE IF ACCUSATION
	 * @param characterCards A list of all character cards.
	 * @param roomCards A list of all room cards
	 * @param weaponCards A list of all weapon cards
	 * @return
	 */
	public CardTuple promptGuess(Player currentPlayer, Room currentRoom, boolean isGuessOrAccusation, List<CharacterCard> characterCards, List<RoomCard> roomCards, List<WeaponCard> weaponCards);
	/**
	 * Ask the interface to get the tile of the next move (1 movement)
	 * @deprecated It would be better to use offerChoices
	 * @param currentPlayer The player that is allowed to move
	 * @return The tile the player moved to, or null if the player does not move.
	 */
	public Tile promptMove(Player currentPlayer);

	/**
	 * Tell the current player that refutePlayer can refute the accusation that they made.
	 * If refutePlayer is null, no one can refute the claim.
	 * @param refutePlayer The first player who can refute the claim (OR NULL FOR NO ONE)
	 * @param refutableCards A list of cards the player can refute with (OR NULL IF NO PLAYER)
	 */
	public void playerCanRefute(Player refutePlayer, List<Card> refutableCards);

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

	/**
	 * return null;
	 * @param correct true for correct accusation, false otherwise
	 */
	public void resolveAccusation(boolean correct);

	/**
	 * Say who won the game
	 * @param player the player who won
	 * @param solution TODO
	 */
	public void printWinner(Player player, CardTuple solution);

	/**
	 * Offer a number of choices to the player.
	 * @param choices Choices available
	 * @return Those choice that was chosen.
	 */
	public int offerChoices(List<String> choices);

	/**
	 * Show them their cards
	 * @param p The player whom we should show their cards
	 */
	public void showCards(Player p);

	/**
	 * Alert the player that they've been blocked in
	 * @param currentPlayer
	 */
	public void alertBlocked(Player currentPlayer);

	/**
	 * Prompt the user to continue. Used when an important message has been outputted, and requires attention before continuing.
	 */
	public void promptContinue();

}
