package framework.graphics.tiles.animated;

import framework.core.game.Game;
import framework.graphics.sprites.Animation;
import framework.graphics.tiles.Tile;

public class WaterTile extends AnimatedTile {

	public WaterTile(Game game, int x, int y) {
		super(game, new Animation(Tile.SPRITESHEET, 0, 2, 4, 200), 2, x, y, false);
	}
}
