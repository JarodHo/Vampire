import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class PowerUps{
	private int x, y;
	private Image img; 	
	private AffineTransform tx;
	private float speedX, speedY = 0;
	private int picture;
	
	public PowerUps(int x, int y, int picture) {
		this.picture = picture;
		if(this.picture == 0) {
			img = getImage("/imgs/heal.png"); //load the image for Tree
		}else if( this.picture == 1) {
			img = getImage("/imgs/Speed.png"); //load the image for Tree
		}else if (this.picture == 2) {
			img = getImage("/imgs/Strength.png"); //load the image for Tree
		}
		
		//put the background here^^^^
		this.x = x;
		this.y = y;
		
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}
	
	public void changePicture(String newFileName) {
		img = getImage(newFileName);
		init(x, y);
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		if(this.picture == 0) {
			img = getImage("/imgs/heal.png"); //load the image for Tree
		}else if( this.picture == 1) {
			img = getImage("/imgs/Speed.png"); //load the image for Tree
		}else if (this.picture == 2) {
			img = getImage("/imgs/Strength.png"); //load the image for Tree
		}
		g2.drawImage(img, tx, null);	
		x += speedX;
		y += speedY;		
		update();
	}
		
	private void update()
	{	
		tx.setToTranslation(x, y);
		tx.scale(1, 1);
	}
	
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(1, 1);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = PowerUps.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	public float getSpeedX() {
		return speedX;
	}
	public void setSpeedX(float speed_X) {
		this.speedX = speed_X;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int getPicture() {
		return picture;
	}

	public void setPicture(int picture) {
		this.picture = picture;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getSpeedY() {
		return speedY;
	}
	public void setSpeedY(float speed_Y) {
		this.speedY = speed_Y;
	}

}
