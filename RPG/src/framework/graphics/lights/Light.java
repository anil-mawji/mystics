package framework.graphics.lights;

import framework.core.game.Game;
import framework.graphics.sprites.Sprite;
import framework.graphics.sprites.SpriteSheet;
import javafx.scene.canvas.GraphicsContext;

public class Light {

	public static final SpriteSheet SPRITESHEET = new SpriteSheet("res/textures/light.png", 256, 256);
	public static final Sprite WHITE = new Sprite(SPRITESHEET, 0, 0);

	private Game game;
	private Sprite sprite;
	private int x;
	private int y;
	private int width;
	private int height;

	public Light(Game game, Sprite sprite, int x, int y, int width, int height) {
		this.game = game;
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(
				sprite.getImage(),
				x - game.getCamera().getOffset().getX(),
				y - game.getCamera().getOffset().getY(),
				width, height
		);
	}
}