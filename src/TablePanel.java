import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TablePanel extends JPanel {
	public static final int HEIGHT = 600;
	public static final int WIDTH = 1000;
	private ArrayList<Player> players;
	private ArrayList<Card> communityCards;
	boolean roundOver;

	/**
	 * Constructor class.
	 */
	public TablePanel(ArrayList<Player> players, ArrayList<Card> communityCards) {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.players = players;
		this.communityCards = communityCards;
		// console to write actions
	}

	/*
	 * paint this square using g. System calls paint whenever square has to be
	 * redrawn.
	 */
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0, 200, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// game information
		g.setColor(Color.black);
		g.drawString("Pot: 1000", 450, 50);

		// community cards
		g.setColor(Color.black);
		g.drawRect(250, 225, 500, 150);
		g.drawString("Community Cards", 250, 225 - 10);
		try {
			if (communityCards.isEmpty()) {
				g.drawImage(ImageIO.read(new File("img/back.png")), 265, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 365, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 465, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 565, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 665, 225 + 25, this);

			} else if (communityCards.size() == 3) {
				g.drawImage(ImageIO.read(new File(communityCards.get(0).getPath())), 265, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(1).getPath())), 365, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(2).getPath())), 465, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 565, 225 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 665, 225 + 25, this);

			} else if (communityCards.size() == 5) {
				g.drawImage(ImageIO.read(new File(communityCards.get(0).getPath())), 265, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(1).getPath())), 365, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(2).getPath())), 465, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(0).getPath())), 565, 225 + 25, this);
				g.drawImage(ImageIO.read(new File(communityCards.get(1).getPath())), 665, 225 + 25, this);
			}

			// player 1
			g.setColor(Color.black);
			g.drawRect(25, 25, 200, 150);
			g.drawString(players.get(0).getName() + " (YOU)", 25, 25 - 10);
			if (players.get(0).getFirstCard() == null) {
				g.drawImage(ImageIO.read(new File("img/back.png")), 40, 25 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 140, 25 + 25, this);

			} else {
				g.drawImage(ImageIO.read(new File(players.get(0).getFirstCard().getPath())), 40, 25 + 25, this);
				g.drawImage(ImageIO.read(new File(players.get(0).getSecondCard().getPath())), 140, 25 + 25, this);
			}

			// player 2
			g.setColor(Color.black);
			g.drawRect(775, 25, 200, 150);
			g.drawString(players.get(1).getName(), 775, 25 - 10);
			if (players.get(1).getFirstCard() == null || !roundOver) {
				g.drawImage(ImageIO.read(new File("img/back.png")), 790, 25 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 890, 25 + 25, this);

			} else {
				g.drawImage(ImageIO.read(new File(players.get(1).getFirstCard().getPath())), 790, 25 + 25, this);
				g.drawImage(ImageIO.read(new File(players.get(1).getSecondCard().getPath())), 890, 25 + 25, this);
			}

			// player 3
			g.setColor(Color.black);
			g.drawRect(775, 425, 200, 150);
			g.drawString(players.get(2).getName(), 775, 425 - 10);
			if (players.get(1).getFirstCard() == null || !roundOver) {
				g.drawImage(ImageIO.read(new File("img/back.png")), 790, 425 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 890, 425 + 25, this);

			} else {
				g.drawImage(ImageIO.read(new File(players.get(2).getFirstCard().getPath())), 790, 425 + 25, this);
				g.drawImage(ImageIO.read(new File(players.get(2).getSecondCard().getPath())), 890, 425 + 25, this);
			}

			// player 4
			g.setColor(Color.black);
			g.drawRect(25, 425, 200, 150);
			g.drawString(players.get(3).getName(), 25, 425 - 10);
			if (players.get(1).getFirstCard() == null || !roundOver) {
				g.drawImage(ImageIO.read(new File("img/back.png")), 40, 425 + 25, this);
				g.drawImage(ImageIO.read(new File("img/back.png")), 140, 425 + 25, this);

			} else {
				g.drawImage(ImageIO.read(new File(players.get(3).getFirstCard().getPath())), 40, 425 + 25, this);
				g.drawImage(ImageIO.read(new File(players.get(3).getSecondCard().getPath())), 140, 425 + 25, this);
			}

		} catch (IOException ex) {
			System.out.println("Invalid card.");
		}

	}

	public void updatePlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void updateCommunityCards(ArrayList<Card> communityCards) {
		this.communityCards = communityCards;
	}

	public void updateRoundOver(boolean roundOver) {
		this.roundOver = roundOver;
	}
}