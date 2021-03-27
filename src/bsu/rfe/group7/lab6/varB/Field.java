package bsu.rfe.group7.lab6.varB;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Field extends JPanel {

	private boolean paused;
	private boolean magnetism=false;
	
	private ArrayList<BouncingBalls> balls = new ArrayList<BouncingBalls>(10);

	private Timer repaintTimer = new Timer(10, new ActionListener() {
		public void actionPerformed(ActionEvent ev) {
			repaint();
		}
	});

	public Field() {
		setBackground(Color.WHITE);
		repaintTimer.start();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D canvas = (Graphics2D) g;
	
		for (BouncingBalls ball: balls) {
			ball.paint(canvas);
		}
	}

	public void addBall() {
		balls.add(new BouncingBalls(this));
	}
	public synchronized void pause() {
		paused = true;
	}
	
	public synchronized void magnetismOn() {
		magnetism = true;
	}

	public synchronized void resume() {
		paused = false;
		notifyAll();
	}

	public synchronized void canMove(BouncingBalls ball) throws InterruptedException {
		if ((paused)||(magnetism && ball.isLocked())) {
			wait();
		}
	}

}
