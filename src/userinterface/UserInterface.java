package userinterface;

import java.util.List;
import cards.CardTuple;


import board.Player;
import board.tiles.Tile;
import board.tiles.Room;

public interface UserInterface {
	public List<Player> initPlayers();
	public void draw();
	public CardTuple promptAccusation(Player currentPlayer, Room currentTile);
	public Tile promptMove(Player currentPlayer);
	/**
	 * Tell the current player that refutePlayer can refute the accusation that they made.
	 * If refutePlayer is null, no one can refute the claim.
	 * @param refutePlayer
	 */
	public void playerCanRefute(Player refutePlayer);
}
