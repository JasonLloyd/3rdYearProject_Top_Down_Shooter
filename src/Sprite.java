
// this class is the super class of all the sprites like the enemy or player or bullet
public abstract class Sprite implements Common
{
	private int directionX; // stores the X direction the sprite is moving
	private int directionY; //stores the Y direction the sprite is moving
	private int positionX; // stores the X position of the sprite
	private int positionY; //stores the Y position of the sprite

	private int width; // stores the Width of the sprite
	private int height; // stores the height of the sprite
	private boolean visible; // stores if the sprite is visible or not
	

	//constuctor
	public Sprite()
	{
		//sets the X and Y direction
		setDirectionX(this.directionX);
		setDirectionY(this.directionY);
		
		//sets the X and Y position
		setPositionX(this.positionX);
		setPositionY(this.positionY);
		
		//sets the height and width of a sprite
		setWidth(this.width);
		setHeight(this.height);
	}

	// setters for the direction and position
	public void setDirectionX(int directionX) 
	{
		this.directionX = directionX;
	}

	public void setDirectionY(int directionY) 
	{
		this.directionY = directionY;
	}

	public void setPositionX(int positionX) 
	{
		this.positionX = positionX;
	}

	public void setPositionY(int positionY) 
	{
		this.positionY = positionY;
	}

	//setting for the width and height
	public void setWidth(int width) 
	{
		this.width = width;
	}

	public void setHeight(int height) 
	{
		this.height = height;
	}
	
	
	public void setVisible(boolean visible) 
	{
		this.visible = visible;
	}
	
	public boolean isVisible()
	{
		return visible;
	}

	/*GETTERS*/
	public int getDirectionX() 
	{
		return directionX;
	}

	public int getDirectionY() 
	{
		return directionY;
	}

	public int getPositionX()
	{
		return positionX;
	}

	public int getPositionY() 
	{
		return positionY;
	}

	public int getWidth() 
	{
		return width;
	}

	public int getHeight() 
	{
		return height;
	}
	
	
}
