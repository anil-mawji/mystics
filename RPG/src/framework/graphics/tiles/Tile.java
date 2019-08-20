package framework.graphics.tiles;

import framework.core.game.Game;
import framework.graphics.sprites.SpriteSheet;
import framework.utils.MathUtils;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Tile implements Comparable<Tile> {

	public static final SpriteSheet SPRITESHEET = new SpriteSheet("res/textures/sheet3.png", 64, 64);
	public static final int ORTHOGONAL_COST = 10;
	public static final int SIZE = 64;

	private Game game;
	private Tile parent;
	private boolean walkable;
	private int id;
	private int x;
	private int y;
	private int g;
	private int h;

	public Tile(Game game, int id, int x, int y, boolean walkable) {
		this.game = game;
		this.id = id;
		this.x = x;
		this.y = y;
		this.walkable = walkable;
	}

	public void render(GraphicsContext gc) {
		gc.fillRect(
				x * SIZE - game.getCamera().getOffset().getX(),
				y * SIZE - game.getCamera().getOffset().getY(),
				SIZE, SIZE
		);
	}

	public Bounds getCollisionBounds(float offsetX, float offsetY) {
		return new Rectangle(x * SIZE + offsetX, y * SIZE + offsetY, SIZE, SIZE).getBoundsInParent();
	}

	/*
	 * Heuristic that determines how costly it is to move to this tile relative to
	 * another one
	 */
	@Override
	public int compareTo(Tile o) {
		return getF() > o.getF() ? 1 : getF() == o.getF() ? 0 : -1;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public int getG() {
		return g;
	}

	/**
	 * G cost is the distance from the start tile to this tile
	 * 
	 * @param parent
	 */
	public void setG(Tile parent) {
		this.g = parent.getG() + ORTHOGONAL_COST;
	}

	public int calcG(Tile parent) {
		return parent.getG() + ORTHOGONAL_COST;
	}

	public int getH() {
		return h;
	}

	/**
	 * H cost is the distance from this tile to the goal
	 * 
	 * @param goal
	 */
	public void setH(Tile goal) {
		this.h = (int) MathUtils.manhattan(x, goal.getX(), y, goal.getY()) * ORTHOGONAL_COST;
	}

	public int getF() {
		return g + h;
	}

	public Tile getParent() {
		return parent;
	}

	public void setParent(Tile parent) {
		this.parent = parent;
	}
}