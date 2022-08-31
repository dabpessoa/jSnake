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
	private double shiftCounter;
	
	public Snake() {
		speed = 1.0d;
		shiftCounter = 0d;
	}
	
	public Snake(int size) {
		this();
		initNodes(size);
	}
	
	public void initNodes(Integer size) {
		this.nodes = new ArrayList<>();
		
		// Add Head.
		addNode(0,0);
		
		// Add others.
		int height = Node.DEFAULT_NODE_SIZE.height;
		for (int i = 1 ; i < size ; i++) {
			addNode(0, height * i);
		}
	}
	
	public void addNode() {
		addNode(null, null);
	}
	
	public void addNode(Integer positionX, Integer positionY) {
		Node node = Node.createDefaultNode();
		if (positionX != null) node.setPositionX(positionX);
		if (positionY != null) node.setPositionY(positionY);
		this.nodes.add(node);
	}
	
	public void checkAddSpeed() {
		if (getNodes().size() % 3 == 0) {
			addSpeed();
		}
	}
	
	public void addSpeed() {
		setSpeed(getSpeed() + 0.1d);
	}
	
	public void processLogics(Long elapsedTime, Direction direction, Dimension windowSize) {
		if (shouldMove(elapsedTime)) {
			// Fazer os outros nós seguirem o primeiro.
			for (int i = nodes.size() - 1 ; i > 0 ; i--) {
				nodes.get(i).setPositionX(nodes.get(i - 1).getPositionX());
				nodes.get(i).setPositionY(nodes.get(i - 1).getPositionY());
			}
			
			verificarDirecaoPrimeiroNo(direction, elapsedTime);
			verificarPassagemPorBordasDaJanela(windowSize, elapsedTime);
		}
	}
	
	/**
	 *  Fazer com que ao chegar no final da janela a cobra apareça do outro lado.
	 * @param windowSize
	 */
	private void verificarPassagemPorBordasDaJanela(Dimension windowSize, Long elapsedTime) {
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
	
	private void verificarDirecaoPrimeiroNo(Direction direction, Long elapsedTime) {
		if (getFirstNode() == null ) return;
		if (direction == null) direction = Direction.RIGHT;
		
		switch (direction) {
			case RIGHT: {
				getHead().setPositionX(getHead().getPositionX() + Node.DEFAULT_NODE_SIZE.width);
			} break;
			case LEFT: {
				getHead().setPositionX(getHead().getPositionX() - Node.DEFAULT_NODE_SIZE.width);
			} break;
			case UP: {
				getHead().setPositionY(getHead().getPositionY() - Node.DEFAULT_NODE_SIZE.height);
			} break;
			case DOWN: {
				getHead().setPositionY(getHead().getPositionY() + Node.DEFAULT_NODE_SIZE.height);
			} break;
			default: {} break;
		}
	}
	
	public boolean shouldMove(Long elapsedTime) {
		double seconds = elapsedTime.doubleValue()/1000000000;
		
		shiftCounter += seconds * getSpeed() * Node.DEFAULT_NODE_SIZE.getWidth(); // Deslocamento de blocos (Nodes) por segundo.
		
		boolean shouldMove = shiftCounter >= 1;
		if (shouldMove) shiftCounter = 0;
		
		return shouldMove;
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
	
	public Node getHead() {
		return getFirstNode();
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
