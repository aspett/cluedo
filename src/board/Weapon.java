package board;

public class Weapon {
	private String weapon;
	
	
	public Weapon(String w){
		weapon=w;
	}
	
	public String getName(){
		return this.weapon;
	}
	public String toString(){
		return String.format("%s", weapon);
	}
	
}
