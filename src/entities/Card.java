package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Card extends JLabel{
	
	private int signWidth, signHeight;
	private int signX;
	private int[] signYY = new int[3];

	private int quantity;
	private Color color;
	private String shape;
	private String filling;
	
	final String poly = "poly";
	final String oval = "oval";
	final String rectangle = "rectangle";
		private String[] shapes = {poly, oval, rectangle};
	final String empty = "empty";
	final String filled = "filled";
	final String lined = "lined";
		private String[] fillings = {empty, filled, lined};
	private Color[] colors = {Color.RED, new Color(0, 200, 0), Color.BLUE};
	
	private int linesQ = 10;
	private Color borderColor = Color.BLACK;
	
	private Point pointIJ;
	
	public Card() {
		createProperties();
		setOpaque(true);
	}

	private void createProperties() {
		quantity = randomInThree()+1;
		color = colors[randomInThree()];
		shape = shapes[randomInThree()];
		filling = fillings[randomInThree()];
	}

	private int randomInThree(){
		return (int)(Math.random()*3);
	}
	
	public boolean checkEqualCard(Card anotherCard){
		boolean a = (anotherCard.getQuantity() == this.quantity);
		boolean b = (anotherCard.getColor().equals(this.color));
		boolean c = (anotherCard.getShape().equals(this.shape));
		boolean d = (anotherCard.getFilling().equals(this.filling));
		if (a && b && c && d)
			return true;
		else
			return false;
	}
	
	public int getQuantity(){return quantity;}
	public Color getColor(){return color;}
	public String getShape(){return shape;}
	public String getFilling(){return filling;}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(color);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		signWidth = (int)(getWidth()*0.6);
		signHeight = (int)(getHeight()*0.2);
		signX = (getWidth()-signWidth)/2;
		calculateSignYY();
		
		switch (shape) {
			case rectangle: 
				drawRectangle(g);
				break;
			case oval: 
				drawOval(g);
				break;
			case poly: 
				drawPoly(g);
				break;
		}
		g.setColor(borderColor);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
	}

	private void calculateSignYY() {
		switch (quantity) {
		case 1:
			signYY[0] = getHeight()/2-signHeight/2;
			break;
		case 2: {
			signYY[0] = getHeight()/2-signHeight*3/2;
			signYY[1] = getHeight()/2+signHeight/2;
		}
			break;
		case 3: {
			signYY[0] = getHeight()/2-signHeight*2;
			signYY[1] = getHeight()/2-signHeight/2;
			signYY[2] = getHeight()/2+signHeight;
		}
			break;
		}
	}

	private void drawRectangle(Graphics g) {
		for (int i = 0; i < quantity; i++) {
			switch (filling) {
				case empty:
					g.drawRect(signX, signYY[i], signWidth, signHeight);
					break;
				case filled:
					g.fillRect(signX, signYY[i], signWidth, signHeight);
					break;
				case lined:{
					Graphics2D g2D = (Graphics2D) g;
					Paint p = g2D.getPaint();
						
					g2D.setPaint(getTexturePaint());
					g2D.fill(new Rectangle(signX, signYY[i], signWidth, signHeight));
					
					g2D.setPaint(p);
					g.drawRect(signX, signYY[i], signWidth, signHeight);
					}
					break;
			}
		}
	}

	private void drawOval(Graphics g) {
		for (int i = 0; i < quantity; i++) {
			switch (filling) {
				case empty:
					g.drawOval(signX, signYY[i], signWidth, signHeight);
					break;
				case filled:
					g.fillOval(signX, signYY[i], signWidth, signHeight);
					break;
				case lined:{
					Graphics2D g2D = (Graphics2D) g;
					Paint p = g2D.getPaint();
					
					g2D.setPaint(getTexturePaint());
					g2D.fillOval(signX, signYY[i], signWidth, signHeight);
					
					g2D.setPaint(p);
					g.drawOval(signX, signYY[i], signWidth, signHeight);
					}
					break;
			}
		}
	}

	private void drawPoly(Graphics g) {
		for (int i = 0; i < quantity; i++) {
			switch (filling) {
				case empty:
					g.drawPolygon(getPolygon(i));
					break;
				case filled:
					g.fillPolygon(getPolygon(i));
					break;
				case lined:{
					Graphics2D g2D = (Graphics2D) g;
					Paint p = g2D.getPaint();
					
					g2D.setPaint(getTexturePaint());
					g2D.fillPolygon(getPolygon(i));
					
					g2D.setPaint(p);
					g.drawPolygon(getPolygon(i));
					}
					break;
			}
		}
	}
	
	private Polygon getPolygon(int i) {
		int x = signX;
			int xx[] = {x, x+signWidth/4, x+signWidth*3/4, x+signWidth, x+signWidth*3/4, x+signWidth/4};
		int y = signYY[i];
		int half = signHeight/2;
			int yy[] = {y+half, y, y, y+half, y+signHeight, y+signHeight};
		Polygon p = new Polygon(xx, yy, 6);
		return p;
	}

	private TexturePaint getTexturePaint(){
		BufferedImage bi = new BufferedImage(signWidth, signHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D biG = bi.createGraphics();
		biG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int dX = signWidth/linesQ;
		biG.setColor(color);
		for (int i = -linesQ; i < linesQ*2; i++) {
			biG.drawLine((i+1)*dX, 0, i*dX, signHeight);
		}
		Rectangle r = new Rectangle(signX, 0, signWidth, signHeight);
		TexturePaint tp = new TexturePaint(bi, r);
		return tp;
	}
	
	public boolean switchBorderColor(){
		if (borderColor.equals(Color.BLACK))
			borderColor = Color.RED;
		else
			borderColor = Color.BLACK;
		repaint();
		
		if (borderColor.equals(Color.RED))
			return true;
		else return false;
	}
	
	public void setBlackBorder(){
		borderColor = Color.BLACK;
		repaint();
	}
	
	public void setPointIJ(int i, int j){
		pointIJ = new Point(i, j);
	}
	
	public Point getPointIJ(){
		return pointIJ;
	}
	
	public boolean isSelected(){
		if (borderColor.equals(Color.BLACK))
			return false;
		else
			return true;
	}
}
