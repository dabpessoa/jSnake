package me.dabpessoa.jSnake.model;

import java.awt.Dimension;

public class Node {

	public static final Dimension DEFAULT_NODE_SIZE = new Dimension(10,10);
	
	private Integer positionX;
	private Integer positionY;
	private Integer width;
	private Integer height;
	
	public Node() {}

	public Node(int positionX, int positionY, int width, int height) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
	}
	
	public static Node createDefaultNode() {
		return new Node(0, 0, DEFAULT_NODE_SIZE.width, DEFAULT_NODE_SIZE.height);
	}
	
	public void incrementX(int amount) {
		if (getPositionX() == null) setPositionX(0);
		setPositionX(getPositionX() + amount);
	}
	
	public void incrementY(int amount) {
		if (getPositionY() == null) setPositionY(0);
		setPositionY(getPositionY() + amount);
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
