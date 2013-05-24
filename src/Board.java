import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Board extends JPanel implements Common 
{

	private static final long serialVersionUID = 1L; // make the class serialable

	private Rectangle gameDetailRect = new Rectangle(BOARD_WIDTH-275, 0, 275, 40); //creates a rectangle to draw onto the screen to contain the gameDetails(enemies left,bullets etc)
	private Rectangle levelRect = new Rectangle(0, 0, 150, 40); // creates a rectangle to draw onto the screen for the levelDetail

	private Font scoreFont = new Font("GAMECUBEN",Font.PLAIN,30); // creates a font of size 30

	private Dimension d; // dimension varible to hold size of screen
	private Timer timer; // timer to update our screen
	
	private Player mainPlayer = new Player(); // creates an instance of our player

	private ImageIcon backGroundImage; // stores our background images(which changes depending on level)
	private Image backGround; // gets the image from the background image variable

	private ImageIcon scoreImage; // stores our scoreboard background image
	private Image score; // get the images from the scoreImage variables

	private ArrayList<Enemy> zombies; // creates an arraylist to store our enemies
	private ArrayList<Bullet> bullets; // creates an arraylist to store our bullets
	private ArrayList<Drop> drops;  // creates an arraylist to store our drops

	private int totalScore; // stores our totalScore
	private int dropChance; // stores our dropchance variable which will be generated randomly
	static int currentLevel = 1; // stores our current level
	
	private final int doubleScorechance = 1; // stores our doubleScoreChance drop identifier (if dropchance = this a doubleScore drop will be dropped)
	private final int doubleBulletChance = 2;// stores our doubleBulletChance drop identifier (if dropchance = this a doubleBullet drop will be dropped)
	static int noOfBullets = 50; // number of bullets the player currently has.
	static int enemySpawnedAmount = 1; // stores number of enemyies spawned
	
	private boolean running = true; // stores if the game is running or not
	private boolean gameEnded = false; // stores if the game has ended or not

	private int positionOfEnemyKilledX = 0; // stores the x position of the last enemy killed (so the drop knows where to drop)
	private int positionOfEnemyKilledY = 0; // stores the y position of the last enemy killed (so the drop knows where to drop)
	
	static int totalEnemiesKilled = 0; // stores the total number of enemies killed (so we know which achievements to check)
	
	LevelManager level;

	public Board() 
	{
		addMouseListener(new TAdapter()); // adds a mouseListener to the board (so we can fire the bullets by clicking the mouse)
		addKeyListener(new TAdapter()); // adds a keyListener to the board (so we can move the player by using the keyboard)
		setFocusable(true); // focuses the component 
		setDoubleBuffered(true); // allows us to double buffer all images which allows use to first load the images into memory then draw them to the screen (this prevents image lagging)
		d = new Dimension(BOARD_WIDTH,BOARD_HEIGHT); // sets the dimensions of the board
		setPreferredSize(d); // sets the size

		currentLevel = 1;
		backGroundImage = new ImageIcon(this.getClass().getResource("/Images/backgroundLevel1.png")); // gets the background images for the first level
		backGround = backGroundImage.getImage(); // gets the image so it can be drawn

		scoreImage = new ImageIcon(this.getClass().getResource("/Images/scoreBg.png")); // stores the score background
		score = scoreImage.getImage(); // gets the images so it can be drawn
		totalScore = 0; // sets the score

		try {
			level =  new LevelManager();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			level.readLevelData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initEnemies(enemySpawnedAmount); // spawns the enemies
		drops = new ArrayList<Drop>(); // creates the drop arraylist

		timer = new Timer(); // creates a new timer
		timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10); // this schdules a task i.e. repaint the screen a 1000ms delay every 10ms
	}

	//draws the mainPlayer to the screen (in the correct direction)
	public void drawMainPlayer(Graphics2D g2d)
	{		
		if(mainPlayer.getDirectionX() == -1 && mainPlayer.getDirectionY() == 0) // if the enemy is moving left
			mainPlayer.drawPlayerLeft(g2d); // draw the left image
		
		else if(mainPlayer.getDirectionX() == -1 && mainPlayer.getDirectionY() == 1) // if the enemy is moving left down
			mainPlayer.drawPlayerLeftDown(g2d); // draw the left down image
		
		else if(mainPlayer.getDirectionX() == -1 && mainPlayer.getDirectionY() == -1) // if the enemy is moving left up
			mainPlayer.drawPlayerLeftUp(g2d); // draw the left up image
		
		else if(mainPlayer.getDirectionX() == 1 && mainPlayer.getDirectionY() == 0) // if the enemy is moving right
			mainPlayer.drawPlayerRight(g2d); // draw the right image
		
		else if(mainPlayer.getDirectionX() == 1 && mainPlayer.getDirectionY() == -1) // if the enemy is moving right up
			mainPlayer.drawPlayerRightUp(g2d); // draw the right up image
		
		else if(mainPlayer.getDirectionX() == 1 && mainPlayer.getDirectionY() == 1) // if the enemy is moving right down
			mainPlayer.drawPlayerRightDown(g2d); // draw the right down image
		
		else if(mainPlayer.getDirectionY() == -1 && mainPlayer.getDirectionX() == 0) // if the enemy is moving up
			mainPlayer.drawPlayerUp(g2d); // draw the up image
		
		else if(mainPlayer.getDirectionY() == 1 && mainPlayer.getDirectionX() == 0) // if the enemy is moving down
			mainPlayer.drawPlayerDown(g2d); // draw the down image
		
		else if(mainPlayer.getDirectionX() == 0 && mainPlayer.getDirectionY() == 0) // if the player has stopped moving
		{
			if(mainPlayer.left) // if player is facing left
				mainPlayer.drawPlayerLeft(g2d); // draw left
			
			else if(mainPlayer.right) // if play is facing right
				mainPlayer.drawPlayerRight(g2d); // draw right
			
			else if(mainPlayer.down) // if player is facing down
				mainPlayer.drawPlayerDown(g2d); // draw down
			
			else if(mainPlayer.up) // if player is facing up
				mainPlayer.drawPlayerUp(g2d); // draw up
			else if(mainPlayer.rightDown)
				mainPlayer.drawPlayerRightDown(g2d);
			else if(mainPlayer.rightUp)
				mainPlayer.drawPlayerRightUp(g2d);
			else if(mainPlayer.leftDown)
				mainPlayer.drawPlayerLeftDown(g2d);
			else if(mainPlayer.leftUp)
				mainPlayer.drawPlayerLeftUp(g2d);
		}
	}

	//spawns/add enemies to the game
	public void initEnemies(int enemySpawned)
	{
		zombies = new ArrayList<Enemy>(); // creates a new enemies arraylist

		Random gen = new Random(); // random generator
		int side; // stores which side enemy will spawn on
		int rightY,rightX,downX,downY,leftX,leftY,upX,upY; // creates x and y variables for different sides of the screen

		for(int i = 0;i<enemySpawned;i++)
		{
			side = gen.nextInt(4); // generates a random number from 0 - 3

			switch(side) // switch statement
			{
			case 0: // if the random number is 0 i.e enemy spawns left
				leftX = gen.nextInt(65) - 65; // set the enemies x position on the left side of the screen
				leftY = gen.nextInt(BOARD_HEIGHT+45-BOARD_HEIGHT)-BOARD_HEIGHT; // set the enemies y position on the left side of the screen
				zombies.add(new Enemy(leftX,leftY,mainPlayer)); // add the enemy to the arraylist
				break;
				
			case 1: // if the random number is 0 i.e enemy spawns top
				upX = gen.nextInt(BOARD_WIDTH); // set the enemies x position on the top side of the screen
				upY = gen.nextInt(BOARD_HEIGHT+45-BOARD_HEIGHT)- BOARD_HEIGHT; // set the enemies Y position on the left side of the screen
				zombies.add(new Enemy(upX,upY,mainPlayer));  // add the enemy to the arraylist
				break;
				
			case 2: // if the random number is 0 i.e enemy spawns RIGHT
				rightX = gen.nextInt(BOARD_WIDTH+45-BOARD_WIDTH) + BOARD_WIDTH;  // set the enemies x position on the left side of the screen
				rightY = gen.nextInt(BOARD_HEIGHT); // set the enemies Y position on the left side of the screen
				zombies.add(new Enemy(rightX,rightY,mainPlayer));  // add the enemy to the arraylist
				break;
				
			case 3: // if the random number is 0 i.e enemy spawns down/bottom
				downX = gen.nextInt(BOARD_WIDTH); // set the enemies x position on the left side of the screen
				downY = gen.nextInt(BOARD_HEIGHT+45-BOARD_HEIGHT) + BOARD_HEIGHT; // set the enemies Y position on the left side of the screen
				zombies.add(new Enemy(downX,downY,mainPlayer));  // add the enemy to the arraylist
				break;
			}
		}

	}

	//used to add drops to the game
	public void initDrops()
	{	
		
		int pekX = positionOfEnemyKilledX; // stores the x position of the last enemy killed
		int pekY = positionOfEnemyKilledY; // stores the y position of the last enemy killed

		Random gen = new Random(); // creates a random number generator
		dropChance = gen.nextInt(3); // generates a random number between 0-2

		if(dropChance == doubleScorechance || dropChance == doubleBulletChance ) // if dropchance =  either doubleScorechance or doubleBulletChance
		{
			drops.add(new Drop(pekX,pekY));	 // it adds a new drop to the list
		}

	}

	//paints screen i.e. draws player/enemies/bullets/background etc..
	public void paint(Graphics g) 
	{
		if(running) // if the game is running draw to the screen
		{
			super.paint(g); // drawn the graphics
			Graphics2D g2d = (Graphics2D)g; // creats a graphics2d object so we can use it to draw our game sprites
			g2d.setFont(scoreFont); // sets the font
			g2d.setColor(Color.RED); // sets the color
			//g2d.drawImage(backGround,0,0,this); // draws our background image
			
			System.out.println(level.blocks.size());
			for(int i = 0;i<level.blocks.size();i++)
			{
				g2d.drawString("(" + level.blocks.get(i).x + "," + level.blocks.get(i).y + ")",level.blocks.get(i).x, level.blocks.get(i).y);
				g2d.drawImage(level.blocks.get(i).blockImage,level.blocks.get(i).x,level.blocks.get(i).y,this);
			}
		
			g2d.draw(levelRect); // draws our levelRect rectangle to contain our level details in
			g2d.drawString("Level:" + currentLevel, 0, 30); // draws the current level to the screen in top left corner

			if(zombies.size() == 0) // if there is no enemies left
			{
				levelCheck(); // check the level
			}

			if(mainPlayer.isVisible()) // if the player is alive
			{
				drawMainPlayer(g2d); // draw the player
			}
			else // if not
			{
				g2d.drawString("GAME OVER!!!!", BOARD_WIDTH/3-60, BOARD_HEIGHT/2); // draw string telling user game over
				g2d.drawString("Final Score=" + totalScore, BOARD_WIDTH/3-40, BOARD_HEIGHT/2+40); // tell the user there score
				timer.cancel(); // stop the timer
			}

			bullets = mainPlayer.getMissiles(); // get the bullets

			for (int i = 0; i < bullets.size(); i++ )  //while there is bullets in the arraylist
			{
				Bullet m = (Bullet) bullets.get(i); // make a new bullet object 
				g2d.drawImage(m.getImage(), m.getPositionX() + 1, m.getPositionY() + 1, this); // draw the bullet to the screen
			}

			for(int i = 0;i<zombies.size();i++) // while there is zombies in the arraylist
			{
				Enemy e = (Enemy)zombies.get(i); // make a new enemy object
				if(e.isVisible()) // if enemy is visible
					g2d.drawImage(e.getImage(), e.getPositionX(), e.getPositionY(), this); // draw the enemy
			}

			for(int i = 0;i<drops.size();i++) // while there is drops in the arraylist
			{
					Drop d = (Drop) drops.get(i); // make a new drop object
					g2d.drawImage(d.getImage(),d.getPositionX(), d.getPositionY(), this); // draw the drop
			}

			
			g2d.draw(gameDetailRect); //score menu code
			g2d.drawString("Score:" + totalScore, BOARD_WIDTH-270, 30); // draws the score string
			g2d.drawImage(score, 10, BOARD_HEIGHT-70,this); // draw the score image
			g2d.drawString("Enemies:" + zombies.size(), (20), (BOARD_HEIGHT-35)); // draw the amount of enemies left
			g2d.drawRect(10, BOARD_HEIGHT-70, BOARD_WIDTH-20,60); // draw rectangle around score details



			if(noOfBullets != 0) // is there bullets left
				g2d.drawString("Bullets Left:"+noOfBullets, (270), (BOARD_HEIGHT-35)); // draw amount of bullets left
			else // if not
				g2d.drawString("Need Bullets", (250), (BOARD_HEIGHT-35)); // tell the player

			Toolkit.getDefaultToolkit().sync(); // syncs the graphics state
			g.dispose(); // release the graphics context
		}

	}
	
	//gets the background image
	public ImageIcon getBackgroundImage()
	{
		return backGroundImage; // return backgroundImage
	}
	
	//sets the background image
	public void setBackgroundImage(ImageIcon bg)
	{
		backGroundImage = bg; // sets backgroundImage = bg
	}

	//fires the bullets
	public void fireBullets()
	{
		bullets = mainPlayer.getMissiles(); // get the bullets
		for (int i = 0; i < bullets.size(); i++)  // while there is bullets
		{
			Bullet iBullet = (Bullet) bullets.get(i); //create a new bullet instance
			if (iBullet.isVisible()) // if the bullet is visible
				iBullet.move(); // move the bullet
			else bullets.remove(i); // else remove the bullet from the arrayList
		}
	}

	//check for collisions 
	public void checkCollisions()
	{
		Rectangle mainPlayerRec = mainPlayer.getBounds(); //creates a rectangle for our player(so we can detect if it collides with another rectangle(enemy))

		for (int j = 0; j<zombies.size(); j++) // while there is zombies in the list
		{
			Enemy a = (Enemy) zombies.get(j); // create an enemy
			Rectangle enemyRec1 = a.getBounds(); // create a rectangle for every enemy (for collision detection)

			if (mainPlayerRec.intersects(enemyRec1))  // this is the collision detection if the mainPlayerRectangle intersects the enemy rectangle
			{
				mainPlayer.setVisible(false); // remove the player from the game
				gameEnded = true; // end the game
			}
		}

		bullets = mainPlayer.getMissiles(); // get the bullets

		for(int i = 0;i<bullets.size();i++)  // while there is bullets
		{
			Bullet ammo = (Bullet) bullets.get(i); //create a new bullet 

			Rectangle bulletRec = ammo.getBounds(); // create a rectangle and attach it to the enemy so we can check for collision detection

			for(int j = 0;j<zombies.size();j++) 
			{
				Enemy e = (Enemy) zombies.get(j); // create an enemy
				Rectangle enemyRec1 = e.getBounds(); // create a rectangle for every enemy (for collision detection)

				//if player fire bullet at enemy
				if(bulletRec.intersects(enemyRec1)) // if the bulletrectangle(bullet) collides/intersects with the enemyRec(enemy)
				{
					totalScore += 10; // add 10 to score
					positionOfEnemyKilledX = e.getPositionX(); // get the x position of enemykilled and store it in positionofEnemyKilled
					positionOfEnemyKilledY = e.getPositionY(); // get the y position of enemykilled and store it in positionofEnemyKilled
					zombies.remove(j); // remove the enemy from the arraylist
					ammo.setVisible(false); // remove the bullet from the screen
					bullets.remove(i); // remove the bullet from the arrayList
					e.setVisible(false); // remove the enemy from the screen
					totalEnemiesKilled++; // add one to totalEnemiesKilled

					initDrops(); // call initDrops

				}
			}
		}
		
		for(int i = 0;i<drops.size();i++)
		{
			Drop d = (Drop) drops.get(i); // create a new drop
			Rectangle dropRec = d.getBounds(); // create a rectangle to contain the drop
			int lastDrop = dropChance; // stores the dropChance as the lastDrop
			System.out.println(lastDrop);
			
			if(mainPlayerRec.intersects(dropRec)) // if the mainplayer collects the drop
			{
				drops.remove(i); // remove the drop
				d.setVisible(false); // remove the drop from the screen
				if(lastDrop == doubleScorechance)
					totalScore = totalScore * 2;
				else if(lastDrop == doubleBulletChance)
					noOfBullets = 51;
			}
		}
	}

	//check what level it is
	public void levelCheck()
	{
		currentLevel++; // add one to the current level
		switch(currentLevel)
		{
		case 1:
			break;
		case 2: // if level = 2
			try {
				Thread.sleep(2000); // sleep the thread
				backGroundImage = new ImageIcon(this.getClass().getResource("/Images/backgroundLevel2.png")); // chance the background
				backGround = backGroundImage.getImage(); // get background image
			} 
			catch (InterruptedException e)
			{
				// error checking
				e.printStackTrace();
			}
		
			initEnemies(enemySpawnedAmount * 2); // double the amount of enemies on the next level
			break;
		case 3:
			//level 3
			try 
			{
				Thread.sleep(2000);  // sleep the thread
				backGroundImage = new ImageIcon(this.getClass().getResource("/Images/backgroundLevel3.png")); // change the background
				backGround = backGroundImage.getImage(); // get background image
			} 
			catch (InterruptedException e) 
			{
				// error checking
				e.printStackTrace();
			}
			
			initEnemies(enemySpawnedAmount * 4); //quadruple the amount of enemies
			break;
		case 4:
			try 
			{
				Thread.sleep(2000); // sleep the thread
				backGroundImage = new ImageIcon(this.getClass().getResource("/Images/backgroundLevel4.png"));// change the background
				backGround = backGroundImage.getImage(); // get background image
			}
			catch (InterruptedException e)
			{
				// error checking
				e.printStackTrace();
			}
			initEnemies(enemySpawnedAmount * 6); //double the amount of enemies
			break;
			
		case 5:
			try 
			{
				Thread.sleep(2000);  // sleep the thread
				backGroundImage = new ImageIcon(this.getClass().getResource("/Images/backgroundLevel5.png")); // change the background
				backGround = backGroundImage.getImage(); // get background image
			} 
			catch (InterruptedException e) 
			{
				// error checking
				e.printStackTrace();
			}
			initEnemies(enemySpawnedAmount * 8); // double the amount of enemies
			break;
		}

	}

	//new scheduletask class
	private class ScheduleTask extends TimerTask
	{
		//run method is ran whenever the scheduletask is ran
		public void run() 
		{
			if(running)// if the game is running
			{
				for(int i = 0;i<zombies.size();i++)
				{
					Enemy em = (Enemy) zombies.get(i); // create a new enemy
					if(em.isVisible()) // if the enemy is visible
					{
						em.findPath(); // find path to the player
						em.move(); // move
						em.detectEdges(); // detect edges of the screen
					}
					else // if not visible 
						zombies.remove(i); // remove enemy from the arraylist
				}

				checkCollisions(); // check the collisions
				fireBullets(); // fire the bullets

				mainPlayer.move(); // move the player
				repaint(); // repaint the screen
			}
			
			if(gameEnded) //  of the game has ended
			{
				this.cancel(); // cancel the task
				JOptionPane optionPane = new JOptionPane("Please Enter Username", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION , null, null, "test"); // new option pane
				optionPane.setWantsInput(true); // allows user input       
		        JDialog dialog = optionPane.createDialog(null, "Highscore Input"); // creates a new dialog
		        dialog.setLocation(BOARD_WIDTH-100, BOARD_HEIGHT-80); // set the location of the dialog
		        dialog.setVisible(true); // sets it visible
		        
		        String name = (String) optionPane.getInputValue(); // get the name the user types in
		        
				int score = totalScore; // gets their totalScore
				
				Highscore h = new Highscore(name,score); // creates a new highscore instance and stores the name and their score in a file.
				h.addScore(name, score); // add a score
				
				try 
				{
					this.finalize(); // garbage collection
					StartGameFrame.f1.dispose(); // release all the resources
					noOfBullets = 50; // reset the number of bullets
				} catch (Throwable e) 
				{
					//error checking
					e.printStackTrace();
				}
				
				//create a new menu instance
				Menu m1 = new Menu();
			}
		}

	}

	// creates a new classes to handle keyboard and mouseEvents
	private class TAdapter extends KeyAdapter implements MouseListener
	{

		public void keyReleased(KeyEvent e)
		{
			mainPlayer.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) 
		{
			int key = e.getKeyCode();

			if(key == KeyEvent.VK_P)
			{
				running = false;
			}
			else if(key == KeyEvent.VK_R)
			{
				running = true;
			}

			mainPlayer.keyPressed(e);

		}

		@Override
		//if the mouse is clicked
		public void mouseClicked(MouseEvent e) 
		{
			mainPlayer.mouseClicked(e); // pass the event to the player class

		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			// TODO Auto-generated method stub

		}
	}

}
