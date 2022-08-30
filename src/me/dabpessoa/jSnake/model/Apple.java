package me.dabpessoa.jSnake.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Apple {

	public static final Dimension DEFAULT_APPLE_SIZE = new Dimension(10,10);
	
	private Integer positionX;
	private Integer positionY;
	private Integer width;
	private Integer height;
	
	public Apple() {}

	public Apple(int positionX, int positionY, int width, int height) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics graphics) {
		graphics.setColor(Color.red);
		graphics.fillRect(getPositionX(), getPositionY(), getWidth(), getHeight());
	}
	
	public boolean verificaColisaoComCobra(Node snakeNode, Dimension windowSize) {
		if (snakeNode == null) return false;
		
		Rectangle snakeNodeZeroRectangle = new Rectangle(snakeNode.getPositionX(), snakeNode.getPositionY(), snakeNode.getWidth(), snakeNode.getHeight());
		Rectangle macaRectangle = new Rectangle(getPositionX(), getPositionY(), getWidth(), getHeight());
		
		if (snakeNodeZeroRectangle.intersects(macaRectangle)) {
			setPositionX(new Random().nextInt(windowSize.width - snakeNode.getWidth()));
			setPositionY(new Random().nextInt(windowSize.height - snakeNode.getHeight()));
			return true;
		}
		
		return false;
	}
	
	public Dimension getSize() {
		return new Dimension(width, height);
	}
	
	public static Apple createRandomPositionDefaultApple(Dimension windowSize) {
		return createRandomPositionApple(windowSize, DEFAULT_APPLE_SIZE);
	}
	
	public static Apple createRandomPositionApple(Dimension windowSize, Dimension appleSize) {
		Apple randomPositionApple = new Apple();
		randomPositionApple.setWidth(DEFAULT_APPLE_SIZE.width);
		randomPositionApple.setHeight(DEFAULT_APPLE_SIZE.height);
		
		randomPositionApple.setPositionX(new Random().nextInt(windowSize.width - appleSize.width));
		randomPositionApple.setPositionY(new Random().nextInt(windowSize.height - appleSize.height));
		
		return randomPositionApple;
	}

	public Integer getPositionX() {
		return positionX;
	}

	public void setPositionX(Integer positionX) {
		this.positionX = positionX;
	}

	public Integer getPositionY() {
		return positionY;
	}

	public void setPositionY(Integer positionY) {
		this.positionY = positionY;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
}
