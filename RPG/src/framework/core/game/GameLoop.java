package framework.core.game;

import javafx.animation.AnimationTimer;

public abstract class GameLoop extends AnimationTimer {

	private boolean running;

	public void pauseGame() {
		running = false;
		stop();
	}

	public void resumeGame() {
		running = true;
		start();
	}

	public boolean isRunning() {
		return running;
	}
}