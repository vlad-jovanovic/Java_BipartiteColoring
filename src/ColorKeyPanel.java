import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


/**
 * This JPanel stores the four squares to be displayed to the right of the color graph.
 * Once visible, it paints four squares according to the random colors chosen by RandomGeometricGraph
 * when the smallest last coloring algorithm was implemented.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 15, 2012
 */
@SuppressWarnings("serial")
public class ColorKeyPanel extends JPanel {
	
	/**
	 * This constructor declares an all white array for the box.
	 */
	public ColorKeyPanel() {
		super();
		array = new Color[]{Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE};
	}
	/**
	 * This contains the four colors to go along with the boxes dispalyed.
	 */
	private Color[] array;
	
	/**
	 * This array allows other classes to change the colors of the array.
	 * Given any size array, the first four are looked at. If there is less than four
	 * then white is automatically chosen.
	 * 
	 * @param arr
	 */
	public void setColors(Color[] arr) {
		array = new Color[]{Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE};
		for(int i=0; i < arr.length && i < 4; i++) {
			if(arr[i] == null)
				array[i] = null;
			else
				array[i] = arr[i];
		}
	}
	
	/**
	 * This contains the letters/numbers to be displayed in each box, length of four.
	 */
	private char[] letters = {'0','1','2','3'};
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0; i < 4; i++) {
			if(array[i] != null) {
				g.setColor(array[i]);
				g.fillRect(0, i*20, 15, 15);
				g.setColor(Color.BLACK);
				g.drawChars(letters, i, 1, 3, i*20+12);
			}
		}
	}
}
