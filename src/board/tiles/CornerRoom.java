package board.tiles;

public class CornerRoom extends Room {

	private RoomTile secretPassage;
	
	public CornerRoom(String name) {
		super(name);
	}
	
	public void setSecretPassage(RoomTile tile){
		this.secretPassage=tile;
	}
	
	public Tile getSecretPassage(){
		return this.secretPassage;
	}
	
	

}
