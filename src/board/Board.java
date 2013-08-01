package board;

import java.util.*;
import board.tiles.*;

public class Board {
	//TODO build board
	//private final TileI[][] boardTiles;

	//TODO change to private
	public Map<String, Room> rooms = new HashMap<String, Room>();

	public Board() {
		this.initializeRooms();


	}

	public void initializeRooms() {
		rooms.put("Spa", new Room("Spa"));
		rooms.put("Theatre", new Room("Theatre"));
		rooms.put("Living Room", new Room("Living Room"));
		rooms.put("Observatory", new Room("Observatory"));
		rooms.put("Patio", new Room("Patio"));
		rooms.put("Pool Room", new Room("Pool Room"));
		rooms.put("Hall", new Room("Hall"));
		rooms.put("Guest House", new Room("Guest House"));
		rooms.put("Dining Room", new Room("Dining Room"));
		rooms.put("Kitchen", new Room("Kitchen"));
	}
}
