package ClassSub;

import java.util.*;

public class Class103 extends Class186
{
    protected static final int DEFAULT_SEGMENT_COUNT = 50;
    private int segmentCount;
    private float radius1;
    private float radius2;
    
    
    public Class103(final float n, final float n2, final float n3, final float n4) {
        this(n, n2, n3, n4, 50);
    }
    
    public Class103(final float n, final float n2, final float radius1, final float radius2, final int segmentCount) {
        this.x = n - radius1;
        this.y = n2 - radius2;
        this.radius1 = radius1;
        this.radius2 = radius2;
        this.segmentCount = segmentCount;
        this.checkPoints();
    }
    
    public void setRadii(final float radius1, final float radius2) {
        this.setRadius1(radius1);
        this.setRadius2(radius2);
    }
    
    public float getRadius1() {
        return this.radius1;
    }
    
    public void setRadius1(final float radius1) {
        if (radius1 != this.radius1) {
            this.radius1 = radius1;
            this.pointsDirty = true;
        }
    }
    
    public float getRadius2() {
        return this.radius2;
    }
    
    public void setRadius2(final float radius2) {
        if (radius2 != this.radius2) {
            this.radius2 = radius2;
            this.pointsDirty = true;
        }
    }
    
    @Override
    protected void createPoints() {
        final ArrayList<Float> list = new ArrayList<Float>();
        this.maxX = -1.4E-45f;
        this.maxY = -1.4E-45f;
        this.minX = Float.MAX_VALUE;
        this.minY = Float.MAX_VALUE;
        final float n = 0.0f;
        final float n2 = 359.0f;
        final float n3 = this.x + this.radius1;
        final float n4 = this.y + this.radius2;
        final int n5 = 360 / this.segmentCount;
        for (float n6 = n; n6 <= n2 + n5; n6 += n5) {
            float n7 = n6;
            if (n7 > n2) {
                n7 = n2;
            }
            final float n8 = (float)(n3 + Class161.cos(Math.toRadians(n7)) * this.radius1);
            final float n9 = (float)(n4 + Class161.sin(Math.toRadians(n7)) * this.radius2);
            if (n8 > this.maxX) {
                this.maxX = n8;
            }
            if (n9 > this.maxY) {
                this.maxY = n9;
            }
            if (n8 < this.minX) {
                this.minX = n8;
            }
            if (n9 < this.minY) {
                this.minY = n9;
            }
            list.add(new Float(n8));
            list.add(new Float(n9));
        }
        this.points = new float[list.size()];
        for (int i = 0; i < this.points.length; ++i) {
            this.points[i] = list.get(i);
        }
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        this.checkPoints();
        final Class269 class142 = new Class269();
        final float[] points = new float[this.points.length];
        class141.transform(this.points, 0, points, 0, this.points.length / 2);
        class142.points = points;
        class142.checkPoints();
        return class142;
    }
    
    @Override
    protected void findCenter() {
        (this.center = new float[2])[0] = this.x + this.radius1;
        this.center[1] = this.y + this.radius2;
    }
    
    @Override
    protected void calculateRadius() {
        this.boundingCircleRadius = ((this.radius1 > this.radius2) ? this.radius1 : this.radius2);
    }
}
