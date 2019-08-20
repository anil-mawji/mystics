package framework.graphics.entities;

import framework.utils.MathUtils;
import framework.utils.vectors.Vector2Int;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import framework.core.game.Game;
import framework.graphics.entities.animated.characters.enemies.Enemy;
import framework.graphics.tiles.Tile;

public abstract class Entity {

	protected Game game;
	protected Rectangle bounds;
	protected Vector2Int position;
	protected int width;
	protected int height;
	private boolean visible;
	private boolean active = true;

	public Entity(Game game, Vector2Int spawn, int width, int height) {
		this.game = game;
		this.width = width;
		this.height = height;
		this.bounds = new Rectangle(0, 0, width, height);
		this.position = new Vector2Int(spawn.getX() * Tile.SIZE, spawn.getY() * Tile.SIZE);

		// Add entity to the handler in order to be updated
		game.getEntityHandler().addEntity(this);
	}

	public abstract void tick();

	public abstract void render(GraphicsContext gc);

	public boolean isColliding(float offsetX, float offsetY) {
		// Check for collisions with other entities
		for (Entity entity : game.getEntityHandler().getEntities()) {
			// If the entity being checked is this one, move on to check the next entity
			// Disable collisions for enemies
			if (entity == this || entity instanceof Enemy) {
				continue;
			}
			// If the collision bounds of this entity and the one being check collide with
			// one another
			if (entity.getCollisionBounds(0, 0).intersects(getCollisionBounds(offsetX, offsetY))) {
				return true;
			}
		}
		// Did not find collisions with any other entities
		return false;
	}
	
	public Bounds getCollisionBounds(float offsetX, float offsetY) {
		return new Rectangle(
				position.getX() + bounds.getX() + offsetX,
				position.getY() + bounds.getY() + offsetY,
				bounds.getWidth(),
				bounds.getHeight()
		).getBoundsInParent();
	}

	/**
	 * Get the distance between this entity and another entity
	 * @param entity
	 * @return distance between the two
	 */
	public int getDistance(Entity entity) {
		return (int) MathUtils.euclid(
				position.getX(),
				entity.getPosition().getX(),
				position.getY(),
				entity.getPosition().getY()
		);
	}

	public Vector2Int getPosition() {
		return position;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}