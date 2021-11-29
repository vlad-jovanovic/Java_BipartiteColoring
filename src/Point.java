import java.util.ArrayList;

/**
 * This class represents a vector in a geometric graph.
 * The point contains all the information necessary to do calculations
 * for the smallest last ordering and any associated algorithms.  It stores
 * the color, degree, and an adjacency list. Each point is uniquely identified
 * by an ID value.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December, 13 2012
 */
public class Point {
	
	/**
	 * The unique ID tag for use in smallest last ordering.
	 */
	public int id;	
	
	/**
	 * The horizontal location in a geometric graph.
	 */
	public double real_x;
	
	/**
	 * The vertical location in a geometric graph.
	 */
	public double real_y;
	
	/**
	 * The horizontal location for display purposes.
	 */
	public int display_x;
	
	/**
	 * The vertical location for display purposes.
	 */
	public int display_y;
	
	/**
	 * The distance vector for use in projection.
	 */
	public double d;
	
	/**
	 * The number of vertices/points adjacent to this vertex/point/
	 */
	public int degree;
	
	/**
	 * The number of vertices/points still adjacent to the vertex before removal
	 * in the smallest last ordering.
	 */
	public int degreeLeft;
	
	/**
	 * The color of the vertex/point. 
	 * Points with the same color represent an independent set. The color values start
	 * from 0, with the largest color class always at 0.
	 */
	public int color;
	
	/**
	 * The component identification number for the point.
	 * This identifies which component the point is part of.
	 */
	public int componentNumber;
	
	/**
	 * The number of distinct colored vertices at the time the vertex/point was colored.
	 */
	public int distinctAdjacentColors = 0;
	
	/**
	 * A list of vertices/points that are adjacent to this vertex/point.
	 */
	public ArrayList<Point> adjacencies;
	
	/**
	 * A list of vertices/points that should be printed to form edges with this vertex/point.
	 * This list is used when the edges are printed in an RGG.
	 */
	public ArrayList<Point> edgePrint;
	
	/**
	 * A copy of the adjacency list in order to facilitate the smallest last ordering.
	 */
	public ArrayList<Point> edgeRemovalList;
	
	// For second bipartite method
	/**
	 * The color value set for the other bipartite in the second method.
	 * This is -1 by default meaning no color has been assigned.
	 */
	public int R3UR2color = -1;
	
	/**
	 * The position of the vertex in the smallest last order list.
	 * This is needed to order the R3 and R2 set.
	 */
	public int SLOrderPosition;
	
	/**
	 * Count of vertices in the first color class adjacent to this vertex/point.
	 * This is 0 by default.
	 */
	public int timesAdjacentToFirst = 0;
	
	/**
	 * Default constructor for the Point class.
	 * This sets the degree at 0, color to not selected (-1), and initializes
	 * the adjacencies and edgePrint lists.
	 */
	public Point() {
		degree = 0;
		color = -1;
		adjacencies = new ArrayList<Point>();
		edgePrint = new ArrayList<Point>();
	}
	
	/**
	 * Typical constructor for the Point class that takes in the real coordinate
	 * and translated graphical display coordinate.
	 * 
	 * @param i Translates to real_x.
	 * @param j Translates to real_y.
	 * @param xaxis Translates to display_x.
	 * @param yaxis Translates to display_y.
	 */
	public Point(double i, double j, int xaxis, int yaxis)
	{
		real_x = i;
		real_y = j;
		color = -1;
		display_x = xaxis;
		display_y = yaxis;
		d = i*i+j*j;
		degree = 0;
		adjacencies = new ArrayList<Point>();
		edgePrint = new ArrayList<Point>();
	}
	
	/**
	 * This method compares the current Point with another point
	 * to see if they are "closeTo" or within range of one another.
	 * The standard distance measure is used, but instead of employing the square
	 * root function, the values are kept squared to cut down on calculation time.
	 * 
	 * @param p The other point to compare to.
	 * @param distanceSq The distance square maximum that needs to be between two points to return true.
	 * @return True if the two points are within the distance squared range. False if otherwise.
	 */
	public boolean closeTo(Point p, double distanceSq) {
		double xsq = (real_x-p.real_x)*(real_x-p.real_x);
		double ysq = (real_y-p.real_y)*(real_y-p.real_y);
		if(xsq+ysq <= distanceSq)
			return true;
		else
			return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point [x=" + real_x + ", y=" + real_y + ", w=" + display_x + ", h=" + display_y
				+ ", color= " + color + ", degree= " + degree + "]";
	}

	/**
	 * This adds another point to the adjacency list of this Point.
	 * If the ID of the new point is less than the ID of this Point,
	 * then it can be assumed that the edge has already been counted once. To prevent
	 * drawing the same edge twice, the second instance is not included in the edgePrint list.
	 * 
	 * @param x The other print to add to the adjacency list.
	 */
	public void addAdjacent(Point x) {
		adjacencies.add(x);
		if(id < x.id) {
			edgePrint.add(x);
		}
	}

	/**
	 * This copies the adjacency list to the edgeRemovalList.
	 * This method is called in the smallest last ordering algorithm to prepare the Points
	 * for manipulation without actually changing any constant attributes like the adjacency list.
	 */
	public void prepareRemoval() {
		edgeRemovalList = new ArrayList<Point>(adjacencies);
		degreeLeft = degree;
	}
	
}