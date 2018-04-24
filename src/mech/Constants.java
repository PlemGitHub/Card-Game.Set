package mech;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
public interface Constants {
	
	int _WINDOW_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int _WINDOW_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	
	int _ELEMENT_WIDTH = _WINDOW_HEIGHT / 4;
	int _ELEMENT_HEIGHT = _ELEMENT_WIDTH / 4;
	
	Dimension ELEMENT_DIMENSION = new Dimension(_ELEMENT_WIDTH, _ELEMENT_HEIGHT);
	
	int PLAYER_1_X = 0;
	int PLAYER_2_X = _WINDOW_WIDTH-_ELEMENT_WIDTH;
	Dimension PLAYER_DIMENSION = new Dimension(_ELEMENT_WIDTH, _WINDOW_HEIGHT);
	
	int START_BUTTON_X = _WINDOW_WIDTH/2 - _ELEMENT_WIDTH/2;
	
	int Y_1 = 0;
	int Y_2 = _ELEMENT_HEIGHT*2;
	int Y_22 = _ELEMENT_HEIGHT*3;
	int Y_3 = _ELEMENT_HEIGHT*4;
	
	Font FONT = new Font("", Font.PLAIN, defineFontSize());
		static int defineFontSize() {
			int size = 0;
			do {
				size++;
			} while (size < _ELEMENT_HEIGHT*.6);
			return size;
		}
		
	int _GAMEFIELD_WIDTH = _WINDOW_WIDTH-_ELEMENT_WIDTH*2;
	double _PROPORTIONS = (double) _WINDOW_HEIGHT/_GAMEFIELD_WIDTH;
	int _GAP = _ELEMENT_HEIGHT/4;
	int CARD_WIDTH[] = new int[22];
	int CARD_HEIGHT[] = new int[22];
	Point CARD_POINT[][] = new Point[7][3];
		static void calculateCardSize(int cardsOnTable){
			for (int i = 1; i <= cardsOnTable/3; i++) {
				CARD_HEIGHT[i*3] = (int)(_WINDOW_HEIGHT * 0.6-_GAP*2)/3;
				CARD_WIDTH[i*3] = (int)(CARD_HEIGHT[i*3]*_PROPORTIONS);
			}
		}
	
		
	static void calculateCardPoints(int cardsOnTable){
		cardsOnTable = (cardsOnTable < 12)? 12:cardsOnTable;
		int third = cardsOnTable/3;
		int yy[] = _calculateYY(cardsOnTable);
		int leftX = 0;
		int halfOfThird = third / 2;
			if (third % 2 == 0){
				leftX = _WINDOW_WIDTH/2 - _GAP/2 - halfOfThird*CARD_WIDTH[cardsOnTable] - (halfOfThird-1)*_GAP;
			}else{
				leftX = _WINDOW_WIDTH/2 - CARD_WIDTH[cardsOnTable]/2-halfOfThird*(CARD_WIDTH[cardsOnTable]+_GAP);
			}
		for (int i = 0; i < third; i++) {
			for (int j = 0; j < 3; j++) {
				int x = leftX + i*(CARD_WIDTH[cardsOnTable]+_GAP);
				CARD_POINT[i][j] = new Point(x, yy[j]);
			}
		}
	}

		static int[] _calculateYY(int cardsOnTable) {
			int yy[] = new int[3];
			int y0 = _WINDOW_HEIGHT/2 - CARD_HEIGHT[cardsOnTable]*3/2-_GAP;
			for (int i = 0; i < 3; i++) {
				yy[i] = y0 + i*(CARD_HEIGHT[cardsOnTable]+_GAP);
			}
			return yy;
		}
}
