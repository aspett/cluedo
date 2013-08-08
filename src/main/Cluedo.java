package main;

import cards.*;

import java.util.*;

import board.*;
import board.tiles.*;
import cards.*;
import java.math.*;

import userinterface.TextBasedInterface;
import userinterface.UserInterface;

/**
 * The main class of the game.
 * Dictates all game logic, and displays the state of the game via a {@link UserInterface user interface}
 * @author Andrew
 *
 */
public class Cluedo {
	/**
	 * A tuple of the randomly chosen solution
	 */
	private CardTuple solution;
	private Board b = new Board();
	/**
	 * The userinterface which the game will use to display the state of the game.
	 */
	private UserInterface ui;
	/**
	 * The {@link State state} of the game
	 */
	private State state;
	private Dice dice;
	Player currentPlayer;


	private List<CharacterCard> allCharacterCards = new ArrayList<CharacterCard>();
	private List<RoomCard> allRoomCards = new ArrayList<RoomCard>();
	private List<WeaponCard> allWeaponCards = new ArrayList<WeaponCard>();

	/**
	 * An enum representing a small number of useful states that the game can be in.
	 * Used to direct game logic.
	 * @author Andrew Pett & Matthew Mortimer
	 *
	 */
	private static enum State {
		PLAYER_NEW_TURN,
		PLAYER_MOVING,
		GAME_END
	}

	public Cluedo() {
		ui = new TextBasedInterface(b);
		dice = new Dice();
		List<Card> deck = initializeCardsSolution();
		System.out.println("deck" +deck.size());
		
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
		List<Card> freeCards = new ArrayList<Card>();
		int left = deck.size();
		for(int i = 0; i < left; i++) {
			freeCards.add(deck.remove(0));
		}
		b.setFreeCards(freeCards);


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
		currentPlayer = startingPlayer;
		Player.setCurrentPlayer(currentPlayer);
		state = State.PLAYER_NEW_TURN;
		while(state != State.GAME_END) {
			if(b.getPlayers().size() < 2) {
				state = State.GAME_END;
				break;
			}
			if(state == State.PLAYER_NEW_TURN || state == State.PLAYER_MOVING) {
				int moves = Dice.roll();

				generatePlayerDisallowedTiles(currentPlayer);

				while(moves>0){
					//keep getting moves
					ui.draw(null);
					ui.alertPlayerTurn(currentPlayer);

					int choice = 0;
					if(state == State.PLAYER_NEW_TURN) {
						List<String> choices = generateNewTurnChoices();
						choice = ui.offerChoices(choices);
					}

					if(choice == 1) { //Look at cards
						ui.showCards(currentPlayer);
						List<String> waitChoice = new ArrayList<String>();
						waitChoice.add("Continue");
						ui.offerChoices(waitChoice);
						choice = 0;
					}
					if(choice == 0 || state == State.PLAYER_MOVING) { //Move
						//ui.draw(null);
						if(currentPlayer.getTile() instanceof RoomTile) {
							Room currentRoom = ((RoomTile) currentPlayer.getTile()).getRoom();
							if(currentRoom.getTiles().size() > 1) {
								chooseExitAndDraw(currentPlayer, currentRoom);
							}
						}
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
						//TODO add to sequence the fololowing line
						moveWeaponAndPlayer(accusation);
						Player refutePlayer = findRefutePlayer(accusation, currentPlayer);
						ui.playerCanRefute(refutePlayer, refutePlayer == null ? null : getRefutableCardsForPlayer(accusation, refutePlayer));
					}
					else if(!isGuessOrAccusation && accusation != null) { //Making an accusation!
						boolean correct = checkAccusation(accusation);
						boolean gameEnd = checkGameEnd(correct, currentPlayer);
						if(gameEnd) break;

					}
				}
				//Set new player
				currentPlayerID = (currentPlayerID + 1) % b.getPlayers().size();
				currentPlayer = b.getPlayers().get(currentPlayerID);
				Player.setCurrentPlayer(currentPlayer);
				state = State.PLAYER_NEW_TURN;
			}
		}
	}



	/**
	 * Generate for the {@link currentPlayer} the tiles which (s)he may not enter
	 * due to their current position
	 * @param currentPlayer
	 */
	private void generatePlayerDisallowedTiles(Player currentPlayer) {
		if(currentPlayer.getTile() instanceof RoomTile) {
			currentPlayer.setMustMove(true);
			Room currentRoom = ((RoomTile) currentPlayer.getTile()).getRoom();
			currentPlayer.setDisallowedTiles(currentRoom.getTiles());
		}
		else
			currentPlayer.setDisallowedTiles(null);
	}



	/**
	 * Prompt the user with their choice of exits and redraw the board
	 * @param player The player whom we should prompt
	 * @param currentRoom The room that the player is in
	 */
	private void chooseExitAndDraw(Player player, Room currentRoom) {
		if(!currentRoom.getTiles().contains(player.getTile())) throw new CluedoException("Room given to prompt exits for is inconsistent with the tile that the player is currently in");
		List<String> choices = new ArrayList<String>();
		List<Tile> exitTiles = new ArrayList<Tile>();

		for(Tile t : currentRoom.getTiles()) {
			if(t.isSecretTile()) {
				choices.add("Take secret passage");
			}
			else {
				choices.add(String.format("Take exit %d", choices.size()));

			}
			exitTiles.add(t);
		}
		ui.draw(exitTiles);
		int exitChoice = ui.offerChoices(choices);
		Tile exitTile = exitTiles.get(exitChoice);
		player.setTile(exitTile, true);
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

	/**
	 * Generates the cards used in the game from a list of {@link Player players}, {@link Weapon weapons}, and {@link Room rooms}
	 * @param players List of players
	 * @param weapons List of weapons
	 * @param rooms List of rooms
	 */
	private void generateCards(List<Player> players, List<Weapon> weapons,
			List<Room> rooms) {
		for(Player p : players) {
			allCharacterCards.add(new CharacterCard(p));
		}
		for(Weapon w : weapons) {
			allWeaponCards.add(new WeaponCard(w));
		}
		for(Room r : rooms) {
			if(r.getName().equalsIgnoreCase("pool room"))continue; //We dont want a pool room card. Dont make one
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

	/**
	 * Run the game.
	 * @param args Not required
	 */
	public static void main (String[] args) {
		Cluedo game = new Cluedo();
	}
	/**
	 * Generate a random number between min and max inclusive.
	 * @param min Minimum number
	 * @param max Maximum number
	 * @return A random number between {@link min} and {@link max}
	 */
	public static int rand(int min, int max) {
		int diff = max-min;
		return (int)((Math.random()*diff)+min);
	}

	/**
	 * Generate the standard choices to offer at the start of a player's turn
	 * @return the choices
	 */
	private List<String> generateNewTurnChoices() {
		List<String> choices = new ArrayList<String>();
		choices.add("Move");
		choices.add("Look at cards");
		return choices;
	}

	/**
	 * Given a rumor CardTuple, check through all of the players other than the current player
	 * and find the first player (if there is one) that has one or more of the cards in the rumor and can therefore refute it.
	 * @param rumor The rumor in question
	 * @param currentPlayer The player that made the rumor
	 * @return
	 */
	public Player findRefutePlayer(CardTuple rumor, Player currentPlayer){
		System.out.println(rumor);
		//TODO we need to
		Player p = b.getPlayers().get((b.getPlayers().indexOf(currentPlayer) + 1)%b.getPlayers().size());
		while(p != currentPlayer) {
			List<Card> refutableCards = getRefutableCardsForPlayer(rumor, p);
			if(refutableCards.size() > 0) {
				return p;
			}
			p = b.getPlayers().get((b.getPlayers().indexOf(p) + 1) % b.getPlayers().size());
		}
		return null;
	}



	private List<Card> getRefutableCardsForPlayer(CardTuple rumour,
			Player p) {
		List<Card> refutableCards = new ArrayList<Card>();
		if(p.hasCard(rumour.getPlayer()))
			refutableCards.add(rumour.getPlayer());
		if(p.hasCard(rumour.getRoom()))
			refutableCards.add(rumour.getRoom());
		if(p.hasCard(rumour.getWeapon()))
			refutableCards.add(rumour.getWeapon());

		return refutableCards;
	}

	//TODO what's this?
	private void offerMoves(Player p) {

	}

	/**
	 * Check whether the game should continue play or not, depending on the value of the 'correct' boolean.
	 * If correct is true then there was an accusation that was correct and the current player wins
	 * If correct was false then there was an incorrect accusation and the current player must be removed from the game.
	 * If after being removed from the game there is a lone player left that player wins.
	 * @param correct A boolean who's value depends on whether the last accusation was correct or not
	 * @param currentPlayer The player who made the accusation
	 * @return True if the game should end. False if the game should continue
	 */
	public boolean checkGameEnd (boolean correct, Player currentPlayer){
		if(correct) {
			state = State.GAME_END;
			ui.resolveAccusation(correct);
			ui.printWinner(currentPlayer);
			return true;
		}
		else {//accusation is wrong. the player is eliminated from the game
			b.getPlayers().remove(currentPlayer);
			b.getFreeCards().addAll(currentPlayer.getCards());
			currentPlayer.getCards().clear();
			ui.resolveAccusation(correct);
			if(b.getPlayers().size() < 2) { //Game is over.
				state = State.GAME_END;
				ui.printWinner(b.getPlayers().get(0));
				return true;
			}
			return false;
		}
	}

	/**
	 * When a player makes a rumor, the weapons and the player in question need to be moved to the room in question.
	 * This method moves the weapon to the room and moves the player to the room if they are human controlled.
	 * @param guess The rumor made by the current player
	 */
	public void moveWeaponAndPlayer(CardTuple guess){
		//Move the player to the currentPlayers roomTile if they correspond to the character card being accused

		Player player = guess.getPlayer().getPlayer(); //The player who is PART OF THE RUMOUR
		if(b.getPlayers().contains(player)){ //IF THAT PLAYER IS A REAL PLAYER
			player.setTile(currentPlayer.getTile()); //Move them to the current player's tile/room.
		}

		Room currentPlayerRoom = ((RoomTile)currentPlayer.getTile()).getRoom();
		Weapon weapon = guess.getWeapon().getWeapon();
		Room currentWeaponRoom = weapon.getRoom();
		currentPlayerRoom.addWeapon(weapon);
		currentWeaponRoom.removeWeapon(weapon);
		weapon.setRoom(currentPlayerRoom);


	}
}
