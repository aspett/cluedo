package board;

public class Player {
	public String name;
	public int playerNumber;

	public String getName(){
		return this.name;
	}

	public Player(String c){
		if(this.character == null || this.character.length() < 1) throw new IllegalArgumentException("Player needs a name");
		this.character=c;
	}


}
