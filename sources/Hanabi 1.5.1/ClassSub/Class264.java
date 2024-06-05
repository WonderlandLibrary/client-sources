package ClassSub;

import java.util.*;

public class Class264 extends Class186
{
    private ArrayList shapes;
    private float offset;
    private Class186 current;
    private Class186 next;
    
    
    public Class264(final Class186 class186) {
        (this.shapes = new ArrayList()).add(class186);
        this.points = new float[class186.points.length];
        this.current = class186;
        this.next = class186;
    }
    
    public void addShape(final Class186 class186) {
        if (class186.points.length != this.points.length) {
            throw new RuntimeException("Attempt to morph between two shapes with different vertex counts");
        }
        final Class186 class187 = this.shapes.get(this.shapes.size() - 1);
        if (this.equalShapes(class187, class186)) {
            this.shapes.add(class187);
        }
        else {
            this.shapes.add(class186);
        }
        if (this.shapes.size() == 2) {
            this.next = this.shapes.get(1);
        }
    }
    
    private boolean equalShapes(final Class186 class186, final Class186 class187) {
        class186.checkPoints();
        class187.checkPoints();
        for (int i = 0; i < class186.points.length; ++i) {
            if (class186.points[i] != class187.points[i]) {
                return false;
            }
        }
        return true;
    }
    
    public void setMorphTime(final float n) {
        final int n2 = (int)n;
        this.setFrame(this.rational(n2), this.rational(n2 + 1), n - n2);
    }
    
    public void updateMorphTime(final float n) {
        this.offset += n;
        if (this.offset < 0.0f) {
            int index = this.shapes.indexOf(this.current);
            if (index < 0) {
                index = this.shapes.size() - 1;
            }
            this.setFrame(index, this.rational(index + 1), this.offset);
            ++this.offset;
        }
        else if (this.offset > 1.0f) {
            int index2 = this.shapes.indexOf(this.next);
            if (index2 < 1) {
                index2 = 0;
            }
            this.setFrame(index2, this.rational(index2 + 1), this.offset);
            --this.offset;
        }
        else {
            this.pointsDirty = true;
        }
    }
    
    public void setExternalFrame(final Class186 current) {
        this.current = current;
        this.next = this.shapes.get(0);
        this.offset = 0.0f;
    }
    
    private int rational(int i) {
        while (i >= this.shapes.size()) {
            i -= this.shapes.size();
        }
        while (i < 0) {
            i += this.shapes.size();
        }
        return i;
    }
    
    private void setFrame(final int n, final int n2, final float offset) {
        this.current = this.shapes.get(n);
        this.next = this.shapes.get(n2);
        this.offset = offset;
        this.pointsDirty = true;
    }
    
    @Override
    protected void createPoints() {
        if (this.current == this.next) {
            System.arraycopy(this.current.points, 0, this.points, 0, this.points.length);
            return;
        }
        final float[] points = this.current.points;
        final float[] points2 = this.next.points;
        for (int i = 0; i < this.points.length; ++i) {
            this.points[i] = points[i] * (1.0f - this.offset);
            final float[] points3 = this.points;
            final int n = i;
            points3[n] += points2[i] * this.offset;
        }
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        this.createPoints();
        return new Class269(this.points);
    }
}
