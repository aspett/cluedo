package board;

import java.util.*;

import board.tiles.*;

public class Board {
	//TODO build board
	//private final TileI[][] boardTiles;

	private List<Room> rooms = new ArrayList<Room>();
	private List<Player> players = new ArrayList<Player>();
	private List<Weapon> weapons = new ArrayList<Weapon>();
	private int playerCount;

	public Board() {
		this.initializeRooms();
		this.initializePlayers();
		this.initializeWeapons();
		playerCount=0;

	}

	public void initializeRooms() {
		rooms.add(new Room("Spa"));
		rooms.add(new Room("Theatre"));
		rooms.add(new Room("Living Room"));
		rooms.add(new Room("Observatory"));
		rooms.add(new Room("Patio"));
		rooms.add(new Room("Pool Room"));
		rooms.add(new Room("Hall"));
		rooms.add(new Room("Guest House"));
		rooms.add(new Room("Dining Room"));
		rooms.add(new Room("Kitchen"));
	}
	public void initializePlayers() {
		players.add(new Player("Kasandra Scarlett"));
		players.add(new Player("Jack Mustard"));
		players.add(new Player("Diane White"));
		players.add(new Player("Jacob Green"));
		players.add(new Player("Eleanor Peacock"));
		players.add(new Player("Victor Plum"));
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
	public List<Player> getPlayers() {
		return players;
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
}
