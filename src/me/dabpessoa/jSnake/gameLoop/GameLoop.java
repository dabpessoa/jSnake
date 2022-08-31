package me.dabpessoa.jSnake.gameLoop;

import java.util.ArrayList;
import java.util.List;

import me.dabpessoa.jSnake.gameLoop.listeners.FrameCounterListener;
import me.dabpessoa.jSnake.gameLoop.listeners.GameLoopCounterListener;

public class GameLoop implements Runnable {

	public static final int DEFAULT_FPS = 80;
    public static final int DEFAULT_MAX_NO_DELAYS = 15;
    public static final int SECOND_IN_NANO = 1000000000;
	
	private LoopSteps game;
	private long lastLoopTime;
	private long overSleepTime;
	private int noDelays;
	private int maxNoDelays = DEFAULT_MAX_NO_DELAYS;
	private long lastFpsTime; /** The last time at which we recorded the frame rate */
	private int fpsCounter; /** The current number of frames recorded */
	private int fps;
	private long desiredSleepTime;
	private List<FrameCounterListener> frameCounterListeners;
	private List<GameLoopCounterListener> gameLoopCounterListeners;
	private boolean running;
	private boolean paused;
	
	public GameLoop(LoopSteps game) {
		this.game = game;
		this.lastFpsTime = 0;
		this.fpsCounter = 0;
		this.noDelays = 0;
		this.overSleepTime = 0;
		this.frameCounterListeners = new ArrayList<FrameCounterListener>(0);
		this.gameLoopCounterListeners = new ArrayList<GameLoopCounterListener>(0);
		changeFPS(DEFAULT_FPS);
	}
	
	public GameLoop(LoopSteps game, int fps) {
		this(game);
		changeFPS(fps);
	}
	
	@Override
	public void run() {
		this.init();
	}
	
	private void init() {
		
		try {
			if (!isRunning()) {

				setRunning(true);
				int count = 0;
				game.setup();
								
				lastLoopTime = System.nanoTime();
				while(isRunning()) {
				
					long time = System.nanoTime();
					long elapsedTime = time - lastLoopTime;
					lastLoopTime = time;
					
					
					// Processa as regras do jogo de acordo com intervalo temporal passado por par?metro
					process(findNewElapsedTime(elapsedTime, time));
					
					
					// update the frame counter
					lastFpsTime += findNewElapsedTime(elapsedTime, time);
					fpsCounter++;					
					  
					// update our FPS counter if a second has passed since
					// we last recorded
					if (lastFpsTime >= SECOND_IN_NANO) {
					   for (FrameCounterListener frameCounterListener : frameCounterListeners) {
						   frameCounterListener.frameCountInfo(fpsCounter);
					   }
					   lastFpsTime = 0;
					   fpsCounter = 0;
					}
										
					
					for (GameLoopCounterListener gameLoopCounterListener : gameLoopCounterListeners) {
						gameLoopCounterListener.gameLoopCounterInfo(count++);
					}
					
					if (count == Integer.MAX_VALUE) {
						count = 0;
					}
					
					
					long sleepTime = desiredSleepTime - (System.nanoTime() - time) - overSleepTime;
					
					
					if (sleepTime > 0)
	                    sleep(sleepTime);
	                else { // Sleep time is negative
	                    overSleepTime = 0L;
	                    yieldIfNeed();
	                }
					
				}
				
			}
		} catch (Exception e) {
			throw new RuntimeException("Exception during game loop", e);
		} finally {
			setRunning(false);
			game.tearDown();
		}
		
	}
	
	private void process(long elapsedTime) {
		game.processLogics(elapsedTime);
		game.renderGraphics();
	}
	
	/**
     * Sleep the given amount of time. Since the sleep() method of the thread
     * class is not precise, the overSleepTime will be calculated.
     * 
     * @param nanos Number of nanoseconds to sleep.
     * @throws InterruptedException If the thread was interrupted 
     */
	private void sleep(long nanos) throws InterruptedException {
		noDelays = 0;
		long beforeSleep = System.nanoTime();
		Thread.sleep(nanos / 1000000L, (int) (nanos % 1000000L));
		overSleepTime = System.nanoTime() - beforeSleep - nanos;
	}
	
	/**
     * If the number of frames without a delay is reached, force the thread to
     * yield, allowing other threads to process.
     */
	private void yieldIfNeed() {
        if (++noDelays == maxNoDelays) {
            Thread.yield();
            noDelays = 0;
        }
    }

	public void changeFPS(int fps) {
		if (fps < 1) throw new IllegalArgumentException("You must display at least one frame per second!");
        if (fps > 1000) fps = 1000; 
		this.fps = fps;
		if (fps != 0) this.desiredSleepTime = SECOND_IN_NANO / fps;
	}
	
	public long findNewElapsedTime(long currentElapsedTime, long fromTime) {
		return (currentElapsedTime + (System.nanoTime() - fromTime));
	}
	
	public int getFps() {
		return fps;
	}
	
	public void stop() {
		setRunning(false);
	}
	
	public void pause() {
		setPaused(true);
	}
	
	public void resume() {
		setPaused(false);
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public void addFrameCounterListener(FrameCounterListener frameCounterListener) {
		this.frameCounterListeners.add(frameCounterListener);
	}
	
	public void addGameLoopCounterListener(GameLoopCounterListener gameLoopCounterListener) {
		this.gameLoopCounterListeners.add(gameLoopCounterListener);
	}
	
}