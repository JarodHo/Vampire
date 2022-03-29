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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	//creating objects such as characters, background, music/sound effects...
	Player player = new Player(400, 250);	
	Enemy enemy = new Enemy(400, 400);
	public void paint(Graphics g) {
		super.paintComponent(g);
		player.paint(g);
		enemy.paint(g);
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
			enemy.setSpeedY(5);
		}
		else if(keycode == 65) {
//			player.setSpeedX(-5);
			enemy.setSpeedX(5);
		}
		else if(keycode == 83) {
//			player.setSpeedY(5);
			enemy.setSpeedY(-5);
		}
		else if(keycode == 68) {
//			player.setSpeedX(5);
			enemy.setSpeedX(-5);
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
