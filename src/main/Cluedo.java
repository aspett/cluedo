package main;

import cards.*;
import java.util.*;
import board.*;
import board.tiles.*;
import cards.*;
import java.math.*;

import userinterface.TextBasedInterface;
import userinterface.UserInterface;
//TODO CORNER ROOMS HAVE SECRET PASSAGES ARRRGH lol. Must incorporate that and offer that option when moving from one
//TODO when all players make wrong accusations and there are no players left we need to make sure the game ends

public class Cluedo {
	private CardTuple solution;
	private Board b = new Board();
	private UserInterface ui;
	private State state;
	private Dice dice;

	private List<Card> freeCards = new ArrayList<Card>();

	private List<CharacterCard> allCharacterCards = new ArrayList<CharacterCard>();
	private List<RoomCard> allRoomCards = new ArrayList<RoomCard>();
	private List<WeaponCard> allWeaponCards = new ArrayList<WeaponCard>();

	private static enum State {
		PLAYER_NEW_TURN,
		PLAYER_MOVING,
		GAME_END
	}

	public Cluedo() {
		ui = new TextBasedInterface(b);
		dice = new Dice();
		List<Card> deck = initializeCardsSolution();

		//List<Player> players = b.getPlayers();

		//Get players
		List<Player> players = ui.initPlayers();
		b.setPlayers(players);

		//Deal cards to players
		int cardsEach = deck.size() / players.size();
		for(Player p : players) {
			for(int i = 0; i < cardsEach; i++) {
				p.addCard(deck.remove(0));
			}
		}

		//Add leftover cards to a set of cards open to anyone.
		int left = deck.size();
		for(int i = 0; i < left; i++) {
			freeCards.add(deck.remove(0));
		}


		//Give players their start positions
		System.out.println(b.getBoardTiles().length);
		System.out.println(b.getBoardTiles()[0].length);
		for(Player p : players) {
			if(p.getName().equals("Eleanor Peacock")) p.setTile(b.getBoardTiles()[1][7]);
			if(p.getName().equals("Kasandra Scarlett")) p.setTile(b.getBoardTiles()[28][19]);
			if(p.getName().equals("Jack Mustard")) p.setTile(b.getBoardTiles()[28][8]);
			if(p.getName().equals("Diane White")) p.setTile(b.getBoardTiles()[19][1]);
			if(p.getName().equals("Jacob Green")) p.setTile(b.getBoardTiles()[10][1]);
			if(p.getName().equals("Victor Plum")) p.setTile(b.getBoardTiles()[1][21]);
		}

		//Choose a random player to start!
		//TODO rules say scarlett is to start. Not sure what happens if no one is her
		int randPlayer = Cluedo.rand(0, players.size());
		Player startingPlayer = b.getPlayers().get(randPlayer);
		System.out.printf("Start player = %d/%s\n", randPlayer, startingPlayer);

		//Testing the board drawing
		//		ui.draw();
		int currentPlayerID = randPlayer;
		Player currentPlayer = startingPlayer;
		Player.setCurrentPlayer(currentPlayer);
		state = State.PLAYER_NEW_TURN;
		while(state != State.GAME_END) {
			if(state == State.PLAYER_NEW_TURN || state == State.PLAYER_MOVING) {
				int moves = dice.roll();
				while(moves>0){
					//keep getting moves
					ui.draw(null);
					ui.alertPlayerTurn(currentPlayer);

					int choice = 0;
					if(state == State.PLAYER_NEW_TURN) {
						List<String> choices = offerChoices(currentPlayer);
						choice = ui.offerChoices(choices);
					}

					if(choice == 1) { //Look at cards
						ui.showCards(currentPlayer);
						List<String> waitChoice = new ArrayList<String>();
						waitChoice.add("Continue");
						ui.offerChoices(waitChoice);
						continue;
					}
					else if(choice == 0 || state == State.PLAYER_MOVING) { //Move
						if(currentPlayer.getTile() instanceof RoomTile) {
							currentPlayer.setMustMove(true);
							Room currentRoom = ((RoomTile) currentPlayer.getTile()).getRoom();
							currentPlayer.setDisallowedTiles(currentRoom.getTiles());
							//Prompt which exit to leave from
							if(currentRoom.getTiles().size() > 1) {
								chooseExitAndDraw(currentPlayer, currentRoom);
							}
						}
						else
							currentPlayer.setDisallowedTiles(null);
						state = State.PLAYER_MOVING;
						ui.alertNumMoves(moves);

						Tile move = ui.promptMove(currentPlayer);

						if(move == null) { //Stay where you are
							moves = 0;
							break;
						}

						currentPlayer.setTile(move);

						moves--;
						if(move instanceof RoomTile) {
							moves = 0;
							break;
						}
					}
				}
				ui.draw(null);
				Tile currentTile = currentPlayer.getTile();
				if(currentTile instanceof RoomTile) {
					RoomTile room = (RoomTile)currentTile;
					boolean isGuessOrAccusation = !room.getRoom().getName().equalsIgnoreCase("Pool room"); //True for guess. False for accusation
					CardTuple accusation = ui.promptGuess(currentPlayer, room.getRoom(), isGuessOrAccusation, allCharacterCards, allRoomCards, allWeaponCards);
					if(isGuessOrAccusation && accusation != null) { //Making a guess
						Player refutePlayer = findRefutePlayer(accusation, currentPlayer);
						ui.playerCanRefute(refutePlayer);
					}
					else if(!isGuessOrAccusation && accusation != null) { //Making an accusation!
						boolean correct = checkAccusation(accusation);
						if(correct) {
							state = State.GAME_END;
							ui.resolveAccusation(correct);
							ui.printWinner(currentPlayer);
						}
						else {//accusation is wrong. the player is eliminated from the game
							b.getPlayers().remove(currentPlayer);
							ui.resolveAccusation(correct);
							if(b.getPlayers().size() < 2) { //Game is over.
								state = State.GAME_END;
								ui.printWinner(b.getPlayers().get(0));
								break;
							}
						}
					}
				}
				//Set new player
				currentPlayerID = (currentPlayerID + 1) % b.getPlayers().size();
				currentPlayer = b.getPlayers().get(currentPlayerID);
				Player.setCurrentPlayer(currentPlayer);
				state = State.PLAYER_NEW_TURN;
			}
		}



		System.out.println(deck);
		System.out.println(deck.size());
		Collections.shuffle(deck);
	}




	private void chooseExitAndDraw(Player currentPlayer, Room currentRoom) {
		List<String> choices = new ArrayList<String>();
		List<Tile> exitTiles = new ArrayList<Tile>();
		ui.draw(exitTiles);
		for(Tile t : currentRoom.getTiles()) {
			if(t.isSecretTile()) {
				choices.add("Take secret passage");
			}
			else {
				choices.add(String.format("Take exit %d", choices.size()));
				exitTiles.add(t);
			}
		}
		int exitChoice = ui.offerChoices(choices);
		Tile exitTile = exitTiles.get(exitChoice);
		currentPlayer.setTile(exitTile);
		ui.draw(null);
	}




	/**
	 * Makes a random solution, and returns a list of the remaining cards in the deck.
	 * @return remaining cards in deck after solution is taken out
	 */
	private List<Card> initializeCardsSolution() {
		List<Player> players = b.getAvailablePlayers();
		List<Weapon> weapons = b.getWeapons();
		List<Room> rooms = b.getRooms();

		this.generateCards(players, weapons, rooms);

		List<CharacterCard> cc = new ArrayList<CharacterCard>(allCharacterCards);
		List<RoomCard> rc = new ArrayList<RoomCard>(allRoomCards);
		List<WeaponCard> wc = new ArrayList<WeaponCard>(allWeaponCards);

		int rand = Cluedo.rand(0, wc.size());
		WeaponCard weaponSolution = wc.remove(rand);

		rand = Cluedo.rand(0, rc.size());

		//can't be murdered in the pool room so we need to check if the card is not the pool room before removing it
		RoomCard roomSolution = rc.get(rand);
		while(roomSolution.toString().equals("Pool Room")){
			rand = Cluedo.rand(0, rc.size());
			roomSolution = rc.get(rand);
		}
		rc.remove(rand);

		rand = Cluedo.rand(0, cc.size());
		CharacterCard characterSolution = cc.remove(rand);

		solution = new CardTuple(characterSolution, roomSolution, weaponSolution);

		List<Card> deck = new ArrayList<Card>();
		deck.addAll(cc);
		deck.addAll(rc);
		deck.addAll(wc);

		Collections.shuffle(deck);
		return deck;
	}


	private void generateCards(List<Player> players, List<Weapon> weapons,
			List<Room> rooms) {
		for(Player p : players) {
			allCharacterCards.add(new CharacterCard(p));
		}
		for(Weapon w : weapons) {
			allWeaponCards.add(new WeaponCard(w));
		}
		for(Room r : rooms) {
			allRoomCards.add(new RoomCard(r));
		}
	}
	/**
	 * A method for checking an accusation against the predetermined solution
	 * @param accusation the proposed accusation
	 * @return True if the accusation is correct, false otherwise
	 */
	public boolean checkAccusation(CardTuple accusation){
		return accusation.equals(solution);
	}


	public static void main (String[] args) {
		Cluedo game = new Cluedo();
	}

	public static int rand(int min, int max) {
		int diff = max-min;
		return (int)((Math.random()*diff)+min);
	}

	private List<String> offerChoices(Player p) {
		List<String> choices = new ArrayList<String>();
		choices.add("Move");
		choices.add("Look at cards");
		return choices;
	}
	
	public Player findRefutePlayer(CardTuple accusation, Player currentPlayer){
		System.out.println(accusation);
		for(Player p : b.getPlayers()) {
			//TODO debug remove below
			System.out.printf("%s has cards: %s\n", p.getName(), p.getCards());
			if(!p.equals(currentPlayer) && (p.hasCard(accusation.getPlayer())
					|| p.hasCard(accusation.getRoom())
					|| p.hasCard(accusation.getWeapon()))) {
				return p;
				
			}
		}
		return null;
	}

	private void offerMoves(Player p) {

	}
}
