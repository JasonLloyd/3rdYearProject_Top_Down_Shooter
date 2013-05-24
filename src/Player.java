import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Sprite implements MouseListener, Common 
{

	//images of player for every(8) directions
	private Image playerRight;
	private Image playerRightUp;
	private Image playerRightDown;
	private Image playerLeft;
	private Image playerLeftDown;
	private Image playerLeftUp;
	private Image playerUp;
	private Image playerDown;

	//boolean variables to tell us the position of the player
	boolean up = false;
	boolean down = false;
	boolean left = false;
	boolean right = true;
	boolean rightUp = false;
	boolean rightDown = false;
	boolean leftDown = false;
	boolean leftUp = false;
	
	private Image playerCurrentImage; // image variable to store the positon of the player


	private ArrayList bullets;

	private final int PLAYER_SIZE = 20;

	public Player() 
	{

		playerCurrentImage = new ImageIcon(Board.class.getResource("/Sprites/soldierRU.png")).getImage();
		loadImages();

		bullets = new ArrayList();
		setVisible(true);
		setPositionX(BOARD_WIDTH / 2);
		setPositionY(BOARD_HEIGHT / 2);

	}


	public void move() 
	{

		setPositionX(getPositionX() + getDirectionX());
		setPositionY(getPositionY() + getDirectionY());

		//this puts an invisible border around the game so the sprite cannot go off screen
		if(getPositionX() > (BOARD_WIDTH - 50))
			setPositionX(getPositionX() -1);


		if(getPositionX() < 0)
			setPositionX(1);


		if(getPositionY() > (BOARD_HEIGHT - 125))
			setPositionY(getPositionY() - 1);


		if(getPositionY() < 0)
			setPositionY(1);


	}

	public ArrayList getMissiles() 
	{
		return bullets;
	}

	/* This are the get methods for the images to tell us which way the enemy is facing*/
	public Image getPlayerRight()
	{
		return playerRight;
	}
	
	public Image getPlayerRightUp()
	{
		return playerRightUp;
	}
	
	public Image getPlayerRightDown()
	{
		return playerRightDown;
	}

	public Image getImage()
	{
		return playerCurrentImage;
	}

	public Image getPlayerLeft()
	{
		return playerLeft;
	}
	
	public Image getPlayerLeftDown()
	{
		return playerLeftDown;
	}
	
	public Image getPlayerLeftUp()
	{
		return playerLeftUp;
	}

	public Image getPlayerDown()
	{
		return playerDown;
	}

	public Image getPlayerUp()
	{
		return playerUp;
	}

	/* end of get images methods */
	
	// gets the area of the player rectangle for collision detection
	public Rectangle getBounds()
	{
		return new Rectangle(getPositionX(),getPositionY(),getWidth(),getHeight()); // returns the area
	}
	
	/* Draws the player depending on which direction he is going */
	public void drawPlayerUp(Graphics2D g2d)
	{
		g2d.drawImage(getPlayerUp(), getPositionX() + 1,getPositionY() + 1,null);
		up = true;
		down = false;
		left = false;
		right = false;
		rightUp = false;
		leftDown = false;
		rightDown = false;
		leftUp = false;
	}

	public void drawPlayerDown(Graphics2D g2d)
	{
		g2d.drawImage(getPlayerDown(), getPositionX() + 1, getPositionY() + 1,null);
		down = true;
		up = false;
		left = false;
		right = false;
		rightUp = false;
		leftDown = false;
		rightDown = false;
		leftUp = false;
	}

	public void drawPlayerLeft(Graphics2D g2d)
	{
		g2d.drawImage(getPlayerLeft(), getPositionX() + 1, getPositionY() + 1,null);
		left = true;
		down = false;
		up = false;
		right = false;
		rightUp = false;
		leftDown = false;
		rightDown = false;
		leftUp = false;
	}
	
	public void drawPlayerLeftDown(Graphics2D g2d)
	{
		g2d.drawImage(getPlayerLeftDown(), getPositionX() + 1, getPositionY() + 1,null);
		left = false;
		down = false;
		up = false;
		right = false;
		rightUp = false;
		leftDown = true;
		rightDown = false;
		leftUp = false;
	}
	
	public void drawPlayerLeftUp(Graphics2D g2d)
	{
		g2d.drawImage(getPlayerLeftUp(), getPositionX() + 1, getPositionY() + 1,null);
		left = false;
		down = false;
		up = false;
		right = false;
		rightUp = false;
		leftDown = false;
		rightDown = false;
		leftUp = true;
	}

	public void drawPlayerRight(Graphics2D g2d)
	{
		g2d.drawImage(getPlayerRight(), getPositionX() + 1, getPositionY() + 1, null);
		right = true;
		left = false;
		down = false;
		up = false;
		rightUp = false;
		leftDown = false;
		rightDown = false;
		leftUp = false;
	}
	
	public void drawPlayerRightUp(Graphics2D g2d)
	{
		g2d.drawImage(getPlayerRightUp(), getPositionX() + 1, getPositionY() + 1, null);
		right = false;
		left = false;
		down = false;
		up = false;
		rightUp = true;
		leftDown = false;
		rightDown = false;
		leftUp = false;
	}
	
	public void drawPlayerRightDown(Graphics2D g2d)
	{
		g2d.drawImage(getPlayerRightDown(), getPositionX() + 1, getPositionY() + 1, null);
		right = false;
		left = false;
		down = false;
		up = false;
		rightUp = false;
		leftDown = false;
		rightDown = true;
		leftUp = false;
	}
	/* End of draw play methods */
	
	//loads the images at the when a play is created (this saves us loading them later)
	public void loadImages()
	{
		playerRight = new ImageIcon(Board.class.getResource("/Sprites/soldierRight.png")).getImage();
		playerRightUp = new ImageIcon(Board.class.getResource("/Sprites/soldierRU.png")).getImage();
		playerRightDown = new ImageIcon(Board.class.getResource("/Sprites/soldierRD.png")).getImage();
		playerLeft = new ImageIcon(Board.class.getResource("/Sprites/soldierLeft.png")).getImage();
		playerLeftDown = new ImageIcon(Board.class.getResource("/Sprites/soldierLD.png")).getImage();
		playerLeftUp = new ImageIcon(Board.class.getResource("/Sprites/soldierLU.png")).getImage();
		playerDown = new ImageIcon(Board.class.getResource("/Sprites/soldierDown.png")).getImage();
		playerUp = new ImageIcon(Board.class.getResource("/Sprites/soldierUP.png")).getImage();

		//sets the players height and width
		setWidth(playerRight.getWidth(null) - 10);
		setHeight(playerRight.getHeight(null) - 10);
	}

	//moves the player when a key is pressed
	public void keyPressed(KeyEvent e)
	{

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) //if A is pressed 
		{
			setDirectionX(-1); // move left
		}

		if (key == KeyEvent.VK_D) // if d is press
		{
			setDirectionX(1); // move right
		}

		if (key == KeyEvent.VK_W) // if w is pressed 
		{
			setDirectionY(-1); //move up
		}

		if (key == KeyEvent.VK_S) // if s is presses
		{
			setDirectionY(1); // move down
		}
	}

	//stops the player from moving when key is released
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A)
		{
			setDirectionX(0);
		}

		if (key == KeyEvent.VK_D)
		{
			setDirectionX(0);
		}

		if (key == KeyEvent.VK_W) 
		{
			setDirectionY(0);
		}

		if (key == KeyEvent.VK_S) 
		{
			setDirectionY(0);
		}
	}

	//tell us when the mouseClicked and fires a bullet
	public void mouseClicked(MouseEvent e) 
	{
		if(Board.noOfBullets != 0)
			fire();

	}

	/* Method do nothing they have to be overwritten cause we implemented the mouselistener*/
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{

	}


	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub


	}


	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub

	}
	/* end of mouse methods */
	

	//adds bullets to the arraylist so they can be fired
	public void fire() 
	{
		Board.noOfBullets = Board.noOfBullets - 1;
		//adds bullets to the arraylist at the positon of the player and fires in the direction he is facing
		bullets.add(new Bullet(getPositionX() + PLAYER_SIZE/2, getPositionY() + PLAYER_SIZE/2,up,down,left,right,rightUp,rightDown,leftDown,leftUp));
	}
}