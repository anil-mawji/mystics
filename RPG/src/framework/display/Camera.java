package framework.display;

import framework.graphics.entities.Entity;
import framework.core.game.Game;
import framework.utils.MathUtils;
import framework.utils.vectors.Vector2f;
import javafx.scene.transform.Affine;

public final class Camera {

	private Game game;
	private Vector2f offset;
	private Entity target;
	private Affine affine;

	public Camera(Game game, float offsetX, float offsetY) {
		this.game = game;
		this.offset = new Vector2f(offsetX, offsetY);
		this.affine = new Affine();
	}

	public void tick() {
		// Calculate the new X position relative to the target
		offset.setX((float) MathUtils.clamp(
				MathUtils.lerp(offset.getX(),
						target.getPosition().getX() - game.getWindow().getWidth() / 2 + target.getWidth() / 2, 0.09f),
						game.getTileMap().getWidthInPixels() - game.getWindow().getWidth()
						+ target.getBounds().getWidth() / 2 - 4));
		
		// Calculate the new Y position relative to the target
		offset.setY((float) MathUtils.clamp(
				MathUtils.lerp(offset.getY(),
						target.getPosition().getY() - game.getWindow().getHeight() / 2 + target.getHeight() / 2, 0.09f),
						game.getTileMap().getHeightInPixels() - game.getWindow().getHeight()
						+ target.getBounds().getHeight() * 2 + 4));
		
		// Reset camera scaling to 1x before updating
		affine.setToIdentity();
		
		// Get the current scale based on the value in the mouse handler
		// Scale the camera around the target
		affine.appendScale(game.getGameState().getMouseHandler().getScale(),
				game.getGameState().getMouseHandler().getScale(),
				target.getPosition().getX() + target.getWidth() / 2 - offset.getX(),
				target.getPosition().getY() + target.getHeight() / 2 - offset.getY());
	}

	public Vector2f getOffset() {
		return offset;
	}

	public Entity gettarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public Affine getAffine() {
		return affine;
	}
}