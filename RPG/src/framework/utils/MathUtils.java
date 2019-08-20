package framework.utils;

public final class MathUtils {

	// Linear interpolation algorithm
	public static double lerp(double a, double b, double f) {
		return (a * (1.0 - f)) + (b * f);
	}

	// Limits a given value from going under 0 or above a max value
	public static double clamp(double value, double max) {
		return value < 0 ? 0 : value > max ? max : value;
	}

	// Euclidean distance formula
	public static double euclid(double x1, double x2, double y1, double y2) {
		return Math.hypot(x2 - x1, y2 - y1);
	}

	// Manhattan distance formula
	public static double manhattan(double x1, double x2, double y1, double y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}
}