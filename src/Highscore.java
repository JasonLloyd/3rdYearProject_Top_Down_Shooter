import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Highscore  implements Serializable,Common
{

	private int score; // stores our score
	private String name; // stores the name
	private static int max = 10; // stores the max

	// An arraylist of the type "score" we will use to work with the scores inside the class
	private ArrayList<Highscore> scores;

	// The name of the file where the highscores will be saved
	private static final String HIGHSCORE_FILE = "scores.dat";

	//Initialising an in and outputStream for working with the file
	private ObjectOutputStream outputStream = null;
	private ObjectInputStream inputStream = null;
	
	
	private String nameArray[]; //stores all the names in the array
	private int scoresArray[]; //stores all the scores in the array

	private JFrame frame; // frame to display the scores

	//constructor
	public Highscore(String name, int score) 
	{
		this.score = score; // sets the score
		this.name = name; // sets the name

		//creates a new arrayList
		scores = new ArrayList<Highscore>();
		
	}
	
	//display the frame
	public void initDisplay()
	{

		JPanel p1 = new JPanel(); /// new panel
		JPanel p2 = new JPanel(); // new panel
		JLabel title = new JLabel("Highscores"); // new label
		JLabel namesOfPlayers; // new label
		
		title.setFont(new Font("GAMECUBEN",Font.BOLD, 48)); // sets the title
		
		frame = new JFrame("Highscores"); // sets the frame
		frame.setLayout(new BorderLayout()); // sets the layout of the frame
		
		GridBagConstraints c = new GridBagConstraints(); // allows us to manipluate the layout
		
		p1.setLayout(new GridBagLayout()); // sets the layout of the panel
		frame.add(p2,BorderLayout.NORTH); // adds the panel to the frame
		frame.add(p1,BorderLayout.CENTER); // add the panel to the frame
		getHighscoreString(); // gets the highscore string
		
		p2.add(title,BorderLayout.CENTER); // adds the title to the panel
		c.fill = GridBagConstraints.HORIZONTAL; // sets the fill property
		
		for(int i = 0;i<10;i++)
		{
			c.gridx = 0; //sets X position
			c.gridy = i + 10; // sets the Y position 
			c.insets = new Insets(10,0,0,0); // sets the spacing 
			
			namesOfPlayers = new JLabel(i+1 + " \t \t \t \t \t " + nameArray[i] + " \t \t \t \t \t"); // stores the name in the jlabel
			JLabel scores = new JLabel("" + scoresArray[i]); // stores the score in the array
			scores.setFont(new Font("GAMECUBEN",Font.PLAIN,15)); // sets the font
			namesOfPlayers.setFont(new Font("GAMECUBEN",Font.PLAIN,15)); // sets the font
			p1.add(namesOfPlayers,c); // adds the label
			c.gridx = 20; //sets X position
			c.gridy = i + 10; // sets the Y position 
			p1.add(scores,c); // adds the label
		}
		
		JButton back = new JButton("back"); //adds the button
		c.gridx = 21;  //sets X position
		c.gridy += 10; // sets the Y position 
		p1.add(back,c); // adds the button
		
		//add a listener to the back button
		back.addActionListener(new ActionListener()
		{
			 public void actionPerformed(ActionEvent e)
	            {
	            	frame.setVisible(false); // sets the frame to visible
	            	frame.dispose(); // disposed the frame
	            	Menu.frame.setVisible(true); // set the menu visible
	            }
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(BOARD_WIDTH+15,BOARD_HEIGHT+38); // sets the size
		frame.setLocationRelativeTo(null);
		frame.setResizable(false); // not resizeable
		frame.setVisible(true); // visible
	}


	//returns the array list getscore
	public ArrayList<Highscore> getScores() 
	{
		loadScoreFile();
		sort();
		return scores;
	}

	//sorts the arraylist
	private void sort() 
	{
		ScoreCompare comparator = new ScoreCompare();
		Collections.sort(scores, comparator);
	}

	//loads the score file
	public void loadScoreFile() 
	{
		try 
		{
			inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
			scores = (ArrayList<Highscore>) inputStream.readObject();
		} 
		catch (Exception e) 
		{
			
		}
		finally 
		{
			try 
			{
				if (outputStream != null) 
				{
					outputStream.flush();
					outputStream.close();
				}
			} 
			catch (IOException e) 
			{
				System.out.println("[Laad] IO Error: " + e.getMessage());
			}
		}
	}


	// add a score to the file
	public void addScore(String name, int score) 
	{
		loadScoreFile();
		scores.add(new Highscore(name, score));
		updateScoreFile();
	}

	//updates the score file
	public void updateScoreFile() 
	{
		try 
		{
			outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
			outputStream.writeObject(scores);
		} 
		catch (Exception e) 
		{
			
		} 
		finally 
		{
			try 
			{
				if (outputStream != null)
				{
					outputStream.flush();
					outputStream.close();
				}
			} 
			catch (IOException e) 
			{
				System.out.println("[Update] Error: " + e.getMessage());
			}
		}
	}

	// gets the total highscore string
	public String getHighscoreString() 
	{
		String highscoreString = "";

		ArrayList<Highscore> scores;
		scores = getScores();
		
		 nameArray = new String[scores.size()];
		 scoresArray = new int[scores.size()];
		 
		 
		 for(int i = 0;i<scores.size();i++)
		 {
			 nameArray[i] = scores.get(i).getName();
			 scoresArray[i] = scores.get(i).getScore(); 
		 }
		 
		 for(int i = 0;i<nameArray.length;i++)
		 {
			 System.out.println(nameArray[i]);
			 System.out.println(scoresArray[i]);
		 }
		

		int i = 0;
		int x = scores.size();

		if (x > max)
		{
			x = max;
		}
		while (i < x) 
		{
			
			highscoreString += (i + 1) + ".\t" + scores.get(i).getName() + "\t\t" + scores.get(i).getScore() + "\n";
			i++;
		}
		return highscoreString;
	}

	public int getScore() 
	{
		return score;
	}

	public String getName() 
	{
		return name;
	}

	//allows us to compare the scores in the file and sort them
	private class ScoreCompare implements Comparator<Highscore>
	{
		public int compare(Highscore score1,Highscore score2)
		{
			int sc1 = score1.getScore();
			int sc2 = score2.getScore();

			if (sc1 > sc2){
				return -1;
			}else if (sc1 < sc2){
				return +1;
			}else{
				return 0;
			}
		}
	}
}



