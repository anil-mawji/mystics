package framework.graphics.entities.statics.items;

import framework.graphics.entities.statics.StaticEntity;
import framework.graphics.sprites.Sprite;
import framework.core.game.Game;
import framework.utils.vectors.Vector2Int;

public class Item extends StaticEntity {

	public Item(Game game, Sprite sprite, Vector2Int spawn, int width, int height) {
		super(game, sprite, spawn, width, height);
	}
}