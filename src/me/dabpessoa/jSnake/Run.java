package me.dabpessoa.jSnake;

import me.dabpessoa.jSnake.core.World;

public class Run {

	public static void main(String[] args) {
		World world = new World(300, 300);
		world.startRolling();
	}
	
}