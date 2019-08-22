package framework.graphics.entities.statics;

import framework.graphics.entities.Entity;
import framework.graphics.sprites.Sprite;
import framework.graphics.sprites.SpriteSheet;
import framework.graphics.tiles.Tile;
import framework.utils.vectors.Vector2Int;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import framework.core.game.Game;

public class StaticEntity extends Entity {

	public static final SpriteSheet TREE_SPRITESHEET = new SpriteSheet("res/textures/tree_sheet.png");

	private Sprite sprite;

	public StaticEntity(Game game, Sprite sprite, Vector2Int spawn, int width, int height) {
		super(game, spawn, width, height);
		this.sprite = sprite;
		this.position = new Vector2Int((int) (spawn.getX() * Tile.SIZE + bounds.getWidth() / 2),
				(int) (spawn.getY() * Tile.SIZE - bounds.getHeight() / 2));
	}

	@Override
	public void tick() {}

	@Override
	public void render(GraphicsContext gc) {
		// Render the entity
		gc.drawImage(
			sprite.getScaledImage((int) game.getGameState().getMouseHandler().getScale()),
			position.getX() - game.getCamera().getOffset().getX(),
			position.getY() - game.getCamera().getOffset().getY(), width, height
		);
		
		// Render collision bounds
		if (game.getGameState().isDebuggingEnabled()) {
			gc.setStroke(Color.RED);
			gc.strokeRect(
				position.getX() + bounds.getX() - game.getCamera().getOffset().getX(),
				position.getY() + bounds.getY() - game.getCamera().getOffset().getY(), bounds.getWidth(),
				bounds.getHeight()
			);
		}
	}
}
