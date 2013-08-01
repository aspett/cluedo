package board.tiles;

public class ImpassableTile implements TileI{

	@Override
	public boolean isPassable() {
		return false;
	}

	@Override
	public void onEnter() {
		throw new RuntimeException("You should never be able to enter an impassable tile");
		
	}

}
