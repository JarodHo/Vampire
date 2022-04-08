import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Background{
	private double x, y;
	private Image img; 	
	private AffineTransform tx;
	private float speedX, speedY = 0;
	
	public Background(int x, int y) {
		img = getImage("/imgs/Map001.png"); //load the image for Tree
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
		g2.drawImage(img, tx, null);
		x += speedX;
		y += speedY;
		update();
	}
	
	private void update()
	{	
		tx.setToTranslation(x, y);
		tx.scale(2, 2);
	}
	
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(2, 2);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Background.class.getResource(path);
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
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public float getSpeedY() {
		return speedY;
	}
	public void setSpeedY(float speed_Y) {
		this.speedY = speed_Y;
	}

}
