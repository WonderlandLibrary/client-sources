package dev.star.utils.addons.waveycapes.sim;

import dev.star.utils.addons.waveycapes.util.Mth;

import java.util.ArrayList;
import java.util.List;

public class StickSimulation {
    public List<Point> points = new ArrayList<Point>();
    public List<Stick> sticks = new ArrayList<Stick>();
    public float gravity = 20.0f;
    public int numIterations = 30;
    private float maxBend = 5.0f;

    /*
     * WARNING - void declaration
     */
    public void simulate() {
        this.gravity = 25.0f;
        final float deltaTime = 0.05f;
        final Vector2 down = new Vector2(0.0f, this.gravity * deltaTime);
        final Vector2 tmp = new Vector2(0.0f, 0.0f);
        for (final Point p : this.points) {
            if (!p.locked) {
                tmp.copy(p.position);
                p.position.subtract(down);
                p.prevPosition.copy(tmp);
            }
        }
        final Point basePoint = this.points.get(0);
        for (final Point p2 : this.points) {
            if (p2 != basePoint && p2.position.x - basePoint.position.x > 0.0f) {
                p2.position.x = basePoint.position.x - 0.1f;
            }
        }
        for (int i = this.points.size() - 2; i >= 1; --i) {
            double angle = this.getAngle(this.points.get(i).position, this.points.get(i - 1).position, this.points.get(i + 1).position);
            angle *= 57.2958;
            if (angle > 360.0) {
                angle -= 360.0;
            }
            if (angle < -360.0) {
                angle += 360.0;
            }
            final double abs = Math.abs(angle);
            if (abs < 180.0f - this.maxBend) {
                final Vector2 replacement = this.getReplacement(this.points.get(i).position, this.points.get(i - 1).position, angle, 180.0f - this.maxBend + 1.0f);
                this.points.get(i + 1).position = replacement;
            }
            if (abs > 180.0f + this.maxBend) {
                final Vector2 replacement = this.getReplacement(this.points.get(i).position, this.points.get(i - 1).position, angle, 180.0f + this.maxBend - 1.0f);
                this.points.get(i + 1).position = replacement;
            }
        }
        for (int i = 0; i < this.numIterations; ++i) {
            for (int x = this.sticks.size() - 1; x >= 0; --x) {
                final Stick stick = this.sticks.get(x);
                final Vector2 stickCentre = stick.pointA.position.clone().add(stick.pointB.position).div(2.0f);
                final Vector2 stickDir = stick.pointA.position.clone().subtract(stick.pointB.position).normalize();
                if (!stick.pointA.locked) {
                    stick.pointA.position = stickCentre.clone().add(stickDir.clone().mul(stick.length / 2.0f));
                }
                if (!stick.pointB.locked) {
                    stick.pointB.position = stickCentre.clone().subtract(stickDir.clone().mul(stick.length / 2.0f));
                }
            }
        }
        for (int x2 = 0; x2 < this.sticks.size(); ++x2) {
            final Stick stick2 = this.sticks.get(x2);
            final Vector2 stickDir2 = stick2.pointA.position.clone().subtract(stick2.pointB.position).normalize();
            if (!stick2.pointB.locked) {
                stick2.pointB.position = stick2.pointA.position.clone().subtract(stickDir2.mul(stick2.length));
            }
        }
    }

    private Vector2 getReplacement(Vector2 middle, Vector2 prev, double angle, double target) {
        double theta = target / 57.2958;
        float x2 = prev.x - middle.x;
        float y2 = prev.y - middle.y;
        if (angle < 0.0) {
            theta *= -1.0;
        }
        double cs = Math.cos(theta);
        double sn = Math.sin(theta);
        return new Vector2((float)((double)x2 * cs - (double)y2 * sn + (double)middle.x), (float)((double)x2 * sn + (double)y2 * cs + (double)middle.y));
    }

    private double getAngle(Vector2 middle, Vector2 prev, Vector2 next) {
        return Math.atan2(next.y - middle.y, next.x - middle.x) - Math.atan2(prev.y - middle.y, prev.x - middle.x);
    }

    public static class Vector2 {
        public float x;
        public float y;

        public Vector2(float x2, float y2) {
            this.x = x2;
            this.y = y2;
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
            float f = (float)Math.sqrt(this.x * this.x + this.y * this.y);
            if (f < 1.0E-4f) {
                this.x = 0.0f;
                this.y = 0.0f;
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

    public static class Stick {
        public Point pointA;
        public Point pointB;
        public float length;

        public Stick(Point pointA, Point pointB, float length) {
            this.pointA = pointA;
            this.pointB = pointB;
            this.length = length;
        }
    }

    public static class Point {
        public Vector2 position = new Vector2(0.0f, 0.0f);
        public Vector2 prevPosition = new Vector2(0.0f, 0.0f);
        public boolean locked;

        public float getLerpX(float delta) {
            return Mth.lerp(delta, this.prevPosition.x, this.position.x);
        }

        public float getLerpY(float delta) {
            return Mth.lerp(delta, this.prevPosition.y, this.position.y);
        }
    }
}

