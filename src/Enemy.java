import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Enemy{
	private double x, y;
	private Image img; 	
	private Image img2;
	private AffineTransform tx;
	private AffineTransform tx2;
	private double speedX, speedY = 0;
	private double attack;
	private double currHealth;
	private double maxHealth;
	private double currHealthPercentage;
	private int enemyType;
	private int level;
	
	public Enemy(int level, boolean left) {
		this.level = level;
		if(left) {
			this.x = -(int)(Math.random() * 500) - 300;
			this.y = -(int)(Math.random() * 300) - 300;
		}
		else {
			this.x = (int)(Math.random() * 500) + 300;
			this.y = (int)(Math.random() * 300) + 300;
		}
		enemyType = (int)(Math.random() * 3);
		if(enemyType == 0) {
			img = getImage("/imgs/vamp.gif"); //load the image for Tree
			img2 = getImage("/imgs/HealthBar.PNG");
			this.currHealth = 100.0*(level+.5);
			this.maxHealth = 100.0*(level+.5);
			this.attack = (Math.random() * 10) + level;
			currHealthPercentage = currHealth/this.maxHealth;
			tx = AffineTransform.getTranslateInstance(x, y);
			tx2 = AffineTransform.getTranslateInstance(x+2, y+65);
			init(x,y);
		}
		else if(enemyType == 1) {
			img = getImage("/imgs/vamp.gif"); //load the image for Tree
			img2 = getImage("/imgs/HealthBar.PNG");
			this.currHealth = 100.0;
			this.maxHealth = 100.0;
			this.attack = (Math.random() * 10);
			currHealthPercentage = currHealth/this.maxHealth;
			tx = AffineTransform.getTranslateInstance(x, y);
			tx2 = AffineTransform.getTranslateInstance(x+2, y+65);
			init(x,y);
		}
		else if(enemyType == 2) {
			img = getImage("/imgs/bat.gif"); //load the image for Tree
			img2 = getImage("/imgs/HealthBar.PNG");
			this.currHealth = 50.0*(level+.5);
			this.maxHealth = 50.0*(level+.5);
			this.attack = (Math.random() * 12)+level*1.5;
			currHealthPercentage = currHealth/this.maxHealth;
			tx = AffineTransform.getTranslateInstance(x, y);
			tx2 = AffineTransform.getTranslateInstance(x+10, y+65);
			init(x,y);
		}
		
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
			g2.drawImage(img, (int)x + img.getWidth(null)/4, (int)y, -img.getWidth(null)/4, img.getHeight(null)/4, null);
		}
		
		g2.drawImage(img2, tx2, null);
		
		x += speedX;
		y += speedY;
		
		update();
		
	}
	
	public double getSpeedX() {
		return speedX;
	}
	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	private void update()
	{	
		tx.setToTranslation(x, y);
		tx.scale(0.25, 0.25);
		if(enemyType == 0) {
			tx2.setToTranslation(x+2, y+65);
		}
		else if(enemyType == 2) {
			tx2.setToTranslation(x+10, y+65);
		}
		else {
			tx2.setToTranslation(x+2, y+65);
		}
		
		tx2.scale(currHealthPercentage/20, .05);	
	}
	public int getEnemyType() {
		return enemyType;
	}

	public void setEnemyType(int enemyType) {
		this.enemyType = enemyType;
	}

	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(0.25, 0.25);
		tx2.setToTranslation(a+2, b+65);
		tx2.scale(currHealthPercentage/20, .05);	

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

	public double getSpeedY() {
		return speedY;
	}
	public void setSpeedY(double speedY) {
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