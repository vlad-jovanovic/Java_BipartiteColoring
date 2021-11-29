import java.util.ArrayList;


/**
 * This subclass of Point represents a vector in the 3D geometric space.
 * For any operation with smallest last ordering, the third dimension is not
 * needed to compute the algorithms. It is only used when projecting the point
 * on to a sphere and determining if thre are edges.
 * 
 * @author Vladimir
 * @version 1.0.0
 * @since December 13, 2012
 */
public class Point3D extends Point {
	
	/**
	 * The depth location in a geometric graph.
	 */
	public double real_z;
	/**
	 * The depth location for display purposes.
	 */
	public int display_z;
	
	/**
	 * This constructor sets the real coordinates to the ones passed into 
	 * and initializes some values.
	 * The d function is found squaring the values in order to have the projection value.
	 * All the real points are then projected on to the surface of the sphere.
	 * 
	 * @param i The real_x location before projection on the sphere.
	 * @param j The real_y location before projection on the sphere.
	 * @param k The real_z location before projection on the sphere.
	 */
	public Point3D(double i, double j, double k) {
		d = Math.sqrt(i*i+j*j+k*k);
		
		real_x = i/d;
		real_y = j/d;
		real_z = k/d;
		
		color = -1;
		degree = 0;
		adjacencies = new ArrayList<Point>();
		edgePrint = new ArrayList<Point>();
		
	}
	
	/**
	 * This is used to override the closeTo function from the Point superclass.
	 * The closeTo function has to also account for the z coordinates now.
	 * 
	 * @param p The Point3D object to compare against.
	 * @param distanceSq The threshold value for the distance.
	 * @return True if this point and the other are within distanceSq. False otherwise.
	 */
	public boolean closeTo(Point3D p, double distanceSq) {
		double xsq = (real_x-p.real_x)*(real_x-p.real_x);
		double ysq = (real_y-p.real_y)*(real_y-p.real_y);
		double zsq = (real_z-p.real_z)*(real_z-p.real_z);
		
		if(xsq+ysq+zsq <= distanceSq)
			return true;
		else
			return false;
	}

	/**
	 * This function is called once the display coordinates have been figured out.
	 * Point3D first takes in a given 3D coordinate and projects it on to a unit sphere.
	 * This information is then used to create the display coordinate system.
	 * 
	 * @param x The display_x value to be replaced with.
	 * @param y The display_y value to be replaced with.
	 * @param z The display_z value to be replaced with.
	 */
	public void addCoord(int x, int y, int z) {
		// TODO Auto-generated method stub
		display_x = x;
		display_y = y;
		display_z = z;
	}
	
}
