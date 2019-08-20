package framework.graphics.entities.animated.characters.enemies;

import framework.core.game.Game;
import framework.utils.vectors.Vector2Int;

public final class Skeleton extends Enemy {

	public Skeleton(Game game, Vector2Int spawn) {
		super(game, "skeleton", spawn, 64, 64);
	}
}