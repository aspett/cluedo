package cards;

import board.Player;

public class CharacterCard extends Card {
	private Player character;
	
	public CharacterCard(Player c){
		character=c;
		this.setName(c.getName());
		
	}
	
}


