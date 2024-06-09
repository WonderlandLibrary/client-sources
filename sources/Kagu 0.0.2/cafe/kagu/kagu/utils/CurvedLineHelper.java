package cafe.kagu.kagu.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import org.apache.commons.lang3.RandomUtils;

import net.minecraft.util.MathHelper;

public class CurvedLineHelper {
	
	/**
	 * @param start The start point
	 * @param end The end point
	 */
	public CurvedLineHelper(float[] start, float[] end) {
		if (start.length < 2 || end.length < 2)
			throw new IllegalArgumentException("Both point arrays must be at least two in size");
		this.start = start;
		this.end = end;
	}
	
	private float[] start, end;
	private ArrayList<float[]> points = new ArrayList<>();
	private float progress = 0;
	
	/**
	 * Calculates and gets the current point on the curve
	 * @return The current point on the curve
	 */
	public float[] getCurrentPoint() {
		Stack<float[]> points = new Stack<float[]>();
		points.add(0, start);
		ArrayList<float[]> scaledPoints = scaledPoints();
		for (float[] p : scaledPoints) {
			points.add(0, p);
		}
		points.add(0, end);
		float progress = this.progress;
		
		float[] point1 = null;
		while ((point1 = points.pop()) != null) {
			if (points.isEmpty())
				return point1;
			float[] point2 = points.pop();
			float[] newPoint = new float[2];
			float[] xPositions = new float[] {point1[0], point2[0]};
			float[] yPositions = new float[] {point1[1], point2[1]};
			newPoint[0] = xPositions[0] + (xPositions[1] - xPositions[0]) * progress;
			newPoint[1] = yPositions[0] + (yPositions[1] - yPositions[0]) * progress;
			points.push(newPoint);
		}
		
		return new float[2];
	}
	
	/**
	 * Scales all points to the start and end points
	 * @return The scaled points
	 */
	private ArrayList<float[]> scaledPoints() {
		ArrayList<float[]> scaledPoints = new ArrayList<>();
		float rangeX = end[0] - start[0];
		float rangeY = Math.max(end[1] - start[1], 5);
		float startX = start[0];
		float startY = start[1];
		for (float[] point : points) {
			float[] newPoint = new float[] {startX + rangeX * point[0], MathHelper.clamp_float(startY + rangeY * point[1], -90, 90)};
			scaledPoints.add(newPoint);
		}
		return scaledPoints;
	}
	
	/**
	 * Creates a quadratic (3 point) curve
	 */
	public void createQuadraticCurve() {
		if (!points.isEmpty())
			points.clear();
		points.add(new float[] {RandomUtils.nextFloat(0, 1), RandomUtils.nextFloat(0, 2) - 1});
	}
	
	/**
	 * Creates a cubic (4 point) curve
	 */
	public void createCubicCurve() {
		if (!points.isEmpty())
			points.clear();
		points.add(new float[] {RandomUtils.nextFloat(0, 1), RandomUtils.nextFloat(0, 2) - 1});
		points.add(new float[] {RandomUtils.nextFloat(0, 1), RandomUtils.nextFloat(0, 2) - 1});
	}
	
	/**
	 * @return the progress
	 */
	public float getProgress() {
		return progress;
	}
	
	/**
	 * @param progress the progress to set
	 */
	public void setProgress(float progress) {
		this.progress = MathHelper.clamp_float(progress, 0, 1);
	}
	
	/**
	 * @param add The amount to add to the current progress
	 */
	public void addProgress(float add) {
		this.progress += add;
		this.progress = MathHelper.clamp_float(this.progress, 0, 1);
	}
	
	/**
	 * @param sub The amount to add to the current progress
	 */
	public void subtractProgress(float sub) {
		this.progress -= sub;
		this.progress = MathHelper.clamp_float(this.progress, 0, 1);
	}
	
	/**
	 * @param points All the points to use, two needed for a straight line, three points needed for a curve
	 */
	public void setPoints(float[] start, float[] end) {
		this.points = new ArrayList<>(Arrays.asList(start, end));
	}
	
	/**
	 * @return the start
	 */
	public float[] getStart() {
		return start;
	}
	
	/**
	 * @param start the start to set
	 */
	public void setStart(float[] start) {
		this.start = start;
	}
	
	/**
	 * @return the end
	 */
	public float[] getEnd() {
		return end;
	}
	
	/**
	 * @param end the end to set
	 */
	public void setEnd(float[] end) {
		this.end = end;
	}
	
}