import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
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
	Background background = new Background(-757, -915);	
	int weaponCounter = 1;
	long start = System.currentTimeMillis();
	long endTime = start + 9000;
	int timer = 60;
	boolean win = false;
	int xpPercent = 0;
	int level = 1;
	boolean movingUp = false;
	boolean movingDown = false;
	boolean movingRight = false;
	boolean movingLeft = false;
	int iFrames = 0;
	boolean waterWalker = false;
	static Music music = new Music("bgm.wav", true);	
	static boolean gameState;
	static Music pew = new Music("pew.wav", false);
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		InputStream myFile = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
		try {
			g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile).deriveFont(Font.BOLD, 12F));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background.paint(g);
		player.paint(g);
		if (gameState) {
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
			g.drawString("Kills until next level: " + (5*level - xpPercent/20), 700, 25);
			if(xpPercent == 100) {
				gameState = false;
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
				for(int i = 0; i < enemies.size(); i++) {
					if(enemies.get(i).getX() < player.getX()) {
						enemies.get(i).setX(enemies.get(i).getX()+1);
	//								System.out.println("moving right");
					}
					else if(enemies.get(i).getX() > player.getX()) {
						enemies.get(i).setX(enemies.get(i).getX()-1);
	//								System.out.println("moving left");
					}
					if(enemies.get(i).getY() < player.getY()) {
						enemies.get(i).setY(enemies.get(i).getY()+1);
	//								System.out.println("moving down");
					}
					else if(enemies.get(i).getY() > player.getY()) {
						enemies.get(i).setY(enemies.get(i).getY()-1);
	//								System.out.println("moving up");
					}
	
				}
				//38 x 58
				for(Enemy enemy1:enemies) {
					for(Enemy enemy2:enemies) {
						if(enemy1.getX() < enemy2.getX()+50 && enemy1.getX() > enemy2.getX() && enemy1.getY() < enemy2.getY()+70 && enemy1.getY() > enemy2.getY()) {
							enemy1.setX(enemy1.getX()+5);
						}
						
					}
				}
				
			
	/////////////////////////////////////////////Enemy hurt detection///////////////////////////////
				if(enemies.size() > 0) {
					for(int i = 0; i < enemies.size(); i++) {
						if(enemies.get(i).getCurrHealth() == 0) {
							enemies.remove(i);
							//xpPercent += 20/level;
							xpPercent += 100;
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
		else {
			for(Enemy enemy:enemies) {
				enemy.setSpeedY(0);
				enemy.setSpeedX(0);
			}
			background.setSpeedX(0);
			background.setSpeedY(0);
			if(player.getCurrHealth() > 0) {
				// level up
				level++;
				xpPercent = 0;
				g.setColor(Color.black);
				g.fillRect(300, 100, 300, 450);
				g.setColor(Color.white);
				g.drawString("Choose a Power-up", 340, 130);
				g.setColor(Color.gray);
				g.fillRect(350, 150, 200, 100);
				g.fillRect(350, 275, 200, 100);
				g.fillRect(350, 400, 200, 100);
				// draw or have image for menu to click one out of three choices for upgrade
				// check if user has clicked one of the options --> turn gameState back to true
			}
			else {
				// game over screen
				// show score
				// retry button
			}
		}
	}
		
	public static void main(String[] arg) {
		Frame f = new Frame();
		gameState = true;
		music.play();
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
	    
	    
	    System.out.println(background.getX() + " : " + background.getY());
	    pew.play();
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
		if(background.getY() >= 0) {
			background.setSpeedY(0);
			background.setY(-1);
			
		}
		if(background.getY() <= -1830) {
			background.setSpeedY(0);
			background.setY(-1829);
		}
		if(background.getX() >= 0) {
			background.setSpeedX(0);
			background.setX(-1);
			
		}
		if(background.getX() <= -1515) {
			background.setSpeedX(0);
			background.setX(-1514);
		}
		
	} 
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if(gameState) {
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
				player.changePicture("/imgs/player.gif");
				background.setSpeedY(2);
				
			}
			
			else if(keycode == 65) {
				player.setRight(false);
				player.changePicture("/imgs/player.gif");
				background.setSpeedX(2);
			
			}
			else if(keycode == 83) {
				player.changePicture("/imgs/player.gif");
				background.setSpeedY(-2);
				
			}
			else if(keycode == 68) {
				player.setRight(true);
				player.changePicture("/imgs/player.gif");
				background.setSpeedX(-2);
				
			}
		}
				}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(gameState) {
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
				player.changePicture("/imgs/player.png");
				background.setSpeedY(0);
			}
			else if(keycode == 65 || keycode == 68) {
				player.changePicture("/imgs/player.png");
				background.setSpeedX(0);
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
