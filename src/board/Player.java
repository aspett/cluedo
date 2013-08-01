package board;

public class Player {
	public String character;
	public int playerNumber;
	
	public Player(String c){
		this.character=c;
	}
	
	public void draw(){
		System.out.println(playerNumber);
	}
}
