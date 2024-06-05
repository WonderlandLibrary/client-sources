package ClassSub;

import java.io.*;

public strictfp class Class224 implements Serializable
{
    private static final long serialVersionUID = 1339934L;
    public float x;
    public float y;
    
    
    public Class224() {
    }
    
    public Class224(final float[] array) {
        this.x = array[0];
        this.y = array[1];
    }
    
    public Class224(final double theta) {
        this.x = 1.0f;
        this.y = 0.0f;
        this.setTheta(theta);
    }
    
    public strictfp void setTheta(double n) {
        if (n < -360.0 || n > 360.0) {
            n %= 360.0;
        }
        if (n < 0.0) {
            n += 360.0;
        }
        double theta = this.getTheta();
        if (n < -360.0 || n > 360.0) {
            theta %= 360.0;
        }
        if (n < 0.0) {}
        final float length = this.length();
        this.x = length * (float)Class161.cos(StrictMath.toRadians(n));
        this.y = length * (float)Class161.sin(StrictMath.toRadians(n));
    }
    
    public strictfp Class224 add(final double n) {
        this.setTheta(this.getTheta() + n);
        return this;
    }
    
    public strictfp Class224 sub(final double n) {
        this.setTheta(this.getTheta() - n);
        return this;
    }
    
    public strictfp double getTheta() {
        double degrees = StrictMath.toDegrees(StrictMath.atan2(this.y, this.x));
        if (degrees < -360.0 || degrees > 360.0) {
            degrees %= 360.0;
        }
        if (degrees < 0.0) {
            degrees += 360.0;
        }
        return degrees;
    }
    
    public strictfp float getX() {
        return this.x;
    }
    
    public strictfp float getY() {
        return this.y;
    }
    
    public Class224(final Class224 class224) {
        this(class224.getX(), class224.getY());
    }
    
    public Class224(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public strictfp void set(final Class224 class224) {
        this.set(class224.getX(), class224.getY());
    }
    
    public strictfp float dot(final Class224 class224) {
        return this.x * class224.getX() + this.y * class224.getY();
    }
    
    public strictfp Class224 set(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public strictfp Class224 getPerpendicular() {
        return new Class224(-this.y, this.x);
    }
    
    public strictfp Class224 set(final float[] array) {
        return this.set(array[0], array[1]);
    }
    
    public strictfp Class224 negate() {
        return new Class224(-this.x, -this.y);
    }
    
    public strictfp Class224 negateLocal() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }
    
    public strictfp Class224 add(final Class224 class224) {
        this.x += class224.getX();
        this.y += class224.getY();
        return this;
    }
    
    public strictfp Class224 sub(final Class224 class224) {
        this.x -= class224.getX();
        this.y -= class224.getY();
        return this;
    }
    
    public strictfp Class224 scale(final float n) {
        this.x *= n;
        this.y *= n;
        return this;
    }
    
    public strictfp Class224 normalise() {
        final float length = this.length();
        if (length == 0.0f) {
            return this;
        }
        this.x /= length;
        this.y /= length;
        return this;
    }
    
    public strictfp Class224 getNormal() {
        final Class224 copy = this.copy();
        copy.normalise();
        return copy;
    }
    
    public strictfp float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }
    
    public strictfp float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }
    
    public strictfp void projectOntoUnit(final Class224 class224, final Class224 class225) {
        final float dot = class224.dot(this);
        class225.x = dot * class224.getX();
        class225.y = dot * class224.getY();
    }
    
    public strictfp Class224 copy() {
        return new Class224(this.x, this.y);
    }
    
    @Override
    public strictfp String toString() {
        return "[Vector2f " + this.x + "," + this.y + " (" + this.length() + ")]";
    }
    
    public strictfp float distance(final Class224 class224) {
        return (float)Math.sqrt(this.distanceSquared(class224));
    }
    
    public strictfp float distanceSquared(final Class224 class224) {
        final float n = class224.getX() - this.getX();
        final float n2 = class224.getY() - this.getY();
        return n * n + n2 * n2;
    }
    
    @Override
    public strictfp int hashCode() {
        return 997 * (int)this.x ^ 991 * (int)this.y;
    }
    
    @Override
    public strictfp boolean equals(final Object o) {
        if (o instanceof Class224) {
            final Class224 class224 = (Class224)o;
            return class224.x == this.x && class224.y == this.y;
        }
        return false;
    }
}
