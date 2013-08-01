package cards;

public final class CardTuple {
	private CharacterCard player;
	private RoomCard room;
	private WeaponCard weapon;

	public CardTuple(CharacterCard p, RoomCard r, WeaponCard w) {
		player = p;
		room = r;
		weapon = w;
	}

	public CharacterCard getPlayer() {
		return player;
	}

	public void setPlayer(CharacterCard player) {
		this.player = player;
	}

	public RoomCard getRoom() {
		return room;
	}

	public void setRoom(RoomCard room) {
		this.room = room;
	}

	public WeaponCard getWeapon() {
		return weapon;
	}

	public void setWeapon(WeaponCard weapon) {
		this.weapon = weapon;
	}
}
