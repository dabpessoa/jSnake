package me.dabpessoa.jSnake.core;

import java.awt.EventQueue;
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
	
	private GameLoop loop;
	private GameWindow window;
	private Snake snake;
	private Apple apple;
	private boolean gameOver;
	
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
		getSnake().processLogics(elapsedTime, getWindow().getDirection(), getWindow().getPreferredSize());
		
		boolean snakeCollision = getSnake().checkSnakeCollision();
		if (snakeCollision) {
			gameOver();
			return;
		}
		
		boolean colisao = getApple().verificaColisaoComCobra(getSnake().getHead(), getWindow().getPreferredSize());
		if(colisao) {
			score++;
			getSnake().addNode();
			getSnake().checkAddSpeed();
		}
	}

	@Override
	public void renderGraphics() {
		getWindow().renderBackground();
		
		Graphics graphics = getWindow().getGameGraphics().getGraphics();
		
		getSnake().render(graphics);
		getApple().render(graphics);
		
		getWindow().renderScore(score);
		
		getWindow().updateAndReleaseGraphics();
	}

	@Override
	public void tearDown() {
		if (gameOver) {
			getWindow().renderGameOver();
			getWindow().updateAndReleaseGraphics();
		}
	}
	
	public void startRolling() {
		loop = new GameLoop(this, DEFAULT_FPS);
		loop.addFrameCounterListener(this);
		loop.addGameLoopCounterListener(this);
		
		EventQueue.invokeLater(() -> {
			getWindow().showFrame("JSnake");
			
			Thread thread = new Thread(loop);
			thread.start();
		});
	}
	
	public void gameOver() {
		gameOver = true;
		loop.stop();
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
	
	public GameLoop getLoop() {
		return loop;
	}

}
