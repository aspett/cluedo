package cards;

import board.Weapon;

public class WeaponCard extends Card{
	
	private Weapon weapon;
	
	public WeaponCard(Weapon w){
		this.weapon=w;
	}

	public String toString(){
		return weapon.getName();
	}
}
