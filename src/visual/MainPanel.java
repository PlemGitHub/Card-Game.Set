package visual;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import entities.Card;
import entities.Player;
import mech.Constants;
import mech.Mech;
import visual.myElements.buttons.MyButton;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements Constants{
	
	private Mech mech;
	private MyButton startBtn;
	private final String exitStr = "exit string";
	private final String playerOneBang = "player 1 bang";
	private final String playerTwoBang = "player 2 bang";
	
	public MainPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
	}
	
	public void connectMech(Mech mech){
		this.mech = mech;
		createStartButton();
		setupKeyBindings();
	}

	private void setupKeyBindings() {
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), exitStr);
		getActionMap().put(exitStr, mech.getExitAction());
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), playerOneBang);
		getActionMap().put(playerOneBang, mech.getPlayerOneAction());
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), playerTwoBang);
		getActionMap().put(playerTwoBang, mech.getPlayerTwoAction());
	}

	private void createStartButton() {
		startBtn = new MyButton();
		startBtn.setSize(ELEMENT_DIMENSION);
		startBtn.setLocation(START_BUTTON_X, Y_1);
		startBtn.setAction(mech.getStartAction());
		startBtn.setText("Start");
		startBtn.setVisible(true);
		add(startBtn);
	}
	
	public void clearPlayers(){
		for (Component c: getComponents()) {
			if (c instanceof Player)
				remove(c);
		}
	}
	
	public void clearCards(){
		for (Component c: getComponents()) {
			if (c instanceof Card)
				remove(c);
		}
	}
	
	public MyButton getStartButton(){
		return startBtn;
	}
	
	public void enableMouseListeners(){
		for (Component c : getComponents()) {
			if (c instanceof Card)
				c.addMouseListener(mech);
		}
	}
	
	public void setBlackBordersOnAllCards(){
		for (Component c : getComponents()) {
			if (c instanceof Card)
				((Card) c).setBlackBorder();
		}
	}
	
	public void removeSelectedCards(){
		for (Component c : getComponents()) {
			if (c instanceof Card)
				if (((Card) c).isSelected()){
					remove(c);
					mech.setNullCardInArray((Card)c);
				}
		}
		repaint();
	}
	
	public Card[] findCheckedCards() {
		Card[] checkedCards= new Card[3];
		int n = 0;
		for (Component c : getComponents()) {
			if (c instanceof Card)
				if (((Card) c).isSelected()){
					checkedCards[n] = (Card) c;
					n++;
				}
		}
		return checkedCards;
	}
}