package framework.graphics.entities.animated.characters;

import framework.graphics.sprites.Animation;
import framework.graphics.sprites.AnimationPack;
import framework.graphics.sprites.SpriteSheet;
import framework.graphics.HealthBar;
import framework.graphics.entities.animated.AnimatedEntity;
import framework.utils.vectors.Vector2Int;
import javafx.scene.canvas.GraphicsContext;
import framework.core.game.Game;

public class Character extends AnimatedEntity {

	private AnimationPack walkAnimations;
	private AnimationPack attackAnimations;
	private Animation deathAnimation;
	private HealthBar healthBar;
	private Vector2Int velocity;

	private boolean moving = false;
	private boolean attacking = false;
	private boolean dying = false;

	private int moveDirection = 1;
	private int speed = 3;
	private int damage = 1;

	public Character(Game game, String fileName, Vector2Int spawn, int width, int height) {
		super(game, spawn, width, height);
		this.velocity = new Vector2Int();
		this.healthBar = new HealthBar(game, this);

		this.walkAnimations = new AnimationPack(
				new SpriteSheet("res/textures/character/png/walkcycle/BODY_" + fileName + ".png", width, height),
				70,      // Speed
				0, 0, 8, // Idle
				0, 0, 8, // Up
				0, 2, 8, // Down
				0, 1, 9, // Left
				0, 3, 7  // Right
		);

		this.attackAnimations = new AnimationPack(
				new SpriteSheet("res/textures/character/png/slash/BODY_" + fileName + "2.png", width, height),
				70,      // Speed
				0, 0, 6, // Idle
				0, 0, 6, // Up
				0, 2, 6, // Left
				0, 1, 6, // Down
				0, 3, 6  // Right
		);

		this.deathAnimation = new Animation(
				new SpriteSheet("res/textures/character/png/hurt/BODY_" + fileName + ".png", width, height),
				0, 0, 6, 80
		);

		bounds.setWidth(32);
		bounds.setX(32 / 2);
		bounds.setHeight(height / 4);
		bounds.setY(height - bounds.getHeight());
	}

	@Override
	public void tick() {
		animate();
		move();
	}

	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		healthBar.render(gc);
	}

	@Override
	protected void animate() {
		super.animate();
		// If character is not moving or dying stop playing the current animation
		if (velocity.isEmpty() && !(getAnimation() == deathAnimation)) {
			getAnimation().setPlaying(false);
		}
	}

	private void move() {
		if (!dying) {
			// Enable walking animation
			setAnimation(walkAnimations.get(moveDirection));
			// If character is moving play the animation
			getAnimation().setPlaying(moving);
			// Set moving to false to prepare for the next iteration
			moving = false;
		} else if (getAnimation().isFinished()) {
			// Kill character once death animation has finished
			dying = false;
			die();
		}
	}

	public void attack() {
		if (!dying) {
			// Enable attack animation
			setAnimation(attackAnimations.get(moveDirection));
			// Play animation regardless of whether character is moving or not
			getAnimation().setPlaying(true);
			attacking = true;
		} else if (getAnimation().isFinished()) {
			// Kill the character once death animation has finished
			dying = false;
			die();
		}
	}

	public void hurt(int damage) {
		healthBar.addHealth(-damage);
		// If character is has no health and is not already dying
		if (!dying && healthBar.getHealth() <= 0) {
			dying = true;
			// Switch to death animation
			setAnimation(deathAnimation);
			getAnimation().setPlaying(true);
		}
	}

	public void die() {
		setActive(false);
	}

	public Vector2Int getVelocity() {
		return velocity;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public int getMoveDirection() {
		return moveDirection;
	}

	public void setMoveDirection(int moveDirection) {
		this.moveDirection = moveDirection;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public int getDamage() {
		return damage;
	}

	public boolean isDying() {
		return dying;
	}

	public HealthBar getHealthBar() {
		return healthBar;
	}
}