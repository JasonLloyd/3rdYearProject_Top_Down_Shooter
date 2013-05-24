import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Achievement implements Common
{
	JFrame frame = new JFrame(); // creates a new jframe
	// creates check boxes for the achievements
	JCheckBox achievementCheck[] = {new JCheckBox(),new JCheckBox(),new JCheckBox(), new JCheckBox(),new JCheckBox(),new JCheckBox(),new JCheckBox(), new JCheckBox(),new JCheckBox(), new JCheckBox()};
	
	//default constuctor
	public Achievement()
	{
		
	}
	
	//displays the gui
	public void initDisplay()
	{
		JPanel p1 = new JPanel(); // new panel to put components on
		JPanel p2 = new JPanel(); // new panel to put components on
		
		JLabel rank = new JLabel("Rank"); // rank jlabel
		JLabel title = new JLabel("Achievements"); // title of the screen
		JLabel achievementTitle = new JLabel("Achivement Title");
		
		Font scoreFont = new Font("GAMECUBEN",Font.PLAIN,15); // get the font of for our game
		
		//our enemy killing achievements
		String enemytitles[] = {"Kill 5 Enemies","Kill 10 Enemies","Kill 15 Enemies","Kill 20 Enemies","Kill 25 Enemies"};
		//our level completion achievements
		String levelTitles[] = {"Complete level 1","Complete level 2","Complete level 3","Complete level 4","Complete level 5"};
		
		 title.setFont(new Font("GAMECUBEN",Font.PLAIN,30)); // sets the font of the game
		 rank.setFont(scoreFont); // sets the rank font
		 achievementTitle.setFont(scoreFont); // set the achievement title font
		 
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the close operation
	     frame.setSize(BOARD_WIDTH+15, BOARD_HEIGHT+38); // sets the size of the frame
	     frame.setLocationRelativeTo(null); // set it relativity
	     frame.setTitle(title.getName()); // sets the title
	     frame.setResizable(false); //set the resizeability 
	     frame.setVisible(true); // displays it
	     frame.setLayout(new BorderLayout()); // sets the frames layout
	     
	     p1.setLayout(new GridBagLayout()); // sets the panel layout
	     
	     frame.add(p1,BorderLayout.CENTER); // add the panel to the centre of the frame
	     frame.add(p2,BorderLayout.NORTH); // add the panel to the top of the frame
	     
	     GridBagConstraints c = new GridBagConstraints(); // new constraints so we can work with gridbaglayout
	     
	     c.fill = GridBagConstraints.HORIZONTAL; // sets the fill property
	        
	     p2.add(title,BorderLayout.CENTER); // add the title to the screen
	     
	 	c.gridx = 0; // sets the X position
		c.gridy = 0; // set the Y position
		p1.add(rank,c); // add the rank jlabel to the screen
		
	    for(int i = 0;i<enemytitles.length;i++)
		{
			c.gridx = 1; // sets the X position
			c.gridy = i + 10; // set the Y position
			c.insets = new Insets(20,0,0,0); // sets the insets/spacing between the elements
			JLabel enemyAchievements = new JLabel("" + enemytitles[i]); // creates new jlabel for the enemytitles
			enemyAchievements.setFont(scoreFont); // sets the font
			p1.add(enemyAchievements,c); // add the label
			
			c.gridx = 0; // sets the X position
			c.gridy = i + 10; // set the Y position
			c.insets = new Insets(20,0,0,40); // sets the insets/spacing between the elements
			JLabel numbers = new JLabel("" + (i+1));  // creates new jlabel for the numbers
			numbers.setFont(scoreFont); // sets the font
			p1.add(numbers,c); // add the label
			
			c.gridx = 2; // sets the X position
			c.gridy = i + 10; // set the Y position
			c.insets = new Insets(20,20,0,0); // sets the insets/spacing between the elements
			p1.add(achievementCheck[i],c); // add the label
		}
		
	    int startingNumber = enemytitles.length + 1; // staring number for the next set of achievments
	    
	    c.gridx = 1; // sets the X position
		c.gridy = 0; // set the Y position
		c.insets = new Insets(0,0,0,0); // sets the insets/spacing between the elements
		p1.add(achievementTitle,c); // add to the panel
		JLabel levelAchievements; // new jlabel
		
		for(int i = 0;i<levelTitles.length;i++)
		{
			c.gridx = 1;  // sets the X position
			c.gridy = (i+20);  // set the Y position
			c.insets = new Insets(20,0,0,0); // sets the insets/spacing between the elements
		    levelAchievements = new JLabel("" + levelTitles[i]); // new jlabel
			levelAchievements.setFont(scoreFont); //sets the font
			p1.add(levelAchievements,c); // add to the panel
			
			c.gridx = 0;  // sets the X position
			c.gridy = i + 20; // set the Y position
			c.insets = new Insets(20,0,0,40); // sets the insets/spacing between the elements
			JLabel numbers = new JLabel("" + startingNumber); // new jlabel
			numbers.setFont(scoreFont); //sets the font
			p1.add(numbers,c); // add to the panel
			
			c.gridx = 2; // sets the X position
			c.gridy = i + 20; // set the Y position
			c.insets = new Insets(20,20,0,50); // sets the insets/spacing between the elements
			p1.add(achievementCheck[5+i],c); // add to the panel
			startingNumber++; //adds one to the starting number
		}
		
		checkAchievements(); // checks the achievements to update the checkboxes
		
		JButton back = new JButton("back");// creates a new back button
		c.gridx = 1; // sets the X position
		c.gridy = 40; // sets the Y position
		p1.add(back,c); // add to the panel
		
		//adss the actionlistener
		back.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	frame.setVisible(false); // sets the frame visible
            	frame.dispose(); // dispose the frame
            	Menu.frame.setVisible(true); // sets the menu frame visible
            }
        });
	}
	
	//check the achivements
	public void checkAchievements()
	{
		int enemiesKilled = Board.totalEnemiesKilled; // gets the total amount of enemies killed
		int levelComplete = Board.currentLevel - 1; // get the current level completed on the game
		
		/*check is the enemies killed is > or = to 5,10,15,20 or 25 if any of them are true it check the checkbox */ 
		if(enemiesKilled >= 5)
		{
			achievementCheck[0].setSelected(true);
		}
		if(enemiesKilled >= 10)
		{
			achievementCheck[1].setSelected(true);
		}
		if(enemiesKilled >= 15)
		{
			achievementCheck[2].setSelected(true);
		}
		if(enemiesKilled >= 20)
		{
			achievementCheck[3].setSelected(true);
		}
		if(enemiesKilled >= 25)
		{
			achievementCheck[4].setSelected(true);
		}
		
		/* END OF ENEMY ACHIEVEMENTS*/
		
		/*check is the level completed is > or = to 1,2,3,4 or 5 if any of them are true it check the checkbox */ 
		if(levelComplete >= 1)
		{
			achievementCheck[5].setSelected(true);
		}
		if(levelComplete >= 2)
		{
			achievementCheck[6].setSelected(true);
		}
		if(levelComplete >= 3)
		{
			achievementCheck[7].setSelected(true);
		}
		if(levelComplete >= 4)
		{
			achievementCheck[8].setSelected(true);
		}
		if(levelComplete >= 5)
		{
			achievementCheck[9].setSelected(true);
		}
		/* END OF LEVEL ACHIEVEMENTS*/
	}
}
