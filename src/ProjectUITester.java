import javax.swing.JFrame;


/**
 * This class starts the whole application.
 * 
 * @author Vladimir Jovanovic
 * @version 1.0.0
 * @since December 14, 2012
 */
public class ProjectUITester {
	public static void main(String[] args) {
		int graphWidth = 650;
		int graphHeight= 650;
		ProjectUI 	projectUI = new ProjectUI(graphWidth,graphHeight);
		projectUI.setBounds(0,0,graphWidth,graphHeight+150);
		projectUI.pack();
		projectUI.setResizable( true );
		projectUI.setLocationRelativeTo( null );
		projectUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		projectUI.setVisible(true);
	}

}
