import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TimerTask;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	//creating objects such as characters, background, music/sound effects...
	Player player = new Player(400, 250);	
	Enemy enemy = new Enemy(400, 400);
	ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	Background background = new Background(0, 0);
	int weaponCounter = 0;
	long start = System.currentTimeMillis();
	long endTime = start + 10;
	int timer = 60;
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		start+= 1;
		background.paint(g);
		player.paint(g);
		enemy.paint(g, player);
		for(Weapon weapon:weapons) {
			weapon.paint(g);
		}
		
//		if(start%1000==0) {
//			timer--;
//		}trying to display timer
		System.out.println(timer);
		if(start == endTime) {
			System.out.println("win");
		}
		
		
		
		
		
		
		//////////////////////////////////////////////////////////////////Enemy Movement//////////////////////////////////////////////////////////////
		if(enemy.getX() < player.getX()) {
			enemy.setX(enemy.getX()+1);
			System.out.println("moving right");
		}
		else if(enemy.getX() > player.getX()) {
			enemy.setX(enemy.getX()-1);
			System.out.println("moving left");
		}
		if(enemy.getY() < player.getY()) {
			enemy.setY(enemy.getY()+1);
			System.out.println("moving down");
		}
		else if(enemy.getY() > player.getY()) {
			enemy.setY(enemy.getY()-1);
//			System.out.println("moving up");
		}
	}
		
	public static void main(String[] arg) {
		Frame f = new Frame();

	}
	public Frame() {			
		JFrame f = new JFrame("Vampire Survivors");
		f.setSize(new Dimension(900, 600));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));	
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(1, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
	    int y = e.getY();
	    float length = (float) Math.sqrt(Math.pow((x - player.getX()), 2) + Math.pow((y - player.getY()), 2));
	    float speed_X = (x - player.getX()) /length * player.weaponSpeed;
	    float speed_Y = (y - player.getY()) /length * player.weaponSpeed;
	    weapons.add(new Weapon());
	    
	    weapons.get(weaponCounter).setSpeedX(speed_X);
	    weapons.get(weaponCounter).setSpeedY(speed_Y);
	    weaponCounter++;
	    System.out.println("pew");
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	} 
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keycode = e.getKeyCode();
		if(keycode == 87) {
//			player.setSpeedY(-5);
			enemy.setSpeedY(4);
		}
		else if(keycode == 65) {
//			player.setSpeedX(-5);
			enemy.setSpeedX(4);
		}
		else if(keycode == 83) {
//			player.setSpeedY(5);
			enemy.setSpeedY(-4);
		}
		else if(keycode == 68) {
//			player.setSpeedX(5);
			enemy.setSpeedX(-4);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keycode = e.getKeyCode();
		if(keycode == 87 || keycode == 83) {
//			player.setSpeedY(0);
			enemy.setSpeedY(0);
		}
		else if(keycode == 65 || keycode == 68) {
//			player.setSpeedX(0);
			enemy.setSpeedX(0);
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
