package framework.graphics.lights;

import java.util.ArrayList;
import java.util.List;

import framework.core.game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

public class LightMap {

	private List<Light> lights = new ArrayList<>();
	private Game game;

	public LightMap(Game game) {
		this.game = game;
	}

	public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha(1);
        // Prepare to render lights by cutting holes in the black layer
        gc.setGlobalBlendMode(BlendMode.MULTIPLY);
        // Draw a black screen
        gc.fillRect(
                -game.getCamera().getOffset().getX(),
                -game.getCamera().getOffset().getY(),
                game.getTileMap().getWidthInPixels(),
                game.getTileMap().getHeightInPixels()
        );
        // If lights are placed on top of one another, the combined alpha values with be added
        gc.setGlobalBlendMode(BlendMode.ADD);
        // Render all lights in the map
        lights.forEach(light -> light.render(gc));
	}
	
	/**
	 * Add the given lights to the map
	 * 
	 * @param lights
	 */
	public void addLights(Light... lights) {
		for (Light light : lights) {
			this.lights.add(light);
		}
	}
	
	/**
	 * Add a light to the map
	 * 
	 * @param light
	 */
	public void addLight(Light light) {
		lights.add(light);
	}
}