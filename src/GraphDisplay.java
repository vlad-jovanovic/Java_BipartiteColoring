import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;


/**
 * This class provides the functionality to display the random geometric graph.
 * This is the subclass of JPanel that allows the user to display a given graph that is
 * a subclass of RandomGeometricGraph.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 14, 2012
 */
@SuppressWarnings("serial")
public class GraphDisplay extends JPanel {
	
	/**
	 * This enum represents the possible drawing objects another component can choose for this JPanel.
	 * 
	 * @author Vladimir
	 * @version 1.0.0
	 * @since December 16, 2012
	 */
	public enum DrawMethod { CLEAR_SCREEN, PAINT_POINTS, PAINT_POSZ, PAINT_NEGZ, COLOR_SET, DRAW_BIPARTITEI, DRAW_BIPARTITEII };
	
	/**
	 * This is a pointer to the current graph to be displayed.
	 */
	RandomGeometricGraph graph;
	
	/**
	 * This is a pointer to the drawing variable for the draw methods.
	 */
	private Graphics2D g2d;
	
	/**
	 * This is the width in pixels of the canvas to draw on.
	 */
	private int pxlWidth;
	
	/**
	 * This is the height in pixels of the canvas to draw on.
	 */
	private int pxlHeight;
	
	/**
	 * This is the radius in pixels of the vertices to be drawn.
	 */
	private int pointRadius;
	
	/**
	 * This is the diameter in pixels of the vertices to be drawn.
	 */
	private int pointDiameter;
	
	/**
	 * This is current drawing funciton to display on screen.
	 * The default is CLEAR_SCREEN indicating that the CLEAR_SCREEN
	 * method should be called.
	 */
	private DrawMethod paintSelected = DrawMethod.CLEAR_SCREEN;
	
	/**
	 * This is optional information that is set for the drawing methods
	 * when needed.
	 */
	private int optional;
	
	/**
	 * This is the setter function for graph.
	 * 
	 * @param rgg The new RandomGeometricGraph reference to store in graph.
	 */
	public void setGraph(RandomGeometricGraph rgg) {
		graph = rgg;
	}
	
	/**
	 * This is a constructor for the class when a graph is known.
	 * It sets the graph to a new value, though this will not apply in most
	 * cases as the graph has not been made yet.
	 * 
	 * @param rgg The new RandomGeometricGraph reference to store in graph.
	 */
	public GraphDisplay(RandomGeometricGraph rgg) {
		setGraph(rgg);
	}
	
	/**
	 * This constructor when the panel is made before the graph.
	 * 
	 * @param w The width in pixels of the JPanel component.
	 * @param h The height in pixels of the JPanel component.
	 */
	public GraphDisplay(int w, int h) {
		super();
		pxlWidth = w;
		pxlHeight = h;
	}
	
	/**
	 * This updes the pixel width and height.
	 * In order to have the proper bounds for the JPanel, this information must
	 * be regularly updated.  In order to get the accurate width and height of
	 * the drawable area, the Dimension of the component should be accessed
	 * after this component was set to visible.
	 * 
	 * @param w The width in pixels of the JPanel component.
	 * @param h The height in pixels of the JPanel component.
	 */
	public void setDimension(int w, int h) {
		pxlWidth = w;
		pxlHeight = h;
		clearScreen();
	}
	
	/**
	 * This method determines what to do with the paint method chosen by other components.
	 * The DrawMethod enum is used to easily determine which drawing method needs to be called.
	 * 
	 * @param g The super graphics component for drawing on this JPanel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		g2d.setBackground(Color.white);
		switch(paintSelected) {
		case CLEAR_SCREEN:  // If doing nothing, show nothing as well.
			clearScreen();
			break;
		case COLOR_SET: // To print a particular set of colors.
			colorSet(optional);
			break;
		case DRAW_BIPARTITEI: // For the first method of bipartite selection
			drawBipartiteI(optional);
			break;
		case DRAW_BIPARTITEII: // For the second method of bipartite selection.
			drawBipartiteII(optional);
			break;
		case PAINT_NEGZ: // To print the negative z-axis hemisphere.
			clearScreen();
			paintNegZ();
			break;
		case PAINT_POINTS: // To print the graph on the first page.
			clearScreen();
			// First, shift the bits of the int by 0, then see if the last bit is one.
			// If the last bit is one, then print out the edges.
			if((optional & 1) == 1)
				paintEdges();
			// Shift the bits over by one and compare the last bits. This is like checking to see if 2 was added.
			if((optional & 2) == 2)
				paintPoints();
			// Shifts the bits over by two and compares the LSD (least significant digit). This is like checking to see if 4 was added.
			if((optional & 4) == 4)
				paintMin();
			// Shifts the bits over by three and compares the LSD.  This is like checking to see if 8 was added.
			if((optional & 8) == 8)
				paintMax();
			break;
		case PAINT_POSZ: // To print the positive z-axis hemisphere.
			clearScreen();
			paintPosZ();
			break;
		default:
			break;
		}
	}
	
	/**
	 * this clears the screen and keeps it blank.
	 */
	private void clearScreen() {
		g2d.clearRect(0, 0, pxlWidth, pxlHeight);
		g2d.setBackground(Color.white);
		g2d.setColor(Color.black);
	}
	
	/**
	 * This paints the entire set of vertices for the RGG according to the pointRadius.
	 */
	private void paintPoints() {
		for(Point p : graph.getListOfPoints()) {
			g2d.setColor(Color.WHITE);
			g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
		}
	}
	
	/**
	 * This paints the edges between all points using the edgePrint lists to reduce the time complexity to n^2/2.
	 */
	private void paintEdges() {
		ArrayList<Point> points = graph.getListOfPoints();
		Point p;
		for(int i = 0; i < points.size(); i++) {
			p = points.get(i);
			for(Point q : p.edgePrint) {
				g2d.drawLine(p.display_x, p.display_y, q.display_x, q.display_y);
			}
		}
	}
	
	/**
	 * This paints all vertices with the same minimum degree blue.
	 * For the first vertex found to meet this criteria, all of its neighbors are colored cyan.
	 */
	private void paintMin() {
		boolean edgesOfOneDrawn = false;
		for(Point p : graph.getListOfPoints()) {
			if(p.degree == graph.getMinDegreeCount()) {
				g2d.setColor(Color.BLUE);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
				if(!edgesOfOneDrawn) {
					edgesOfOneDrawn = true;
					g2d.setColor(Color.CYAN);
					for(Point q : p.adjacencies) {
						g2d.fillOval(q.display_x-pointRadius, q.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
					}
					g2d.setColor(Color.BLACK);
					for(Point q : p.adjacencies) {
						g2d.drawOval(q.display_x-pointRadius, q.display_y-pointRadius, pointDiameter, pointDiameter);
					}
				}
			}
		}
	}
	
	/**
	 * This paints all vertices with the same maximum degree red.
	 * For the first vertex found to meet this criteria, all of its neighbors are colored pink.
	 */
	private void paintMax() {
		boolean edgesOfOneDrawn = false;
		for(Point p : graph.getListOfPoints()) {
			if(p.degree == graph.getMaxDegreeCount()) {
				g2d.setColor(Color.RED);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
				if(!edgesOfOneDrawn) {
					edgesOfOneDrawn = true;
					g2d.setColor(Color.PINK);
					for(Point q : p.adjacencies) {
						g2d.fillOval(q.display_x-pointRadius, q.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
					}
					g2d.setColor(Color.BLACK);
					for(Point q : p.adjacencies) {
						g2d.drawOval(q.display_x-pointRadius, q.display_y-pointRadius, pointDiameter, pointDiameter);
					}
				}
			}
		}
	}
	
	/**
	 * This changes the point radius to a new value and calculates the point diameter as well.
	 * 
	 * @param r This is the new radius to be set to.
	 */
	public void setPointRadius(int r) {
		pointRadius = r;
		pointDiameter = r*2;
	}

	/**
	 * This prints only the edges of a Point3D list of vectors in the positive Z space.
	 */
	private void paintPosZ() {
		ArrayList<Point> points = graph.getListOfPoints();
		Point3D p;
		g2d.setColor(Color.BLACK);
		for(int i = 0; i < points.size(); i++) {
			p = (Point3D)points.get(i);
			if(p.real_z > 0) {
				for(Point q : p.adjacencies) {
					if(((Point3D) q).real_z >= 0)
						g2d.drawLine(p.display_x, p.display_y, q.display_x, q.display_y);
				}
				g2d.setColor(Color.WHITE);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
			}
		}
	}
	
	/**
	 * This prints only the edges of a POint3D list of vectors in the negative Z space.
	 */
	private void paintNegZ() {
		ArrayList<Point> points = graph.getListOfPoints();
		Point3D p;
		g2d.setColor(Color.BLACK);
		for(int i = 0; i < points.size(); i++) {
			p = (Point3D)points.get(i);
			if(p.real_z < 0) {
				for(Point q : p.adjacencies) {
					if(((Point3D) q).real_z <= 0)
						g2d.drawLine(p.display_x, p.display_y, q.display_x, q.display_y);
				}
				g2d.setColor(Color.WHITE);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
			}
		}
	}
	
	/**
	 * This draws every point on the screen along with its associated color class.
	 */
	private void colorPoints() {
		clearScreen();
		for(Point p : graph.listOfPoints) {
			g2d.setColor(graph.getClassColors()[p.color]);
			g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
		}
	}

	/**
	 * This prints only one of the color classes depending on the selectedIndex.
	 * 
	 * @param selectedIndex This is the color class with the range of (0 to n) where n represents all of the color classes. 
	 */
	@SuppressWarnings("unchecked")
	private void colorSet(int selectedIndex) {
		if(selectedIndex == graph.getNumberOfColors()) {
			colorPoints();
		}
		else {
			clearScreen();
			for(Point p : (ArrayList<Point>) graph.getColorPointLists()[selectedIndex]) {
				g2d.setColor(graph.getClassColors()[p.color]);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
			}
		}
	}


	/**
	 * This draws up the first bipartite method results.
	 * 
	 * @param buttonCount The option from the JFrame to determine which bipartite to display.
	 */
	@SuppressWarnings("unchecked")
	private void drawBipartiteI(int buttonCount) {
		int firstColor = 0, secondColor = 1; // First independent set color value, and second independent set color value
		// This switch statement maps the buttonCount to the color class of the first and second independent set.
		switch(buttonCount) {
		case 0:
			firstColor = 0;
			secondColor = 1;
			break;
		case 1:
			firstColor = 0;
			secondColor = 2;
			break;
		case 2:
			firstColor = 0;
			secondColor = 3;
			break;
		case 3:
			firstColor = 1;
			secondColor = 2;
			break;
		case 4:
			firstColor = 1;
			secondColor = 3;
			break;
		case 5:
			firstColor = 2;
			secondColor = 3;
			break;
		}
		firstColor = graph.fourLargestIndex[firstColor];
		secondColor = graph.fourLargestIndex[secondColor];
		
		ArrayList<Point> firstSet = (ArrayList<Point>) graph.getColorPointLists()[firstColor];
		ArrayList<Point> secondSet = (ArrayList<Point>) graph.getColorPointLists()[secondColor];
		
		clearScreen();
		if(graph.distributionType().equals("Sphere")) { // if it's a sphere, do the same thing but only showing the positive hemisphere.
			for(Point p : firstSet) {
				if(((Point3D) p).real_z  > 0)
					for(Point q : p.adjacencies) {
						if(q.color == secondColor && ((Point3D)q).real_z > 0) {
							g2d.drawLine(p.display_x, p.display_y, q.display_x, q.display_y);
						}
					}
			}
			
			// This draws the vertices of the first set in the positive z-hemisphere.
			for(Point p : firstSet) {
				if(((Point3D) p).real_z > 0) {
					g2d.setColor(graph.getClassColors()[p.color]);
					g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
					g2d.setColor(Color.BLACK);
					g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
				}
			}
			
			// This draws the vertices of the second set in the positive z-hemisphere.
			for(Point p : secondSet) {
				if(((Point3D) p).real_z > 0) {
					g2d.setColor(graph.getClassColors()[p.color]);
					g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
					g2d.setColor(Color.BLACK);
					g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);	
				}
			}
		}
		else {
			// This draws all of the edges from a point in the first set to its adjacencies that have the same color as the second set.
			for(Point p : firstSet) {
				for(Point q : p.adjacencies) {
					if(q.color == secondColor) {
						g2d.drawLine(p.display_x, p.display_y, q.display_x, q.display_y);
					}
				}
			}
			
			// This draws the vertices of the first set.
			for(Point p : firstSet) {
				g2d.setColor(graph.getClassColors()[p.color]);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
			}
			// This draws the vertices of the second set.
			for(Point p : secondSet) {
				g2d.setColor(graph.getClassColors()[p.color]);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
			}
		}
	}

	/**
	 * This draws up the results of the second bipartite method.
	 * 
	 * @param buttonCount An option value to indicate whether the first, second or third bipartite is to be displayed.
	 */
	@SuppressWarnings("unchecked")
	private void drawBipartiteII(int buttonCount) {
		int firstColor = 0, secondColor = buttonCount; // First independent set color value, and second independent set color value
		
		ArrayList<Point> firstSet = (ArrayList<Point>) graph.getColorPointLists()[firstColor];
		ArrayList<Point> secondSet = (ArrayList<Point>) graph.getBipartiteIISecondSets()[secondColor];
		
		clearScreen();
		if(graph.distributionType().equals("Sphere")) { // if it's a sphere, do something different
			// Print the edges from the first to the second set as long as it is in the positive z-hemisphere.
			for(Point p : firstSet) {
				if(((Point3D) p).real_z  > 0)
					for(Point q : p.adjacencies) {
						if(q.R3UR2color == secondColor && ((Point3D)q).real_z > 0) {
							g2d.drawLine(p.display_x, p.display_y, q.display_x, q.display_y);
						}
					}
			}
			// Print the first set that is in the positive z-hemisphere.
			for(Point p : firstSet) {
				if(((Point3D) p).real_z > 0) {
					g2d.setColor(graph.getClassColors()[p.color]);
					g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
					g2d.setColor(Color.BLACK);
					g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
				}
			}
			// Print the second set that is in the positive z-hemisphere.
			for(Point p : secondSet) {
				if(((Point3D) p).real_z > 0) {
					g2d.setColor(graph.getClassColors()[p.R3UR2color+1]);
					g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
					g2d.setColor(Color.BLACK);
					g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);	
				}
			}
		}
		else {
			// This prints all the edges between the first and second set.
			for(Point p : firstSet) {
				for(Point q : p.adjacencies) {
					if(q.R3UR2color == secondColor) {
						g2d.drawLine(p.display_x, p.display_y, q.display_x, q.display_y);
					}
				}
			}
			
			// Prints the first set.
			for(Point p : firstSet) {
				g2d.setColor(graph.getClassColors()[p.color]);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
			}
			
			// Prints the second set.
			for(Point p : secondSet) {
				g2d.setColor(graph.getClassColors()[p.R3UR2color+1]);
				g2d.fillOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter+1, pointDiameter+1);
				g2d.setColor(Color.BLACK);
				g2d.drawOval(p.display_x-pointRadius, p.display_y-pointRadius, pointDiameter, pointDiameter);
			}
		}
	}

	/**
	 * This method translates a users method choice and option choice.
	 * It is designed to easily update the screen. The other components simply have to determine which draw method
	 * to call upon and then call this method to do so.  This also prevents missing sections of the graph from appearing
	 * once the user covers up the graph partially.
	 * 
	 * @param method This translates to the paintSelected variable.
	 * @param also This translates to the optional varibale.
	 */
	public void paintThis(DrawMethod method, int also) {
		paintSelected = method;
		optional = also;
		repaint();
	}
}
