package board.tiles;

public class ImpassableTile implements TileI{
	private boolean isWall;
	
	
	public ImpassableTile(boolean value){
		isWall=value;
	}
	
	@Override
	public boolean isPassable() {
		return false;
	}
	public boolean isWall(){
		return isWall;
	}
	

	@Override
	public void onEnter() {
		throw new RuntimeException("You should never be able to enter an impassable tile");
		
	}

}
