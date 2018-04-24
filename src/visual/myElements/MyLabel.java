package visual.myElements;

import javax.swing.JLabel;
import mech.Constants;

@SuppressWarnings("serial")
public class MyLabel extends JLabel implements Constants{
	
	public MyLabel(String text) {
		setVerticalAlignment(JLabel.CENTER);
		setHorizontalAlignment(JLabel.CENTER);
		setOpaque(false);
		setSize(ELEMENT_DIMENSION);
		setText(text);
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
		setFont(FONT);
	}
}
