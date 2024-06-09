package waveycapes.sim;

import java.util.ArrayList;
import java.util.List;

import io.github.liticane.clients.util.misc.MathUtils;
import waveycapes.utils.Mth;

public class StickSimulation {
	public List<Point> points = new ArrayList<>();

	public List<Stick> sticks = new ArrayList<>();

	public float gravity = 20.0F;

	public int numIterations = 30;

	private float maxBend = 5.0F;

	public void simulate() {
		float deltaTime = 50f / 1000f;
		Vector2 down = new Vector2(0, 25 * deltaTime);
		Vector2 tmp = new Vector2(0, 0);

		for (Point p : points) {
			if (!p.locked) {
				tmp.copy(p.position);
				p.position.subtract(down);
				p.prevPosition.copy(tmp);
			}
		}

		Point basePoint = points.get(0);
		for (Point p : points) {
			if (p != basePoint && p.position.x - basePoint.position.x > 0) {
				p.position.x = basePoint.position.x - 0.1f;
			}
		}

		for (int i = points.size() - 2; i >= 1; i--) {
			double angle = getAngle(points.get(i).position, points.get(i - 1).position, points.get(i + 1).position);
			angle *= 57.2958;

			if (angle > 360) {
				angle -= 360;
			}

			if (angle < -360) {
				angle += 360;
			}

			double abs = MathUtils.abs(angle);
			if (abs < 180 - this.maxBend) {
				this.points.get(i + 1).position = this.getReplacement(points.get(i).position, points.get(i - 1).position, angle, 180 - this.maxBend + 1);
			}
			if (abs > 180 + this.maxBend) {
				this.points.get(i + 1).position = this.getReplacement(points.get(i).position, points.get(i - 1).position, angle, 180 + this.maxBend - 1);
			}
		}

		// move into correct direction
		for (int i = 0; i < numIterations; i++) {
			for (int x = sticks.size() - 1; x >= 0; x--) {
				Stick stick = sticks.get(x);
				Vector2 stickCentre = stick.pointA.position.clone().add(stick.pointB.position).div(2);
				Vector2 stickDir = stick.pointA.position.clone().subtract(stick.pointB.position).normalize();
				if (!stick.pointA.locked) {
					stick.pointA.position = stickCentre.clone().add(stickDir.clone().mul(stick.length / 2));
				}
				if (!stick.pointB.locked) {
					stick.pointB.position = stickCentre.clone().subtract(stickDir.clone().mul(stick.length / 2));
				}
			}
		}
		// fix in the position/length, this prevents it from acting like a spring/stretchy
		for (int x = 0; x < sticks.size(); x++) {
			Stick stick = sticks.get(x);
			Vector2 stickDir = stick.pointA.position.clone().subtract(stick.pointB.position).normalize();
			if (!stick.pointB.locked) {
				stick.pointB.position = stick.pointA.position.clone().subtract(stickDir.mul(stick.length));
			}
		}
	}

	private Vector2 getReplacement(Vector2 middle, Vector2 prev, double angle, double target) {
		double theta = target / 57.2958D;
		float x = prev.x - middle.x;
		float y = prev.y - middle.y;
		if (angle < 0.0D)
			theta *= -1.0D;
		double cs = Math.cos(theta);
		double sn = Math.sin(theta);
		return new Vector2((float) (x * cs - y * sn + middle.x), (float) (x * sn + y * cs + middle.y));
	}

	private double getAngle(Vector2 middle, Vector2 prev, Vector2 next) {
		return Math.atan2((next.y - middle.y), (next.x - middle.x))
				- Math.atan2((prev.y - middle.y), (prev.x - middle.x));
	}

	public static class Point {
		public StickSimulation.Vector2 position = new StickSimulation.Vector2(0.0F, 0.0F);

		public StickSimulation.Vector2 prevPosition = new StickSimulation.Vector2(0.0F, 0.0F);

		public boolean locked;

		public float getLerpX(float delta) {
			return Mth.lerp(delta, this.prevPosition.x, this.position.x);
		}

		public float getLerpY(float delta) {
			return Mth.lerp(delta, this.prevPosition.y, this.position.y);
		}
	}

	public static class Stick {
		public StickSimulation.Point pointA;

		public StickSimulation.Point pointB;

		public float length;

		public Stick(StickSimulation.Point pointA, StickSimulation.Point pointB, float length) {
			this.pointA = pointA;
			this.pointB = pointB;
			this.length = length;
		}
	}

	public static class Vector2 {
		public float x;

		public float y;

		public Vector2(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public Vector2 clone() {
			return new Vector2(this.x, this.y);
		}

		public void copy(Vector2 vec) {
			this.x = vec.x;
			this.y = vec.y;
		}

		public Vector2 add(Vector2 vec) {
			this.x += vec.x;
			this.y += vec.y;
			return this;
		}

		public Vector2 subtract(Vector2 vec) {
			this.x -= vec.x;
			this.y -= vec.y;
			return this;
		}

		public Vector2 div(float amount) {
			this.x /= amount;
			this.y /= amount;
			return this;
		}

		public Vector2 mul(float amount) {
			this.x *= amount;
			this.y *= amount;
			return this;
		}

		public Vector2 normalize() {
			float f = (float) Math.sqrt((this.x * this.x + this.y * this.y));
			if (f < 1.0E-4F) {
				this.x = 0.0F;
				this.y = 0.0F;
			} else {
				this.x /= f;
				this.y /= f;
			}
			return this;
		}

		public String toString() {
			return "Vector2 [x=" + this.x + ", y=" + this.y + "]";
		}
	}
}
