package cards;

/**
 * Represents a combination of a {@link CharacterCard character card}, {@link RoomCard room card}, {@link WeaponCard weapon card}
 * @author Andrew Pett & Matthew Mortimer
 *
 */
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

	public String toString() {
		return String.format("[Character: %s, Weapon: %s, Room: %s]", player, weapon, room);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((weapon == null) ? 0 : weapon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardTuple other = (CardTuple) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}
	
}
