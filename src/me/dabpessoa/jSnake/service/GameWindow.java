package me.dabpessoa.jSnake.service;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import me.dabpessoa.jSnake.model.enums.Direction;

public class GameWindow extends Canvas implements KeyListener {

	private GameGraphics gameGraphics;
	private Direction direction;
	
	public GameWindow(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.addKeyListener(this);
		
		this.gameGraphics = new GameGraphics(this);
	}

	public void showFrame(String windowName) {
		JFrame frame = new JFrame(windowName);
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}
	
	public void renderBackground() {
		Graphics graphics = getGameGraphics().getGraphics();
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, 480, 480);
	}
	
	public void renderGameOver() {
		Graphics graphics = getGameGraphics().getGraphics();
		
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 16);
        FontMetrics metr = getFontMetrics(small);

        graphics.setColor(Color.white);
        graphics.setFont(small);
        graphics.drawString(msg, (getPreferredSize().width - metr.stringWidth(msg)) / 2, getPreferredSize().height / 2);
    }
	
	public void updateAndReleaseGraphics() {
		Toolkit.getDefaultToolkit().sync();
		getGameGraphics().getGraphics().dispose();
		getGameGraphics().checkBufferStrategy().show();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = Direction.RIGHT;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = Direction.LEFT;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			direction = Direction.UP;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = Direction.DOWN;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public GameGraphics getGameGraphics() {
		return gameGraphics;
	}
	
	public void setGameGraphics(GameGraphics gameGraphics) {
		this.gameGraphics = gameGraphics;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
}
