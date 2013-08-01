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
}
