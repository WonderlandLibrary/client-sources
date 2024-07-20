/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.cape.sim;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.cape.util.Mth;

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
        void var5_12;
        void var5_10;
        void var5_8;
        float deltaTime = 0.05f;
        Vector2 down = new Vector2(0.0f, this.gravity * 4.0f * deltaTime);
        Vector2 tmp = new Vector2(0.0f, 0.0f);
        for (Point point : this.points) {
            if (point.locked) continue;
            tmp.copy(point.position);
            point.prevPosition.copy(tmp);
            point.position.subtract(down);
        }
        Point basePoint = this.points.get(0);
        for (Point p : this.points) {
            if (p == basePoint || !(p.position.x - basePoint.position.x > 0.0f)) continue;
            p.position.x = basePoint.position.x - 0.2f;
        }
        int n = this.points.size() - 2;
        while (var5_8 >= true) {
            Vector2 replacement;
            double abs;
            double angle = this.getAngle(this.points.get((int)var5_8).position, this.points.get((int)(var5_8 - true)).position, this.points.get((int)(var5_8 + true)).position);
            if ((angle *= 57.2958) > 360.0) {
                angle -= 360.0;
            }
            if (angle < -360.0) {
                angle += 360.0;
            }
            if ((abs = Math.abs(angle)) < (double)(180.0f - this.maxBend)) {
                this.points.get((int)(var5_8 + true)).position = replacement = this.getReplacement(this.points.get((int)var5_8).position, this.points.get((int)(var5_8 - true)).position, angle, 180.0f - this.maxBend + 1.0f);
            }
            if (abs > (double)(180.0f + this.maxBend)) {
                this.points.get((int)(var5_8 + true)).position = replacement = this.getReplacement(this.points.get((int)var5_8).position, this.points.get((int)(var5_8 - true)).position, angle, 180.0f + this.maxBend - 1.0f);
            }
            --var5_8;
        }
        boolean bl = false;
        while (var5_10 < this.numIterations) {
            for (int x = this.sticks.size() - 1; x >= 0; --x) {
                Stick stick = this.sticks.get(x);
                Vector2 stickCentre = stick.pointA.position.clone().add(stick.pointB.position).div(2.0f);
                Vector2 stickDir = stick.pointA.position.clone().subtract(stick.pointB.position).normalize();
                if (!stick.pointA.locked) {
                    stick.pointA.position = stickCentre.clone().add(stickDir.clone().mul(stick.length / 2.0f));
                }
                if (stick.pointB.locked) continue;
                stick.pointB.position = stickCentre.clone().subtract(stickDir.clone().mul(stick.length / 2.0f));
            }
            ++var5_10;
        }
        boolean bl2 = false;
        while (var5_12 < this.sticks.size()) {
            Stick stick = this.sticks.get((int)var5_12);
            Vector2 stickDir = stick.pointA.position.clone().subtract(stick.pointB.position).normalize();
            if (!stick.pointB.locked) {
                stick.pointB.position = stick.pointA.position.clone().subtract(stickDir.mul(stick.length));
            }
            ++var5_12;
        }
    }

    private Vector2 getReplacement(Vector2 middle, Vector2 prev, double angle, double target) {
        double theta = target / 57.2958;
        float x = prev.x - middle.x;
        float y = prev.y - middle.y;
        if (angle < 0.0) {
            theta *= -1.0;
        }
        double cs = Math.cos(theta);
        double sn = Math.sin(theta);
        return new Vector2((float)((double)x * cs - (double)y * sn + (double)middle.x), (float)((double)x * sn + (double)y * cs + (double)middle.y));
    }

    private double getAngle(Vector2 middle, Vector2 prev, Vector2 next) {
        return Math.atan2(next.y - middle.y, next.x - middle.x) - Math.atan2(prev.y - middle.y, prev.x - middle.x);
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
}

