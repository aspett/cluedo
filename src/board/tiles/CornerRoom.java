package board.tiles;

public class CornerRoom extends Room {

	private Tile secretPassage;
	
	public CornerRoom(String name) {
		super(name);
	}
	
	public void setSecretPassage(Tile tile){
		this.secretPassage=tile;
	}
	
	public Tile getSecretPassage(){
		return this.secretPassage;
	}
	
	

}
