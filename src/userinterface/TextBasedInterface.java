package userinterface;

import java.util.Scanner;

import board.Board;
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
	
	public void scanInput(){
		Scanner scan = new Scanner(System.in);
		b.setPlayerCount(scan.nextInt());
		
	}
}
