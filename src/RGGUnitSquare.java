import java.util.Random;

/**
 * This subclass creates a unit square distribution.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 14, 2012
 */
public class RGGUnitSquare extends RandomGeometricGraph {

	/**
	 * @param width The pixel width
	 * @param height The pixel height
	 */
	public RGGUnitSquare(int width, int height) {
		super(width, height);
	}

	/* (non-Javadoc)
	 * @see RandomGeometricGraph#createDistributionOfPoints()
	 */
	@Override
	protected void createDistributionOfPoints() {
		Random r = new Random();
		
		for (int i=0; i < vertexCount; i++) {
	    	double j = r.nextDouble();
	    	double k = r.nextDouble();
	    	
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
		return "Square";
	}
	
	
}
