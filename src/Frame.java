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
	Background background = new Background(-800, -750);	
	Background loseScreenVampire = new Background(350, 200);
	int weaponCounter = 1;
	long start = System.currentTimeMillis();
	long endTime = start + 18000;
	int timer = 120;
	boolean win = false;
	boolean menu = true;
	int xpPercent = 0;
	int level = 1;
	int enemyLevel = 1;
	boolean movingUp = true;
	boolean movingDown = true;
	boolean movingRight = true;
	boolean movingLeft = true;
	int iFrames = 0;
	boolean waterWalker = false;
	static Music music = new Music("bgm.wav", true);	
	static boolean gameState;
	static Music pew = new Music("pew.wav", false);
	static Music enemyDeath = new Music("enemydeath.wav", false);
	static Music playerHurt = new Music("playerhurt.wav", false);
	PowerUps heal = new PowerUps(350, 175+9, 0);//change these to numbers
	PowerUps speed = new PowerUps(350, 300+9, 1);
	PowerUps damage = new PowerUps(350, 425+9, 2);
	static ArrayList<Integer> powerUps = new ArrayList<Integer>();
	static ArrayList<Integer> shown = new ArrayList<Integer>();
	int buffer = 0;
	Red r = new Red(0, 0);
	double[][] obstacles = new double[4][4];
	int score = 0;
	boolean alive = true;
	boolean levelUp = false;
	int x = 387;
	int y1 = 200;
	int y2 = 210;
	int y3 = 325;
	int y4 = 335;
	int y5 = 450;
	int y6 = 460;
	
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
		if(menu) {
			g.setColor(Color.gray);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.white);
			InputStream myFile3 = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
			try {
			g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile3).deriveFont(Font.BOLD, 36F));
			} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();	
			}	
			g.drawString("Vampire Survivors", 150, 100);
			loseScreenVampire.changePicture("/imgs/player.gif");
			loseScreenVampire.scale2();
			loseScreenVampire.setX(370);
			loseScreenVampire.paint(g);
			g.setColor(Color.black);
			g.fillRect(375, 500, 150, 50);
			InputStream myFile4 = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
			try {
			g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile4).deriveFont(Font.BOLD, 12F));
			} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();	
			}	
			g.setColor(Color.white);
			g.drawString("Play", 425, 533);
		}
		else {
			background.paint(g);
			if(alive && !menu) {
				player.paint(g);
			}
			if(player.getCurrHealth() <= 0 && !menu) {
				alive = false;
				gameState = false;
			}
			if (gameState && !menu) {
	//			System.out.println(movingUp + " " + movingDown + " " + movingRight + " " + movingLeft);
	//			System.out.println(background.getX() + " : " + background.getY());
				
				System.out.println(level);

				if(player.getCurrHealth() < 100-(.05*powerUps.get(0))) {
				player.setCurrHealth(player.getCurrHealth() + (0.02*powerUps.get(0)));
				player.setCurrHealthPercentage(player.getCurrHealth()/player.getMaxHealth());
				}
	
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
				g.drawString("Kills until next level: " + (5*level - (xpPercent*level)/20), 700, 25);
				if(xpPercent >= 100) {
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
				if((endTime-start)%150 == 90 || (endTime-start)%150 == 60 || (endTime-start)%150 == 30) {
					enemyLevel++;
				}
	
				//////////////////////////Enemy Spawn////////////////////////
				if(!win) {
					int spawn = (int)(Math.random() * 200);
					if(spawn == 2) {
						enemies.add(new Enemy(enemyLevel, false));
					}
					if(spawn == 3) {
						enemies.add(new Enemy(enemyLevel,true));
					}
				}
				
				
				
				//////////////////////////////////////////////////////////////////Enemy Movement//////////////////////////////////////////////////////////////
				if(enemies.size() > 0) {
					for(int i = 0; i < enemies.size(); i++) {
	
						if(!movingUp) {
							enemies.get(i).setSpeedY(0);
						}
						if(!movingDown) {
							enemies.get(i).setSpeedY(0);
						}
						if(!movingLeft) {
							enemies.get(i).setSpeedX(0);
						}
						if(!movingRight) {
							enemies.get(i).setSpeedX(0);
						}
						if(enemies.get(i).getX() < player.getX()) {
							enemies.get(i).setX(enemies.get(i).getX()+0.5+(0.5*enemies.get(i).getEnemyType()));
		//								System.out.println("moving right");
						}
						else if(enemies.get(i).getX() > player.getX()+38) {
							enemies.get(i).setX(enemies.get(i).getX()-0.5-(0.5*enemies.get(i).getEnemyType()));
		//								System.out.println("moving left");
						}
						if(enemies.get(i).getY() < player.getY()) {
							enemies.get(i).setY(enemies.get(i).getY()+0.5+(0.5*enemies.get(i).getEnemyType()));
		//								System.out.println("moving down");
						}
						else if(enemies.get(i).getY() > player.getY() + 58) {
							enemies.get(i).setY(enemies.get(i).getY()-0.5-(0.5*enemies.get(i).getEnemyType()));
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
							if(enemies.get(i).getCurrHealth() <= 0) {
								enemies.remove(i);
								enemyDeath.play();
								xpPercent += 20/level;
	//							player.setCurrHealth(player.getCurrHealth() + 2 * powerUps.get(3));
	//							player.setCurrHealthPercentage(player.getCurrHealth() / player.getMaxHealth());
	//							xpPercent += 100;
								score += 50;
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
										e.setCurrHealth(e.getCurrHealth()-(15+(powerUps.get(2)*5)));
										e.setCurrHealthPercentage(e.getCurrHealth()/e.getMaxHealth());
									}
								}
							}	
						}
					}
					////////////////Player hurt detection/////////////////
					iFrames++;
	//				Color hurt = new Color(255, 0, 0, 127);
	//				g.setColor(hurt);
					for(Enemy e: enemies) {
						if(e.getX() >= player.getX() && e.getX() <= player.getX()+50 && iFrames > 100) {
							if(e.getY() >= player.getY() && e.getY() <= player.getY() + 77) {
	//							g.fillRect(0, 0, 10000, 10000);
								r.paint(g);
								player.setCurrHealth(player.getCurrHealth()-10);
								playerHurt.play();
								player.setCurrHealthPercentage(player.getCurrHealth()/player.getMaxHealth());
								iFrames = 0;
							}	
						}
					}
				}	
				if(background.getY() >= 0) {
					background.setSpeedY(0);
					background.setY(0);
					movingUp = false;
				}
				else {
					movingUp = true;
				}
				if(background.getY() <= -1830) {
					background.setSpeedY(0);
					background.setY(-1830);
					movingDown = false;
				}
				else {
					movingDown = true;
				}
				if(background.getX() >= 0) {
					background.setSpeedX(0);
					background.setX(0);
					movingLeft = false;
				}
				else {
					movingLeft = true;
				}
				if(background.getX() <= -1515) {
					background.setSpeedX(0);
					background.setX(-1515);
					movingRight = false;
				}
				else {
					movingRight = true;
				}
				int counter = 0;
				for(double[] obstacle:obstacles) {
	//				System.out.println("obstacle: " + counter);
					counter++;
					if(counter == 4) {
						counter = 0;
					}
	//				System.out.println(obstacle[0] + " " + obstacle[1] + " " + obstacle[2] + " " + obstacle[3]);
	//				System.out.println();
					if(background.getX() >= obstacle[0]-2 && background.getX() <= obstacle[0] && background.getY() < obstacle[2]	&& background.getY() > obstacle[3]) {
						System.out.println("can't move right");
						background.setSpeedX(0);
						background.setX(obstacle[0]);
						movingRight = false;
					}
					if(background.getX() <= obstacle[1]+2 && background.getX() >= obstacle[1] && background.getY() < obstacle[2]	&& background.getY() > obstacle[3]) {
						System.out.println("can't move left");
						background.setSpeedX(0);
						background.setX(obstacle[1]);
						movingLeft = false;
					}
					if(background.getY() >= obstacle[2]-2 && background.getY() <= obstacle[2]&& background.getX() < obstacle[0] && background.getX() > obstacle[1]) {
						System.out.println("can't move down");
						background.setSpeedY(0);
						background.setY(obstacle[2]);
						movingDown = false;
					}
					if(background.getY() <= obstacle[3]+2 && background.getY() >= obstacle[3] && background.getX() < obstacle[0] && background.getX() > obstacle[1]) {
						System.out.println("can't move up");
						background.setSpeedY(0);
						background.setY(obstacle[3]);
						movingUp = false;
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
				if(player.getCurrHealth() > 0 && !win) {
					// level up

					if(!levelUp) { 
						level++;
							int random1 = (int)(Math.random() * 3);
							int random2 = (int)(Math.random() * 3);
							int random3 = (int)(Math.random() * 3);
							while(random1 == random2) {
								random2 = (int)(Math.random() * 3);
							}
							while(random1 == random3 || random2 == random3) {
								random3 = (int)(Math.random() * 3);
							}
						shown.set(0, random1);
						shown.set(1, random2);
						shown.set(2, random3);					
						
						levelUp = true;
					}
					xpPercent = 0;
					g.setColor(Color.black);
					g.fillRect(300, 100, 300, 450);
					g.setColor(Color.white);
					g.drawString("Choose a Power-up", 340, 130);
					g.setColor(Color.gray);
					g.fillRect(350, 150, 200, 100);
					g.fillRect(350, 275, 200, 100);
					g.fillRect(350, 400, 200, 100);
					heal.paint(g);
					speed.paint(g);
					damage.paint(g);
					g.setColor(Color.white);
					buffer++;
					InputStream myFile2 = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
					try {
						g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile2).deriveFont(Font.BOLD, 8F));
					} catch (FontFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {	
						// TODO Auto-generated catch block
						e.printStackTrace();	
					}
					
					
					/*
					 * for(int i = 0; i < shown.size(); i++) {
						if(shown.get(i) == 0) {
							if(i == 0){
								g.drawString("Regenerate a bit of ", x, y1);
								g.drawString("health every second", x, y2);
								
							}else if (i == 1){
								g.drawString("Regenerate a bit of ", x, y3);
								g.drawString("health every second", x, y4);
							}else if (i == 2){
								g.drawString("Regenerate a bit of ", x, y5);
								g.drawString("health every second", x, y6);
							}
							
						}
					}
					 */
					
					g.drawString("Regenerate a bit of ", x, 200);
					g.drawString("health every second", x, 210);
					g.drawString("Increased movement", x, 325);
					g.drawString("speed", x, 335);
					g.drawString("Inceased weapon", x, 450);
					g.drawString("damage", x, 460);
					
				}
				else if(!alive){
					g.setColor(Color.gray);
					g.fillRect(0, 0, 1000, 1000);
					g.setColor(Color.red);
					InputStream myFile3 = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
					try {
						g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile3).deriveFont(Font.BOLD, 36F));
					} catch (FontFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {	
						// TODO Auto-generated catch block
						e.printStackTrace();	
					}	
					g.drawString("Game Over", 300, 200);
					loseScreenVampire.changePicture("/imgs/vamp.gif");
					loseScreenVampire.scale();
					loseScreenVampire.paint(g);
					g.setColor(Color.black);
					g.drawString("Your score: "+ score, 200, 150);
					g.fillRect(375, 500, 150, 50);
					InputStream myFile4 = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
					try {
						g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile4).deriveFont(Font.BOLD, 24F));
					} catch (FontFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {	
						// TODO Auto-generated catch block
						e.printStackTrace();	
					}	
					g.setColor(Color.white);
					g.drawString("Retry", 390, 540);
					for(int i =0; i < enemies.size(); i++) {
		    			enemies.remove(0);
		    		}
				}
					
			}
			////////////////////////////////////////////win/////////////////////////////
					
			if(timer == -1 && alive) {
			win = true;
			System.out.println("win");
			gameState = false;
			
			g.setColor(Color.gray);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.white);
			InputStream myFile3 = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
			try {
			g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile3).deriveFont(Font.BOLD, 36F));
			} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();	
			}	
			g.drawString("You survived!", 200, 100);
			loseScreenVampire.changePicture("/imgs/player.gif");
			loseScreenVampire.scale2();
			loseScreenVampire.setX(370);
			loseScreenVampire.paint(g);
			g.setColor(Color.black);
			g.drawString("Your score: "+ score, 200, 150);
			g.fillRect(375, 500, 150, 50);
			InputStream myFile4 = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
			try {
			g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile4).deriveFont(Font.BOLD, 12F));
			} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();	
			}	
			g.setColor(Color.white);
			g.drawString("Play again", 390, 535);
			for(int i =0; i < enemies.size(); i++) {
			enemies.remove(0);
			}
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
		obstacles[0][0] = -515.5;
		obstacles[0][1] = -748;
		obstacles[0][2] = -784;
		obstacles[0][3] = -1038;
		obstacles[1][0] = -325;
		obstacles[1][1] = -647.5;
		obstacles[1][2] = 0;
		obstacles[1][3] = -318;
		obstacles[2][0] = -898;
		obstacles[2][1] = -1093;
		obstacles[2][2] = 0;
		obstacles[2][3] = -126;
		obstacles[3][0] = -1042;
		obstacles[3][1] = -1087;
		obstacles[3][2] = 0;
		obstacles[3][3] = -297;
				//int heal1 = 0;
		//		int speed1 = 0;
		//		int damage1 = 0;
				powerUps.add(0); //heal
				powerUps.add(0); //speed
				powerUps.add(0); //damage
				powerUps.add(0); //life steal (on kill)
				shown.add(0);
				shown.add(0);
				shown.add(0);
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
	    
	    pew.play();
	    if(menu) {
	    	if(x >= 375 && x <= 375+180) {
	    		if(y >= 500 && y <= 500 + 80) {
	    			menu = false;
	    			gameState = true;
	    		}
	    	}
	    }
	    if(!gameState && player.getCurrHealth() > 0) {
	    	if(x >= 350 && x <= 550 && y >= 150 && y <= 250 && buffer > 20) {
	    		powerUps.set(0, powerUps.get(0)+1);
	    		gameState = true;
	    		buffer = 0;
	    		levelUp = false;
	    	}
	    	if(x >= 350 && x <= 550 && y >= 275 && y <= 375 && buffer > 20) {
	    		powerUps.set(1, powerUps.get(1)+1);
	    		gameState = true;
	    		buffer = 0;
	    		levelUp = false;
	    	}
	    	if(x >= 350 && x <= 550 && y >= 400 && y <= 500 && buffer > 20) {
	    		powerUps.set(2, powerUps.get(2)+1);
	    		gameState = true;
	    		buffer = 0;
	    		levelUp = false;
	    	}
	    }
	    if((!alive && !gameState) || win) {
//	    	g.fillRect(375, 500, 150, 50);
	    	if(x >= 375 && x <= 375+180) {
	    		if(y >= 500 && y <= 500 + 80) {
	    			System.out.println("a");
	    			alive = true;
		    		gameState = true;
		    		
		    		player.setX(400);
		    		player.setY(250);
		    		background.setX(-800);
		    		background.setY(-750);
		    		player.setCurrHealth(100);
		    		player.setCurrHealthPercentage(player.getCurrHealth() / player.getMaxHealth());
		    		start = System.currentTimeMillis();
		    		endTime = start + 18000;
		    		timer = 120;
		    		win = false;
		    		xpPercent = 0;
		    		level = 1;
		    		score = 0;
		    		for(int i =0; i < powerUps.size(); i++) {
		    			powerUps.set(i, 0);
		    		}
	    		}
	    	}
	    }
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

		if(gameState) {
			int keycode = e.getKeyCode();
			if(enemies.size() > 0) {
				for(Enemy enemy:enemies) {
					if(keycode == 87 && movingUp) {
 						enemy.setSpeedY(1.5 + .05*powerUps.get(1));
 					}
					else if(keycode == 65 && movingLeft) {
 						enemy.setSpeedX(1.5 + .05*powerUps.get(1));
 					}
					else if(keycode == 83 && movingDown) {
 						enemy.setSpeedY(-1.5 - .05*powerUps.get(1));
 					}
					else if(keycode == 68 && movingRight) {
 						enemy.setSpeedX(-1.5 - .05*powerUps.get(1));
 					}
				}
			}
			
			if(keycode == 87) {
				player.changePicture("/imgs/player.gif");
				background.setSpeedY((float)(1.5 + .05*powerUps.get(1)));
				
			}
			
			else if(keycode == 65) {
				player.setRight(false);
				player.changePicture("/imgs/player.gif");
				background.setSpeedX((float)(1.5 + .05*powerUps.get(1)));
			
			}
			else if(keycode == 83) {
				player.changePicture("/imgs/player.gif");
				background.setSpeedY((float)(-1.5 - .05*powerUps.get(1)));
				
			}
			else if(keycode == 68) {
				player.setRight(true);
				player.changePicture("/imgs/player.gif");
				background.setSpeedX((float)(-1.5 - .15*powerUps.get(1)));
				
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

