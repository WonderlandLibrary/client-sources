package ClassSub;

import java.util.*;

public class Class156 extends Class186
{
    private ArrayList localPoints;
    private float cx;
    private float cy;
    private boolean closed;
    private ArrayList holes;
    private ArrayList hole;
    
    
    public Class156(final float cx, final float cy) {
        this.localPoints = new ArrayList();
        this.holes = new ArrayList();
        this.localPoints.add(new float[] { cx, cy });
        this.cx = cx;
        this.cy = cy;
        this.pointsDirty = true;
    }
    
    public void startHole(final float n, final float n2) {
        this.hole = new ArrayList();
        this.holes.add(this.hole);
    }
    
    public void lineTo(final float cx, final float cy) {
        if (this.hole != null) {
            this.hole.add(new float[] { cx, cy });
        }
        else {
            this.localPoints.add(new float[] { cx, cy });
        }
        this.cx = cx;
        this.cy = cy;
        this.pointsDirty = true;
    }
    
    public void close() {
        this.closed = true;
    }
    
    public void curveTo(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.curveTo(n, n2, n3, n4, n5, n6, 10);
    }
    
    public void curveTo(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final int n7) {
        if (this.cx == n && this.cy == n2) {
            return;
        }
        final Class254 class254 = new Class254(new Class224(this.cx, this.cy), new Class224(n3, n4), new Class224(n5, n6), new Class224(n, n2));
        final float n8 = 1.0f / n7;
        for (int i = 1; i < n7 + 1; ++i) {
            final Class224 point = class254.pointAt(i * n8);
            if (this.hole != null) {
                this.hole.add(new float[] { point.x, point.y });
            }
            else {
                this.localPoints.add(new float[] { point.x, point.y });
            }
            this.cx = point.x;
            this.cy = point.y;
        }
        this.pointsDirty = true;
    }
    
    @Override
    protected void createPoints() {
        this.points = new float[this.localPoints.size() * 2];
        for (int i = 0; i < this.localPoints.size(); ++i) {
            final float[] array = this.localPoints.get(i);
            this.points[i * 2] = array[0];
            this.points[i * 2 + 1] = array[1];
        }
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        final Class156 class142 = new Class156(this.cx, this.cy);
        class142.localPoints = this.transform(this.localPoints, class141);
        for (int i = 0; i < this.holes.size(); ++i) {
            class142.holes.add(this.transform((ArrayList)this.holes.get(i), class141));
        }
        class142.closed = this.closed;
        return class142;
    }
    
    private ArrayList transform(final ArrayList list, final Class141 class141) {
        final float[] array = new float[list.size() * 2];
        final float[] array2 = new float[list.size() * 2];
        for (int i = 0; i < list.size(); ++i) {
            array[i * 2] = ((float[])list.get(i))[0];
            array[i * 2 + 1] = ((float[])list.get(i))[1];
        }
        class141.transform(array, 0, array2, 0, list.size());
        final ArrayList<float[]> list2 = new ArrayList<float[]>();
        for (int j = 0; j < list.size(); ++j) {
            list2.add(new float[] { array2[j * 2], array2[j * 2 + 1] });
        }
        return list2;
    }
    
    @Override
    public boolean closed() {
        return this.closed;
    }
}
