package me.dabpessoa.jSnake.gameLoop;
public interface LoopSteps {

	void setup();
	void processLogics(Long elapsedTime);
	void renderGraphics();
	void tearDown();
	
}