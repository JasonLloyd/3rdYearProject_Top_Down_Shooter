import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Drop extends Sprite
{
	private Image image; // images of the frop
	
	public Drop(int x,int y)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Sprites/dropScore.png")); // gets the image
		image = ii.getImage(); // scores the image 
		setVisible(true); // set it visiable
		
		// sets its width and height
		setWidth(image.getWidth(null) - 5); 
		setHeight(image.getHeight(null) - 5);
		
		//sets its X and Y position
		setPositionX(x);
		setPositionY(y);
		
	}
	//gets the image
	public Image getImage() 
	{
		return image;
	}
	
	//gets the area of the rectangle
	public Rectangle getBounds() 
	{
		return new Rectangle(getPositionX(), getPositionY(), getWidth(), getHeight());
	}
}
