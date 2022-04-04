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
	Player player = new Player(400, 250, 100);	
	static ArrayList<Enemy> enemies = new ArrayList<Enemy>(); 
	static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	Background background = new Background(0, 0);	
	int weaponCounter = 1;
	long start = System.currentTimeMillis();
	long endTime = start + 9000;
	int timer = 60;
	boolean win = false;
	int xpPercent = 0;
	int level = 1;
	int iFrames = 0;
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		background.paint(g);
		player.paint(g);
		for(Enemy enemy:enemies) {
			enemy.paint(g, player);
		}
		
		
		for(Weapon weapon:weapons) {
			if(weapon != null) weapon.paint(g);
		}
		
		
		//////////////////////////xp bar////////////////////
		g.setColor(Color.cyan);
		g.fillRect(0, 10, xpPercent*9, 20);
		Font c1 = new Font ("Terminal", Font.BOLD, 15);
		g.setFont(c1);
		g.setColor(Color.black);
		g.drawString("LV: " + level, 10, 25);
		g.drawString("Kills until next level: " + (5 - xpPercent/20), 700, 25);
		if(xpPercent == 100) {
			level++;
			xpPercent = 0;
		}
		/////////////////////////timer//////////////////////
		Font c2 = new Font ("Terminal", Font.BOLD, 25);
		g.setFont(c2);
		g.setColor(Color.black);
		g.drawString(timer+"", 410, 100);//keep this on top of everything painted
		start+=2;
		if((endTime-start)%150 == 0 && !win) {	
			timer--;
		}
		
		if(start == endTime) {
			win = true;
			System.out.println("win");
		}
		
		//////////////////////////Enemy Spawn////////////////////////
		if(!win) {
			int spawn = (int)(Math.random() * 200);
			if(spawn == 2) {
				enemies.add(new Enemy((int)(Math.random() * 500) + 300, (int)(Math.random() * 300) + 300, 100.0, (Math.random() * 10)));
			}
			if(spawn == 3) {
				enemies.add(new Enemy(-(int)(Math.random() * 500) - 300, -(int)(Math.random() * 300) - 300, 100.0, (Math.random() * 10)));
			}
		}
		
		
		
		//////////////////////////////////////////////////////////////////Enemy Movement//////////////////////////////////////////////////////////////
		if(enemies.size() > 0) {
			for(Enemy enemy:enemies) {
				if(enemy.getX() < player.getX()) {
					enemy.setX(enemy.getX()+1);
//					System.out.println("moving right");
				}
				else if(enemy.getX() > player.getX()) {
					enemy.setX(enemy.getX()-1);
//					System.out.println("moving left");
				}
				if(enemy.getY() < player.getY()) {
					enemy.setY(enemy.getY()+1);
//					System.out.println("moving down");
				}
				else if(enemy.getY() > player.getY()) {
					enemy.setY(enemy.getY()-1);
//					System.out.println("moving up");
				}	
		}
		
/////////////////////////////////////////////Enemy hurt detection///////////////////////////////
			if(enemies.size() > 0) {
				for(int i = 0; i < enemies.size(); i++) {
					if(enemies.get(i).getCurrHealth() == 0) {
						enemies.remove(i);
						xpPercent += 20/level;
					}
				}
			}
			if(weapons.size() > 0) {
				for(int i = 1; i < weapons.size(); i++) {
					for(Enemy e : enemies) {
						if(weapons.get(i) != null && weapons.get(i).getX() >= e.getX()+5 && weapons.get(i).getX() <= e.getX()+38+5) {
							if(weapons.get(i).getY() >= e.getY()+7 && weapons.get(i).getY() <= e.getY()+7+58) {
								weapons.remove(i);
								i--;
								weaponCounter--;
								System.out.println("hit");
								e.setCurrHealth(e.getCurrHealth()-10);
								e.setCurrHealthPercentage(e.getCurrHealth()/e.getMaxHealth());
							}
						}
					}
				}
			}
		////////////////Player hurt detection/////////////////
		iFrames++;
		g.drawRect(player.getX(), player.getY(), 50, 77);
		for(Enemy e: enemies) {
			if(e.getX() >= player.getX() && e.getX() <= player.getX()+50 && iFrames > 100) {
				if(e.getY() >= player.getY() && e.getY() <= player.getY() + 77) {
					player.setCurrHealth(player.getCurrHealth()-5);
					player.setCurrHealthPercentage(player.getCurrHealth()/player.getMaxHealth());
					iFrames = 0;
				}
			}
		}
		
		
		
		
		
		}
	}
		
	public static void main(String[] arg) {
		Frame f = new Frame();
		weapons.add(null);
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
	    if(weapons.get(weaponCounter) != null) {
	    	weapons.get(weaponCounter).setSpeedX(speed_X);
		    weapons.get(weaponCounter).setSpeedY(speed_Y);
		    weaponCounter++;
	    }
	    
	    
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
		if(enemies.size() > 0) {
			for(Enemy enemy:enemies) {
				if(keycode == 87) {
					enemy.setSpeedY(2);
				}
				else if(keycode == 65) {
					enemy.setSpeedX(2);
				}
				else if(keycode == 83) {
					enemy.setSpeedY(-2);
				}
				else if(keycode == 68) {
					enemy.setSpeedX(-2);
				}
			}
		}
		
		if(keycode == 87) {
			background.setSpeedY(2);
		}
		
		else if(keycode == 65) {
			background.setSpeedX(2);
		}
		else if(keycode == 83) {
			background.setSpeedY(-2);
		}
		else if(keycode == 68) {
			background.setSpeedX(-2);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keycode = e.getKeyCode();
		if(enemies.size() > 0) {
			for(Enemy enemy:enemies) {
				if(keycode == 87 || keycode == 83) {
					enemy.setSpeedY(0);
				}
				else if(keycode == 65 || keycode == 68) {
					enemy.setSpeedX(0);
				}
			}
		}
		if(keycode == 87 || keycode == 83) {
				background.setSpeedY(0);
			}
			else if(keycode == 65 || keycode == 68) {
				background.setSpeedX(0);
			}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
