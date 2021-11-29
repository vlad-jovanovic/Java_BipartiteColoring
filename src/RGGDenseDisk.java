import java.util.Random;


/**
 * This subclass creates a dense disk distribution.
 * The vertices have a 2:1 chance of being on the rim of the unit circle.
 * The rim is the band that is R/2 away from 1.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 14, 2012
 */
public class RGGDenseDisk extends RandomGeometricGraph {

	/**
	 * @param width The pixel width
	 * @param height The pixel height
	 */
	public RGGDenseDisk(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see RandomGeometricGraph#createDistributionOfPoints()
	 */
	@Override
	protected void createDistributionOfPoints() {
		Random r = new Random();
		double length, angle, j, k;
		int x, y;
		// Going to use polar coordinates here
		for (int i=0; i < vertexCount; i++) {
			if(r.nextInt(3) == 2) { // if it's 2 (1/3) then should be in inner
				length = (.5-radius/2.0)*r.nextDouble();
			}
			else { // if it's 0 or 1 (2/3), then should be in outer
				length = (.5-radius/2.0)+r.nextDouble()*radius/2.0;
			}
	    	 //length from origin
	    	angle = r.nextInt(360)+r.nextDouble(); //angle from [0 to 360)
	    	
	    	j = length * Math.cos(Math.toRadians(angle))+.5; // The real_x value
	    	k = length * Math.sin(Math.toRadians(angle))+.5; // The real_y value
	    	
	    	x = (int) (j*pxlWidth);
	        y = (int)(k*pxlHeight);
	        
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
		return "Dense Rim Disk";
	}

}
