package framework.graphics.tiles.statics;

import framework.core.game.Game;
import framework.graphics.sprites.Sprite;
import framework.graphics.tiles.Tile;

public final class GrassTile extends StaticTile {
	
	private static final Sprite SPRITE = new Sprite(Tile.SPRITESHEET, 3, 0);

	public GrassTile(Game game, int x, int y) {
		super(game, SPRITE, 1, x, y, true);
	}
}