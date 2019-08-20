package framework.display;

import framework.core.game.Game;
import framework.display.states.GameState;
import framework.display.states.MenuState;
import framework.display.states.State;
import javafx.stage.Stage;

public final class Window extends Stage {

	private State state;
	private int width;
	private int height;
	private boolean resizeable;

	public Window(Game game, String title, int width, int height, boolean resizeable, boolean fullScreen) {
		this.width = width;
		this.height = height;
		this.resizeable = resizeable;

		setTitle(title);
		setWidth(width);
		setHeight(height);
		setResizable(resizeable);
		setMaximized(fullScreen);

		if (resizeable) {
			// If the window is resized in the X direction
			widthProperty().addListener((obs, oldVal, newVal) -> {
				if (state instanceof GameState) {
					// Update the size of the rendering area
					game.getGameState().getStackedRenderer().getRenderers().forEach(renderer -> renderer.updateWidth());
				}
			});
			// If the window is resized in the Y direction
			heightProperty().addListener((obs, oldVal, newVal) -> {
				if (state instanceof GameState) {
					// Update the size of the rendering area
					game.getGameState().getStackedRenderer().getRenderers()
							.forEach(renderer -> renderer.updateHeight());
				}
			});
		}
	}

	public void setState(State state) {
		this.state = state;

		if (state instanceof MenuState) {
			// Menu scene is a fixed size of 800x700
			setResizable(false);
			setWidth(800);
			setHeight(700);
		} else {
			// Prepare game scene
			setResizable(resizeable);
			setWidth(width);
			setHeight(height);
		}
		// Update the scene to the new one
		setScene(state.getScene());
	}

	public State getState() {
		return state;
	}
}