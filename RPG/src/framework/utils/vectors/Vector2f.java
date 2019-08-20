package framework.utils.vectors;

public final class Vector2f {

	private float x = 0;
	private float y = 0;
	private boolean empty;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;

		empty = false;
	}

	public Vector2f() {}

	public void clear() {
		x = 0;
		y = 0;
		empty = true;
	}

	public boolean isEmpty() {
		return empty;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void addX(float i) {
		x += i;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void addY(float i) {
		y += i;
	}
}