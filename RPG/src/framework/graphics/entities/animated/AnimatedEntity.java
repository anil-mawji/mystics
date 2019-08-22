package framework.graphics.entities.animated;

import framework.graphics.entities.Entity;
import framework.graphics.sprites.Animation;
import framework.utils.vectors.Vector2Int;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import framework.core.game.Game;

public class AnimatedEntity extends Entity {

	private Animation animation;
	
	public AnimatedEntity(Game game, Animation animation, Vector2Int spawn, int width, int height) {
		super(game, spawn, width, height);
		this.animation = animation;
	}
	
	public AnimatedEntity(Game game, Vector2Int spawn, int width, int height) {
		super(game, spawn, width, height);
	}

	@Override
	public void tick() {
		animate();
	}

	protected void animate() {
		if (animation != null) {
			if (animation.isPlaying()) {
				animation.tick();
			} else {
				// Reset animation
				animation.setFrameIndex(0);
			}
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		// Render the entity
		gc.drawImage(
			animation.getCurrentFrame().getScaledImage((int) game.getGameState().getMouseHandler().getScale()),
			position.getX() - game.getCamera().getOffset().getX(),
			position.getY() - game.getCamera().getOffset().getY(),
			width, height
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

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
}
