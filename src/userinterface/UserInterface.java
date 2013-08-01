package userinterface;

import java.util.List;

import main.Tile;

import board.Board;
import board.Player;
import board.tiles.Room;

public interface UserInterface {
	public List<Player> initPlayers();
	public void draw();
	public void promptAccusation(Player currentPlayer, Room currentTile);
	public Tile promptMove(Player currentPlayer);
}
