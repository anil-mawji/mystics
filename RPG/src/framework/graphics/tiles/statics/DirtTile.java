package framework.graphics.tiles.statics;

import framework.core.game.Game;
import framework.graphics.sprites.Sprite;
import framework.graphics.tiles.Tile;

public final class DirtTile extends StaticTile {
	
	private static final Sprite SPRITE = new Sprite(Tile.SPRITESHEET, 0, 1);
	
	public DirtTile(Game game, int x, int y) {
		super(game, SPRITE, 3, x, y, true);
	}
}
