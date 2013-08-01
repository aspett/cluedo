package board;

public class Player {
	public String character;
	public int playerNumber;

	public Player(String c){
		if(this.character == null || this.character.length() < 1) throw new IllegalArgumentException("Player needs a name");
		this.character=c;
	}

	public void draw(){
		System.out.println(playerNumber);
	}

}
