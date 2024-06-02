/**
 * 
 */
package cafe.kagu.kagu.utils;

/**
 * @author lavaflowglow
 *
 */
public class MathUtils {
	
	/**
	 * Calculates the distance between two points
	 * @param x1 The first x position
	 * @param y1 The first y position
	 * @param x2 The second x position
	 * @param y2 The second y position
	 * @return The distance between the two points
	 */
	public static double getDistance2D(double x1, double y1, double x2, double y2) {
		double distX = Math.max(x1, x2) - Math.min(x1, x2);
		return getDistance2D(distX, y1, y2);
	}
	
	/**
	 * Calculates the distance between two points
	 * @param side1 The length of 1 of the sides
	 * @param y1 The first y position
	 * @param y2 The second y position
	 * @return The distance between the two points
	 */
	public static double getDistance2D(double side1, double y1, double y2) {
		double distY = y2 - y1;
		return getDistance2D(side1, distY);
	}
	
	/**
	 * Calculates the distance between two points
	 * @param side1 The length of the first side
	 * @param side2 The length of the second side
	 * @return The distance between the two points
	 */
	public static double getDistance2D(double side1, double side2) {
		return Math.sqrt((side1 * side1) + (side2 * side2));
	}
	
	/**
	 * Lerps two values
	 * @param start The starting value
	 * @param end The ending value
	 * @param progress The current progress
	 * @return The final lerped number
	 */
	public static double lerp(double start, double end, double progress) {
		return start + Math.abs(end - start) * progress;
	}
	
	/**
	 * Calculates the range between two values and returns it
	 * @param value1 The first value
	 * @param value2 The second value
	 * @return The range between the two values
	 */
	public static double getRange(double value1, double value2) {
		return Math.abs(value2 - value1);
	}
	
	/**
	 * Calculates the range between two values and returns it
	 * @param value1 The first value
	 * @param value2 The second value
	 * @return The range between the two values
	 */
	public static float getRange(float value1, float value2) {
		return Math.abs(value2 - value1);
	}
	
	/**
	 * @author https://www.geeksforgeeks.org/check-whether-a-given-point-lies-inside-a-triangle-or-not/
	 * A utility function to calculate area of triangle formed by (x1, y1) (x2, y2)
	 * and (x3, y3)
	 */
	public static double areaTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
	}

	/**
	 * @author https://www.geeksforgeeks.org/check-whether-a-given-point-lies-inside-a-triangle-or-not/
	 * A function to check whether point P(x, y) lies inside the triangle formed by
	 * A(x1, y1), B(x2, y2) and C(x3, y3)
	 */
	public static boolean isInsideTriangle(double x1, double y1, double x2, double y2, double x3, double y3, double x, double y) {
		// Calculate area of triangle ABC
		double A = areaTriangle(x1, y1, x2, y2, x3, y3);

		// Calculate area of triangle PBC
		double A1 = areaTriangle(x, y, x2, y2, x3, y3);

		// Calculate area of triangle PAC
		double A2 = areaTriangle(x1, y1, x, y, x3, y3);

		// Calculate area of triangle PAB
		double A3 = areaTriangle(x1, y1, x2, y2, x, y);

		// Check if sum of A1, A2 and A3 is same as A
		return (Math.ceil(A) == Math.ceil(A1 + A2 + A3));
	}
	
	
}
