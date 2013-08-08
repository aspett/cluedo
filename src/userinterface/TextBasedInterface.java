package userinterface;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.CluedoException;

import cards.Card;
import cards.CardTuple;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;

import board.Board;
import board.Player;
import board.Weapon;
import board.tiles.*;
//TODO Players entering the pool room need an option to enter the pool room to view left over cards from dealing. And also show them, unless we assume the players have looked IRL
/**
 * A text based interface for interacting with the Cluedo game.
 * @author Andrew Pett & Matthew Mortimer
 *
 */
public class TextBasedInterface implements UserInterface {

	private Board b;
	private Scanner scan;

	public TextBasedInterface(Board b){
		this.b=b;
		scan = new Scanner(System.in);
	}

	@Override
	public void draw(List<Tile> numberedTiles){
		clearScreen();
		for(Tile[] tArray:b.getBoardTiles()){
			for(Tile t:tArray){
				boolean drawTile = true;
				if(numberedTiles != null && numberedTiles.contains(t)) { System.out.printf("%d ", numberedTiles.indexOf(t)); continue; }
				for(Player p : b.getPlayers()) {
					if(p.getTile().equals(t)) {
						drawTile = false;
						if(Player.getCurrentPlayer().equals(p))
							System.out.print("P ");
						else System.out.print("C ");
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

	private void clearScreen() {
		//System.out.printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		StringBuilder str = new StringBuilder("");
		for(int i = 0; i < 100; i++) {
			str.append("\n");
		}
		System.out.printf("%s", str.toString());
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
		List<Tile> availableTiles = b.getAvailableTiles(tile, p);

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
		System.out.printf("0) No\n1) Yes\n> ");
		int answer;
		while(true) {
			answer = scan.nextInt();
			if(answer != 0 && answer != 1) continue;
			break;
		}
		if(answer == 1) { //They want to make a guess/accusation
			if(isGuessOrAccusation) { // Make a guess.
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
	public void playerCanRefute(Player refutePlayer, List<Card> refutableCards) {
		if(refutePlayer == null) { System.out.println("No one can refute the claim"); return; }
		List<String> continueChoices = new ArrayList<String>();
		continueChoices.add(String.format("See the cards you (%s) may refute the rumour with", refutePlayer.getName()));
		System.out.printf("%s can refute the claim.\nEnter 0 to continue\n", refutePlayer.getName());
		offerChoices(continueChoices);
		List<String> refuteChoices = new ArrayList<String>();
		for(Card c : refutableCards) {
			refuteChoices.add(c.getName());
		}
		System.out.printf("Choose a card to refute with:\n");
		int refuteCardIndex = offerChoices(refuteChoices);
		clearScreen();
		System.out.printf("%s has refuted the rumour with the '%s' card!\nEnter 0 to continue.\n", refutePlayer.getName(), refuteChoices.get(refuteCardIndex));
		continueChoices.set(0, "Continue");
		offerChoices(continueChoices);

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
			System.out.printf("Your accusation is correct!\n");
		}else{
			System.out.printf("Your accusation was incorrect. You have been eliminated from the game\nRemaining players:\n");
			for(int i = 0; i < b.getPlayers().size(); i++) {
				System.out.print(b.getPlayers().get(i));
				if(i < b.getPlayers().size()-1) System.out.print(", ");
			}
			System.out.print("\n");
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
				if(choice < 0 || choice >= choices.size()) {
					throw new NumberFormatException();
					/*System.out.println("Please choose a valid option");
					continue;*/
				}
			} catch(NumberFormatException e) {
				System.out.println("Please choose a valid option");
				continue;
			}
			return choice;
		}
	}

	@Override
	public void showCards(Player p) {
		System.out.printf("Your cards:\n");
		for(Card c : p.getCards()) {
			System.out.printf("- %s\n", c.toString());
		}
	}
}
