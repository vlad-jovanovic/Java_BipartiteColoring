import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * This JFrame contains the entire application for RandomGeometricGraphs and subsequent algorithms.
 * This provides the interactive capabilities to do most needed things with a graph. Users can create different types
 * of graphs, display different versions of them, and form bipartite graphs. On top of this all, statistics
 * can be stored to a file for any given graph.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 14, 2012
 */
@SuppressWarnings("serial")
public class ProjectUI extends JFrame {
	/**
	 * These two values contain the current height and width of the graph.
	 */
	private int graphWidth, graphHeight;
	
	/**
	 * This contains the area where the graph can be displayed.
	 */
	private GraphDisplay graphDisplay;
	
	/**
	 * This contains the random geometric graph to call functions upon.
	 * It always points to the same RandomGeometricGraph as the graphDisplay's member. 
	 */
	private RandomGeometricGraph rgg;
	
	/**
	 * The options for the different distributions for the RGG.
	 */
	private final String[] graphTypes = {"Choose Type","Square","Disk","Dense Rim Disk","Sphere"};
	/**
	 * The container for the different graph choices
	 */
	private JComboBox graphTypesComboBox;
	
	/**
	 * The standard input is needed each time for the number of vertices.
	 */
	private JTextField fieldNumberOfVertices;
	/**
	 * The standard input is needed each time for the radius.
	 */
	private JTextField fieldRadiusValue;
	
	/**
	 * The button for when the user wants to create a new graph of a particular distribution.
	 */
	private JButton bttnCreateGraph;
	/**
	 * The button for when the user wants to draw a created graph.
	 */
	private JButton bttnDraw;
	
	/**
	 * This is for the two ways to draw a sphere graph.
	 */
	private JButton bttnPosZ, bttnNegZ;
	
	/**
	 * These are all of the buttons in the second panel.
	 */
	private JButton smallestLast,color,printOutput,bipartiteFirst,bipartiteSecond,appendGraphInfo;
	
	
	/**
	 * Checkbox for when edges need to be displayed.
	 */
	private JCheckBox checkEdges;
	/**
	 * Checkbox for when the minimums need to be displayed.
	 */
	private JCheckBox checkMin;
	/**
	 * Checkbox for when the maximums need to be displayed.
	 */
	private JCheckBox checkMax;
	/**
	 * Checkbox for when a beep should be sound when intensive computations are necessary.
	 */
	private JCheckBox beepWhenDone;
	
	/**
	 * There are three message areas, one for each tab.
	 * The first message area is related to drawing/creating the graph.
	 * The second message area is related to the smallest last ordering and associated algorithms.
	 * The third message area is to contain the summary table of the graph.
	 */
	private JTextArea messageTextArea;
	/**
	 * The text area for the second text message.
	 */
	private JTextArea part2TextArea;
	/**
	 * The text area for the third text message.
	 */
	private JTextArea part3TextArea;
	
	/**
	 * This variables are used to customize the graphing.
	 * The pointSizeSlider determines the size of each vertex.
	 * The colorKey shows what the first four class colors are.
	 * The colorChooser allows the user to choose which classes to display one at a time.
	 */
	private JSlider pointSizeSlider;
	/**
	 * Displays an example size of the vectors that will be drawn.
	 */
	private JPanel pointExample;
	/**
	 * The panel to draw the first four colors of the independent sets.
	 */
	private ColorKeyPanel colorKey;
	/**
	 * The combo box that allows the user to choose which set to display.
	 */
	private JComboBox colorChooser;
	
	/**
	 * This tabbed pane and panels are used to form the majority of the user input and output aside from graphing.
	 */
	private JTabbedPane tabbing;
	/**
	 * The first tab.
	 */
	private JComponent part1panel;
	/**
	 * The second tab.
	 */
	private JComponent part2panel;
	/**
	 * The third tab.
	 */
	private JComponent part3panel;
	
	/**
	 * This constructor creates all the components and inserts them into the JFrame with the appropriate
	 * layout managers.
	 * Due to the use of layout managers, it is not possible to guarantee the size of the drawing area.
	 * Thus, it is important to recheck the drawing size to ensure everything looks okay.
	 * 
	 * @param w The width desired for the graphing area.
	 * @param h The height desired for the graphing area.
	 */
	public ProjectUI(int w, int h) {
		super("Wireless Sensor Project");
		
		// Set these variables to w and h, but should be rechecked once pack() is called
		graphWidth = w;
		graphHeight = h;
		
		// part1, part2, and part3 all go into the tabbed pane.
		tabbing = new JTabbedPane();
		part1panel = new JPanel();
		part2panel = new JPanel();
		part3panel = new JPanel();
		
		// Create a border layout (can put in north, east, west, south and center area.
		// Horizontal and vertical gap of 5 pixels between the components in here.
		setLayout(new BorderLayout(5,5));
		
		// Create the graphing area and add it to the center.
		// By putting it in center, the preferred size can be ignored by the layout manager.
		graphDisplay = new GraphDisplay(w,h);
		graphDisplay.setPreferredSize( new Dimension( w, h ) );
		add(graphDisplay, BorderLayout.CENTER);
	
		// This will hold all the inputs in tab 1.
		JPanel featurePane = new JPanel(new BorderLayout());
		
		// The top half and bottom half of tab 1
		JPanel featurePaneTop = new JPanel();
		JPanel featurePaneBottom = new JPanel();
		
		// Do the top row of buttons for the first panel
		// This combo box needs a handler to determine what happens with each choice inside.
		graphTypesComboBox = new JComboBox(graphTypes);
		graphTypesComboBox.setEnabled(true);
		graphTypesComboBox.addActionListener(new GraphTypeComboBoxHandler());
		featurePaneTop.add(graphTypesComboBox);
		
		JLabel lblNumberOfVertices = new JLabel("N:");
		featurePaneTop.add(lblNumberOfVertices);
		
		fieldNumberOfVertices = new JTextField();
		fieldNumberOfVertices.setColumns(5);
		fieldNumberOfVertices.setEnabled(false);
		featurePaneTop.add(fieldNumberOfVertices);
		
		JLabel lblRadiusValue = new JLabel("R:");
		featurePaneTop.add(lblRadiusValue);
		
		fieldRadiusValue = new JTextField();
		fieldRadiusValue.setColumns(5);
		fieldRadiusValue.setEnabled(false);
		featurePaneTop.add(fieldRadiusValue);
		
		// This button will be able to create a new rgg graph if appropriate parameters are given
		bttnCreateGraph = new JButton("Create Graph");
		bttnCreateGraph.setEnabled(false);
		bttnCreateGraph.addActionListener(new BttnCreateGraphHandler());
		featurePaneTop.add(bttnCreateGraph);
		
		// This option was created when the calculations take too long.
		beepWhenDone = new JCheckBox("Beep when done",false);
		beepWhenDone.setEnabled(true);
		featurePaneTop.add(beepWhenDone);
		
		// Do the bottom row of buttons
		checkEdges = new JCheckBox("Draw Edges", false);
		checkEdges.setEnabled(false);
		featurePaneBottom.add(checkEdges);
		
		checkMin = new JCheckBox("Draw Min Vertices",false);
		checkMin.setEnabled(false);
		featurePaneBottom.add(checkMin);
		
		checkMax = new JCheckBox("Draw Max Vertices",false);
		checkMax.setEnabled(false);
		featurePaneBottom.add(checkMax);
		
		// This calls the repaint function in teh graph drawing panel.
		bttnDraw = new JButton("Draw Graph");
		bttnDraw.setEnabled(false);
		bttnDraw.addActionListener(new BttnDrawHandler());
		featurePaneBottom.add(bttnDraw);
		
		// Prints only the positive hemisphere for the sphere distribution
		bttnPosZ = new JButton("+Z");
		bttnPosZ.setEnabled(false);
		bttnPosZ.addActionListener(new BttnPosZHandler());
		featurePaneBottom.add(bttnPosZ);
		
		// Prints only the negative hemisphere
		bttnNegZ = new JButton("-Z");
		bttnNegZ.setEnabled(false);
		bttnNegZ.addActionListener(new BttnNegZHandler());
		featurePaneBottom.add(bttnNegZ);
		
		// Add the top row and bottom row together		
		featurePane.add(featurePaneTop, BorderLayout.NORTH);
		featurePane.add(featurePaneBottom, BorderLayout.CENTER);
		
		// Add in a message area at the bottom after the 2 interaciton rows
		messageTextArea = new JTextArea("Please choose type of graph first.");
		messageTextArea.setAutoscrolls(true);
		messageTextArea.setMargin(new Insets(2, 2, 2, 2));
		messageTextArea.setLineWrap(true);
		messageTextArea.setWrapStyleWord(true);
		messageTextArea.setEditable(false);
		
		// A scroll pane is needed to allow the text to be scrollable.
		JScrollPane scrollPanePanelI = new JScrollPane(messageTextArea);
		scrollPanePanelI.setPreferredSize( new Dimension( w, 40) );
		scrollPanePanelI.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		featurePane.add(scrollPanePanelI,BorderLayout.SOUTH);
		
		part1panel.add(featurePane);
		
		// This creates the four components that go to the right of the graph.
		JPanel featurePaneRight = new JPanel();
		featurePaneRight.setLayout(new BoxLayout(featurePaneRight,BoxLayout.Y_AXIS));
		
		// Add the slider and drawing to another pane
		pointSizeSlider = new JSlider(SwingConstants.VERTICAL, 0, 25, 12);
		pointSizeSlider.setPreferredSize(new Dimension(10,100));
		pointSizeSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
		pointSizeSlider.setEnabled(false);
		pointSizeSlider.addChangeListener(new PointSizeSliderHandler());
		featurePaneRight.add(pointSizeSlider);
		
		graphDisplay.setPointRadius(pointSizeSlider.getValue());
		
		// This is the dynamic example of the size of the points to be drawn on the graph
		pointExample = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawOval(25-pointSizeSlider.getValue(), 25-pointSizeSlider.getValue(), pointSizeSlider.getValue()*2, pointSizeSlider.getValue()*2);
			}
		};
		pointExample.setPreferredSize(new Dimension(51,51));
		pointExample.setAlignmentX(Component.CENTER_ALIGNMENT);
		featurePaneRight.add(pointExample);
		
		colorKey = new ColorKeyPanel();
		colorKey.setVisible(false);
		colorKey.setAlignmentX(Component.CENTER_ALIGNMENT);
		featurePaneRight.add(colorKey);
		
		colorChooser = new JComboBox();
		colorChooser.setVisible(false);
		colorChooser.setEnabled(false);
		colorChooser.setPreferredSize(new Dimension(15,15));
		colorChooser.setAlignmentX(Component.CENTER_ALIGNMENT);
		colorChooser.addActionListener(new ColorChooserHandler());
		featurePaneRight.add(colorChooser);
		
		// Add that new section to the right of the picture
		add(featurePaneRight,BorderLayout.EAST);
		
		// Add teh first panel to the tabbing section
		tabbing.add("Part I",part1panel);
		
		// Now to do Part II stuff
		JPanel featurePane2 = new JPanel(new BorderLayout());
		JPanel featurePane2Top = new JPanel();
		JPanel featurePane2Bottom = new JPanel();
		
		// This next part creates all the buttons for the second panel in the tabbed section
		// This just calls the smallest last ordering on the graph.
		smallestLast = new JButton("Smallest Last Ordering");
		smallestLast.setEnabled(false);
		smallestLast.addActionListener(new SmallestLastHandler());
		featurePane2Top.add(smallestLast);
		
		// Creates the full graph with all the vertices colored.
		color = new JButton("Color Graph");
		color.setEnabled(false);
		color.addActionListener(new ColorHandler());
		featurePane2Top.add(color);
		
		printOutput = new JButton("Create Output");
		printOutput.addActionListener(new PrintOutputHandler());
		printOutput.setEnabled(false);
		featurePane2Top.add(printOutput);
		
		// Bipartite method one
		bipartiteFirst = new JButton("Draw bipartites from first method.");
		bipartiteFirst.addActionListener(new BipartiteFirstHandler());
		bipartiteFirst.setEnabled(false);
		featurePane2Bottom.add(bipartiteFirst);
		
		// Bipartite method two
		bipartiteSecond = new JButton("Draw bipartites from second method.");
		bipartiteSecond.setEnabled(false);
		bipartiteSecond.addActionListener(new BipartiteSecondHandler());
		featurePane2Bottom.add(bipartiteSecond);
		
		// Button to display the information in the third panel.
		appendGraphInfo = new JButton("Print to Summary Tab");
		appendGraphInfo.setEnabled(false);
		appendGraphInfo.addActionListener(new AppendGraphInfoHandler());
		featurePane2Bottom.add(appendGraphInfo);
		
		featurePane2.add(featurePane2Top,BorderLayout.NORTH);
		featurePane2.add(featurePane2Bottom,BorderLayout.CENTER);
		
		// The message area for the second panel.
		part2TextArea = new JTextArea("Please choose type of graph first.");
		part2TextArea.setMargin(new Insets(2,2,2,2));
		part2TextArea.setAutoscrolls(true);
		part2TextArea.setLineWrap(true);
		part2TextArea.setWrapStyleWord(true);
		part2TextArea.setEditable(false);
		
		JScrollPane scrollPanePanelII = new JScrollPane(part2TextArea);
		scrollPanePanelII.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanePanelII.setPreferredSize( new Dimension( w, 40) );
		
		featurePane2.add(scrollPanePanelII,BorderLayout.SOUTH);
		
		// Adding the second completed panel to the tabbed sections.
		part2panel.add(featurePane2);
		tabbing.add("Part II",part2panel);
		
		// The third section only needs the text area and scroll pane.
		part3TextArea = new JTextArea("Please create a graph first.",6,61);
		part3TextArea.setMargin(new Insets(2,2,2,2));
		part3TextArea.setAutoscrolls(true);
		part3TextArea.setLineWrap(true);
		part3TextArea.setWrapStyleWord(true);
		part3TextArea.setEditable(false);
		
		JScrollPane scrollPanePanel3 = new JScrollPane(part3TextArea);
		scrollPanePanel3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		// Setting the layout with 12 pixel gap between horizontal and vertical spaces.
		part3panel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 12));
		part3panel.add(scrollPanePanel3);
		tabbing.add("Summary",part3panel);
		
		tabbing.setPreferredSize(new Dimension(graphWidth, 150));
		
		// Everything has been added to tabbing, it can finally be added to the JFrame
		add(tabbing, BorderLayout.SOUTH);
	}
	
	/**
	 * Inner private class to handle when somethign is chosen in the combo box of distribution types.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 14, 2012
	 */
	private class GraphTypeComboBoxHandler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Make sure that the graph display area is properly sized.
			graphWidth = graphDisplay.getSize().width;
			graphHeight = graphDisplay.getSize().height;
			graphDisplay.setDimension(graphWidth, graphHeight);
			
			// Reset the RGG value for the JFrame and graphDisplay.
			rgg = null;
			graphDisplay.paintThis(GraphDisplay.DrawMethod.CLEAR_SCREEN, 0);
			graphDisplay.setGraph(null);
			
			// All drawing capabilities should be taken out.
			bttnDraw.setEnabled(false);
			bttnPosZ.setEnabled(false);
			bttnNegZ.setEnabled(false);
			
			// Should not be able to change these options.
			checkEdges.setEnabled(false);
			checkMin.setEnabled(false);
			checkMax.setEnabled(false);	
			
			// This is for the second graph, everything should be reset
			smallestLast.setEnabled(false);
			color.setEnabled(false);
			printOutput.setEnabled(false);
			// The bipartite methods need to be reset to display the standard information
			bipartiteFirst.setEnabled(false);
			((BipartiteFirstHandler)bipartiteFirst.getActionListeners()[0]).resetButton();
			bipartiteSecond.setEnabled(false);
			((BipartiteSecondHandler)bipartiteSecond.getActionListeners()[0]).resetButton();
			appendGraphInfo.setEnabled(false);
			
			// The components on the right of the graph need to become invisible or not be enabled
			pointSizeSlider.setEnabled(false);
			colorKey.setVisible(false);
			colorChooser.setVisible(false);
			colorChooser.setEnabled(false);
			
			// This switch statement determines what to do depending on what the user selected
			switch(graphTypesComboBox.getSelectedIndex()) {
			case 0: // Default clear everything and go back to the beginning. No input is allowed until another option is chosen
				messageTextArea.setText("Clearing the previous graph");
				fieldNumberOfVertices.setEnabled(false);
				fieldRadiusValue.setEnabled(false);
				
				bttnCreateGraph.setEnabled(false);
				break;
			case 1: // Square chosen
				rgg = new RGGUnitSquare(graphWidth,graphHeight);
				break;
			case 2: // Disk chosen
				rgg = new RGGUnitCircle(graphWidth,graphHeight);
				break;
			case 3: // Dense Disk chosen
				rgg = new RGGDenseDisk(graphWidth,graphHeight);
				break;
			case 4: // Sphere chosen
				rgg = new RGGSphere(graphWidth,graphHeight);
				break;
			}
			
			// If not the first option, one of the distributions is chosen, allow the user to input information.
			if(graphTypesComboBox.getSelectedIndex() != 0) {
				fieldNumberOfVertices.setEnabled(true);
				fieldRadiusValue.setEnabled(true);
				
				bttnCreateGraph.setEnabled(true);
				
				messageTextArea.setText("Set the N and R values, then press the \"Create Graph\" button.");
			}
			System.gc();
		}
		
	}
	
	/**
	 * This inner class handles the situation of the create graph button being pressed.
	 * If all the parameters are correct for N and R, then a new graph should be created.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 14, 2012
	 */
	private class BttnCreateGraphHandler implements ActionListener {
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// The message to be printed at the bottom after this button is pressed.
			String messageToPrint = "";
			
			// Check to see if the number of vertices to create is a number input.
			String stringNumberOfVertices = fieldNumberOfVertices.getText();
			int numberOfVertices = 0;
			boolean invalidNumberOfVertices = false;
			try {
				numberOfVertices = Integer.parseInt(stringNumberOfVertices);
			}
			catch (NumberFormatException nfe) {
				messageToPrint += "Invalid N value: " + stringNumberOfVertices + "\r\n";
				invalidNumberOfVertices = true;
			}
			
			// Check to see if the radius for the RGG is a number.
			String stringRadiusValue = fieldRadiusValue.getText();
			double radius = 0.0;
			boolean invalidRadiusValue = false;
			try {
				radius = Double.parseDouble(stringRadiusValue);
			}
			catch (NumberFormatException nfe) {
				messageToPrint += "Invalid R value: " + stringRadiusValue + "\r\n";
				invalidRadiusValue = true;
			}
			
			// If both are valid numbers, then determine if in the right range.
			if(!invalidNumberOfVertices && !invalidRadiusValue) {
				// If at least one vertex, and radius is [0,1] or [0,2] for the sphere
				if( numberOfVertices >= 1 && radius >= 0 && (radius <= 1 || (graphTypesComboBox.getSelectedIndex()==4 && radius <= 2)) ) {
					if(graphTypesComboBox.getSelectedIndex()==4) { // Chose the sphere
						pointSizeSlider.setValue(0);
						
						bttnPosZ.setEnabled(true);
						bttnNegZ.setEnabled(true);
						((RGGSphere) rgg).createPoints(numberOfVertices, radius);
						messageToPrint = "Finished placing points on graph.\r\nCheck options and then press \"+Z\" or \"-Z\""+
								" +Z is for positive hemisphere, -Z is for negative hemisphere.";
					}
					else {
						// Change the value of the pointer size to something more reasonable
						if((int)Math.log(numberOfVertices) == 0)
							pointSizeSlider.setValue(25);
						else
							pointSizeSlider.setValue(25/(int)Math.log(numberOfVertices));
						
						// Allow the draw functions ot be available
						checkEdges.setEnabled(true);
						checkMin.setEnabled(true);
						checkMax.setEnabled(true);
						bttnDraw.setEnabled(true);
						
						// Create the new RGG
						rgg.createPoints(numberOfVertices,radius);
						messageToPrint = "Finished placing points on graph.\r\nCheck options and then press \"Draw Graph\"";
					}
					// Part II section resets
					smallestLast.setEnabled(true);
					color.setEnabled(false);
					printOutput.setEnabled(false);
					
					// Same as before, need to make the buttons say something else.
					bipartiteFirst.setEnabled(false);
					((BipartiteFirstHandler)bipartiteFirst.getActionListeners()[0]).resetButton();
					bipartiteSecond.setEnabled(false);
					((BipartiteSecondHandler)bipartiteSecond.getActionListeners()[0]).resetButton();
					
					appendGraphInfo.setEnabled(false);
					
					pointSizeSlider.setEnabled(true);
					graphDisplay.setGraph(rgg);
					graphDisplay.paintThis(GraphDisplay.DrawMethod.CLEAR_SCREEN, 0);
					part3TextArea.setText("");
					part2TextArea.setText("");
					
					if(beepWhenDone.isSelected())
						Toolkit.getDefaultToolkit().beep();
				}
				else { // If either value is not appropriate
					if(numberOfVertices < 1) {
						messageToPrint += "N has to be 1 or more.\r\n";
					}
					if(radius < 0) {
						messageToPrint += "R has to be 0 or greater.\r\n";
					}
					else {
						messageToPrint += "R has to be less than 1.\r\n";
					}
				}
			}
			
			messageTextArea.setText(messageToPrint);
			System.gc();
		}
	}
	
	/**
	 * This inner class handles the situation of drawing the current graph without
	 * any of the things related to smallest last ordering like color.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 14, 2012
	 */
	private class BttnDrawHandler implements ActionListener {
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int option = 0;
			// Option is created by holding the bit choices in an integer
			if(checkEdges.isSelected()) 
				option += 1; // if 1, paint edges
			
			option += 2; // Paint the points
			
			if(checkMin.isSelected()) {
				option += 4; // Paint the min
			}
			if(checkMax.isSelected()) {
				option += 8; // Pain the max
			}
			graphDisplay.paintThis(GraphDisplay.DrawMethod.PAINT_POINTS,option);
			messageTextArea.setText("Finished drawing graph.\nEdges: "+
					rgg.getTotalEdges()/2+", Min Degree: "+rgg.getMinDegreeCount()+", Max Degree: "+rgg.getMaxDegreeCount());
		}
	}
	
	// When the slider changes value
	/**
	 * This inner class handles the situation when the slider on the right is changed.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 14, 2012
	 */
	private class PointSizeSliderHandler implements ChangeListener {
		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		@Override
		public void stateChanged(ChangeEvent arg0) {
			// Repaint the point according to the new value.
			pointExample.repaint();
			graphDisplay.setPointRadius(pointSizeSlider.getValue());
		}
	}

	/**
	 * This inner class handles the situation when the user wants to show the positive hemisphere.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 14, 2012
	 */
	private class BttnPosZHandler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			graphDisplay.paintThis(GraphDisplay.DrawMethod.PAINT_POSZ, 0 );
			messageTextArea.setText("Finished drawing graph.\nEdges: "+
					rgg.getTotalEdges()/2+", Min Degree: "+rgg.getMinDegreeCount()+", Max Degree: "+rgg.getMaxDegreeCount());
		}
		
	}
	
	/**
	 * This inner class handles the situation when the user wants to show the negative hemisphere.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 14, 2012
	 */
	private class BttnNegZHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			graphDisplay.paintThis(GraphDisplay.DrawMethod.PAINT_NEGZ, 0);
			messageTextArea.setText("Finished drawing graph.\nEdges: "+
					rgg.getTotalEdges()/2+", Min Degree: "+rgg.getMinDegreeCount()+", Max Degree: "+rgg.getMaxDegreeCount());
		}
		
	}

	/**
	 * This inner class handles the situation when the user wants create the samllest last order.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 14, 2012
	 */
	private class SmallestLastHandler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Create the smallest last order and then allow the user to see the colors made from it
			rgg.createSmallestLastOrdering();
			color.setEnabled(true);
			smallestLast.setEnabled(false);
			part2TextArea.setText("Created smallest last ordering.\r\n");
			
			// Create the color classes here as well to prevent unnecessary processing in other areas.
			rgg.createColorClasses();
			
			// Create the list of options for the color selector
			int maxColors = rgg.getNumberOfColors();
			String[] listOfColorOptions = new String[maxColors+1];
			for(int i=0; i < maxColors; i++) {
				listOfColorOptions[i] = ""+i; 
			}
			listOfColorOptions[maxColors] = "All";
			colorChooser.setModel(new JComboBox(listOfColorOptions).getModel());
			
			if(beepWhenDone.isSelected())
				Toolkit.getDefaultToolkit().beep();
			System.gc();
		}
		
	}
	
	/**
	 * This inner class handles the situation when the user wants to show the vertices with their colors.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 14, 2012
	 */
	private class ColorHandler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Allow everything to be seen for output
			printOutput.setEnabled(true);
			part2TextArea.setText("Created color classes with max color of "+rgg.getNumberOfColors());
			
			colorKey.setColors(rgg.getClassColors());
			colorKey.setVisible(true);
			colorKey.repaint();
			
			colorChooser.setVisible(true);
			colorChooser.setEnabled(true);
			
			colorChooser.setSelectedIndex(rgg.getNumberOfColors());
			
			// If haven't already done this method... then try to do bipartites.
			if(!appendGraphInfo.isEnabled()) {
				// Try to do the second bipartite method
				if( rgg.createSecondBipartites())
					bipartiteSecond.setEnabled(true);
				else
					part2TextArea.setText("WARNING: Cannot do second bipartite algorithm, not enough classes\r\n"+part2TextArea.getText());
				
				// Try to do the first bipartite method if haven't already done this method
				if( rgg.createFirstBipartites())
					bipartiteFirst.setEnabled(true);
				else
					part2TextArea.setText("WARNING: Cannot do first bipartite algorithm, not enough classes\r\n"+part2TextArea.getText());
			}
			
			appendGraphInfo.setEnabled(true);
			
			if(beepWhenDone.isSelected())
				Toolkit.getDefaultToolkit().beep();
			System.gc();
		}
	}
	
	/**
	 * This inner class handles the situation when the user wants to print to file relevant graph information.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 15, 2012
	 */
	private class PrintOutputHandler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int response = JOptionPane.showConfirmDialog(null,"Do you want to individually name each file?","File Printing",JOptionPane.YES_NO_CANCEL_OPTION);
			String fileName;
			switch(response) {
			case 0: // said yes
				// First ask where to print the degree plot
				fileName = JOptionPane.showInputDialog("Enter file location to output degree plots to.\nClick cancel for default testDegreePlot.csv");
				if(fileName == null || fileName.isEmpty())
					fileName = "testDegreePlots.csv";
				rgg.printToFileDegreePlots(fileName);
				
				// Second ask where to print the color sizes
				fileName = JOptionPane.showInputDialog("Enter file location to output color size plots to.\nClick cancel for default testColorSize.csv");
				if(fileName == null || fileName.isEmpty())
					fileName = "testColorSize.csv";
				rgg.printToFileColorSize(fileName);
				
				// Third ask where to print the degree distributions
				fileName = JOptionPane.showInputDialog("Enter file location to output degree distribution to.\nClick cancel for default testDegreeDistribution.csv");
				if(fileName == null || fileName.isEmpty())
					fileName = "testDegreeDistribution.csv";
				rgg.printToFileDegreeDistribution(fileName);
				
				// Fourth ask where to print the summary table
				fileName = JOptionPane.showInputDialog("Enter file location to output summary table.\nClick cancel for default testSummaryTable.csv");
				if(fileName == null || fileName.isEmpty())
					fileName = "testSummaryTable.csv";
				rgg.printToFileSummaryTable(fileName);
				break;
			case 1: // said no
				fileName = JOptionPane.showInputDialog("Enter file header.\nClick cancel for default test");
				if(fileName == null || fileName.isEmpty())
					fileName = "test";
				rgg.printToFileDegreePlots(fileName+"DegreePlot.csv");
				rgg.printToFileColorSize(fileName+"ColorSize.csv");
				rgg.printToFileDegreeDistribution(fileName+"DegreeDistribution.csv");
				rgg.printToFileSummaryTable(fileName+"SummaryTable.csv");
				break;
			default:
				part2TextArea.setText("Cancelled file output.\r\n"+part2TextArea.getText());
			}
		}
		
	}
	
	/**
	 * This inner class handles the situation when the user wants to show the results of the first bipartite method.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 15, 2012
	 */
	private class BipartiteFirstHandler implements ActionListener {
		/**
		 * This keeps track of how many times the button was clicked
		 */
		public int buttonCount = -1;
		/**
		 * The bipartites that can be displayed list
		 */
		String[] groupings;
		
		/**
		 * This resets the button list when the second tab needs to be cleared
		 */
		public void resetButton() {
			buttonCount = -1;
			bipartiteFirst.setText("Draw bipartites from first method.");
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Loop through the six options
			if(buttonCount > 4)
				buttonCount = 0;
			else
				buttonCount++;
			
			groupings = rgg.groupingsForBipartiteI;
			
			graphDisplay.paintThis(GraphDisplay.DrawMethod.DRAW_BIPARTITEI, buttonCount);
			
			//This play on the button the next bipartite to be displayed in the list
			part2TextArea.setText("The bipartite subgraph " + groupings[buttonCount] + " graph has:\r\n" +
					"Vertices: " + rgg.getVertexCountForBipartiteI()[buttonCount] +
					" Edges: " + rgg.getEdgesForBipartiteI()[buttonCount] +
					" Components: " + rgg.getComponentsForBipartiteI()[buttonCount] +
							" Faces: " + rgg.getFacesForBipartiteI()[buttonCount]);
			bipartiteFirst.setText("Draw bipartite of: " + (buttonCount==5?groupings[0]:groupings[buttonCount+1]) );  
		}
	}
	
	/**
	 * This inner class handles the situation when the user wants to show the results of the second bipartite method.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 15, 2012
	 */
	private class BipartiteSecondHandler implements ActionListener {
		/**
		 * This keeps track of how many times the button was clicked
		 */
		public int buttonCount = -1;
		/**
		 * The bipartites that can be displayed list
		 */
		String[] groupings = {"<0,R3UR2.0>","<0,R3UR2.1>","<0,R3UR2.2>"};
		
		/**
		 * This resets the button list when the second tab needs to be cleared
		 */
		public void resetButton() {
			buttonCount = -1;
			bipartiteFirst.setText("Draw bipartites from second method.");
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Loop through the three options
			if(buttonCount > 1)
				buttonCount = 0;
			else
				buttonCount++;
			
			graphDisplay.paintThis(GraphDisplay.DrawMethod.DRAW_BIPARTITEII, buttonCount);
			
			//This play on the button the next bipartite to be displayed in the list
			part2TextArea.setText("The bipartite subgraph " + groupings[buttonCount] + " graph has:\r\n" +
					"Vertices: " + rgg.getVertexCountForBipartiteII()[buttonCount] +
					" Edges: " + rgg.getEdgesForBipartiteII()[buttonCount] +
					" Components: " + rgg.getComponentsForBipartiteII()[buttonCount] +
							" Faces: " + rgg.getFacesForBipartiteII()[buttonCount]);
			bipartiteSecond.setText("Draw bipartite of: " + (buttonCount==2?groupings[0]:groupings[buttonCount+1]) );
		}
		
	}
	
	/**
	 * This inner class handles the situation when the user wants to show only one of the independent set colors.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 15, 2012
	 */
	private class ColorChooserHandler implements ActionListener {
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			graphDisplay.paintThis(GraphDisplay.DrawMethod.COLOR_SET, colorChooser.getSelectedIndex());
		}
	}
	
	/**
	 * This inner class handles the situation when the user wants to show summary of the graph in the third panel.
	 * 
	 * @author Vladimir Jovanovic
	 * @version 1.0.0
	 * @since December 15, 2012
	 */
	private class AppendGraphInfoHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			part3TextArea.setText(rgg.printToPanel());
			tabbing.setSelectedIndex(2);
		}
	}
}
