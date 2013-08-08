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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		WeaponCard other = (WeaponCard) obj;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}

	@Override
	public String getName() {
		return weapon.getName();
	}


	public Weapon getWeapon(){
		return weapon;
	}


}
