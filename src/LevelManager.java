import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class LevelManager 
{
	BufferedReader readFile;
	int WIDTH = 100;
	int HEIGHT = 100;
	private ArrayList<String> lines = new ArrayList<String>();
 ArrayList<Block> blocks = new ArrayList<Block>();

	
	
	
	public LevelManager() throws FileNotFoundException
	{
		readFile = new BufferedReader(new FileReader("level.txt"));
	}
	
	/* 	i
	 *  ---------------
		-			  -
		-			  -
		-			  -
		--------------- */
	
 	public void readLevelData() throws IOException
	{
 	
		String line = " ";
		while((line = readFile.readLine()) != null)
		{
			lines.add(line);
		}
		
		System.out.println(lines.size());
		
		int currentElement = 0;
		for(String s : lines)
		{
			char[] letters = s.toCharArray();
			System.out.println(letters.length);
			for(int i = 0;i<letters.length;i++)
			{
					if(letters[i] == '-')
					{
						blocks.add(new Block(WIDTH * i,HEIGHT + currentElement));
					}
			}
			
			currentElement += 100;
		}
		
		System.out.println(blocks.size());
	}
 	
 	/* 
 	 * 
 	 * */
	
	public class Block
	{
		int x;
		int y;
		private ImageIcon blockImg; // stores our background images(which changes depending on level)
		 Image blockImage; // gets the image from the background image variable
		
	
		
		public Block(int x, int y)
		{
			this.x = x;
			this.y = y;
			
			blockImg = new ImageIcon(this.getClass().getResource("/Images/blockImage.png")); // gets the background images for the first level
			blockImage = blockImg.getImage(); // gets the image so it can be drawn

		}
	}
	
	public static void main(String[] args) throws IOException
	{
		new LevelManager().readLevelData();
	}
}


