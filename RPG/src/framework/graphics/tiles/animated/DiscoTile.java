package framework.graphics.tiles.animated;

import framework.core.game.Game;
import framework.graphics.sprites.Animation;
import framework.graphics.tiles.Tile;

public class DiscoTile extends AnimatedTile {

	public DiscoTile(Game game, int x, int y) {
		super(game, new Animation(Tile.SPRITESHEET, 1, 1, 5, 200), 3, x, y, true);
	}
}