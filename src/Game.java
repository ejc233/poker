import java.util.ArrayList;

/**
 * Top-level class that represents the Poker game.
 * 
 * @author echan
 */
public class Game {
	private static Table table; // GUI
	private Deck deck;
	private int turn;
	private int currentBet;
	private int pot;
	private int smallBlind = 0;
	private int bigBlind = 0;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Card> communityCards = new ArrayList<Card>();

	public static void main(String args[]) {
		Game game = new Game();
		table = new Table();
		game.run(1);
	}

	/**
	 * Constructor class.
	 */
	public Game() {
		players.add(new HumanPlayer("Tony"));
		players.add(new ComputerPlayer("Bruce"));
		players.add(new ComputerPlayer("Thor"));
		players.add(new ComputerPlayer("Steve"));
	}

	/**
	 * Runs the game for n rounds.
	 * 
	 * @param n
	 *            the number of rounds to run the game
	 */
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			// initialize the round
			startRound();
			table.setTitle("Round " + (i + 1));
			pause();

			// set initial bets
			startBets();
			table.setTitle("Round " + (i + 2));
			table.updatePlayerName("Test");
			table.getContentPane().repaint();
			pause();

			// reveal three cards and run bet round
			reveal(3);
			continueBets();

			// reveal two more cards and run another bet round
			reveal(2);
			continueBets();

			// determine the round winner
			endRound();
		}

		// determine the game winner
		endGame();
	}

	public void pause() {
		try {
			Thread.sleep(250000);

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Start a new round with new deck and hands.
	 */
	public void startRound() {
		deck = new Deck();

		// Double blinds per round
		bigBlind = bigBlind * 4;
		smallBlind = bigBlind / 2;
		pot = 0;

		// Used to test for number of bankrupt people
		int numberBankrupt = 0;
		for (int i = 0; i < 4; i++) {
			if (players.get(i).getMoney() == 0) {
				numberBankrupt++;
			}
			// Resets players hands and the community cards
			players.get(i).setFolded(false);
			communityCards = new ArrayList<Card>();
			players.get(i).setBet(0);
		}

		// Ends game quickly if three players go bankrupt
		if (numberBankrupt == 3) {
			endGame();
		}
	}

	/**
	 * Run the start bets.
	 */
	public void startBets() {
		// first player pays big blind
		players.get(turn).setBet(bigBlind);
		pot += bigBlind;
		turn++;

		// reset turn value to zero if it exceeds bound
		if (turn > 3) {
			turn = 0;
		}

		// additional players pay small blind
		for (int i = 0; i < 3; i++) {
			players.get(turn).setBet(smallBlind);
			pot += smallBlind;
			turn++;

			// reset turn value to zero if it exceeds bound
			if (turn > 3) {
				turn = 0;
			}
		}
		currentBet = smallBlind;

		// each player draws two cards
		for (int i = 0; i < 4; i++) {
			players.get(i).setFirstCard(deck.drawCard());
			players.get(i).setSecondCard(deck.drawCard());
		}
	}

	/**
	 * Reveal n cards to all the players.
	 * 
	 * @param n
	 *            the number of cards revealed to all players
	 */
	public void reveal(int n) {
		communityCards.addAll(deck.drawCards(n));
		for (int i = 0; i < 4; i++) {
			players.get(i).setCommunityCards(communityCards);
			players.get(i).updateHand(communityCards);
		}
	}

	/**
	 * Run intermediate bets.
	 */
	public void continueBets() {
		for (int i = 0; i < 4; i++) {
			// ask for bet from players who have not folded
			// AI LOGIC; fold, call or bet
			System.out.println(currentBet);
		}

		// reset turn value to zero if it exceeds boud
		turn++;
		if (turn > 3) {
			turn = 0;
		}
	}

	/**
	 * End the round, and determine the round winner.
	 */
	public void endRound() {
		// if n players have same score, split the plot n ways
		System.out.println(pot);
	}

	/**
	 * End the game, and determine the game winner.
	 */
	public void endGame() {
		table.setVisible(false);
		System.exit(0);
	}

}