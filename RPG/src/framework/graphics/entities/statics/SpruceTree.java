package framework.graphics.entities.statics;

import framework.core.game.Game;
import framework.graphics.sprites.Sprite;
import framework.graphics.tiles.Tile;
import framework.utils.vectors.Vector2Int;

public class SpruceTree extends StaticEntity {

	private static final Sprite SPRITE = new Sprite(StaticEntity.TREE_SPRITESHEET, 1, 1, 107, 145);

	public SpruceTree(Game game, Vector2Int spawn) {
		super(game, SPRITE, spawn, (int) (Tile.SIZE * 2.5), (int) (Tile.SIZE * 3.5));

		bounds.setWidth(width / 6);
		bounds.setX(width / 2 - bounds.getWidth() / 2);
		bounds.setHeight(height / 10);
		bounds.setY(height - bounds.getHeight() - 5);
	}
}