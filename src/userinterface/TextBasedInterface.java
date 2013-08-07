package userinterface;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.CluedoException;

import cards.CardTuple;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;

import board.Board;
import board.Player;
import board.tiles.*;
//TODO Update class diagram for the interface to hold the board
//TODO Players entering the pool room need an option to enter the pool room to view left over cards from dealing. And also show them, unless we assume the players have looked IRL
public class TextBasedInterface implements UserInterface {

	private Board b;
	private Scanner scan;

	public TextBasedInterface(Board b){
		this.b=b;
		scan = new Scanner(System.in);
	}

	@Override
	public void draw(){
		System.out.printf("\n\n\n\n\n\n\n\n\n\n\n\n");
		for(Tile[] tArray:b.getBoardTiles()){
			for(Tile t:tArray){
				boolean drawTile = true;
				for(Player p : b.getPlayers()) {
					if(p.getTile().equals(t)) {
						drawTile = false;
						if(Player.getCurrentPlayer().equals(p))
							System.out.print("P ");
						else System.out.print("p ");
						break;
					}
				}
				if(!drawTile) continue;
				if(t instanceof IntrigueTile)System.out.print("? ");
				else if(t instanceof RoomTile)System.out.print("E ");
				//TODO *** Discuss whether we want to draw start tiles
				//else if(t instanceof StartTile)System.out.print("S ");
				else if(t instanceof StartTile)System.out.print(". ");
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

	@Override
	public List<Player> initPlayers(){
		//Scanner scan = new Scanner(System.in);
		System.out.println("How many players?");
		//TODO ***** !!!! add error support on scanning and bounds of player ammount etc
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
		//scan.close();
		return actualPlayers;
	}

	@Override
	public Tile promptMove(Player p){

		//TODO move some of this logic to the board.
		Tile tile = p.getTile();

		//All tiles that are free from the current tile.
		List<Tile> availableTiles = b.getAvailableTiles(tile);

		System.out.println("Where would you like to move?");
		for(int i=0;i<availableTiles.size();i++){
			if(availableTiles.get(i).getY()>tile.getY()){
				System.out.printf("(%d) Move Down?",i);
			}else if(availableTiles.get(i).getY()<tile.getY()){
				System.out.printf("(%d) Move Up?",i);
			}else if(availableTiles.get(i).getX()>tile.getX()){
				System.out.printf("(%d) Move Right?",i);
			}else if(availableTiles.get(i).getX()<tile.getX()){
				System.out.printf("(%d) Move Left?",i);
			}
			//TODO remove: debug message: System.out.printf(" (%d occupants)\n", availableTiles.get(i).currentOccupants(b));
			System.out.print("\n");
		}


		if(p.canStayInTile(tile)) System.out.printf("(%d) Stay Put?\n",availableTiles.size());
		if(tile instanceof RoomTile){
			Room room = ((RoomTile)tile).getRoom();
			if(room instanceof CornerRoom)
				System.out.printf("(%d) Take the secret passage?\n",availableTiles.size());
		}

		//Scanner scan = new Scanner(System.in);
		scan.nextLine();
		//TODO fix for error checking
		int choice = scan.nextInt();
		//TODO test and/or refine the last part of the while operand
		while((choice<0 || choice>availableTiles.size()) && (!p.canStayInTile(tile) || !(((RoomTile)p.getTile()).getRoom() instanceof CornerRoom))){
			System.out.println("Please make a valid choice..\n");
			choice = scan.nextInt();
		}
		if(choice < availableTiles.size()){//they want to move
			return availableTiles.get(choice);
		}
		//TODO not sure if a player should be able to make a guess straight after using passage
		if(choice == availableTiles.size() && p.getTile() instanceof RoomTile){//they want to take a passage
			Room room = ((RoomTile)tile).getRoom();
			return ((CornerRoom)room).getSecretPassage();
		}




		//They don't want to move
		return null;

	}

	@Override
	public CardTuple promptGuess(Player currentPlayer, Room currentRoom,
			boolean isGuessOrAccusation, List<CharacterCard> characterCards, List<RoomCard> roomCards, List<WeaponCard> weaponCards) {

		System.out.printf("You are in the %s\nDo you wish to make %s %s?\n", currentRoom.getName(), isGuessOrAccusation?"a":"an", isGuessOrAccusation?"guess":"accusation");
		System.out.printf("0) Yes\n1) No\n> ");
		int answer;
		while(true) {
			answer = scan.nextInt();
			if(answer != 0 && answer != 1) continue;
			break;
		}
		//TODO move the player and possibly weapon(not even needed I guess) to the room being used for guess
		if(answer == 0) { //They want to make a guess/accusation
			//TODO move the player and possibly weapon(not even needed I guess) to the room being used for guess
			if(isGuessOrAccusation) { // Make a guess.
				//TODO Give them their options
				RoomCard roomCard = null;
				CharacterCard characterCard = null;
				WeaponCard weaponCard = null;
				for(RoomCard rc : roomCards) {
					if(rc.toString().equals(currentRoom.getName())) roomCard = rc;
				}

				System.out.printf("Please choose a character to guess:\n");
				for(int i = 0; i < characterCards.size(); i++) {
					System.out.printf("%d) %s\n", i, characterCards.get(i).toString());
				}
				int choice = -1;
				while(choice < 0 || choice >= characterCards.size()) {
					System.out.printf("> ");
					choice = scan.nextInt();
				}

				characterCard = characterCards.get(choice);

				System.out.printf("Please choose a weapon to guess:\n");
				for(int i = 0; i < weaponCards.size(); i++) {
					System.out.printf("%d) %s\n", i, weaponCards.get(i).toString());
				}

				choice = -1;
				while(choice < 0 || choice >= weaponCards.size()) {
					System.out.printf("> ");
					choice = scan.nextInt();
				}

				weaponCard = weaponCards.get(choice);

				if(roomCard == null) throw new CluedoException("Couldn't find player's current room");
				if(characterCard == null) throw new CluedoException("Error getting character card from accusation/guess");
				if(weaponCard == null) throw new CluedoException("Error getting weapon card from accusation/guess");

				//Move the player to the currentPlayers roomTile if they correspond to the character card being accused
				Player player = characterCard.getPlayer();
				if(b.getPlayers().contains(player)){
					player.setTile(currentPlayer.getTile());
				}

				return new CardTuple(characterCard, roomCard, weaponCard);
			}
			else { //Make an ACCUSATION
				RoomCard roomCard = null;
				CharacterCard characterCard = null;
				WeaponCard weaponCard = null;
				System.out.printf("Please choose a room for your accusation:\n");
				for(int i = 0;i < roomCards.size(); i++){
					System.out.printf("(%d) %s\n", i, roomCards.get(i).toString());
				}

				int choice = -1;
				while(choice < 0 || choice >= roomCards.size()) {
					System.out.printf("> ");
					choice = scan.nextInt();
				}

				roomCard=roomCards.get(choice);

				System.out.printf("Please choose a character for your accusation:\n");
				for(int i = 0; i < characterCards.size(); i++) {
					System.out.printf("%d) %s\n", i, characterCards.get(i).toString());
				}
				choice = -1;
				while(choice < 0 || choice >= characterCards.size()) {
					System.out.printf("> ");
					choice = scan.nextInt();
				}

				characterCard = characterCards.get(choice);

				System.out.printf("Please choose a weapon for your accusation:\n");
				for(int i = 0; i < weaponCards.size(); i++) {
					System.out.printf("%d) %s\n", i, weaponCards.get(i).toString());
				}

				choice = -1;
				while(choice < 0 || choice >= weaponCards.size()) {
					System.out.printf("> ");
					choice = scan.nextInt();
				}

				weaponCard = weaponCards.get(choice);

				if(roomCard == null) throw new CluedoException("Couldn't find player's current room");
				if(characterCard == null) throw new CluedoException("Error getting character card from accusation/guess");
				if(weaponCard == null) throw new CluedoException("Error getting weapon card from accusation/guess");

				return new CardTuple(characterCard, roomCard, weaponCard);



			}
		}
		else { return null; }

	}

	@Override
	public void playerCanRefute(Player refutePlayer) {
		if(refutePlayer == null) { System.out.println("No one can refute the claim"); return; }
		System.out.printf("%s can refute the claim.\nPress any key, and hit ENTER to continue.\n", refutePlayer.getName());
		scan.next();

	}

	@Override
	public void alertPlayerTurn(Player currentPlayer) {
		System.out.printf("%s, it's your turn!\n", currentPlayer.getName());

	}

	@Override
	public void alertNumMoves(int moves) {
		System.out.printf("You have %d moves left\n", moves);
	}

	@Override
	public void resolveAccusation(boolean correct) {
		if(correct){
			System.out.printf("Your accusation is correct!");
		}else{
			//TODO test accusation resolution text
			System.out.printf("Your accusation was incorrect. You have been eliminated from the game\nRemaining players:\n");
			for(int i = 0; i < b.getPlayers().size(); i++) {
				System.out.print(b.getPlayers().get(i));
				if(i < b.getPlayers().size()-1) System.out.print(", ");
			}
		}

	}

	@Override
	public void printWinner(Player player) {
		System.out.printf("Game over! The winner is:\n%s\n", player.getName());
	}

	@Override
	public int offerChoices(List<String> choices) {
		for(int i = 0; i < choices.size(); i++) {
			System.out.printf("%d) %s\n", i, choices.get(i));
		}
		while(true) {
			String reply = scan.next();
			int choice;
			try {
				choice = Integer.parseInt(reply);
			} catch(NumberFormatException e) {
				System.out.println("Please choose a valid option");
				continue;
			}
			return choice;
		}
	}
}
