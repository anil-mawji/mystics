package framework.graphics.renderers;

import framework.core.game.Game;
import javafx.scene.effect.BlendMode;

public final class EffectsRenderer extends Renderer {

	private Game game;
	
	public EffectsRenderer(Game game) {
		super(game);
		this.game = game;

		getCanvas().setBlendMode(BlendMode.MULTIPLY);
		gc.setGlobalAlpha(0.8);
	}

	@Override
	public void render() {
		game.getLightMap().render(gc);
	}
}