package board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import userinterface.TextBasedInterface;

import board.tiles.*;

public class Board {
	private Tile[][] boardTiles;

	private List<Room> rooms = new ArrayList<Room>();
	private List<Player> availablePlayers = new ArrayList<Player>();
	private List<Player> players = new ArrayList<Player>();
	private List<Weapon> weapons = new ArrayList<Weapon>();
	private int playerCount;

	public Board() {

		this.initializePlayers();
		this.initializeWeapons();
		this.initializeRooms();
		this.createBoard();
		playerCount=0;


	}

	private void createBoard() {
		Scanner scan;
		try {
			scan = new Scanner(new File("boardinput"));
			int rows=scan.nextInt();
			int cols=scan.nextInt();
			scan.nextLine();
			setBoardTiles(new Tile[rows][cols]);
			for(int i=0;i<rows;i++){
				String line=scan.nextLine();
				for(int j=0;j<cols;j++){
					char c=line.charAt(j);
					switch(c){
					case ' ':ImpassableTile gap = new ImpassableTile(false);
							gap.setX(j);
							gap.setY(i);
							getBoardTiles()[i][j] = gap;
							break;

					case '#':ImpassableTile wall = new ImpassableTile(true);
							wall.setX(j);
							wall.setY(i);
							getBoardTiles()[i][j] = wall;
							break;
					case 'P':StartTile start = new StartTile();
							start.setX(j);
							start.setY(i);
							getBoardTiles()[i][j] = start;
							break;
					case '?':IntrigueTile intrigue = new IntrigueTile();
							intrigue.setX(j);
							intrigue.setY(i);
							getBoardTiles()[i][j] = intrigue;
							break;
					case '.':RegularTile reg = new RegularTile();
							reg.setX(j);
							reg.setY(i);
							getBoardTiles()[i][j] = reg;
							break;
					default: int roomNum=c-48;
							RoomTile roomTile = new RoomTile();
							roomTile.setX(j);
							roomTile.setY(i);
							getBoardTiles()[i][j] = roomTile; 
							switch(roomNum){
							case 0:roomTile.setRoom(rooms.get(0));
									rooms.get(0).addTile(roomTile);
									break;
							case 1:roomTile.setRoom(rooms.get(1));
									rooms.get(1).addTile(roomTile);
									break;
							case 2:roomTile.setRoom(rooms.get(2));
									rooms.get(2).addTile(roomTile);
									break;
							case 3:roomTile.setRoom(rooms.get(3));
									rooms.get(3).addTile(roomTile);
									break;
							case 4:roomTile.setRoom(rooms.get(4));
									rooms.get(4).addTile(roomTile);
									break;
							case 5:roomTile.setRoom(rooms.get(5));
									rooms.get(5).addTile(roomTile);
									break;
							case 6:roomTile.setRoom(rooms.get(6));
									rooms.get(6).addTile(roomTile);
									break;
							case 7:roomTile.setRoom(rooms.get(7));
									rooms.get(7).addTile(roomTile);
									break;
							case 8:roomTile.setRoom(rooms.get(8));
									rooms.get(8).addTile(roomTile);
									break;
							case 9:roomTile.setRoom(rooms.get(9));
									rooms.get(9).addTile(roomTile);
									break;
							}

					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

	public void initializeRooms() {
		rooms.add(new Room("Spa"));
		rooms.add(new Room("Theatre"));
		rooms.add(new Room("Living Room"));
		rooms.add(new Room("Observatory"));
		rooms.add(new Room("Patio"));		
		rooms.add(new Room("Hall"));
		rooms.add(new Room("Guest House"));
		rooms.add(new Room("Dining Room"));
		rooms.add(new Room("Kitchen"));

		//assign a weapon randomly to each room.
		Collections.shuffle(weapons);
		for(int i=0; i<rooms.size();i++){
			rooms.get(i).addWeapon(weapons.get(i));
		}

		rooms.add(new Room("Pool Room"));//Pool Room added last as it can not hold a weapon

	}
	public void initializePlayers() {
		availablePlayers.add(new Player("Kasandra Scarlett"));
		availablePlayers.add(new Player("Jack Mustard"));
		availablePlayers.add(new Player("Diane White"));
		availablePlayers.add(new Player("Jacob Green"));
		availablePlayers.add(new Player("Eleanor Peacock"));
		availablePlayers.add(new Player("Victor Plum"));
	}
	public void initializeWeapons() {
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Candle Stick"));
		weapons.add(new Weapon("Knife"));
		weapons.add(new Weapon("Pistol"));
		weapons.add(new Weapon("Baseball Bat"));
		weapons.add(new Weapon("Dumbbell"));
		weapons.add(new Weapon("Trophy"));
		weapons.add(new Weapon("Poison"));
		weapons.add(new Weapon("Axe"));
	}
	
	public List<Tile> getAdjacentTiles(Tile tile){
		int x = tile.getX();
		int y = tile.getY();
		
		List<Tile>adjacentTiles = new ArrayList<Tile>();
		adjacentTiles.add(boardTiles[y+1][x]);
		adjacentTiles.add(boardTiles[y-1][x]);
		adjacentTiles.add(boardTiles[y][x-1]);
		adjacentTiles.add(boardTiles[y][x+1]);
		return adjacentTiles;
	}
	
	public List<Tile> getAvailableTiles(Tile tile) {
		List<Tile> availableTiles = new ArrayList<Tile>();
		for(Tile t : getAdjacentTiles(tile)){
			if(t.isPassable()){
					if(t.currentOccupants(this) < t.maxOccupants()) availableTiles.add(t);
			}
		}
		return availableTiles;
	}
	
	public List<Player> getAvailablePlayers() {
		return availablePlayers;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public List<Weapon> getWeapons() {
		return weapons;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setPlayerCount(int c){
		playerCount=c;
	}

	public Tile[][] getBoardTiles() {
		return boardTiles;
	}

	public void setBoardTiles(Tile[][] boardTiles) {
		this.boardTiles = boardTiles;
	}
	
	/**
	 * Get a room by it's name
	 * @param str Name of the room
	 * @return Room|null
	 */
	public Room getRoom(String str) {
		for(Room r : getRooms()) {
			if(r.toString().equalsIgnoreCase(str)) return r;
		}
		return null;
	}
}
