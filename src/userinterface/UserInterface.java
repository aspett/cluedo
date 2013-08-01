package userinterface;

import java.util.List;

import board.Board;
import board.Player;
import board.tiles.Tile;

public interface UserInterface {
	public List<Player> initPlayers();
	public void draw();
	public Tile promptMove(Player p);
}
