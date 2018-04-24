package visual.myElements.buttons.sub;

import java.awt.Color;
import visual.myElements.buttons.MyButton;

@SuppressWarnings("serial")
public class SkipButton extends MyButton{

	public void switchTextColor(){
		if (getForeground().equals(Color.BLACK))
			setForeground(Color.RED);
		else
			setForeground(Color.BLACK);
	}
}
