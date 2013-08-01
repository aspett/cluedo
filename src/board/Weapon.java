package board;

public class Weapon {
	private Weapon.Weapons weapon;
	
	public static enum Weapons{
		CANDLE,
		KNIFE,
		PISTOL,
		BAT,
		DUMBBELL,
		TROPHY,
		POISON,
		AXE		
	}
	
	public Weapon(Weapon.Weapons w){
		weapon=w;
	}
	
}
