package framework.graphics.renderers;

import framework.core.game.Game;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Renderer {

	private Game game;

	private Canvas canvas;
	protected GraphicsContext gc;
	
	public Renderer(Game game) {
		this.game = game;
		this.canvas = new Canvas(game.getWindow().getWidth(), game.getWindow().getHeight());
		this.gc = canvas.getGraphicsContext2D();
	}

	public void prepare() {
		// Clear the layer to prepare for the next drawing
		gc.clearRect(0, 0, game.getWindow().getWidth(), game.getWindow().getHeight());
		// Scale the renderer to fit the camera
		gc.setTransform(game.getCamera().getAffine());
	}

	public abstract void render();

	public void updateWidth() {
		canvas.setWidth(game.getWindow().getWidth());
	}

	public void updateHeight() {
		canvas.setHeight(game.getWindow().getHeight());
	}

	public Canvas getCanvas() {
		return canvas;
	}
}