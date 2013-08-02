package board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
							switch(roomNum){
							case 0:getBoardTiles()[i][j]=rooms.get(0);
									rooms.get(0).setX(j);
									rooms.get(0).setY(i);
									break;
							case 1:getBoardTiles()[i][j]=rooms.get(1);
									rooms.get(1).setX(j);
									rooms.get(1).setY(i);
									break;
							case 2:getBoardTiles()[i][j]=rooms.get(2);
									rooms.get(2).setX(j);
									rooms.get(2).setY(i);
									break;
							case 3:getBoardTiles()[i][j]=rooms.get(3);
									rooms.get(3).setX(j);
									rooms.get(3).setY(i);
									break;
							case 4:getBoardTiles()[i][j]=rooms.get(4);
									rooms.get(4).setX(j);
									rooms.get(4).setY(i);
									break;
							case 5:getBoardTiles()[i][j]=rooms.get(5);
									rooms.get(5).setX(j);
									rooms.get(5).setY(i);
									break;
							case 6:getBoardTiles()[i][j]=rooms.get(6);
									rooms.get(6).setX(j);
									rooms.get(6).setY(i);
									break;
							case 7:getBoardTiles()[i][j]=rooms.get(7);
									rooms.get(7).setX(j);
									rooms.get(7).setY(i);
									break;
							case 8:getBoardTiles()[i][j]=rooms.get(8);
									rooms.get(8).setX(j);
									rooms.get(8).setY(i);
									break;
							case 9:getBoardTiles()[i][j]=rooms.get(9);
									rooms.get(9).setX(j);
									rooms.get(9).setY(i);
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
}
