import java.util.Random;

/**
 * This subclass creates a unit circle distribution.
 * Random polar coordinates are made first, and then those are translated
 * into x and y coordinates.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 14, 2012
 */
public class RGGUnitCircle extends RandomGeometricGraph {

	/**
	 * @param width The pixel width
	 * @param height The pixel height
	 */
	public RGGUnitCircle(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see RandomGeometricGraph#createDistributionOfPoints()
	 */
	@Override
	protected void createDistributionOfPoints() {
		Random r = new Random();
		// Going to use polar coordinates here
		for (int i=0; i < vertexCount; i++) {
	    	double length = r.nextDouble()/2.0; //length from origin
	    	double angle = r.nextInt(360)+r.nextDouble(); //angle from [0 to 360)
	    	
	    	double j = length * Math.cos(Math.toRadians(angle))+.5; // The real_x value
	    	double k = length * Math.sin(Math.toRadians(angle))+.5; // The real_y value
	    	
	    	int x = (int) (j*pxlWidth);
	        int y = (int)(k*pxlHeight);
	        
	        Point p = new Point(j,k,x,y);
	        p.id = i;
	    	listOfPoints.add(p);
	    }
	}

	/* (non-Javadoc)
	 * @see RandomGeometricGraph#distributionType()
	 */
	@Override
	public String distributionType() {
		// TODO Auto-generated method stub
		return "Disk";
	}

}
