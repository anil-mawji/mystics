package framework.graphics.renderers;

import framework.core.game.Game;

public final class PrimaryRenderer extends Renderer {

	private Game game;
	
	public PrimaryRenderer(Game game) {
		super(game);
		this.game = game;
	}

	@Override
	public void render() {
		game.getTileMap().render(gc);
		game.getEntityHandler().render(gc);
	}
}