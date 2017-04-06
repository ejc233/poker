import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	private int smallBlind = 5;
	private int bigBlind = 10;
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Card> communityCards = new ArrayList<Card>();

	public static void main(String args[]) {
		Game game = new Game();
		table = new Table(players, communityCards);
		table.setTitle("Poker");
		game.run(15);
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
			startPhase();
			table.updateRound(i + 1, n, "Start Phase", false);
			table.updatePlayers(players);
			table.updateCommunityCards(communityCards);
			table.getContentPane().repaint();
			table.awaitClick();

			// set initial bets
			startBets();
			table.updateRound(i + 1, n, "Pre-Flop", false);
			table.updatePlayers(players);
			table.updateCommunityCards(communityCards);
			table.getContentPane().repaint();
			table.awaitClick();

			// reveal three cards and run bet round
			reveal(3);
			table.updateRound(i + 1, n, "Flop", false);
			table.updatePlayers(players);
			table.updateCommunityCards(communityCards);
			table.getContentPane().repaint();
			continueBets();
			table.updatePlayers(players);
			table.getContentPane().repaint();
			table.awaitClick();

			// reveal one more card and run another bet round
			reveal(1);
			table.updateRound(i + 1, n, "Turn", false);
			table.updatePlayers(players);
			table.updateCommunityCards(communityCards);
			table.getContentPane().repaint();
			continueBets();
			table.updatePlayers(players);
			table.getContentPane().repaint();
			table.awaitClick();

			// reveal one more card and run another bet round
			reveal(1);
			table.updateRound(i + 1, n, "River", false);
			table.updatePlayers(players);
			table.updateCommunityCards(communityCards);
			table.getContentPane().repaint();
			continueBets();
			table.updatePlayers(players);
			table.getContentPane().repaint();
			table.awaitClick();

			// determine the round winner
			endRound();
			table.updateRound(i + 1, n, "Showdown", true);
			table.getContentPane().repaint();
			table.awaitClick();
		}

		// determine the game winner
		endGame();
	}

	public void pause(int time) {
		try {
			Thread.sleep(time);

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Start a new round with new deck and hands.
	 */
	public void startPhase() {
		deck = new Deck();

		// double blinds per round
		bigBlind = bigBlind * 4;
		smallBlind = bigBlind / 2;
		pot = 0;

		// test for number of bankrupt people
		int numberBankrupt = 0;
		for (int i = 0; i < 4; i++) {
			if (players.get(i).getMoney() == 0) {
				numberBankrupt++;
			}
			// Resets players hands and the community cards
			players.get(i).setFolded(false);
			communityCards = new ArrayList<Card>();
			players.get(i).setFirstCard(null);
			players.get(i).setSecondCard(null);
			players.get(i).setHand(null);
			players.get(i).setBet(0);
		}

		// end game quickly if three players go bankrupt
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
	}

	/**
	 * End the game, and determine the game winner.
	 */
	public void endGame() {
		JOptionPane.showMessageDialog(null, "Game complete.");
		table.setVisible(false);
		System.exit(0);
	}

}

// one pair tie breaker is to look at the kicker card.