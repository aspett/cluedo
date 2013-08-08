package cards;

public abstract class Card implements Comparable<Card>{
	private String name;

	public void setName(String n){
		this.name=n;
	}

	public abstract String getName();

	@Override
	public int compareTo(Card other) {
		if(other == null) return -1;
		if(this instanceof CharacterCard) {
			if(other instanceof CharacterCard) return this.getName().compareTo(other.getName());
			return -1;
		}
		else if(this instanceof RoomCard) {
			if(other instanceof RoomCard) return this.getName().compareTo(other.getName());
			if(other instanceof CharacterCard) return 1;
			return -1;
		}
		else {
			if(other instanceof WeaponCard) return this.getName().compareTo(other.getName());
			return 1;
		}
	}
}
