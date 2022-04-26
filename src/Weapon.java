import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Weapon {
	private int x = 407, y = 255;
	private Image img; 	
	private AffineTransform tx;
	private float speedX, speedY = 0;
	public Weapon() {
		img = getImage("/imgs/weapon.png"); //load the image for Tree
		

		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}
	public Weapon(float x, float y) {
		img = getImage("/imgs/weapon.png"); //load the image for Tree
		speedX = x;
		speedY = y;

		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}
	public Weapon(int x, int y) {
		img = getImage("/imgs/weapon.png"); //load the image for Tree
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
		g2.drawImage(img, tx, null);
		x += speedX;
		y += speedY;
		
		update();
		
	}
	
	public float getSpeedX() {
		return speedX;
	}
	public void setSpeedX(float speed_X) {
		this.speedX = speed_X;
	}
	private void update()
	{	
		tx.setToTranslation(x, y);
		tx.scale(.02, .02);
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
	

	public float getSpeedY() {
		return speedY;
	}
	public void setSpeedY(float speed_Y) {
		this.speedY = speed_Y;
	}
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(0.02, 0.02);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Player.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}


}
