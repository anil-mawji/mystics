package framework.graphics.entities.animated.characters.enemies;

import framework.graphics.entities.Entity;
import framework.graphics.entities.animated.characters.Character;
import framework.graphics.tiles.Tile;

import java.util.LinkedList;
import java.util.List;

import framework.utils.AStar;
import framework.core.game.Game;
import framework.utils.vectors.Vector2Int;

public class Enemy extends Character {

	private Game game;
	private Character target;
	private List<Tile> attackPath;
	
	public Enemy(Game game, String fileName, Vector2Int spawn, int width, int height) {
		super(game, fileName, spawn, width, height);
		this.game = game;

		bounds.setHeight(height / 4);
		bounds.setY(height - bounds.getHeight());
		
		setSpeed(2);
		setTarget(game.getPlayer());
	}

	@Override
	public void tick() {
		super.tick();

		// If enemy has a target and is still alive
		if (target != null && !isDying()) {
			// Get distance between enemy and target
			int distance = getDistance(target);
			// If distance is within a 60 pixel radius
			if (distance <= 60) {
				attack();
				if (getAnimation().isFinished()) {
					// Hurt the target with attack animation has finished playing
					target.hurt(getDamage());
				}
			} else if (distance >= 20 && distance <= 650) {
				// Find path towards target
				attackPath = AStar.findPath(game.getTileMap(),
						(int) ((position.getX() + bounds.getX() + bounds.getWidth() / 2) / Tile.SIZE),
						(int) ((position.getY() + bounds.getY() + bounds.getHeight() / 2) / Tile.SIZE),
						(int) ((target.getPosition().getX() + target.getBounds().getX()
								+ target.getBounds().getWidth() / 2) / Tile.SIZE),
						(int) ((target.getPosition().getY() + target.getBounds().getY()
								+ target.getBounds().getHeight() / 2) / Tile.SIZE));
				
				// If a valid path was found
				if (attackPath != null && attackPath.size() > 0) {
					// Find the closest tile on the path to the enemy
					Tile next = ((LinkedList<Tile>) attackPath).getFirst();
					int nextX = next.getX() * Tile.SIZE;
					int nextY = next.getY() * Tile.SIZE;

					// If the enemy is already in the next tile in the path
					if (position.getX() == nextX && position.getY() == nextY) {
						// Remove the current tile from the path
						// Next tile is calculated in the next iteration
						((LinkedList<Tile>) attackPath).removeFirst();
					} else if (position.getX() != nextX) {
						moveX(nextX);
					} else if (position.getY() != nextY) {
						moveY(nextY);
					}
				}
			}
		}
	}

	private void moveX(int nextX) {
		setMoving(true);
		if (getPosition().getX() > nextX) {
			setMoveDirection(3);
			position.addX(-getSpeed());
		} else if (getPosition().getX() < nextX) {
			setMoveDirection(4);
			position.addX(getSpeed());
		}
	}

	private void moveY(int nextY) {
		setMoving(true);
		if (getPosition().getY() > nextY) {
			setMoveDirection(1);
			position.addY(-getSpeed());
		} else if (getPosition().getY() < nextY) {
			setMoveDirection(2);
			position.addY(getSpeed());
		}
	}

	public List<Tile> getAttackPath() {
		return attackPath;
	}

	public void setAttackPath(List<Tile> attackPath) {
		this.attackPath = attackPath;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}
}