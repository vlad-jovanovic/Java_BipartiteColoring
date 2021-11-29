import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * This contains the entire RGG by holding a list of all the points.
 * Various methods have been added to perform different algorithms on the RGG.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 14, 2012
 */
/**
 * @author Vladimir
 *
 */
public abstract class RandomGeometricGraph {
	/**
	 * The list of points for the graph
	 */
	protected ArrayList<Point> listOfPoints;
	
	/**
	 * The radius threshold squared for distance comparisons.
	 */
	protected double distanceSq;
	/**
	 * The radius to determine if an edge is formed.
	 */
	protected double radius;
	
	/**
	 * The number of vertices in the graph.
	 */
	protected int vertexCount;
	/**
	 * The minimum degree of the points
	 */
	protected int minDegreeCount;
	/**
	 * The maximum degree of the points
	 */
	protected int maxDegreeCount;
	/**
	 * The height and width in pixels of the graph.
	 */
	protected int pxlWidth, pxlHeight;
	/**
	 * The total number of edges double counted. 
	 * Cut in half to get the "real" amount.
	 */
	protected int totalEdges;
	/**
	 * The number of color classes for this RGG.
	 */
	protected int numberOfColors;
	
	/**
	 * The degree of a point when it was removed in smallest last order (SLO).
	 */
	protected int[] degreeRemovedAt;
	/**
	 * The original degree for each point in SLO.
	 */
	protected int[] originalDegree;
	/**
	 * The list of points in SLO.
	 */
	protected int[] smallestLastOrder;
	/**
	 * The size of each color class.
	 */
	protected int[] colorSizes;
	/**
	 * The number of vertices with a particular degree.
	 */
	protected int[] degreeDistribution;
	
	/**
	 * A list of all the bipartite groups in String form
	 */
	public String[] groupingsForBipartiteI;
	/**
	 * Four largest classes of colors
	 */
	public int[] fourLargestIndex;
	/**
	 * The number of vertices for the different bipartites in the first method.
	 */
	protected int[] vertexCountForBipartiteI;
	/**
	 * The number of edges for the different bipartites in the first method.
	 */
	protected int[] edgesForBipartiteI;
	/**
	 * The number of face for the different bipartites in the first method.
	 */
	protected int[] facesForBipartiteI;
	/**
	 * The number of components for the different bipartites in the first method.
	 */
	protected int[] componentsForBipartiteI;
	
	/**
	 * The number of vertices for the different bipartites in the second method.
	 */
	protected int[] vertexCountForBipartiteII;
	/**
	 * The number of edges for the different bipartites in the second method.
	 */
	protected int[] edgesForBipartiteII;
	/**
	 * The number of faces for the different bipartites in the second method.
	 */
	protected int[] facesForBipartiteII;
	/**
	 * The number of components for the different bipartites in the second method.
	 */
	protected int[] componentsForBipartiteII;
	/**
	 * The lists of objects for the different second sets created in the bipartites of the second method.
	 * Notice that this is not needed for the first method because it just looks at the color classes.
	 */
	protected Object[] bipartiteIISecondSets;
	
	/**
	 * The point lists for a particular color.
	 */
	protected Object[] colorPointLists;
	/**
	 * The Color object for a particular class.
	 */
	private Color[] classColors;
	
	
	/**
	 * @return the list of points
	 */
	public ArrayList<Point> getListOfPoints() {
		return listOfPoints;
	}

	/**
	 * @return the number of vertices
	 */
	public int getVertexCount() {
		return vertexCount;
	}

	/**
	 * @return the minimum degree
	 */
	public int getMinDegreeCount() {
		return minDegreeCount;
	}

	/**
	 * @return the maximum degree
	 */
	public int getMaxDegreeCount() {
		return maxDegreeCount;
	}

	/**
	 * @return the pixel width for the RGG
	 */
	public int getPxlWidth() {
		return pxlWidth;
	}

	/**
	 * @return the pixel height for the rgg
	 */
	public int getPxlHeight() {
		return pxlHeight;
	}

	/**
	 * @return the total number of edges, double counted
	 */
	public int getTotalEdges() {
		return totalEdges;
	}
	
	/**
	 * @return the number of color classes
	 */
	public int getNumberOfColors() {
		return numberOfColors;
	}

	/**
	 * @return the Color objects for each class
	 */
	public Color[] getClassColors() {
		return classColors;
	}
	
	/**
	 * @return the list of vertex counts for the first bipartite method
	 */
	public int[] getVertexCountForBipartiteI() {
		return vertexCountForBipartiteI;
	}
	
	/**
	 * @return the list of edges for the first bipartite method
	 */
	public int[] getEdgesForBipartiteI() {
		return edgesForBipartiteI;
	}
	
	/**
	 * @return the list of points for each color class
	 */
	public Object[] getColorPointLists() {
		return colorPointLists;
	}
	
	/**
	 * @return the list of faces for the first bipartite method
	 */
	public int[] getFacesForBipartiteI() {
		return facesForBipartiteI;
	}

	/**
	 * @return the list of components for the first bipartite method
	 */
	public int[] getComponentsForBipartiteI() {
		return componentsForBipartiteI;
	}
	
	/**
	 * @return the list of vertex counts for the second bipartite method
	 */
	public int[] getVertexCountForBipartiteII() {
		return vertexCountForBipartiteII;
	}
	
	/**
	 * @return the list of edges for the second bipartite method
	 */
	public int[] getEdgesForBipartiteII() {
		return edgesForBipartiteII;
	}
	
	/**
	 * @return the list of points for the second bipartite method
	 */
	public Object[] getBipartiteIISecondSets() {
		return bipartiteIISecondSets;
	}
	
	/**
	 * @return the list of faces for the second bipartite method
	 */
	public int[] getFacesForBipartiteII() {
		return facesForBipartiteII;
	}

	/**
	 * @return the list of components for the second bipartite method
	 */
	public int[] getComponentsForBipartiteII() {
		return componentsForBipartiteII;
	}
	
	/**
	 * @param width the width of the pixels for this display
	 * @param height the height of the pixels for this display
	 */
	public RandomGeometricGraph(int width, int height) {
	    pxlWidth = width;
	    pxlHeight = height;
	}
	
	/**
	 * This creates the list of points for the RGG and the associated edges.
	 * 
	 * @param n the number of vertices for the RGG
	 * @param rad the threshold to form an edge
	 */
	public void createPoints(int n, double rad ) {
		listOfPoints = new ArrayList<Point>();
		distanceSq = rad*rad;
		radius = rad;
		vertexCount = n;

	    minDegreeCount = n+1;
	    maxDegreeCount = -1;
	    totalEdges = 0;
	    
	    // Set up the arrays to be used later.
	    degreeRemovedAt = new int[vertexCount];
		originalDegree  = new int[vertexCount];
		smallestLastOrder = new int[vertexCount];
	    
	    // Create the random points
	    createDistributionOfPoints();
	    
	    // Get the degrees
	    for(Point p:listOfPoints)
	    {
	    	for(Point x:listOfPoints)
	    	{
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
	
	/**
	 * This abstract method changes for each subclass distribution type.
	 */
	protected abstract void createDistributionOfPoints();
	
	/**
	 * @return the name of the distribution in String form
	 */
	public abstract String distributionType();
	
	/**
	 * This creates the smallest last ordering of the list of points in the graph.
	 * This method also stores the degree when deleted for the points, and the order they were deleted in.
	 */
	@SuppressWarnings("unchecked")
	public void createSmallestLastOrdering() {
		Object[] degreeCounts = new Object[maxDegreeCount+1]; // Buckets to hold lists
		
		for(int i = 0; i < maxDegreeCount+1 ; i++) {
			degreeCounts[i] = new ArrayList<Point>();
		}
		
		// Create the buckets
		// Also create the degree distribution
		degreeDistribution = new int[maxDegreeCount+1];
		for(Point p : listOfPoints) {
			p.prepareRemoval();
			degreeDistribution[p.degree]++;
			ArrayList<Point> alp = (ArrayList<Point>) degreeCounts[p.degreeLeft];
			alp.add(p);
		}
		
		// Go through this method the number of times there are vertices in the graph
		for(int i = 0, j = vertexCount-1; j >= 0; i++) {
			// search through the list of buckets and find the first non-empty one
			if( i == maxDegreeCount+1 || i < 0)
				i = 0;
			ArrayList<Point> alp = (ArrayList<Point>)degreeCounts[i];
			if(!alp.isEmpty()) {
				Point p = alp.remove(0); // Get the next point to remove
				for(Point x : p.edgeRemovalList) {
					// Take out the adjacent ones from one bucket, move it down one
					((ArrayList<Point>) degreeCounts[x.degreeLeft]).remove(x);
					// Moving it down one
					((ArrayList<Point>) degreeCounts[x.degreeLeft-1]).add(x);
					x.degreeLeft--; // Subtract the degree
					x.edgeRemovalList.remove(p); // remove the p from the adjacency
				}
				degreeRemovedAt[j] = p.degreeLeft;
				originalDegree[j] = p.degree;
				smallestLastOrder[j] = p.id;
				p.SLOrderPosition = j;
				i = i-2;
				j--;
			}
		}
	}
	
	/**
	 * This colors the vertices using SLO.
	 */
	@SuppressWarnings("unchecked")
	public void createColorClasses() {
		numberOfColors = -1;
		// This goes through all the points in SLO
		for(int i=0; i < vertexCount; i++) {
			Point p = listOfPoints.get(smallestLastOrder[i]);
			
			// Figure out which adjacenct verteces have a color already
			int[] colorSet = new int[p.degree];
			for(Point x : p.adjacencies) {
				if(x.color != -1 && x.color < p.degree) {
					if(colorSet[x.color] == 0)
						p.distinctAdjacentColors++;
					colorSet[x.color]++;
				}
			}
			int color = p.degree;
			// Find the first not used color
			for(int j = 0; j < p.degree; j++)
				if(colorSet[j] == 0) {
					color = j;
					break;
				}
			// Assign it
			p.color = color;
			if(color > numberOfColors) {
				numberOfColors = color;
			}
		}
		numberOfColors++; //increase 1 because colors go form 0 to n
		colorSizes = new int[numberOfColors];
		// Create the size of each color set
		for(Point p : listOfPoints) {
			colorSizes[p.color]++;
		}
		// Create the random colors for them
		classColors = new Color[numberOfColors];
		Random random = new Random();
		float differenceValue = 1.0f/(float) numberOfColors;
		float[] hueColors = new float[numberOfColors];
		for(int i=0; i < numberOfColors; i++) {
			//to get rainbow, pastel colors
			float hue = differenceValue*random.nextInt(numberOfColors);
			float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
			float luminance = 1.0f; //1.0 for brighter, 0.0 for black
			classColors[i] = Color.getHSBColor(hue, saturation, luminance);
			hueColors[i] = hue;
			// If the colors are not that much different...
			for(int j = 0; j < i; j++) {
				if(hueColors[j] == hue) {
					i--;
					break;
				}
			}
		}
		
		colorPointLists = new Object[numberOfColors];
		for(int i = 0; i < numberOfColors; i++) {
			colorPointLists[i] = new ArrayList<Point>();
		}
		for(Point p : listOfPoints) {
			((ArrayList<Point>) colorPointLists[p.color]).add(p);
		}
	}
	
	/**
	 * This prints the results of the SLO and coloring to a file
	 * 
	 * @param file The name of the file to print to.
	 */
	public void printToFileDegreePlots(String file) {
		FileWriter out;
		try {
			out = new FileWriter(file);
			out.write("sep=,\r\n");
			out.write("ID,Original Degree,Degree at Deletion,Distinct Colors Adjacent\r\n");
			for(int i=0; i < vertexCount; i++) {
				out.write(String.format("%d,%d,%d,%d\r\n",smallestLastOrder[i],originalDegree[i],degreeRemovedAt[i],listOfPoints.get(smallestLastOrder[i]).distinctAdjacentColors));
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This prints the size of each color class.
	 * 
	 * @param file The name of the file to print to.
	 */
	public void printToFileColorSize(String file) {
		FileWriter out;
		try {
			out = new FileWriter(file);
			out.write("sep=,\r\n");
			out.write("Color,Size\r\n");
			for(int i=0; i < numberOfColors; i++) {
				out.write(String.format("%d,%d\r\n",i,colorSizes[i]));
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This prints the summary of the RGG in table format.
	 * 
	 * @param file The name of the file to print to.
	 */
	public void printToFileSummaryTable(String file) {
		FileWriter out;
		try {
			out = new FileWriter(file);
			out.write("sep=,\r\n");
			out.write(printToPanel());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This prints the distribution of the points on degrees.
	 * 
	 * @param file The name of the file to print to.
	 */
	public void printToFileDegreeDistribution(String file) {
		FileWriter out;
		try {
			out = new FileWriter(file);
			out.write("sep=,\r\n");
			out.write("Degree,Vertex Count\r\n");
			for(int i=0; i <= maxDegreeCount; i++) {
				out.write(String.format("%d,%d\r\n",i,degreeDistribution[i]));
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tries to create the bipartites according to the first method.
	 * It uses the color classes as the independent sets in the bipartite, and then
	 * cycles through the second, third and fourth to create six possible bipartites.
	 * 
	 * @return true if the method created bipartites, false if otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean createFirstBipartites() {
		// Four largest are always 1, 2, 3, 4...right?
		if(numberOfColors >= 4) {
			vertexCountForBipartiteI = new int[6];
			edgesForBipartiteI = new int[6];
			componentsForBipartiteI = new int[6];
			facesForBipartiteI = new int[6];
			
			// Find the four largest vertices
			fourLargestIndex = new int[]{0,1,2,3};
			int[] copyOf4 = new int[4];
			copyOf4[0] = colorSizes[0];
			copyOf4[1] = colorSizes[1];
			copyOf4[2] = colorSizes[2];
			copyOf4[3] = colorSizes[3];
			for(int i =0; i < 4; i++) {
				int maxIndex = 0;
				for(int j = 1; j < 4; j++) {
					if(copyOf4[j] > copyOf4[maxIndex]){
						maxIndex = j;
					}
				}
				copyOf4[maxIndex] = -1;
				fourLargestIndex[i] = maxIndex;
			}
			
			for(int i = 4; i < colorSizes.length; i++) {
				if(colorSizes[i] > colorSizes[fourLargestIndex[0]]) {
					fourLargestIndex[0] = i;
				}
				else if(colorSizes[i] > colorSizes[fourLargestIndex[1]]) {
					fourLargestIndex[1] = i;
				}
				else if(colorSizes[i] > colorSizes[fourLargestIndex[2]]) {
					fourLargestIndex[2] = i;
				}
				else if(colorSizes[i] > colorSizes[fourLargestIndex[3]]) {
					fourLargestIndex[3] = i;
				}
			}
			
			groupingsForBipartiteI = new String[6];
			
			for(int i = 0; i < 4; i++) { // First independent set
				int edgeTotal = 0;
				for(int j = i+1; j < 4; j++) { // Second independent set
					for(Point p : (ArrayList<Point>) colorPointLists[fourLargestIndex[i]]) { // Points in first
						for(Point x : p.adjacencies) { // A first point's adjacenties
							if(x.color == fourLargestIndex[j]) { // See if there is an edge
								edgeTotal++;
							}
						}
					} // DOne looking through first and comparing to second
					int indexToPlaceIn = 0;
					// Find out which position in the array to place in
					switch(i*4 + j) {
					case 1:
						indexToPlaceIn = 0;
						break;
					case 2:
						indexToPlaceIn = 1;
						break;
					case 3:
						indexToPlaceIn = 2;
						break;
					case 6:
						indexToPlaceIn = 3;
						break;
					case 7:
						indexToPlaceIn = 4;
						break;
					case 11:
						indexToPlaceIn = 5;
						break;
					}
					groupingsForBipartiteI[indexToPlaceIn] = "<"+fourLargestIndex[i]+","+fourLargestIndex[j]+">";
					vertexCountForBipartiteI[indexToPlaceIn] = colorSizes[fourLargestIndex[i]] + colorSizes[fourLargestIndex[j]];
					edgesForBipartiteI[indexToPlaceIn] = edgeTotal;
					// Create the number of components and faces for this graph
					findFacesI( (ArrayList<Point>) colorPointLists[fourLargestIndex[i]], (ArrayList<Point>) colorPointLists[fourLargestIndex[j]], fourLargestIndex[j], indexToPlaceIn, componentsForBipartiteI, facesForBipartiteI );
					edgeTotal = 0;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Tries to create the bipartites according to the second method.
	 * It uses the first color class for the first independent set in the bipartite, and then
	 * creates the second independent set by taking all the vertices that are adjacent to the first
	 * set two, or three or more times. Then, using SLO, the three or more set of vertices is colored again
	 * followed by the two adjacent set.  This first, second and third color is looked to form the second
	 * half of the bipartite.
	 * 
	 * @return true if the method created bipartites, false if otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean createSecondBipartites() {
		if(numberOfColors > 1) {
			// Go through list of points for 1st set. Set it's part2 value to 0 for all in coloring part. Set adjacent ones to increment each time
			// Create list for R2 and R3
			// Create sl ordering by going through R3 first, then R2 (go back to sl, give odering value to each point in Point that is set there).
			// do first color with R3UR2 first color, second, third.
			// Add in colors appropriately.
			
			// Check to make sure there are three colors before doing this part of the experiment
			
			// Figure out how much each point has an edge with first set
			for(Point p : (ArrayList<Point>) colorPointLists[0]) {
				for(Point x : p.adjacencies) {
					x.timesAdjacentToFirst++;
				}
			}
			
			ArrayList<Point> R3 = new ArrayList<Point>();
			ArrayList<Point> R2 = new ArrayList<Point>();
			// Create list R3 and R2 depending on if >=3 edges shared with first set, or =2 edges shared with first set
			for(int i = 1; i < numberOfColors; i++) {
				for(Point p : (ArrayList<Point>) colorPointLists[i]) {
					if(p.timesAdjacentToFirst == 2) {
						R2.add(p);
					}
					else if(p.timesAdjacentToFirst == 3) {
						R3.add(p);
					}
				}
			}
			// Sort the points based on their SL (smallest last) Order
			Collections.sort(R3, new PointCompartor());
			Collections.sort(R2, new PointCompartor());
			// Combine the two into one large list
			ArrayList<Point> R3UR2 = new ArrayList<Point>();
			R3UR2.addAll(R3);
			R3UR2.addAll(R2);
			// Color the large list going through the SL order
			int currentColorCount = -1;
			for(Point p : R3UR2) { // Go through the list and do the coloring
				int[] colorSet = new int[p.degree];
				for(Point x : p.adjacencies) {
					if(x.timesAdjacentToFirst >= 2 && x.R3UR2color != -1 && x.R3UR2color < p.degree) {
						colorSet[x.R3UR2color]++;
					}
				}
				int color = p.degree;
				for(int j = 0; j < p.degree; j++)
					if(colorSet[j] == 0) {
						color = j;
						break;
					}
				p.R3UR2color = color;
				if(color > currentColorCount) {
					currentColorCount = color;
				}
			}
			currentColorCount++;
			// If there are three or more colors made, then try to find the edges, faces and components.
			if(currentColorCount >= 3) {
				vertexCountForBipartiteII = new int[3];
				edgesForBipartiteII = new int[3];
				facesForBipartiteII = new int[3];
				componentsForBipartiteII = new int[3];
				bipartiteIISecondSets = new Object[3];
				
				for(int i=0; i < 3; i ++) {
					edgesForBipartiteII[i] = 0;
					facesForBipartiteII[i] = 0;
					componentsForBipartiteII[i] = 0;
					bipartiteIISecondSets[i] = new ArrayList<Point>();
				}
				
				// Seperate the three sets
				for(Point p : R3UR2) {
					if(p.R3UR2color < 3) {
						edgesForBipartiteII[p.R3UR2color] += p.timesAdjacentToFirst; // add in the edge count
						((ArrayList<Point>) bipartiteIISecondSets[p.R3UR2color]).add(p); // add in the point into the set
					}
				}
				ArrayList<Point> firstSet = (ArrayList<Point>) colorPointLists[0];
				// Go through and find faces and components for each
				for(int i = 0; i < 3; i ++) {
					ArrayList<Point> secondSet = (ArrayList<Point>) bipartiteIISecondSets[i];
					findFacesII(firstSet,secondSet,i,i,componentsForBipartiteII,facesForBipartiteII);
					vertexCountForBipartiteII[i] = firstSet.size() + secondSet.size();
				}
				return true;
			}
			else
				return false;
		}
		else 
			return false;
	}
	
	/**
	 * This method takes two independent sets and tries to find all the components and faces in the resulting graph for the first bipartite method.
	 * The components are created by first assuming each vertex in both sets is a component. Then, for each edge found in the set,
	 * the two vertices' components have to be merged together.  Once each component is created, Euler's formula can be used to
	 * determine how many faces are in the graph.  The faces will include only one "background" face.
	 * 
	 * @param firstSet The first independent set of vertices
	 * @param secondSet The second independent set of vertices
	 * @param secondColor The associated color of the second set for edge finding.
	 * @param arrayIndex The position in the component and face arrays to fill in with the new values
	 * @param componentCount The array of components for each bipartite combination.
	 * @param facesCount The array of faces for each bipartite combination.
	 */
	public void findFacesI(ArrayList<Point> firstSet, ArrayList<Point> secondSet, int secondColor, int arrayIndex, int[] componentCount, int[] facesCount) {
		ArrayList<ArrayList<Point>> componentsList = new ArrayList<ArrayList<Point>>(firstSet.size()+secondSet.size());
		int[] numberOfEdgesPerComponent = new int[firstSet.size()+secondSet.size()];
		
		int id = 0;
		// Make a component of size one, for each point
		for(Point p : firstSet) {
			ArrayList<Point> componentPointList = new ArrayList<Point>();
			componentPointList.add(p);
			componentsList.add(componentPointList);
			numberOfEdgesPerComponent[id] = 0;
			p.componentNumber = id++;
		}
		for(Point p : secondSet) {
			ArrayList<Point> componentPointList = new ArrayList<Point>();
			componentPointList.add(p);
			componentsList.add(componentPointList);
			p.componentNumber = id++;
		}
		
		// Go through all the edges
		for(Point p : firstSet) {
			for(Point x : p.adjacencies) {
				if(x.color == secondColor) {
					// If they're not both in the same component then merge
					if(p.componentNumber != x.componentNumber) {
						int i = p.componentNumber;
						int j = x.componentNumber;
						if(componentsList.get(i).size() < componentsList.get(j).size()) { // swap pointers if needed
							int temp = j;
							j = i;
							i = temp;
						}
						// Join two components into one
						for(Point toJoinTo : componentsList.get(j)) {
							componentsList.get(i).add(toJoinTo);
							toJoinTo.componentNumber = i;
						}
						// Add the edges up from both components
						numberOfEdgesPerComponent[i] += numberOfEdgesPerComponent[j] + 1;
						// Null out the information for the smaller component, not needed anymore.
						numberOfEdgesPerComponent[j] = 0;
						componentsList.set(j, null);
					}
					else {
						numberOfEdgesPerComponent[p.componentNumber]++;
					}
				}
			}
		}
		int numberOfComponents = 0;
		int numberOfFaces = 1; // Face for the whole white space first
		// Go through each component and fine how many face there are minus the background face.
		for(int i = 0; i < componentsList.size(); i++) {
			ArrayList<Point> componentPointList = componentsList.get(i);
			if(componentPointList != null) {
				numberOfComponents++;
				/*
				 * v - e + f = 2
				 * v - e + (1+x) = 2
				 * (1+x) = 2 - v + e
				 * x = 2 - v + e - - 1
				 * f = 2 - v + e
				 * f = 1 - v + e - Have to set to 1, because background already included
				 */
				if(componentPointList.size() > 1) {
				//	 int faceCount = 2 - componentPointList.size() + numberOfEdgesPerComponent[i] - 1;
					 numberOfFaces += 1 - componentPointList.size() + numberOfEdgesPerComponent[i];
				}
			}
		}
		componentCount[arrayIndex] = numberOfComponents;
		facesCount[arrayIndex] = numberOfFaces;
	}
	
	/**
	 * This method takes two independent sets and tries to find all the components and faces in the resulting graph for the second bipartite method.
	 * The components are created by first assuming each vertex in both sets is a component. Then, for each edge found in the set,
	 * the two vertices' components have to be merged together.  Once each component is created, Euler's formula can be used to
	 * determine how many faces are in the graph.  The faces will include only one "background" face.
	 * 
	 * @param firstSet The first independent set of vertices
	 * @param secondSet The second independent set of vertices
	 * @param secondColor The associated color of the second set for edge finding in terms after it was colored a second time.
	 * @param arrayIndex The position in the component and face arrays to fill in with the new values
	 * @param componentCount The array of components for each bipartite combination.
	 * @param facesCount The array of faces for each bipartite combination.
	 */
	public void findFacesII(ArrayList<Point> firstSet, ArrayList<Point> secondSet, int secondColor, int arrayIndex, int[] componentCount, int[] facesCount) {
		// See the findFacesI commentary to see how this was done.
		ArrayList<ArrayList<Point>> componentsList = new ArrayList<ArrayList<Point>>(firstSet.size()+secondSet.size());
		int[] numberOfEdgesPerComponent = new int[firstSet.size()+secondSet.size()];
		
		int id = 0;
		// Make a component of size one, for each point
		for(Point p : firstSet) {
			ArrayList<Point> componentPointList = new ArrayList<Point>();
			componentPointList.add(p);
			componentsList.add(componentPointList);
			numberOfEdgesPerComponent[id] = 0;
			p.componentNumber = id++;
		}
		for(Point p : secondSet) {
			ArrayList<Point> componentPointList = new ArrayList<Point>();
			componentPointList.add(p);
			componentsList.add(componentPointList);
			p.componentNumber = id++;
		}
		
		for(Point p : firstSet) {
			for(Point x : p.adjacencies) {
				//Only difference is in this statement, R3UR2 color was looked at, not the color variable.
				if(x.R3UR2color == secondColor) {
					if(p.componentNumber != x.componentNumber) {
						int i = p.componentNumber;
						int j = x.componentNumber;
						if(componentsList.get(i).size() < componentsList.get(j).size()) { // swap pointers if needed
							int temp = j;
							j = i;
							i = temp;
						}
						// Join two components into one
						for(Point toJoinTo : componentsList.get(j)) {
							componentsList.get(i).add(toJoinTo);
							toJoinTo.componentNumber = i;
						}
						numberOfEdgesPerComponent[i] += numberOfEdgesPerComponent[j] + 1;
						numberOfEdgesPerComponent[j] = 0;
						componentsList.set(j, null);
					}
					else {
						numberOfEdgesPerComponent[p.componentNumber]++;
					}
				}
			}
		}
		int numberOfComponents = 0;
		int numberOfFaces = 1; // Face for the whole white space first
		for(int i = 0; i < componentsList.size(); i++) {
			ArrayList<Point> componentPointList = componentsList.get(i);
			if(componentPointList != null) {
				numberOfComponents++;
				/*
				 * v - e + f = 2
				 * v - e + (1+x) = 2
				 * (1+x) = 2 - v + e
				 * x = 2 - v + e - - 1
				 * f = 2 - v + e
				 * f = 1 - v + e - Have to set to 1, because background already included
				 */
				if(componentPointList.size() > 1) {
					 numberOfFaces += 1 - componentPointList.size() + numberOfEdgesPerComponent[i];
				}
			}
		}
		componentCount[arrayIndex] = numberOfComponents;
		facesCount[arrayIndex] = numberOfFaces;
	}

	/**
	 * Print out the summary table for the graph into a String to be used elsewhere.
	 * 
	 * @return the string for the summary.
	 */
	public String printToPanel() {
		StringBuffer out = new StringBuffer();
		out.append("Attribute,Value\r\n");
		//ID-Number
		out.append( String.format("%s,%s\r\n","ID","#") );
		//N
		out.append( String.format("%s,%d\r\n","N",vertexCount) );
		//R
		out.append( String.format("%s,%.3f\r\n","R",radius) );
		//Distribution Type
		out.append( String.format("%s,%s\r\n","Distribution",distributionType()) );
		
		int sumDegree = 0;
		int maxDegreeWhenDeleted = 0;
		int terminalCliqueSize = 0;
		boolean inClique = true;
		// Find the click by going through the degree when removed list and finding when it stops counting up.
		for(int i = 0; i < vertexCount; i++) {
			Point p = listOfPoints.get(smallestLastOrder[i]);
			sumDegree += p.degree;
			if(degreeRemovedAt[i] > maxDegreeWhenDeleted)
				maxDegreeWhenDeleted = degreeRemovedAt[i];
			if(inClique && degreeRemovedAt[i] == terminalCliqueSize) { //got one more to go
				terminalCliqueSize++;
			}
			else
				inClique = false;
		}
		
		//M number of edges
		out.append( String.format("%s,%d\r\n","M",totalEdges/2) );
		//Min Degree
		out.append( String.format("%s,%d\r\n","Min Degree",minDegreeCount) );
		//Avg Degree
		out.append( String.format("%s,%d\r\n","Avg Degree",sumDegree/vertexCount) );
		//Max Degree
		out.append( String.format("%s,%d\r\n","Max Degree",maxDegreeCount) );
		//Max Degree when Deleted
		out.append( String.format("%s,%d\r\n","Max Degree when deleted",maxDegreeWhenDeleted) );
		//Number of Colors
		out.append( String.format("%s,%d\r\n","Number of Colors",numberOfColors) );
		//Max color size class (size of two largest color classes)
		out.append( String.format("%s,%s\r\n","Color Size of first two", colorSizes[0]+(colorSizes.length>1?colorSizes[1]:0) ) );
		//Terminal clique size
		out.append( String.format("%s,%d\r\n","Terminal clique size",terminalCliqueSize) );
		
		// If no bipartites were made, don't try to print the information
		if(edgesForBipartiteI == null) {
			out.append( String.format("%s,%s\r\n", "ERROR!", "Not enough of classes for first bipartite method."));
		}
		else {
			int indexOfLargest = 0;
			for(int i=0; i < 6; i++) {
				if(edgesForBipartiteI[i] > edgesForBipartiteI[indexOfLargest]  )
					indexOfLargest = i;
			}
			//Number of edges in largest bipartite for first procedure
			out.append( String.format("%s,%d\r\n","Edges for Largest Bipartite in first procedure",edgesForBipartiteI[indexOfLargest]) );
			//Number of components for first
			out.append( String.format("%s,%d\r\n","Components for Largest Bipartite in first procedure",componentsForBipartiteI[indexOfLargest]) );
			//Number of faces for first
			out.append( String.format("%s,%d\r\n","Faces for Largest Bipartite in first procedure",facesForBipartiteI[indexOfLargest]) );
		}
		
		if(edgesForBipartiteII == null) {
			out.append( String.format("%s,%s", "ERROR!", "Not enough of classes for second bipartite method."));
		}
		else {
			int indexOfLargest = 0;
			for(int i=0; i < 3; i++) {
				if(edgesForBipartiteII[i] > edgesForBipartiteII[indexOfLargest]  )
					indexOfLargest = i;
			}
			//Number of edges in largest bipartite for second procedure
			out.append( String.format("%s,%d\r\n","Edges for Largest Bipartite in second procedure",edgesForBipartiteII[indexOfLargest]) );
			//Number of components for second
			out.append( String.format("%s,%d\r\n","Components for Largest Bipartite in second procedure",componentsForBipartiteII[indexOfLargest]) );
			//Number of faces for second
			out.append( String.format("%s,%d","Faces for Largest Bipartite in second procedure",facesForBipartiteII[indexOfLargest]) );
		}
		return out.toString();
	}
}
