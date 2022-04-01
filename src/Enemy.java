import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Enemy{
	private int x, y;
	private Image img; 	
	private Image img2;
	private AffineTransform tx;
	private AffineTransform tx2;
	private int speedX, speedY = 0;
	private double currHealth;
	private double maxHealth;
	private double currHealthPercentage;
	
	public Enemy() {
		img = getImage("/imgs/vamp.png"); //load the image for Tree
		

		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}	
	public Enemy(int x, int y, double maxHealth) {
		img = getImage("/imgs/vamp.png"); //load the image for Tree
		img2 = getImage("/imgs/HealthBar.PNG");
		this.x = x;
		this.y = y;
		this.currHealth = maxHealth;
		this.maxHealth = maxHealth;
		currHealthPercentage = currHealth/this.maxHealth;
		tx = AffineTransform.getTranslateInstance(x, y);
		tx2 = AffineTransform.getTranslateInstance(x+3, y+65);
		init(x,y);
	}
	
	public void changePicture(String newFileName) {
		img = getImage(newFileName);
		init(x, y);
	}
	
	public void paint(Graphics g, Player other) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		if (this.getX() < other.getX()) {
			g2.drawImage(img, tx, null);
		}
		else {
			g2.drawImage(img, x + img.getWidth(null)/4, y, -img.getWidth(null)/4, img.getHeight(null)/4, null);
		}
		
		g2.drawImage(img2, tx2, null);
		
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
		tx2.setToTranslation(x+3, y+65);
		tx2.scale(currHealthPercentage/20, .05);	
	}
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(0.25, 0.25);
		tx2.setToTranslation(a+3, b+65);
		tx2.scale(currHealthPercentage/20, .05);	

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

	public double getCurrHealthPercentage() {
		return currHealthPercentage;
	}
	public void setCurrHealthPercentage(double currHealthPercentage) {
		this.currHealthPercentage = currHealthPercentage;
	}

	public double getCurrHealth() {
		return currHealth;
	}
	public void setCurrHealth(double currHealth) {
		this.currHealth = currHealth;
	}
	public double getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
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