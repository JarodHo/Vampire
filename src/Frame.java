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
	static ArrayList<Weapon> aura = new ArrayList<Weapon>();
	int auraTimer = 0;
	Background background = new Background(-800, -750);	
	Background loseScreenVampire = new Background(350, 200);
	int weaponCounter = 1;
	long start = System.currentTimeMillis();
	long endTime = start + 18000;
	int timer = 120;
	int oldTimer;
	boolean win = false;
	boolean menu = true;
	boolean instructions = false;
	int xpPercent = 0;
	int level = 1;
	int enemyLevel = 1;
	boolean movingUp = true;
	boolean movingDown = true;
	boolean movingRight = true;
	boolean movingLeft = true;
	double moveVert, moveHori;
	double oldX, oldY;
	int iFrames = 0;
	boolean waterWalker = false;
	static Music music = new Music("bgm.wav", true);	
	static boolean gameState;
	static Music pew = new Music("pew.wav", false);
	static Music enemyDeath = new Music("enemydeath.wav", false);
	static Music playerHurt = new Music("playerhurt.wav", false);
	PowerUps pw1 = new PowerUps(350, 175+9, 0);//change these to numbers
	PowerUps pw2 = new PowerUps(350, 300+9, 1);
	PowerUps pw3 = new PowerUps(350, 425+9, 2);
	static ArrayList<Integer> powerUps = new ArrayList<Integer>();
	static ArrayList<Integer> shown = new ArrayList<Integer>();
	int buffer = 0;
	Red r = new Red(0, 0);
	double[][] obstacles = new double[4][4];
	int score = 0;
	boolean alive = true;
	boolean levelUp = false;
	boolean enemyLevelUp = true;
	int x = 387;
	int y1 = 200;
	int y2 = 210;
	int y3 = 325;
	int y4 = 335;
	int y5 = 450;
	int y6 = 460;
	boolean aura2 = false;
	
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
		else if(instructions){
			g.setColor(Color.gray);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.white);
			InputStream myFile3 = Frame.class.getResourceAsStream("/fonts/PressStart2P.ttf");
			try {
				g.setFont(Font.createFont(Font.TRUETYPE_FONT, myFile3).deriveFont(Font.BOLD, 24F));
			} catch (FontFormatException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {	
			// TODO Auto-generated catch block
				e.printStackTrace();	
			}	
			g.drawString("How to Play", 150, 100);
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
			g.drawString("Use WASD to move", 150, 150);
			g.drawString("Use Left-Click to attack", 150, 175);
			g.drawString("Avoid the monsters, get stronger, and survive" , 150, 200);
			g.drawString("for 120 seconds!", 150, 225);
			
			g.setColor(Color.black);
			g.fillRect(375, 500, 150, 50);
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


				if(player.getCurrHealth() < 100-(.05*powerUps.get(0))) {
				player.setCurrHealth(player.getCurrHealth() + (0.02*powerUps.get(0)));
				player.setCurrHealthPercentage(player.getCurrHealth()/player.getMaxHealth());
				}
	
				for(Enemy enemy:enemies) {
					enemy.paint(g, player);
				}
				////////////aura///////////////
					if(powerUps.get(4) > 0 && auraTimer%2 == 0 && !aura2) {	
						aura2 = true;
						for(int i = 1; i < aura.size(); i++) {
							if(aura.get(i).getX() < -1 || aura.get(i).getX() > 901) {
								aura.remove(i);
								i--;
							}
						}
						aura.add(new Weapon());
						aura.add(new Weapon());
						aura.add(new Weapon());
						aura.add(new Weapon());
						for(int i = 1; i < aura.size(); i++) {
							if(i % 4 == 1) {
								aura.get(i).setSpeedX(-player.weaponSpeed);
								aura.get(i).setSpeedY(-player.weaponSpeed);
							}
							if(i % 4 == 2) {
								aura.get(i).setSpeedX(-player.weaponSpeed);
								aura.get(i).setSpeedY(player.weaponSpeed);
							}
							if(i % 4 == 3) {
								aura.get(i).setSpeedX(player.weaponSpeed);
								aura.get(i).setSpeedY(-player.weaponSpeed);
							}
							if(i % 4 == 0) {
								aura.get(i).setSpeedX(player.weaponSpeed);
								aura.get(i).setSpeedY(player.weaponSpeed);
							}

						}
						
					}
					if(auraTimer %2 == 1) {
						aura2 = false;
					}
					for(Weapon w: aura) {
						if(w != null) {
							if(w.getSpeedX() != 0) {
								w.paint(g);
							}
						}
					}
				//////////////////////////////
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
					auraTimer++;
					oldX = background.getX();
					oldY = background.getY();
					moveHori = background.getX() - oldX;
					moveVert = background.getY() - oldY;
				}
				if(timer == 91 || timer == 61 || timer == 31){
					enemyLevelUp = true;
				}
				if((timer == 90 || timer == 60 || timer == 30) && enemyLevelUp) {
					enemyLevel++;
					enemyLevelUp = false;
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
						if(enemies.get(i).getX() < player.getX()+moveHori) {
							enemies.get(i).setX(enemies.get(i).getX()+Math.random()*1.25+(0.5*enemies.get(i).getEnemyType()));
						}
						else if(enemies.get(i).getX() > player.getX()+38+moveHori) {
							enemies.get(i).setX(enemies.get(i).getX()-Math.random()*1.25-(0.5*enemies.get(i).getEnemyType()));
						}
						if(enemies.get(i).getY() < player.getY() + moveVert) {
							enemies.get(i).setY(enemies.get(i).getY()+Math.random()*1.25+(0.5*enemies.get(i).getEnemyType()));
						}
						else if(enemies.get(i).getY() > player.getY() + 58+ moveVert) {
							enemies.get(i).setY(enemies.get(i).getY()-Math.random()*1.25-(0.5*enemies.get(i).getEnemyType()));
						}
		
					}
					//38 x 58
					
				
		/////////////////////////////////////////////Enemy hurt detection///////////////////////////////
					if(enemies.size() > 0) {
						for(int i = 0; i < enemies.size(); i++) {
							if(enemies.get(i).getCurrHealth() <= 0) {
								enemies.remove(i);
								enemyDeath.play();
								xpPercent += 20/level;
								if(player.getCurrHealth() < 100) {
									player.setCurrHealth(player.getCurrHealth() + 2 * powerUps.get(3));
								}
								
								player.setCurrHealthPercentage(player.getCurrHealth() / player.getMaxHealth());
								//xpPercent += 100;
								score += 50;
							}
						}
					}

					if(aura.size() > 0) {
						for(int i = 1; i < aura.size(); i++) {
							for(Enemy e : enemies) {
								if(aura.get(i) != null && aura.get(i).getX() >= e.getX()+5 && aura.get(i).getX() <= e.getX()+38+5) {
									if(aura.get(i).getY() >= e.getY()+7 && aura.get(i).getY() <= e.getY()+7+58) {
//										aura.get(i).setX(100000);	
										e.setCurrHealth(e.getCurrHealth() - (5*powerUps.get(4)));
										e.setCurrHealthPercentage(e.getCurrHealth()/e.getMaxHealth());
									}
								}
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
					counter++;
					if(counter == 4) {
						counter = 0;
					}
	
					if(background.getX() >= obstacle[0]-2 && background.getX() <= obstacle[0] && background.getY() < obstacle[2]	&& background.getY() > obstacle[3]) {
						background.setSpeedX(0);
						background.setX(obstacle[0]);
						movingRight = false;
					}
					if(background.getX() <= obstacle[1]+2 && background.getX() >= obstacle[1] && background.getY() < obstacle[2]	&& background.getY() > obstacle[3]) {
						background.setSpeedX(0);
						background.setX(obstacle[1]);
						movingLeft = false;
					}
					if(background.getY() >= obstacle[2]-2 && background.getY() <= obstacle[2]&& background.getX() < obstacle[0] && background.getX() > obstacle[1]) {
						background.setSpeedY(0);
						background.setY(obstacle[2]);
						movingDown = false;
					}
					if(background.getY() <= obstacle[3]+2 && background.getY() >= obstacle[3] && background.getX() < obstacle[0] && background.getX() > obstacle[1]) {
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
						int random1 = (int)(Math.random() * 5);
						int random2 = (int)(Math.random() * 5);
						int random3 = (int)(Math.random() * 5);
						while(random1 == random2) {
							random2 = (int)(Math.random() * 5);
						}
						while(random1 == random3 || random2 == random3) {
							random3 = (int)(Math.random() * 5);
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
					pw1.paint(g);
					pw2.paint(g);
					pw3.paint(g);
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
					
					
					
					  for(int i = 0; i < shown.size(); i++) {
						if(shown.get(i) == 0) {
							if(i == 0){
								g.drawString("Regenerate a bit of ", x, y1);
								g.drawString("health every second", x, y2);
								pw1.setPicture(0);
							}else if (i == 1){
								g.drawString("Regenerate a bit of ", x, y3);
								g.drawString("health every second", x, y4);
								pw2.setPicture(0);
							}else if (i == 2){
								g.drawString("Regenerate a bit of ", x, y5);
								g.drawString("health every second", x, y6);
								pw3.setPicture(0);
							}
							
						}
						if(shown.get(i) == 1){
							if(i==0){
								g.drawString("Increased movement", x, y1);
								g.drawString("speed", x, y2);
								pw1.setPicture(1);
							}else if (i == 1){
								g.drawString("Increased movement", x, y3);
								g.drawString("speed", x, y4);
								pw2.setPicture(1);
							}else if (i == 2){
								g.drawString("Increased movement", x, y5);
								g.drawString("speed", x, y6);
								pw3.setPicture(1);
							}
						}
						if(shown.get(i) == 2){
							if(i==0){
									g.drawString("Increased weapon", x, y1);
									g.drawString("damage", x, y2);
									pw1.setPicture(2);
								}else if (i == 1){
									g.drawString("Increased weapon", x, y3);
									g.drawString("damage", x, y4);
									pw2.setPicture(2);
								}else if (i == 2){
									g.drawString("Increased weapon", x, y5);
									g.drawString("damage", x, y6);
									pw3.setPicture(2);
								}
						}
						if(shown.get(i) == 3){
							if(i==0){
									g.drawString("Heal after slaying", x, y1);
									g.drawString("an enemy", x, y2);
									pw1.setPicture(3);
								}else if (i == 1){
									g.drawString("Heal after slaying", x, y3);
									g.drawString("an enemy", x, y4);
									pw2.setPicture(3);
								}else if (i == 2){
									g.drawString("Heal after slaying", x, y5);
									g.drawString("an enemy", x, y6);
									pw3.setPicture(3);
								}
						}
						if(shown.get(i) == 4){
							if(i==0){
									g.drawString("Automatically shoot ", x, y1);
									g.drawString("around you", x, y2);
									pw1.setPicture(4);
								}else if (i == 1){
									g.drawString("Automatically shoot ", x, y3);
									g.drawString("around you", x, y4);
									pw2.setPicture(4);
								}else if (i == 2){
									g.drawString("Automatically shoot ", x, y5);
									g.drawString("around you", x, y6);
									pw3.setPicture(4);
								}
						}
					}

					
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
		aura.add(null);
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
				powerUps.add(0); //aura (auto shoots projectiles diagonally)
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
	    			instructions = true;
	    		}
	    	}
	    }
	    else if(instructions) {
	    	if(x >= 375 && x <= 375+180) {
	    		if(y >= 500 && y <= 500 + 80) {
	    			instructions = false;
	    			gameState = true;
	    		}
	    	}
	    }
	    if(!gameState && player.getCurrHealth() > 0) {
	    	if(x >= 350 && x <= 550 && y >= 150 && y <= 250 && buffer > 20) {
	    		if(pw1.getPicture() == 0) {
	    			powerUps.set(0, powerUps.get(0)+1);
	    		}else if (pw1.getPicture() == 1) {
	    			powerUps.set(1, powerUps.get(1)+1);
	    		}else if (pw1.getPicture() == 2) {
	    			powerUps.set(2, powerUps.get(2)+1);
	    		}else if (pw1.getPicture() == 3) {
	    			powerUps.set(3, powerUps.get(3)+1);
	    		}else if (pw1.getPicture() == 4) {
	    			powerUps.set(4, powerUps.get(4)+1);
	    		}
	    		gameState = true;
	    		buffer = 0;
	    		levelUp = false;
	    	}
	    	if(x >= 350 && x <= 550 && y >= 275 && y <= 375 && buffer > 20) {
	    		if(pw2.getPicture() == 0) {
	    			powerUps.set(0, powerUps.get(0)+1);
	    		}else if (pw2.getPicture() == 1) {
	    			powerUps.set(1, powerUps.get(1)+1);
	    		}else if (pw2.getPicture() == 2) {
	    			powerUps.set(2, powerUps.get(2)+1);
	    		}else if (pw2.getPicture() == 3) {
	    			powerUps.set(3, powerUps.get(3)+1);
	    		}else if (pw2.getPicture() == 4) {
	    			powerUps.set(4, powerUps.get(4)+1);
	    		}
	    		gameState = true;
	    		buffer = 0;
	    		levelUp = false;
	    	}
	    	if(x >= 350 && x <= 550 && y >= 400 && y <= 500 && buffer > 20) {
	    		if(pw3.getPicture() == 0) {
	    			powerUps.set(0, powerUps.get(0)+1);
	    		}else if (pw3.getPicture() == 1) {
	    			powerUps.set(1, powerUps.get(1)+1);
	    		}else if (pw3.getPicture() == 2) {
	    			powerUps.set(2, powerUps.get(2)+1);
	    		}else if (pw3.getPicture() == 3) {
	    			powerUps.set(3, powerUps.get(3)+1);
	    		}else if (pw3.getPicture() == 4) {
	    			powerUps.set(4, powerUps.get(4)+1);
	    		}
	    		gameState = true;
	    		buffer = 0;
	    		levelUp = false;
	    	}
	    }
	    if((!alive && !gameState) || win) {
//	    	g.fillRect(375, 500, 150, 50);
	    	if(x >= 375 && x <= 375+180) {
	    		if(y >= 500 && y <= 500 + 80) {
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
		    		enemyLevel = 1;
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

