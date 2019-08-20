package framework.utils.vectors;

public final class Vector2Int {

	private int x = 0;
	private int y = 0;
	private boolean empty;

	public Vector2Int(int x, int y) {
		this.x = x;
		this.y = y;

		empty = false;
	}

	public Vector2Int() {}

	public void clear() {
		x = 0;
		y = 0;
		empty = true;
	}

	public boolean isEmpty() {
		return empty;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void addX(int i) {
		x += i;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addY(int i) {
		y += i;
	}
}