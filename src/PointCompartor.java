import java.util.Comparator;


/**
 * This class allows the easy comparison of ArrayList<Points>.
 * The comparator looks at the SLOrderPosition of two points to see which is bigger.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 15, 2012
 */
public class PointCompartor implements Comparator<Point> {
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Point o1, Point o2) {
		return o1.SLOrderPosition - o2.SLOrderPosition;
	}
}
