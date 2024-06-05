package ClassSub;

import java.util.*;

public class Class269 extends Class186
{
    private boolean allowDups;
    private boolean closed;
    
    
    public Class269(final float[] array) {
        this.allowDups = false;
        this.closed = true;
        final int length = array.length;
        this.points = new float[length];
        this.maxX = -1.4E-45f;
        this.maxY = -1.4E-45f;
        this.minX = Float.MAX_VALUE;
        this.minY = Float.MAX_VALUE;
        this.x = Float.MAX_VALUE;
        this.y = Float.MAX_VALUE;
        for (int i = 0; i < length; ++i) {
            this.points[i] = array[i];
            if (i % 2 == 0) {
                if (array[i] > this.maxX) {
                    this.maxX = array[i];
                }
                if (array[i] < this.minX) {
                    this.minX = array[i];
                }
                if (array[i] < this.x) {
                    this.x = array[i];
                }
            }
            else {
                if (array[i] > this.maxY) {
                    this.maxY = array[i];
                }
                if (array[i] < this.minY) {
                    this.minY = array[i];
                }
                if (array[i] < this.y) {
                    this.y = array[i];
                }
            }
        }
        this.findCenter();
        this.calculateRadius();
        this.pointsDirty = true;
    }
    
    public Class269() {
        this.allowDups = false;
        this.closed = true;
        this.points = new float[0];
        this.maxX = -1.4E-45f;
        this.maxY = -1.4E-45f;
        this.minX = Float.MAX_VALUE;
        this.minY = Float.MAX_VALUE;
    }
    
    public void setAllowDuplicatePoints(final boolean allowDups) {
        this.allowDups = allowDups;
    }
    
    public void addPoint(final float n, final float n2) {
        if (this.hasVertex(n, n2) && !this.allowDups) {
            return;
        }
        final ArrayList<Float> list = new ArrayList<Float>();
        for (int i = 0; i < this.points.length; ++i) {
            list.add(new Float(this.points[i]));
        }
        list.add(new Float(n));
        list.add(new Float(n2));
        final int size = list.size();
        this.points = new float[size];
        for (int j = 0; j < size; ++j) {
            this.points[j] = list.get(j);
        }
        if (n > this.maxX) {
            this.maxX = n;
        }
        if (n2 > this.maxY) {
            this.maxY = n2;
        }
        if (n < this.minX) {
            this.minX = n;
        }
        if (n2 < this.minY) {
            this.minY = n2;
        }
        this.findCenter();
        this.calculateRadius();
        this.pointsDirty = true;
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        this.checkPoints();
        final Class269 class142 = new Class269();
        final float[] points = new float[this.points.length];
        class141.transform(this.points, 0, points, 0, this.points.length / 2);
        class142.points = points;
        class142.findCenter();
        class142.closed = this.closed;
        return class142;
    }
    
    @Override
    public void setX(final float x) {
        super.setX(x);
        this.pointsDirty = false;
    }
    
    @Override
    public void setY(final float y) {
        super.setY(y);
        this.pointsDirty = false;
    }
    
    @Override
    protected void createPoints() {
    }
    
    @Override
    public boolean closed() {
        return this.closed;
    }
    
    public void setClosed(final boolean closed) {
        this.closed = closed;
    }
    
    public Class269 copy() {
        final float[] array = new float[this.points.length];
        System.arraycopy(this.points, 0, array, 0, array.length);
        return new Class269(array);
    }
}
