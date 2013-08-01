package userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.Board;
import board.Player;
import board.tiles.*;
//TODO Update class diagram for the interface to hold the board
public class TextBasedInterface implements UserInterface {
	private Board b;
	
	public TextBasedInterface(Board b){
		this.b=b;
	}
	/*
	public void draw(Board b){
		for(TileI t:b.boardTiles){
			if(t instanceof IntrigueTile)System.out.print("?");
			else if(t instanceof Room)System.out.print("E");
			else if(t instanceof StartTile)System.out.print("S");
			else if(t instanceof RegularTile)System.out.print(" ");
		}
	}*/
	
	public List<Player> initPlayers(){
		Scanner scan = new Scanner(System.in);
		System.out.println("How many players?");
		//TODO add error support on scanning
		int playerCount = scan.nextInt();
		List<Player> actualPlayers = new ArrayList<Player>();
		for(int i=0;i<playerCount;i++){
			System.out.printf("Player %d, Select a character.",i);
			List<Player> availablePlayers=b.getAvailablePlayers();
			for(int j=0;j<availablePlayers.size();j++){
				System.out.printf("%i, %s",j,availablePlayers.get(j));
			}
		}
		scan.close();
		return null;
	}
}
