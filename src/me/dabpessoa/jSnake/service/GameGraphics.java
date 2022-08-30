package me.dabpessoa.jSnake.service;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class GameGraphics {

	private Canvas graphicsComponent;
	
	public GameGraphics(Canvas graphicsComponent) {
		this.graphicsComponent = graphicsComponent;
	}
	
	public BufferStrategy checkBufferStrategy() {
		BufferStrategy bufferStrategy = graphicsComponent.getBufferStrategy();
		if (bufferStrategy == null) {
			graphicsComponent.createBufferStrategy(3);
			bufferStrategy = graphicsComponent.getBufferStrategy();
		}
		
		return bufferStrategy;
	}
	
	public Graphics getGraphics() {
		return checkBufferStrategy().getDrawGraphics();
	}
	
	public void endRendering() {
		BufferStrategy bufferStrategy = checkBufferStrategy();
		
		bufferStrategy.getDrawGraphics().dispose();
		bufferStrategy.show();
	}

	public Canvas getGraphicsComponent() {
		return graphicsComponent;
	}

	public void setGraphicsComponent(Canvas graphicsComponent) {
		this.graphicsComponent = graphicsComponent;
	}
	
}
