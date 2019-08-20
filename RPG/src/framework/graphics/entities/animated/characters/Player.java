package framework.graphics.entities.animated.characters;

import framework.graphics.entities.Entity;
import framework.graphics.entities.animated.characters.enemies.Enemy;
import framework.graphics.tiles.Tile;
import framework.utils.vectors.Vector2Int;
import framework.core.game.Game;
import framework.core.input.KeyHandler;
import framework.core.input.MouseHandler;

public class Player extends Character {

	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;

	public Player(Game game, Vector2Int spawn) {
		super(game, "skeleton", spawn, 64, 64);
		this.keyHandler = game.getGameState().getKeyHandler();
		this.mouseHandler = game.getGameState().getMouseHandler();
	}

	@Override
	public void tick() {
		super.tick();

		// If player is not colliding in the X direction
		if (!isColliding(getVelocity().getX(), 0)) {
			// If player is moving in the X direction
			if (getVelocity().getX() != 0) {
				setMoving(true);
			}
			// Retrieve the next anticipated X position
			int newX = position.getX() + getVelocity().getX();
			// If the new position is located on the map
			if (newX + bounds.getX() >= 0
					&& newX + bounds.getX() + bounds.getWidth() < game.getTileMap().getWidthInPixels()) {
				// Update the current position
				position.setX(newX);
			}
		}
		// If player is not colliding in the Y direction
		if (!isColliding(0, getVelocity().getY())) {
			// If player is moving in the Y direction
			if (getVelocity().getY() != 0) {
				setMoving(true);
			}
			// Retrieve the next anticipated Y position
			int newY = position.getY() + getVelocity().getY();
			// If the new position is located on the map
			if (newY + bounds.getHeight() >= 0
					&& newY + bounds.getY() + bounds.getHeight() < game.getTileMap().getHeightInPixels()) {
				// Update the current position
				position.setY(newY);
			}
		}
		handleInput();
	}

	@Override
	public boolean isColliding(float offsetX, float offsetY) {
		// Check if player is colliding with another entity
		if (super.isColliding(offsetX, offsetY)) {
			return true;
		}
		// Check if player is colliding with a solid tile
		for (int x = game.getTileMap().getStartX(); x < game.getTileMap().getEndX(); x++) {
			for (int y = game.getTileMap().getStartY(); y < game.getTileMap().getEndY(); y++) {
				Tile tile = game.getTileMap().getTile(x, y);
				if (tile != null && !tile.isWalkable()
						&& tile.getCollisionBounds(0, 0).intersects(getCollisionBounds(offsetX, offsetY))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void die() {
		// Reposition the player
		getPosition().setX(18 * 64);
		getPosition().setY(10 * 64);
		getHealthBar().replenish();
	}

	private void handleInput() {
		// Reset movement properties before checking for new ones
		getAnimation().resetSpeed();
		getVelocity().clear();
		setSpeed(2);

		if (mouseHandler.isButtonHeld("PRIMARY")) {
			attack();
			if (getAnimation().isFinished()) {
				for (Entity entity : game.getEntityHandler().getEntities()) {
					if (entity != this && entity instanceof Enemy && getDistance(entity) <= 60) {
						((Enemy) entity).hurt(getDamage());
					}
				}
			}
		}
		if (keyHandler.isKeyHeld("SHIFT")) {
			setSpeed(getSpeed() * 2);
		}
		if (keyHandler.isKeyHeld("RIGHT") || keyHandler.isKeyHeld("D")) {
			getVelocity().setX(getSpeed());
			setMoveDirection(4);
		} else if (keyHandler.isKeyHeld("LEFT") || keyHandler.isKeyHeld("A")) {
			getVelocity().setX(-getSpeed());
			setMoveDirection(3);
		}
		if (keyHandler.isKeyHeld("DOWN") || keyHandler.isKeyHeld("S")) {
			getVelocity().setY(getSpeed());
			setMoveDirection(2);
		} else if (keyHandler.isKeyHeld("UP") || keyHandler.isKeyHeld("W")) {
			getVelocity().setY(-getSpeed());
			setMoveDirection(1);
		}
	}
}