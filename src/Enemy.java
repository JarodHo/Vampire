import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Enemy{
	private int x, y;
	private Image img; 	
	private AffineTransform tx;
	private int speedX, speedY = 0;
	
	public Enemy() {
		img = getImage("/imgs/vamp.png"); //load the image for Tree
		

		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}	
	public Enemy(int x, int y) {
		img = getImage("/imgs/vamp.png"); //load the image for Tree
		this.x = x;
		this.y = y;

		tx = AffineTransform.getTranslateInstance(x, y);
		init(x,y);
	}
	
	public void changePicture(String newFileName) {
		img = getImage(newFileName);
		init(x, y);
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		if (speedX < 0) {
			g2.drawImage(img, tx, null);
		}
		else {
			g2.drawImage(img, x + img.getWidth(null)/4, y, -img.getWidth(null)/4, img.getHeight(null)/4, null);

		}
		
		x += speedX;
		y += speedY;
		
		update();
		
	}
	
	public int getSpeedX() {
		return speedX;
	}
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	private void update()
	{	
		tx.setToTranslation(x, y);
		tx.scale(0.25, 0.25);
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
	public void setY(int y) {
		this.y = y;
	}

	public int getSpeedY() {
		return speedY;
	}
	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(0.25, 0.25);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Enemy.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}