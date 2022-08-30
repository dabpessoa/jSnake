package me.dabpessoa.jSnake.core;

import java.awt.Graphics;

import me.dabpessoa.jSnake.gameLoop.GameLoop;
import me.dabpessoa.jSnake.gameLoop.LoopSteps;
import me.dabpessoa.jSnake.gameLoop.listeners.FrameCounterListener;
import me.dabpessoa.jSnake.gameLoop.listeners.GameLoopCounterListener;
import me.dabpessoa.jSnake.model.Apple;
import me.dabpessoa.jSnake.model.Snake;
import me.dabpessoa.jSnake.service.GameWindow;

public class World implements LoopSteps, FrameCounterListener, GameLoopCounterListener {

	private static final Integer DEFAULT_FPS = 60;
	
	private GameWindow window;
	private Snake snake;
	private Apple apple;
	
	private Integer score;
	
	public World(Integer width, Integer height) {
		setWindow(new GameWindow(width, height));
		this.score = 0;
	}
	
	@Override
	public void setup() {
		setSnake(Snake.createDefaultSnake());
		setApple(Apple.createRandomPositionDefaultApple(getWindow().getPreferredSize()));
	}
	
	@Override
	public void processLogics(Long elapsedTime) {
		getSnake().processLogics(elapsedTime);
		getSnake().verificarPassagemPorBordasDaJanela(getWindow().getPreferredSize(), elapsedTime);
		getSnake().verificarDirecao(getWindow().getDirection(), elapsedTime);
		
		boolean colisao = getApple().verificaColisaoComCobra(getSnake().getFirstNode(), getWindow().getPreferredSize());
		if(colisao) {
			score++;
			getSnake().addNode();
			getSnake().addSpeed();
		}
	}

	@Override
	public void renderGraphics() {
		getWindow().renderBackground();
		
		Graphics graphics = getWindow().getGameGraphics().getGraphics();
		
		getSnake().render(graphics);
		getApple().render(graphics);
		
		getWindow().updateAndReleaseGraphics();
	}

	@Override
	public void tearDown() {
		
	}
	
	public void startRolling() {
		getWindow().showFrame("JSnake");
		
		GameLoop loop = new GameLoop(this, DEFAULT_FPS);
		loop.addFrameCounterListener(this);
		loop.addGameLoopCounterListener(this);
		
		Thread thread = new Thread(loop);
		thread.start();
	}
	
	@Override
	public void gameLoopCounterInfo(int count) {
		// System.out.println("Loop: "+count);
	}

	@Override
	public void frameCountInfo(int count) {
		// System.out.println("FPS: "+count);
	}

	public GameWindow getWindow() {
		return window;
	}

	public void setWindow(GameWindow window) {
		this.window = window;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public Apple getApple() {
		return apple;
	}

	public void setApple(Apple apple) {
		this.apple = apple;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
