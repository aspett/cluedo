package userinterface;

import java.util.List;

import board.Board;
import board.Player;

public interface UserInterface {
	public List<Player> initPlayers();
	public void draw(Board b);
}
