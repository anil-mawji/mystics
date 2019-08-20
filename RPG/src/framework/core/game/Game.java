package framework.core.game;

import framework.graphics.entities.EntityHandler;
import framework.graphics.entities.animated.characters.Player;
import framework.graphics.entities.animated.characters.enemies.Skeleton;
import framework.graphics.entities.statics.SpruceTree;
import framework.utils.vectors.Vector2Int;

import framework.display.Camera;
import framework.display.Window;
import framework.display.states.GameState;
import framework.display.states.MenuState;
import framework.graphics.lights.Light;
import framework.graphics.lights.LightMap;
import framework.graphics.tiles.TileMap;

public final class Game {

	private GameLoop gameLoop;
	private Window window;
	private MenuState menuState;
	private GameState gameState;
	private EntityHandler entityHandler;
	private Player player;
	private Camera camera;
	private TileMap tileMap;
	private LightMap lightMap;

	public Game() {
		this.window = new Window(this, "Mystics", 800, 700, true, false);
		this.menuState = new MenuState(this);
		this.gameState = new GameState(this);
		this.entityHandler = new EntityHandler(this);
		this.tileMap = new TileMap(this, TileMap.MAP_1);
		this.lightMap = new LightMap(this);
		this.player = new Player(this, new Vector2Int(6, 1));
		this.camera = new Camera(this, 0, 0);

		window.setState(menuState);
		window.show();
		camera.setTarget(player);

		lightMap.addLights(
				new Light(this, Light.WHITE, 25, 25, 1000, 1000),
				new Light(this, Light.WHITE, 700, 50, 1000, 1000), new Light(this, Light.WHITE, 700, 50, 1000, 1000),
				new Light(this, Light.WHITE, 50, 70, 1000, 1000), new Light(this, Light.WHITE, 700, 700, 1000, 1000)
		);

		for (int i = 0; i / 4 < 4; i += 4) {
			new SpruceTree(this, new Vector2Int(5, 3 + i));
		}

		new Skeleton(this, new Vector2Int(15, 25));
		new Skeleton(this, new Vector2Int(15, 5));
		new Skeleton(this, new Vector2Int(18, 20));

		gameLoop = new GameLoop() {
			@Override
			public void handle(long now) {
				tick();
				render();
			}
		};
	}

	public void tick() {
		gameState.tick();
		entityHandler.tick();
		camera.tick();
	}

	public void render() {
		gameState.render();
	}

	public GameLoop getGameLoop() {
		return gameLoop;
	}

	public Window getWindow() {
		return window;
	}

	public MenuState getMenuState() {
		return menuState;
	}

	public GameState getGameState() {
		return gameState;
	}

	public EntityHandler getEntityHandler() {
		return entityHandler;
	}

	public Player getPlayer() {
		return player;
	}

	public Camera getCamera() {
		return camera;
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	public void setTileMap(TileMap tileMap) {
		this.tileMap = tileMap;
	}

	public LightMap getLightMap() {
		return lightMap;
	}

	public void setLightMap(LightMap lightMap) {
		this.lightMap = lightMap;
	}
}