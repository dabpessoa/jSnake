package me.dabpessoa.jSnake.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.dabpessoa.jSnake.model.enums.Direction;

public class Snake {

	public static final Integer DEFAULT_SNAKE_SIZE = 3;
	
	private List<Node> nodes;
	private double speed;
	
	public Snake() {
		speed = 1;
	}
	
	public Snake(int size) {
		this();
		initNodes(size);
	}
	
	public void initNodes(Integer size) {
		this.nodes = new ArrayList<>();
		for (int i = 0 ; i < size ; i++) {
			Node node = Node.createDefaultNode();
			nodes.add(node);
		}
	}
	
	public void addNode() {
		Node node = Node.createDefaultNode();
		node.setPositionX(nodes.get(nodes.size()-1).getPositionX() + Node.DEFAULT_NODE_SIZE.width);
		node.setPositionY(nodes.get(nodes.size()-1).getPositionY() + Node.DEFAULT_NODE_SIZE.height);
		
		this.nodes.add(node);
	}
	
	public void addSpeed() {
		setSpeed(getSpeed() + 1);
	}
	
	public void processLogics(Long elapsedTime) {
		// Fazer os outros nós seguirem o primeiro.
		for (int i = nodes.size()-1 ; i > 0; i--) {
			nodes.get(i).setPositionX(nodes.get(i-1).getPositionX());
			nodes.get(i).setPositionY(nodes.get(i-1).getPositionY());
		}
	}
	
	/**
	 *  Fazer com que ao chegar no final da janela a cobra apareça do outro lado.
	 * @param windowSize
	 */
	public void verificarPassagemPorBordasDaJanela(Dimension windowSize, Long elapsedTime) {
		if (getFirstNode() == null) return;
		
		if (getFirstNode().getPositionX() + getFirstNode().getWidth() < 0) {
			getFirstNode().setPositionX(windowSize.width);
		} else if (getFirstNode().getPositionX() >= windowSize.width) {
			getFirstNode().setPositionX(getFirstNode().getWidth() * -1);
		}
		if (getFirstNode().getPositionY() + getFirstNode().getHeight() < 0) {
			getFirstNode().setPositionY(windowSize.height);
		} else if (getFirstNode().getPositionY() >= windowSize.height) {
			getFirstNode().setPositionY(getFirstNode().getHeight() * -1);
		}
	}
	
	public void verificarDirecao(Direction direction, Long elapsedTime) {
		if (getFirstNode() == null ) return;
		if (direction == null) direction = Direction.RIGHT;
		
		double seconds = elapsedTime.doubleValue()/1000000000;
		Long shift = Math.round(seconds * getSpeed() * Node.DEFAULT_NODE_SIZE.getWidth());
		if (shift <= 0) shift = 1l;
		
		switch (direction) {
			case RIGHT: {
				getFirstNode().setPositionX(getFirstNode().getPositionX() + shift.intValue());
			} break;
			case LEFT: {
				getFirstNode().setPositionX(getFirstNode().getPositionX() - shift.intValue());
			} break;
			case UP: {
				getFirstNode().setPositionY(getFirstNode().getPositionY() - shift.intValue());
			} break;
			case DOWN: {
				getFirstNode().setPositionY(getFirstNode().getPositionY() + shift.intValue());
			} break;
			default: {} break;
		}
	}
	
	public void render(Graphics graphics) {
		for(int i = 0 ; i < nodes.size() ; i++) {
			graphics.setColor(Color.blue);
			graphics.fillRect(nodes.get(i).getPositionX(), nodes.get(i).getPositionY(), nodes.get(i).getWidth(), nodes.get(i).getHeight());
		}
	}
	
	public static Snake createDefaultSnake() {
		return new Snake(DEFAULT_SNAKE_SIZE);
	}
	
	public Node getFirstNode() {
		if (getNodes() == null || getNodes().size() <= 0) return null;
		return getNodes().get(0);
	}

	public List<Node> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
}
