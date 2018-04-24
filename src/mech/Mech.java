package mech;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;

import entities.Card;
import entities.Deck;
import entities.Player;
import visual.MainPanel;

public class Mech implements Constants, MouseListener, ActionListener{
	
	private MainPanel mainPanel;
	private Deck deck;
	private Player player_1, player_2;
	private Player currentPlayer;
	private int selectedCards;
	private int maxCardsOnTable;
	private Card[][] cardsOnTableArray;
	
	public Mech(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	@SuppressWarnings("serial")
	public AbstractAction getStartAction(){
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewGame();
			}
		};
	}
	
	@SuppressWarnings("serial")
	public AbstractAction getExitAction(){
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
	}
	
	@SuppressWarnings("serial")
	Action playerOneAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			currentPlayer = player_1;
			switchPlayersInterface();
		}
	};
	
		public Action getPlayerOneAction(){
			return playerOneAction;
		}

	@SuppressWarnings("serial")
	Action playerTwoAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			currentPlayer = player_2;
			switchPlayersInterface();
		}
	};
	
		public Action getPlayerTwoAction(){
			return playerTwoAction;
		}

		private void switchPlayersInterface() {
			currentPlayer.switchButtonsVisibility();
			mainPanel.enableMouseListeners();
			_switchPlayersSkipIsEnabled();
			_switchPlayersActions();
		}
		
			private void _switchPlayersSkipIsEnabled() {
				player_1.switchSkipIsEnabled();
				player_2.switchSkipIsEnabled();
			}
	
			private void _switchPlayersActions(){
				if (playerOneAction.isEnabled()){
					playerOneAction.setEnabled(false);
					playerTwoAction.setEnabled(false);
				}else{
					playerOneAction.setEnabled(true);
					playerTwoAction.setEnabled(true);
				}
			}
	
	@SuppressWarnings("serial")
	public AbstractAction getSkipAction(Player player){
		return new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.switchSkipColor();
				if (player_1.isSkipped() && player_2.isSkipped()){
					dealAdditionalThreeCards();
				}
			}
		};
	}
	
		private void dealAdditionalThreeCards() {
			maxCardsOnTable += 3;
			if (maxCardsOnTable > 21)
				maxCardsOnTable -= 3;
			calculateCardsProperties();
			dealCardsOnFreeSpace();
			switchSkipButtons();
		}
	
			private void switchSkipButtons() {
				player_1.switchSkipColor();
				player_2.switchSkipColor();
			}

	private void createNewGame() {
		mainPanel.clearPlayers();
			createPlayers(mainPanel);
			enablePlayersActions();
			selectedCards = 0;
		deck = new Deck();
		maxCardsOnTable = 12;
			calculateCardsProperties();
		cardsOnTableArray = new Card[7][3];
		dealCardsOnFreeSpace();
	}
	
		private void enablePlayersActions(){
			playerOneAction.setEnabled(true);
			playerTwoAction.setEnabled(true);
		}

		private void createPlayers(MainPanel mainPanel) {
			player_1 = new Player(this);
			player_1.setLocation(PLAYER_1_X, Y_1);
			mainPanel.add(player_1);
			
			player_2 = new Player(this);
			player_2.setLocation(PLAYER_2_X, Y_1);
			mainPanel.add(player_2);
		}

		private void dealCardsOnFreeSpace() {
			mainPanel.clearCards();
			feelUpCardsArray();
			for (int i = 0; i < maxCardsOnTable/3; i++) {
				for (int j = 0; j < 3; j++) {
					Card card = cardsOnTableArray[i][j];
					if (card != null){
						mainPanel.add(card);
						card.setSize(CARD_WIDTH[maxCardsOnTable], CARD_HEIGHT[maxCardsOnTable]);
						card.setLocation(CARD_POINT[i][j]);
						card.setPointIJ(i, j);
					}
				}
			}
			mainPanel.repaint();		
		}
		
			private void feelUpCardsArray() {
				int n = 0;
				int maxN = calculateFreeSpaceOnTable();
				for (int j = 0; j < 3; j++) {
					for (int i = 0; i < maxCardsOnTable/3; i++) {
						if (cardsOnTableArray[i][j] == null){
							cardsOnTableArray[i][j] = deck.drawNextCard();
							deck.increaseCurrentPos();
							n++;
						}
						if (n == maxN)
							return;
					}
				}
			}
	
				private int calculateFreeSpaceOnTable(){
					int free = 0;
					for (int i = 0; i < maxCardsOnTable/3; i++) {
						for (int j = 0; j < 3; j++) {
							if (cardsOnTableArray[i][j] == null)
								free++;
						}
					}
					return free;
				}
	
	private void calculateCardsProperties(){
		Constants.calculateCardSize(maxCardsOnTable);
		Constants.calculateCardPoints(maxCardsOnTable);
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {
		Card card = (Card) e.getSource();
		if (card.switchBorderColor())
			selectedCards++;
		else{
			selectedCards--;
			if (selectedCards == 2)
				currentPlayer.switchConfirmIsEnabled();
		}
		
		if (selectedCards == 3){
			currentPlayer.switchConfirmIsEnabled();
		}
		
		if (selectedCards == 4){
			card.switchBorderColor();
			selectedCards--;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(currentPlayer.getCancelButton())){
			currentPlayer.decreaseScore();
			switchPlayersInterface();
			mainPanel.setBlackBordersOnAllCards();
			if (selectedCards == 3)
				currentPlayer.switchConfirmIsEnabled();
			selectedCards = 0;
		}
		
		if (e.getSource().equals(currentPlayer.getConfirmButton())){
			if (isSet()){
				switchPlayersInterface();
				currentPlayer.increaseScore();
				currentPlayer.switchConfirmIsEnabled();
				mainPanel.removeSelectedCards();
				selectedCards = 0;
				if (maxCardsOnTable > 12){
					compactCards();
					maxCardsOnTable -= 3;
						calculateCardsProperties();
				}
				dealCardsOnFreeSpace();	
			}else{
				currentPlayer.getCancelButton().doClick();
			}
		}
	}
	
		public void setNullCardInArray(Card card){
			int i = card.getPointIJ().x;
			int j = card.getPointIJ().y;
			cardsOnTableArray[i][j] = null;
		}

		private void compactCards() {
			Point[] emptyPoints = findEmptyPoints();
			int p = 0;
			for (int j = 2; j >= 0; j--) {
				int i = maxCardsOnTable/3 - 1;
				if (cardsOnTableArray[i][j] != null){
					int k = emptyPoints[p].x;
					int n = emptyPoints[p].y;
					cardsOnTableArray[k][n] = cardsOnTableArray[i][j];
					cardsOnTableArray[i][j] = null;
					p++;
				}
			}
		}

			private Point[] findEmptyPoints() {
				Point[] emptyPoints = new Point[3];
				int p = 0;
				for (int i = 0; i < maxCardsOnTable/3; i++) {
					for (int j = 0; j < 3; j++) {
						if (cardsOnTableArray[i][j] == null){
							emptyPoints[p] = new Point(i, j);
							p++;
							if (p == 3)
								break;
						}
							
					}
					if (p == 3)
						break;
				}
				return emptyPoints;
			}

	private boolean isSet() {
		Card[] checkedCards = mainPanel.findCheckedCards();
		boolean a = checkQuantity(checkedCards);
		boolean b = checkColor(checkedCards);
		boolean c = checkShape(checkedCards);
		boolean d = checkFilling(checkedCards);
		if (a && b && c && d)
			return true;
		else
			return false;
	}

	private boolean checkQuantity(Card[] cards) {
		int col0 = cards[0].getQuantity();
		int col1 = cards[1].getQuantity();
		int col2 = cards[2].getQuantity();
		if ((col0 == col1) && (col0 == col2))
			return true;
		if ((col0 != col1) && (col0 != col2) && (col0 != col1))
			return true;
		return false;
	}

	private boolean checkColor(Card[] cards) {
		Color col0 = cards[0].getColor();
		Color col1 = cards[1].getColor();
		Color col2 = cards[2].getColor();
		if (col0.equals(col1) && col0.equals(col2))
			return true;
		if (!col0.equals(col1) && !col0.equals(col2) && !col1.equals(col2))
			return true;
		return false;
	}

	private boolean checkShape(Card[] cards) {
		String sh0 = cards[0].getShape();
		String sh1 = cards[1].getShape();
		String sh2 = cards[2].getShape();
		if (sh0.equals(sh1) && sh0.equals(sh2))
			return true;
		if (!sh0.equals(sh1) && !sh0.equals(sh2) && !sh1.equals(sh2))
			return true;
		return false;
	}

	private boolean checkFilling(Card[] cards) {
		String f0 = cards[0].getFilling();
		String f1 = cards[1].getFilling();
		String f2 = cards[2].getFilling();
		if (f0.equals(f1) && f0.equals(f2))
			return true;
		if (!f0.equals(f1) && !f0.equals(f2) && !f1.equals(f2))
			return true;
		return false;
	}
}
