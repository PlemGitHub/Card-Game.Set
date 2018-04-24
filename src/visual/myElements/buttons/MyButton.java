package visual.myElements.buttons;

import java.awt.Color;
import javax.swing.JButton;
import mech.Constants;

@SuppressWarnings("serial")
public class MyButton extends JButton implements Constants{
	
	public MyButton() {
		setVisible(false);
		setHorizontalAlignment(JButton.CENTER);
		setVerticalAlignment(JButton.CENTER);
		setFocusable(false);
		setSize(ELEMENT_DIMENSION);
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
		setFont(FONT);
		setForeground(Color.BLACK);
	}
			
	public void switchVisibility(){
		if (isVisible())
			setVisible(false);
		else
			setVisible(true);
	}

	
	public void switchIsEnabled(){
		if (isEnabled())
			setEnabled(false);
		else
			setEnabled(true);
	}
}
