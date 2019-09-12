package framework.graphics.tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import framework.core.game.Game;
import framework.graphics.entities.Entity;
import framework.graphics.entities.animated.characters.enemies.Enemy;
import framework.graphics.tiles.animated.AnimatedTile;
import framework.graphics.tiles.animated.DiscoTile;
import framework.graphics.tiles.animated.WaterTile;
import framework.graphics.tiles.statics.DirtTile;
import framework.graphics.tiles.statics.GrassTile;
import framework.graphics.tiles.statics.StoneTile;

public final class TileMap {

	public static final String[][] MAP_1 = TileMapReader
		.loadTextFileAsMap("C:/Users/mawji/eclipse-workspace/Mystics/src/res/worlds/world1.txt");

	private static Tile VOID;

	private Game game;
	private Tile[][] tiles;
	private int width;
	private int height;

	/**
	 * A collection of tiles in the game state that are rendered within the same
	 * scene
	 * 
	 * @param game
	 * @param map  set of id numbers to determine which and where each tile is
	 *             located
	 */
	public TileMap(Game game, String[][] map) {
		this.game = game;
		this.width = map[0].length;
		this.height = map.length;
		this.tiles = new Tile[width][height];

		// A white void tile is rendered if the camera somehow moves off of the map
		VOID = new Tile(game, 0, 0, 0, false);

		// Create all tile objects based on the contents of the text file
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (map[y][x].equals("0")) {
					setTile(new GrassTile(game, x, y));
				} else if (map[y][x].equals("1")) {
					setTile(new StoneTile(game, x, y));
				} else if (map[y][x].equals("2")) {
					setTile(new DirtTile(game, x, y));
				} else if (map[y][x].equals("3")) {
					setTile(new WaterTile(game, x, y));
				} else if (map[y][x].equals("4")) {
					setTile(new DiscoTile(game, x, y));
				}
			}
		}
	}

	public void render(GraphicsContext gc) {
		for (int y = getStartY(); y < getEndY(); y++) {
			for (int x = getStartX(); x < getEndX(); x++) {
				Tile tile = getTile(x, y);
				if (tile != null) {
					// Update the tile sprite if it is animated
					if (tile instanceof AnimatedTile) {
						((AnimatedTile) tile).tick();
					}
					tile.render(gc);
					if (game.getGameState().isDebuggingEnabled()) {
						// Render tile outlines
						gc.setStroke(Color.BLACK);
						gc.strokeRect(
							x * Tile.SIZE - game.getCamera().getOffset().getX(),
							y * Tile.SIZE - game.getCamera().getOffset().getY(),
							Tile.SIZE, Tile.SIZE
						);
						// Render A* paths
						for (Entity entity : game.getEntityHandler().getEntities()) {
							if (entity.isVisible() && entity instanceof Enemy
									&& ((Enemy) entity).getAttackPath() != null
									&& ((Enemy) entity).getAttackPath().contains(getTile(x, y))) {
								gc.setFill(Color.YELLOW);
								gc.fillRect(
									x * Tile.SIZE - game.getCamera().getOffset().getX(),
									y * Tile.SIZE - game.getCamera().getOffset().getY(),
									Tile.SIZE, Tile.SIZE
								);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Retrieve a tile at the given x and y indices
	 * 
	 * @param ix
	 * @param iy
	 * @return
	 */
	public Tile getTile(int ix, int iy) {
		if (!positionExistsOnMap(ix, iy)) {
			return VOID;
		}
		Tile tile = tiles[ix][iy];
		if (tile == null) {
			return VOID;
		}
		return tile;
	}

	public void setTile(Tile tile) {
		tiles[tile.getX()][tile.getY()] = tile;
	}

	/*
	 * Get left side of screen
	 */
	public int getStartX() {
		return (int) Math.max(0, game.getCamera().getOffset().getX() / Tile.SIZE);
	}

	/*
	 * Get top of screen
	 */
	public int getStartY() {
		return (int) Math.max(0, game.getCamera().getOffset().getY() / Tile.SIZE);
	}

	/*
	 * Get right side of screen
	 */
	public int getEndX() {
		return (int) Math.min(width, (game.getCamera().getOffset().getX() + game.getWindow().getWidth()) / Tile.SIZE + 1);
	}

	/*
	 * Get bottom of screen
	 */
	public int getEndY() {
		return (int) Math.min(height, (game.getCamera().getOffset().getY() + game.getWindow().getHeight()) / Tile.SIZE + 1);
	}

	public boolean positionExistsOnMap(int ix, int iy) {
		return ix >= 0 && iy >= 0 && ix < width && iy < height;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidthInPixels() {
		return width * Tile.SIZE;
	}

	public int getHeightInPixels() {
		return height * Tile.SIZE;
	}
}
