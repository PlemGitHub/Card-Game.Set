package entities;

import java.awt.Color;

import javax.swing.JPanel;
import mech.Constants;
import mech.Mech;
import visual.myElements.MyLabel;
import visual.myElements.buttons.MyButton;
import visual.myElements.buttons.sub.SkipButton;

@SuppressWarnings("serial")
public class Player extends JPanel implements Constants{
	
	private Mech mech;
	private int score;
	private MyLabel scoreLabel;
	private MyButton confirmBtn, cancelBtn;
	private SkipButton skipBtn;

	public Player(Mech mech) {
		this.mech = mech;
		setLayout(null);
		setSize(PLAYER_DIMENSION);
		createScoreLabel();
		createButtons();
	}

	private void createScoreLabel() {
		scoreLabel = new MyLabel("0");
		scoreLabel.setLocation(0, Y_1);
		add(scoreLabel);
	}

	private void createButtons() {
		confirmBtn = new MyButton();
		confirmBtn.setLocation(0, Y_2);
		confirmBtn.setText("Confirm");
		confirmBtn.setEnabled(false);
		confirmBtn.addActionListener(mech);
		add(confirmBtn);
		
		cancelBtn = new MyButton();
		cancelBtn.setLocation(0, Y_22);
		cancelBtn.setText("Cancel");
		cancelBtn.addActionListener(mech);
		add(cancelBtn);
		
		skipBtn = new SkipButton();
		skipBtn.setLocation(0, Y_3);
		skipBtn.setAction(mech.getSkipAction(this));
		skipBtn.setText("Skip");
		skipBtn.setVisible(true);
		add(skipBtn);
	}
	
	public boolean isSkipped(){
		if (skipBtn.getForeground().equals(Color.BLACK))
			return false;
		else return true;
	}
	
	public void switchSkipColor(){
		skipBtn.switchTextColor();
	}
	
	public void switchSkipIsEnabled(){
		skipBtn.switchIsEnabled();
	}
	
	public void switchButtonsVisibility(){
		confirmBtn.switchVisibility();
		cancelBtn.switchVisibility();
	}
	
	public void switchConfirmIsEnabled(){
		confirmBtn.switchIsEnabled();
	}
	
	public void increaseScore(){
		score++;
		scoreLabel.setText(String.valueOf(score));
	}
	
	public void decreaseScore(){
		score--;
		scoreLabel.setText(String.valueOf(score));
	}
	
	public MyButton getCancelButton(){
		return cancelBtn;
	}
	
	public MyButton getConfirmButton(){
		return confirmBtn;
	}
}
