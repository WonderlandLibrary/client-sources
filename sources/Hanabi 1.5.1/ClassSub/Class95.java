package ClassSub;

import java.util.*;

public class Class95 extends Class324
{
    public static final int TOP_LEFT = 1;
    public static final int TOP_RIGHT = 2;
    public static final int BOTTOM_RIGHT = 4;
    public static final int BOTTOM_LEFT = 8;
    public static final int ALL = 15;
    private static final int DEFAULT_SEGMENT_COUNT = 25;
    private float cornerRadius;
    private int segmentCount;
    private int cornerFlags;
    
    
    public Class95(final float n, final float n2, final float n3, final float n4, final float n5) {
        this(n, n2, n3, n4, n5, 25);
    }
    
    public Class95(final float n, final float n2, final float n3, final float n4, final float n5, final int n6) {
        this(n, n2, n3, n4, n5, n6, 15);
    }
    
    public Class95(final float x, final float y, final float width, final float height, final float cornerRadius, final int segmentCount, final int cornerFlags) {
        super(x, y, width, height);
        if (cornerRadius < 0.0f) {
            throw new IllegalArgumentException("corner radius must be >= 0");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cornerRadius = cornerRadius;
        this.segmentCount = segmentCount;
        this.pointsDirty = true;
        this.cornerFlags = cornerFlags;
    }
    
    public float getCornerRadius() {
        return this.cornerRadius;
    }
    
    public void setCornerRadius(final float cornerRadius) {
        if (cornerRadius >= 0.0f && cornerRadius != this.cornerRadius) {
            this.cornerRadius = cornerRadius;
            this.pointsDirty = true;
        }
    }
    
    @Override
    public float getHeight() {
        return this.height;
    }
    
    @Override
    public void setHeight(final float height) {
        if (this.height != height) {
            this.height = height;
            this.pointsDirty = true;
        }
    }
    
    @Override
    public float getWidth() {
        return this.width;
    }
    
    @Override
    public void setWidth(final float width) {
        if (width != this.width) {
            this.width = width;
            this.pointsDirty = true;
        }
    }
    
    @Override
    protected void createPoints() {
        this.maxX = this.x + this.width;
        this.maxY = this.y + this.height;
        this.minX = this.x;
        this.minY = this.y;
        final float n = this.width - 1.0f;
        final float n2 = this.height - 1.0f;
        if (this.cornerRadius == 0.0f) {
            (this.points = new float[8])[0] = this.x;
            this.points[1] = this.y;
            this.points[2] = this.x + n;
            this.points[3] = this.y;
            this.points[4] = this.x + n;
            this.points[5] = this.y + n2;
            this.points[6] = this.x;
            this.points[7] = this.y + n2;
        }
        else {
            float n3 = this.cornerRadius * 2.0f;
            if (n3 > n) {
                n3 = n;
                this.cornerRadius = n3 / 2.0f;
            }
            if (n3 > n2) {
                this.cornerRadius = n2 / 2.0f;
            }
            final ArrayList<Float> list = new ArrayList<Float>();
            if ((this.cornerFlags & 0x1) != 0x0) {
                list.addAll(this.createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + this.cornerRadius, 180.0f, 270.0f));
            }
            else {
                list.add(new Float(this.x));
                list.add(new Float(this.y));
            }
            if ((this.cornerFlags & 0x2) != 0x0) {
                list.addAll(this.createPoints(this.segmentCount, this.cornerRadius, this.x + n - this.cornerRadius, this.y + this.cornerRadius, 270.0f, 360.0f));
            }
            else {
                list.add(new Float(this.x + n));
                list.add(new Float(this.y));
            }
            if ((this.cornerFlags & 0x4) != 0x0) {
                list.addAll(this.createPoints(this.segmentCount, this.cornerRadius, this.x + n - this.cornerRadius, this.y + n2 - this.cornerRadius, 0.0f, 90.0f));
            }
            else {
                list.add(new Float(this.x + n));
                list.add(new Float(this.y + n2));
            }
            if ((this.cornerFlags & 0x8) != 0x0) {
                list.addAll(this.createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + n2 - this.cornerRadius, 90.0f, 180.0f));
            }
            else {
                list.add(new Float(this.x));
                list.add(new Float(this.y + n2));
            }
            this.points = new float[list.size()];
            for (int i = 0; i < list.size(); ++i) {
                this.points[i] = list.get(i);
            }
        }
        this.findCenter();
        this.calculateRadius();
    }
    
    private List createPoints(final int n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final ArrayList<Float> list = new ArrayList<Float>();
        final int n7 = 360 / n;
        for (float n8 = n5; n8 <= n6 + n7; n8 += n7) {
            float n9 = n8;
            if (n9 > n6) {
                n9 = n6;
            }
            final float n10 = (float)(n3 + Class161.cos(Math.toRadians(n9)) * n2);
            final float n11 = (float)(n4 + Class161.sin(Math.toRadians(n9)) * n2);
            list.add(new Float(n10));
            list.add(new Float(n11));
        }
        return list;
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        this.checkPoints();
        final Class269 class142 = new Class269();
        final float[] points = new float[this.points.length];
        class141.transform(this.points, 0, points, 0, this.points.length / 2);
        class142.points = points;
        class142.findCenter();
        return class142;
    }
}
