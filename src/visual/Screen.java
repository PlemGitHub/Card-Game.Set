package visual;

import java.awt.Frame;
import javax.swing.JFrame;
import mech.Mech;

public class Screen {
	
	private JFrame fr;
	private MainPanel mp;
	private Mech mech;
	
	public Screen() {
		mp = new MainPanel();
		
		fr = new JFrame("Set: The Board Game");
		fr.setContentPane(mp);
		fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mech = new Mech(mp);
		mp.connectMech(mech);
		mp.repaint();
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Screen scr = new Screen();
	}
}