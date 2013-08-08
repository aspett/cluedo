package cards;

import board.Player;

/**
 * A card representing a character in the game.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class CharacterCard extends Card{
	private Player character;

	public CharacterCard(Player c){
		character=c;
		this.setName(c.getName());

	}

	public String toString(){
		return character.getName();
	}

	/**
	 * Get the player that the card represents
	 * @return
	 */
	public Player getPlayer(){
		return character;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((character == null) ? 0 : character.hashCode());
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
		CharacterCard other = (CharacterCard) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		return true;
	}

	@Override
	public String getName() {
		return character.getName();
	}

}


