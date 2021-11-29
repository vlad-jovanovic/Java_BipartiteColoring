import java.util.ArrayList;
import java.util.Random;

/**
 * This subclass creates a unit sphere distribution.
 * X is [-1,1].
 * Y is [-1,1].
 * Z is [-1,1].
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 14, 2012
 */
public class RGGSphere extends RandomGeometricGraph {

	/**
	 * @param width The pixel width
	 * @param height The pixel height
	 */
	public RGGSphere(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see RandomGeometricGraph#createDistributionOfPoints()
	 */
	@Override
	protected void createDistributionOfPoints() {
		Random rand = new Random();
	    
	    for (int i=0; i < vertexCount; i++) {
	    	double j = rand.nextDouble()*2-1;
	    	double k = rand.nextDouble()*2-1;
	    	double l = rand.nextDouble()*2-1;
	    	
	    	Point3D p = new Point3D(j,k,l);
	    	
	    	
	    	int x = (int) (pxlWidth*.5+p.real_x*pxlWidth*.5);
	        int y = (int)(pxlHeight*.5+p.real_y*pxlHeight*.5);
	        int z = (int)(pxlHeight*.5+p.real_z*.5);
	        
	        p.addCoord(x,y,z);
	        p.id = i;
	    	listOfPoints.add(p);
	    }
	}
	
	/* (non-Javadoc)
	 * @see RandomGeometricGraph#createPoints(int, double)
	 */
	@Override
	public void createPoints(int n, double rad ) {
		listOfPoints = new ArrayList<Point>();
		distanceSq = rad*rad;
		radius = rad;
		vertexCount = n;

	    minDegreeCount = n+1;
	    maxDegreeCount = -1;
	    totalEdges = 0;
	    
	    degreeRemovedAt = new int[vertexCount];
		originalDegree  = new int[vertexCount];
		smallestLastOrder = new int[vertexCount];
	    
	    // Create the random points
	    createDistributionOfPoints();
	    
	    // Get the degrees
	    Point3D p, x;
	    for(int i = 0; i < listOfPoints.size(); i++)
	    {
	    	p = (Point3D)listOfPoints.get(i);
	    	for(int j = 0; j < listOfPoints.size(); j++)
	    	{
	    		x = (Point3D)listOfPoints.get(j);
	    		if(p != x && p.closeTo(x,distanceSq)){
	    			p.degree++;
	    			p.addAdjacent(x);
	    			totalEdges++;
	    		}
	    			
	    	}
	    	if(p.degree > maxDegreeCount)
	    	{
	    		maxDegreeCount = p.degree;
	    	}
	    	if(p.degree < minDegreeCount)
	    		minDegreeCount = p.degree;
	    }
	}

	/* (non-Javadoc)
	 * @see RandomGeometricGraph#distributionType()
	 */
	@Override
	public String distributionType() {
		// TODO Auto-generated method stub
		return "Sphere";
	}
}
