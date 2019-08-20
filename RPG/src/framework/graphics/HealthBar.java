package framework.graphics;

import framework.core.game.Game;
import framework.graphics.entities.animated.characters.Character;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthBar {

	private Game game;
	private Character character;

	private float healthCap = 50;
	private float health = healthCap;

	public HealthBar(Game game, Character character) {
		this.game = game;
		this.character = character;
	}

	public void render(GraphicsContext gc) {
		if (health < healthCap) {
			final float x = character.getPosition().getX() + character.getWidth() / 2 - healthCap / 2
					- game.getCamera().getOffset().getX();
			final float y = character.getPosition().getY() - game.getCamera().getOffset().getY();

			// Calculate color of the health bar based upon how much health the character has
			if (health == healthCap) {
				gc.setFill(Color.LIMEGREEN);
			} else {
				// If the character isn't at full health, render the background of the bar
				gc.setFill(Color.GREY);
				gc.fillRect(x, y, healthCap, 5);

				if (health / healthCap >= 0.75) {
					gc.setFill(Color.LIMEGREEN);
				} else if (health / healthCap >= 0.5) {
					gc.setFill(Color.YELLOW);
				} else if (health / healthCap >= 0.3) {
					gc.setFill(Color.DARKORANGE);
				} else {
					gc.setFill(Color.RED);
				}
			}
			// Render the health bar
			gc.fillRect(x, y, health, 5);
			
			// Render a black border
			gc.setStroke(Color.BLACK);
			gc.strokeRect(x, y, healthCap, 5);
		}
	}

	public void addHealth(float i) {
		this.health += i;
	}

	public void replenish() {
		this.health = healthCap;
	}

	public float getHealth() {
		return health;
	}
}