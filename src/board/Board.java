package board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import cards.Card;

import main.CluedoException;

import sun.security.util.Resources;
import userinterface.TextBasedInterface;

import board.tiles.*;

/**
 * The board holds lists of all rooms, weapons, players and controlled players.
 * The board uses a 2D array of Tiles to represent it.
 * @author Matt Mortimer and Andrew Pett
 *
 */

public class Board {
	private Tile[][] boardTiles;
	private int alivePlayers = 0;

	private List<Room> rooms = new ArrayList<Room>();
	/**
	 * A list of all available characters
	 */
	private List<Player> availablePlayers = new ArrayList<Player>();// list of all characters
	/**
	 * A list of REAL players who are playing
	 */
	private List<Player> players = new ArrayList<Player>();//list of ACTUAL players in the game
	private List<Weapon> weapons = new ArrayList<Weapon>();
	private int playerCount;
	private List<Card> freeCards;

	public Board() {

		this.initializePlayers();
		this.initializeWeapons();
		this.initializeRooms();
		this.createBoard();
		playerCount=0;


	}

	/**
	 * Create the board from the boardinput file by reading each character and constructing the corresponding tile for it, and
	 * adding the tile to the correct place in the boardTiles array. If the tile is a roomTile add it to the corresponding rooms set of exits/entrances
	 *
	 */
	private void createBoard() {
		Scanner scan;
		try {
			//String boardString = "30 26\n##########################\n#0    #P.#   #.#    #P.#3#\n#     #..#   #?#    #..# #\n#     #..#   1.#    #..# #\n#     #..#   #.#    #..# #\n#     #.?#   #.#    #..# #\n#     0..#   #.2    #..# #\n#    #...#   #.#    #.?# #\n######...##1##.## ###..# #\n#...?...........#2#....3##\n#P..........?............#\n#####............?.......#\n#   ##4##..####9###.###5##\n#       4..#      #.#    #\n#       #?.#      #.5    #n#       #..#      #.5    #\n#       4..9######9.#    #\n#   ##4##......?....######\n#####....................#\n#P.........##7###........#\n#..........#    #....6####\n#######....#    7....#   #\n#      8...#    #....#   #\n#      #..##    ##...#   #\n#      #..#      #...#   #\n#      #..#      #...#   #\n#      #..#      #...#   #\n#      #..#      #...#   #\n#8     #P.#      #.P.#  6#\n##########################";
			//scan = new Scanner(new File("boardinput"));

			scan = new Scanner(this.getClass().getResourceAsStream("boardinput"));
			int rows=scan.nextInt();
			int cols=scan.nextInt();
			scan.nextLine();
			boardTiles = new Tile[rows][cols];
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
			initializePassages();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}


	}

	/**
	 * Initialize and connect the secret passages for each of the corner rooms
	 */
	private void initializePassages() {
		//Set spa's secret passage to guest house
		if(!(rooms.get(0) instanceof CornerRoom))throw new CluedoException("The room at index 0 should be a corner room");
		else{
			CornerRoom spa=(CornerRoom) rooms.get(0);
			spa.setSecretPassage((RoomTile)boardTiles[28][24]);
			boardTiles[28][24].setSecretTile(true);
		}
		//Set kitchen's secret passage to observatory
		if(!(rooms.get(8) instanceof CornerRoom))throw new CluedoException("The room at index 8 should be a corner room");
		else{
			CornerRoom kitchen=(CornerRoom) rooms.get(8);
			kitchen.setSecretPassage((RoomTile)boardTiles[1][24]);
			boardTiles[1][24].setSecretTile(true);
		}
		//Set observatory's secret passage to kitchen
		if(!(rooms.get(3) instanceof CornerRoom))throw new CluedoException("The room at index 3 should be a corner room");
		else{
			CornerRoom observatory=(CornerRoom) rooms.get(3);
			observatory.setSecretPassage((RoomTile)boardTiles[28][1]);
			boardTiles[28][1].setSecretTile(true);
		}
		//Set guest house's secret passage to spa
		if(!(rooms.get(6) instanceof CornerRoom))throw new CluedoException("The room at index 6 should be a corner room");
		else{
			CornerRoom guestHouse=(CornerRoom) rooms.get(6);
			guestHouse.setSecretPassage((RoomTile)boardTiles[1][1]);
			boardTiles[1][1].setSecretTile(true);
		}


	}


	/**
	 * Create a list that contains all of the rooms in the game. Create the Room objects for each room as we go.
	 * For each Room object (Other than the pool room) place a randomly selected weapon in its weapon set so each room has one weapon at the beginning of the game.
	 */
	public void initializeRooms() {
		rooms.add(new CornerRoom("Spa"));
		rooms.add(new Room("Theatre"));
		rooms.add(new Room("Living Room"));
		rooms.add(new CornerRoom("Observatory"));
		rooms.add(new Room("Patio"));
		rooms.add(new Room("Hall"));
		rooms.add(new CornerRoom("Guest House"));
		rooms.add(new Room("Dining Room"));
		rooms.add(new CornerRoom("Kitchen"));

		//assign a weapon randomly to each room.
		Collections.shuffle(weapons);
		for(int i=0; i<rooms.size();i++){
			rooms.get(i).addWeapon(weapons.get(i));
			weapons.get(i).setRoom(rooms.get(i));
		}

		rooms.add(new Room("Pool Room"));//Pool Room added last as it can not hold a weapon

	}

	/**
	 * Create a list of all the characters/players in the game regardless of whether they are a player controlled character or not.
	 */
	public void initializePlayers() {
		availablePlayers.add(new Player("Kasandra Scarlett"));
		availablePlayers.add(new Player("Jack Mustard"));
		availablePlayers.add(new Player("Diane White"));
		availablePlayers.add(new Player("Jacob Green"));
		availablePlayers.add(new Player("Eleanor Peacock"));
		availablePlayers.add(new Player("Victor Plum"));
	}

	/**
	 * Create a list of all the weapons used in the game.
	 */
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

	/**
	 * Given aTile find all of the adjacent tiles. The tile North, South, East, and West of it.
	 * @param tile The tile in question
	 * @return A list of all the adjacent tiles.
	 */
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

	/**
	 * Given a tile and a player find all of the tiles that the player is allowed to move to from the tile.
	 * @param tile The tile occupied by the player.
	 * @param p The player wanting to move.
	 * @return A List of all the tiles the player is allowed to move to
	 */
	public List<Tile> getAvailableTiles(Tile tile, Player p) {
		List<Tile> availableTiles = new ArrayList<Tile>();
		for(Tile t : getAdjacentTiles(tile)){
			if(t.isPassable()){
				if(t.currentOccupants(this) < t.maxOccupants()) {
					if(!p.getDisallowedTiles().contains(t))
						availableTiles.add(t);
				}
			}
		}
		return availableTiles;
	}

	/**
	 * Get a room by it's name
	 * @param str Name of the room
	 * @return Room|null
	 */
	public Room getRoom(String str) {
		for(Room r : getRooms()) {
			if(r.getName().equalsIgnoreCase(str)) return r;
		}
		return null;
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

	/**
	 * Set the count of human controlled players in the game
	 * @param c The count of players
	 */
	public void setPlayerCount(int c){
		playerCount=c;
	}

	public Tile[][] getBoardTiles() {
		return boardTiles;
	}

	public List<Card> getFreeCards() {
		return freeCards;
	}

	public void setFreeCards(List<Card> freeCards2) {
		this.freeCards=freeCards2;

	}

	public void setAlivePlayers(int count){
		this.alivePlayers = count;
	}

	public int getAlivePlayerCount(){
		return this.alivePlayers;
	}


}
