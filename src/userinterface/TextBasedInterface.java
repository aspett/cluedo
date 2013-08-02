package userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cards.CardTuple;

import board.Board;
import board.Player;
import board.tiles.*;
//TODO Update class diagram for the interface to hold the board
public class TextBasedInterface implements UserInterface {
	private Board b;

	public TextBasedInterface(Board b){
		this.b=b;
	}

	public void draw(){
		for(Tile[] tArray:b.getBoardTiles()){
			for(Tile t:tArray){
				boolean drawTile = true;
				for(Player p : b.getPlayers()) {
					if(p.getTile().equals(t)) {
						drawTile = false;
						System.out.println("p ");
					}
				}
				if(!drawTile) continue;
				if(t instanceof IntrigueTile)System.out.print("? ");
				else if(t instanceof Room)System.out.print("E ");
				else if(t instanceof StartTile)System.out.print("S ");
				else if(t instanceof RegularTile)System.out.print(". ");
				else if(t instanceof ImpassableTile){
					ImpassableTile iT=(ImpassableTile)t;
					if(iT.isWall())System.out.print("# ");
					else System.out.print("  ");

				}
			}
			System.out.println();
		}
	}

	public List<Player> initPlayers(){
		Scanner scan = new Scanner(System.in);
		System.out.println("How many players?");
		//TODO add error support on scanning and bounds of player ammount etc
		int playerCount = scan.nextInt();
		List<Player> actualPlayers = new ArrayList<Player>();
		for(int i=0;i<playerCount;i++){
			System.out.printf("Player %d, Select a character.\n",i);
			List<Player> availablePlayers=b.getAvailablePlayers();
			for(int j=0;j<availablePlayers.size();j++){
				System.out.printf("%d, %s\n",j,availablePlayers.get(j));
			}
			int choice = scan.nextInt();
			actualPlayers.add(b.getAvailablePlayers().remove(choice));
		}
		scan.close();
		return actualPlayers;
	}

	public Tile promptMove(Player p){
		Tile tile = p.getTile();
		//all addjacent tiles
		List<Tile> adjacentTiles = b.getAdjacentTiles(tile);
		//possible tiles to move to
		List<Tile> availableTiles = new ArrayList<Tile>();
		for(Tile t : adjacentTiles){
			if(t.isPassable()){
				availableTiles.add(t);
			}
		}

		System.out.println("Where would you like to move?");
		for(int i=0;i<availableTiles.size();i++){
			if(availableTiles.get(i).getY()>tile.getY()){
				System.out.printf("(%d) Move Up?",i);
			}else if(availableTiles.get(i).getY()<tile.getY()){
				System.out.printf("(%d) Move Down?",i);
			}else if(availableTiles.get(i).getX()>tile.getX()){
				System.out.printf("(%d) Move Right?",i);
			}else if(availableTiles.get(i).getX()<tile.getX()){
				System.out.printf("(%d) Move Left?",i);
			}
		}

		System.out.printf("(%d) Stay Put?",availableTiles.size());

		Scanner scan = new Scanner(System.in);

		//TODO fix for error checking		
		int choice = scan.nextInt();
		while(choice<0 && choice>availableTiles.size()){
			System.out.println("Please make a valid choice..");
			choice = scan.nextInt();
		}
		if(choice<availableTiles.size()){//they want to move
			scan.close();
			return availableTiles.get(choice);
		}

		scan.close();
		return null;

	}

	@Override
	public CardTuple promptGuess(Player currentPlayer, Room currentTile,
			boolean isGuessOrAccusation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void playerCanRefute(Player refutePlayer) {
		// TODO Auto-generated method stub

	}
}
