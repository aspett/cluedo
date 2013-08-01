package board;

public class Player {
	public String character;
	public int playerNumber;

	public String getName(){
		return this.character;
	}

	public Player(String c){
		if(c == null || c.length() < 1) throw new IllegalArgumentException("Player needs a name");
		this.character=c;
	}

	public String toString() {
		return String.format("%s", character);
	}


}
