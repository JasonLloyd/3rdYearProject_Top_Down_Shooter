import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Bullet extends Sprite implements Common
{
	private Image image; // Image for the bullet
	private final int BULLET_SPEED = 2; // bullet speed
	
	private ImageIcon images[]; // array for the bullet images

	/* Boolean to know which way the bullet is going*/
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = true;
	private boolean rightUp = false;
	private boolean rightDown = false;
	private boolean leftDown = false;
	private boolean leftUp = false;
	
	//constructor which takes in x and y and all the booleans
	public Bullet(int x, int y,boolean up,boolean down,boolean left,boolean right,boolean rightUp,boolean rightDown,boolean leftDown,boolean leftUp)
	{
		
		images = new ImageIcon[8]; // creates a new array of imageicons
		
		/* stores in the images in the array */
		images[0] = new ImageIcon(this.getClass().getResource("/Sprites/bulletRight.png"));
		images[1] = new ImageIcon(this.getClass().getResource("/Sprites/bulletRU.png"));
		images[2] = new ImageIcon(this.getClass().getResource("/Sprites/bulletDR.png"));
		images[3] = new ImageIcon(this.getClass().getResource("/Sprites/bulletDL.png"));
		images[4] = new ImageIcon(this.getClass().getResource("/Sprites/bulletLU.png"));
		images[5] = new ImageIcon(this.getClass().getResource("/Sprites/bulletLeft.png"));
		images[6] = new ImageIcon(this.getClass().getResource("/Sprites/bulletUp.png"));
		images[7] = new ImageIcon(this.getClass().getResource("/Sprites/bulletDown.png"));
		
		image = images[0].getImage(); // get the images
		
		setVisible(true); // set it visible
		
		/* sets the boolean variables */
		this.up = up;
		this.right = right;
		this.left = left;
		this.down = down;
		this.rightUp = rightUp;
		this.leftDown = leftDown;
		this.rightDown = rightDown;
		this.leftUp = leftUp;
		
		
		//sets Height and Width
		setWidth(image.getWidth(null) - 5) ;
		setHeight(image.getHeight(null) - 5);
		
		//sets X and Y
		setPositionX(x);
		setPositionY(y);

	}

	//gets the images
	public Image getImage() 
	{
		return image;
	}

	//moves the bullet depending on players direction
	public void move()
	{
		if(left) // if the player is facing left
			moveLeft(); //move bullet left
		else if(leftDown)  // if the player is facing leftDown
			moveLeftDown(); //move bullet down
		else if(leftUp)  // if the player is facing leftUp
			moveLeftUp(); //move bullet leftUp
		else if(right)  // if the player is facing right
			moveRight(); //move bullet right
		else if(rightUp)  // if the player is facing rightUp
			moveRightUp(); //move bullet rightup
		else if(rightDown)  // if the player is facing rightDown
			moveRightDown(); //move bullet rightdown
		else if(up)  // if the player is facing up
			moveUp();  //move bullet up
		else if(down)  // if the player is facing down
			moveDown(); //move bullet down
	}
	//move bullet right
	public void moveRight()
	{
		image = images[0].getImage(); // gets correct bullet image
		
		setPositionX(getPositionX() + BULLET_SPEED); // move the bullet in correct direction
		
		
		if (getPositionX() > BOARD_WIDTH) // if is goes of the board remove it from the screen
			setVisible(false);
	}
	
	//move bullet rightup
	public void moveRightUp()
	{
		
		image = images[1].getImage(); // gets correct bullet image
		
		setPositionX(getPositionX() + BULLET_SPEED); // move the bullet in correct direction
		setPositionY(getPositionY() - BULLET_SPEED); // move the bullet in correct direction

		if (getPositionX() > BOARD_WIDTH) // if is goes of the board remove it from the screen
			setVisible(false);
	}
	
	//move bullet rightDown
	public void moveRightDown()
	{
		
		image = images[2].getImage(); // gets correct bullet image
		
		setPositionX(getPositionX() + BULLET_SPEED);
		setPositionY(getPositionY() + BULLET_SPEED);

		if (getPositionX() > BOARD_WIDTH) // if is goes of the board remove it from the screen
			setVisible(false);
	}
	
	//move bullet leftDown
	public void moveLeftDown()
	{
		
		image = images[3].getImage(); // gets correct bullet image
		
		setPositionX(getPositionX() - BULLET_SPEED); // move the bullet in correct direction
		setPositionY(getPositionY() + BULLET_SPEED); // move the bullet in correct direction

		if (getPositionX() > BOARD_WIDTH) // if is goes of the board remove it from the screen
			setVisible(false);
	}
	
	//move bullet leftUp
	public void moveLeftUp()
	{
		
		image = images[4].getImage(); // gets correct bullet image
		
		setPositionX(getPositionX() - BULLET_SPEED); // move the bullet in correct direction
		setPositionY(getPositionY() - BULLET_SPEED); // move the bullet in correct direction

		if (getPositionX() > BOARD_WIDTH) // if is goes of the board remove it from the screen
			setVisible(false);
	}
	
	//move bullet left
	public void moveLeft() 
	{
		
		image = images[5].getImage(); // gets correct bullet image
		
		setPositionX(getPositionX() - BULLET_SPEED); // move the bullet in correct direction
 
		if (getPositionX() < 0) // if is goes of the board remove it from the screen
			setVisible(false);
	}
	
	//move bullet up
	public void moveUp()
	{
		image = images[6].getImage(); // gets correct bullet image
		
		setPositionY(getPositionY() - BULLET_SPEED); // move the bullet in correct direction

		if (getPositionY() > BOARD_HEIGHT) // if is goes of the board remove it from the screen
			setVisible(false);
	}
	//move bullet down
	public void moveDown() 
	{
		image = images[7].getImage(); // gets correct bullet image
		
		setPositionY(getPositionY() + BULLET_SPEED); // move the bullet in correct direction

		if (getPositionY() < 0) // if is goes of the board remove it from the screen
			setVisible(false);
	}

	// gets the area of the bullet for collision detection
	public Rectangle getBounds() 
	{
		return new Rectangle(getPositionX(), getPositionY(), getWidth(), getHeight()); // returns the area
	}

}
