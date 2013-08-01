package board.tiles;


public class Room implements TileI {
	private final String name;

	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static enum RoomEnum {
		SPA,
		THEATRE,
		LIVING_ROOM,
		OBSERVATORY,
		HALL,
		GUEST_HOUSE,
		DINING_ROOM,
		KITCHEN,
		PATIO,
		POOL_ROOM
	}

}
