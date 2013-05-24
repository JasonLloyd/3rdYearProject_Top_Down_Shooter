import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Menu
{
	
	 static JFrame frame = new JFrame(); // create a new jframe
	
	private final int HEIGHT = 300; // sets the height
	private final int WIDTH = 400; // sets the width
    
    private JPanel panel1 = new JPanel(); // new jpanel
    private JPanel panel2 = new JPanel(); // new jpanel
    private JButton start = new JButton("Start Game"); //  create a start button
    private JButton highscore = new JButton("View Highscores"); //  create a Highscores button
    private JButton achievements = new JButton("View Achievements"); //  create a Achievements button
    private JButton exit = new JButton("Exit"); //  create a exit button
    private JLabel title = new JLabel("Top down shooter");  //  title
    
	public Menu()
	{

	    frame.setSize(WIDTH, HEIGHT); // sets the size of the frame
	    frame.setLocationRelativeTo(null); // sets the location
	    frame.setTitle("Top Down Shooter Prototype"); // set the title of the  of the jframe
	    frame.setResizable(false); // resizeable to false
	    frame.setVisible(true); // sets it visible
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets default close operation
	    frame.setLayout(new BorderLayout()); // sets the layout from the frame
	     panel1.setLayout(new GridLayout(4,1)); // sets the layout of the panel
	     panel2.setLayout(new BorderLayout()); // set the layout of the panel
	     frame.add(panel1,BorderLayout.SOUTH); // places the panel on the frame
	     frame.add(panel2,BorderLayout.CENTER); // places the panel on the frame
	     
	     title.setFont(new Font("Times",Font.BOLD,45));
	     panel2.add(new JLabel("sssss"),BorderLayout.CENTER);
	     panel2.add(title); // places the title
	     
	     panel1.add(start); // places the start button
	     
	     panel1.add(highscore); // places the high score button
	     
	     panel1.add(achievements); //places the achivement button
	     
	     panel1.add(exit); // place the exist button
	     
	     
	     start.addActionListener(new theHandler()); // add an action listener to the start button
	     highscore.addActionListener(new theHandler()); // add an listener to the highscore button
	     achievements.addActionListener(new theHandler()); // add an listener to the achivements button
	     exit.addActionListener(new theHandler());  // add an listener to the ecit button
	     
	     
	}
	
	
	
	private class theHandler implements ActionListener
	{

		@Override
		//if there is an actionpreformed
		public void actionPerformed(ActionEvent e) 
		{
			JButton pressed = (JButton) e.getSource(); // cast the jbutton
			
			if(pressed.equals(start)) // if pressed button is start
			{
				StartGameFrame x = new StartGameFrame(); // create a new start
				frame.dispose(); // dispose the frame
			}
			
			if(pressed.equals(highscore)) // if pressed button is highscore
			{
				Highscore h1 = new Highscore("",0);  //create a highscore
				h1.initDisplay();
			}
			
			if(pressed.equals(achievements)) // if pressed button is achievements
			{
				Achievement ach = new Achievement(); //create a achievements
				ach.initDisplay(); // display the achievement screen
			}
			
			if(pressed.equals(exit)) // if pressed button is exit
			{
				Board.enemySpawnedAmount = 1; // sets the amount of enemies spawned at the start
				Board.totalEnemiesKilled = 0; // sets the amount of enemies currently killed
				Board.currentLevel = 1;  // sets the current level
				System.exit(0); // exit the system
			}
		}
		
	}
	
	public static void main(String args[])
	{
		Menu x = new Menu(); // create a new menu
	}
}
