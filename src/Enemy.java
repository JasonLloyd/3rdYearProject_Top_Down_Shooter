import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;


public class Enemy extends Sprite
{
	private ImageIcon enemyPictures[]; // array to store the enmypictures
	private Image enemyImage; // to get the enemy picture

	private int distanceFromPlayerX = 0; // X distance from player
	private int distanceFromPlayerY = 0; // Y distance from player
	
	boolean shouldSetRandom = true; // if random movement should be set

	private Player player = new Player(); // creates a new player instance
	
	//constructor
	public Enemy(int x, int y,Player p)
	{
		player = p; // makes player = to player
		enemyPictures = new ImageIcon[8]; // sets up the enemy picture array
		
		//stores the images in the array
		enemyPictures[0] = new ImageIcon(Board.class.getResource("/Sprites/zombie_right.png"));
		enemyPictures[1] = new ImageIcon(Board.class.getResource("/Sprites/zombie_left.png"));
		enemyPictures[2] = new ImageIcon(Board.class.getResource("/Sprites/zombie_up.png"));
		enemyPictures[3] = new ImageIcon(Board.class.getResource("/Sprites/zombie_down.png"));
		enemyPictures[4] = new ImageIcon(Board.class.getResource("/Sprites/zombie_LD.png"));
		enemyPictures[5] = new ImageIcon(Board.class.getResource("/Sprites/zombie_LU.png"));
		enemyPictures[6] = new ImageIcon(Board.class.getResource("/Sprites/zombie_RD.png"));
		enemyPictures[7] = new ImageIcon(Board.class.getResource("/Sprites/zombie_RU.png"));
		
		enemyImage = enemyPictures[0].getImage(); // get the image
		
		setWidth(enemyImage.getWidth(null) - 5); // sets the width of the enemyImage
		setHeight(enemyImage.getHeight(null) - 5); //sets the height of the enemyImage

		setPositionX(x); // sets the X position of the enemy
		setPositionY(y); // sets the Y position of the enemy

		setVisible(true); // set enemy visible
	}

	public void move()
	{
		distanceFromPlayerX = getPositionX() - player.getPositionX(); // gets the X distance from the player to enemy
		distanceFromPlayerY = getPositionY() - player.getPositionY();  // gets the Y distance from the player to enemy
		setPositionX(getPositionX() + getDirectionX()); //sets the X position of the enemy
		setPositionY(getPositionY() + getDirectionY()); //sets the Y position of the enemy


		if(getDirectionX() == 1 && getDirectionY() == 0) 	//right
			enemyImage = enemyPictures[0].getImage();
		else if(getDirectionX() == -1 && getDirectionY() == 0) 	//left
			enemyImage = enemyPictures[1].getImage();
		else if(getDirectionX() == 0 && getDirectionY() == -1) 	//up
			enemyImage = enemyPictures[2].getImage();
		else if(getDirectionY() == 1 && getDirectionX() == 0) 		//down
			enemyImage = enemyPictures[3].getImage();
		else if(getDirectionX() == -1 && getDirectionY() == 1) 		//leftDown
			enemyImage = enemyPictures[4].getImage();
		else if(getDirectionX() == -1 && getDirectionY() == -1) 		//leftUP
			enemyImage = enemyPictures[5].getImage();
		else if(getDirectionX() == 1 && getDirectionY() == 1) 		//rightDown
			enemyImage = enemyPictures[6].getImage();
		else if(getDirectionX() == 1 && getDirectionY() == -1) 		//rightUp
			enemyImage = enemyPictures[7].getImage();

			

	}

	//moves the enemy randomly
	public int moveRandomly()
	{
		//if the Y position is outside the board move the enemy towards the screen
		if(getPositionY() > (BOARD_HEIGHT -125))
		{
			return -1; // move opposite direction
		}
		
		if(getPositionX() == 0 && getPositionY() == 0 || getPositionX() == 1 && getPositionY() == 1) // if the x and y are 0
		{
			return 1; // move opposite direction
		}
		
		Random r = new Random(); // random generator
		int[] directions = new int[2]; // directions that enemy can move
		directions[0] = 0; // foward
		directions[1] = -1; // backwards
		int randChoice = r.nextInt(2); // generates random number betwwen 1 and -1
		return directions[randChoice]; // return a element in the array for the enemy to move
	}

	//find path to the player
	public void findPath()
	{
		//if the x position and y position is withing 100 pixels of the player move towards the player
		if(distanceFromPlayerX > -100 && distanceFromPlayerY < 100)
		{
			if(getPositionX() < player.getPositionX())
				setDirectionX(1); // move right
			if(getPositionX() > player.getPositionX())
				setDirectionX(-1); // move left
			if(getPositionY() < player.getPositionY())
				setDirectionY(1); // move up
			if(getPositionY() > player.getPositionY())
				setDirectionY(-1); // move down
		}
		else
		{
			if(shouldSetRandom) // if random is set
			{
				System.out.println("moving Randomly");
				setDirectionX(moveRandomly()); // set direction x to random number
				setDirectionY(moveRandomly()); // set direction y to random number
				shouldSetRandom = false; // set random to false
			}
		}
	}

	//detect edges of the screen
	public void detectEdges()
	{
		if(getPositionX() > (BOARD_WIDTH - 50)) // if the x position of the enemy is greater than BOARD_WIDTH
			setPositionX(getPositionX() -1);  //move toward the screen


		if(getPositionX() < 0) // if X of the enemy is less than 0 
			setPositionX(1); //move the enemy right toward the screen


		if(getPositionY() > (BOARD_HEIGHT - 125)) // if the y position is greater than the BOARD_HEIGHT 
			setPositionY(getPositionY() - 1); //move up


		if(getPositionY() < 0) // if the y position is less than 0
			setPositionY(1); // move down
	}
	
	public Image getImage() // get the enemy image
	{
		return enemyImage; // return the enemy image
	}

	public Rectangle getBounds() //get the area of the enemy rectangle
	{
		return new Rectangle(getPositionX(), getPositionY(), getWidth(), getHeight()); // returns the area
	}

}
