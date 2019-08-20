package framework.graphics.tiles.statics;

import framework.core.game.Game;
import framework.graphics.sprites.Sprite;
import framework.graphics.tiles.Tile;

public final class StoneTile extends StaticTile {

	private static final Sprite SPRITE = new Sprite(Tile.SPRITESHEET, 2, 1);

	public StoneTile(Game game, int x, int y) {
		super(game, SPRITE, 3, x, y, false);
	}
}