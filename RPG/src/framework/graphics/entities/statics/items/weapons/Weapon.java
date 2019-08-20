package framework.graphics.entities.statics.items.weapons;

import framework.core.game.Game;
import framework.graphics.entities.statics.items.Item;
import framework.graphics.sprites.Sprite;
import framework.utils.vectors.Vector2Int;

public class Weapon extends Item {

	private float damage;

	public Weapon(Game game, Sprite sprite, Vector2Int spawn, int width, int height) {
		super(game, sprite, spawn, width, height);
	}
}