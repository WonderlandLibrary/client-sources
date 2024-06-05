package ClassSub;

import java.io.*;

public abstract class Class186 implements Serializable
{
    protected float[] points;
    protected float[] center;
    protected float x;
    protected float y;
    protected float maxX;
    protected float maxY;
    protected float minX;
    protected float minY;
    protected float boundingCircleRadius;
    protected boolean pointsDirty;
    protected transient Class92 tris;
    protected boolean trianglesDirty;
    
    
    public Class186() {
        this.pointsDirty = true;
    }
    
    public void setLocation(final float x, final float y) {
        this.setX(x);
        this.setY(y);
    }
    
    public abstract Class186 transform(final Class141 p0);
    
    protected abstract void createPoints();
    
    public float getX() {
        return this.x;
    }
    
    public void setX(float x) {
        if (x != this.x) {
            final float n = x - this.x;
            this.x = x;
            if (this.points == null || this.center == null) {
                this.checkPoints();
            }
            for (int i = 0; i < this.points.length / 2; ++i) {
                final float[] points = this.points;
                final int n2 = i * 2;
                points[n2] += n;
            }
            final float[] center = this.center;
            final int n3 = 0;
            center[n3] += n;
            x += n;
            this.maxX += n;
            this.minX += n;
            this.trianglesDirty = true;
        }
    }
    
    public void setY(float y) {
        if (y != this.y) {
            final float n = y - this.y;
            this.y = y;
            if (this.points == null || this.center == null) {
                this.checkPoints();
            }
            for (int i = 0; i < this.points.length / 2; ++i) {
                final float[] points = this.points;
                final int n2 = i * 2 + 1;
                points[n2] += n;
            }
            final float[] center = this.center;
            final int n3 = 1;
            center[n3] += n;
            y += n;
            this.maxY += n;
            this.minY += n;
            this.trianglesDirty = true;
        }
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setLocation(final Class224 class224) {
        this.setX(class224.x);
        this.setY(class224.y);
    }
    
    public float getCenterX() {
        this.checkPoints();
        return this.center[0];
    }
    
    public void setCenterX(final float n) {
        if (this.points == null || this.center == null) {
            this.checkPoints();
        }
        this.setX(this.x + (n - this.getCenterX()));
    }
    
    public float getCenterY() {
        this.checkPoints();
        return this.center[1];
    }
    
    public void setCenterY(final float n) {
        if (this.points == null || this.center == null) {
            this.checkPoints();
        }
        this.setY(this.y + (n - this.getCenterY()));
    }
    
    public float getMaxX() {
        this.checkPoints();
        return this.maxX;
    }
    
    public float getMaxY() {
        this.checkPoints();
        return this.maxY;
    }
    
    public float getMinX() {
        this.checkPoints();
        return this.minX;
    }
    
    public float getMinY() {
        this.checkPoints();
        return this.minY;
    }
    
    public float getBoundingCircleRadius() {
        this.checkPoints();
        return this.boundingCircleRadius;
    }
    
    public float[] getCenter() {
        this.checkPoints();
        return this.center;
    }
    
    public float[] getPoints() {
        this.checkPoints();
        return this.points;
    }
    
    public int getPointCount() {
        this.checkPoints();
        return this.points.length / 2;
    }
    
    public float[] getPoint(final int n) {
        this.checkPoints();
        return new float[] { this.points[n * 2], this.points[n * 2 + 1] };
    }
    
    public float[] getNormal(final int n) {
        final float[] array = this.getPoint(n);
        final float[] array2 = this.getPoint((n - 1 < 0) ? (this.getPointCount() - 1) : (n - 1));
        final float[] array3 = this.getPoint((n + 1 >= this.getPointCount()) ? 0 : (n + 1));
        final float[] array4 = this.getNormal(array2, array);
        final float[] array5 = this.getNormal(array, array3);
        if (n == 0 && !this.closed()) {
            return array5;
        }
        if (n == this.getPointCount() - 1 && !this.closed()) {
            return array4;
        }
        final float n2 = (array4[0] + array5[0]) / 2.0f;
        final float n3 = (array4[1] + array5[1]) / 2.0f;
        final float n4 = (float)Math.sqrt(n2 * n2 + n3 * n3);
        return new float[] { n2 / n4, n3 / n4 };
    }
    
    public boolean contains(final Class186 class186) {
        if (class186.intersects(this)) {
            return false;
        }
        for (int i = 0; i < class186.getPointCount(); ++i) {
            final float[] array = class186.getPoint(i);
            if (!this.contains(array[0], array[1])) {
                return false;
            }
        }
        return true;
    }
    
    private float[] getNormal(final float[] array, final float[] array2) {
        final float n = array[0] - array2[0];
        final float n2 = array[1] - array2[1];
        final float n3 = (float)Math.sqrt(n * n + n2 * n2);
        return new float[] { -(n2 / n3), n / n3 };
    }
    
    public boolean includes(final float n, final float n2) {
        if (this.points.length == 0) {
            return false;
        }
        this.checkPoints();
        final Class303 class303 = new Class303(0.0f, 0.0f, 0.0f, 0.0f);
        final Class224 class304 = new Class224(n, n2);
        for (int i = 0; i < this.points.length; i += 2) {
            int n3 = i + 2;
            if (n3 >= this.points.length) {
                n3 = 0;
            }
            class303.set(this.points[i], this.points[i + 1], this.points[n3], this.points[n3 + 1]);
            if (class303.on(class304)) {
                return true;
            }
        }
        return false;
    }
    
    public int indexOf(final float n, final float n2) {
        for (int i = 0; i < this.points.length; i += 2) {
            if (this.points[i] == n && this.points[i + 1] == n2) {
                return i / 2;
            }
        }
        return -1;
    }
    
    public boolean contains(final float n, final float n2) {
        this.checkPoints();
        if (this.points.length == 0) {
            return false;
        }
        boolean b = false;
        final int length = this.points.length;
        float n3 = this.points[length - 2];
        float n4 = this.points[length - 1];
        for (int i = 0; i < length; i += 2) {
            final float n5 = this.points[i];
            final float n6 = this.points[i + 1];
            float n7;
            float n8;
            float n9;
            float n10;
            if (n5 > n3) {
                n7 = n3;
                n8 = n5;
                n9 = n4;
                n10 = n6;
            }
            else {
                n7 = n5;
                n8 = n3;
                n9 = n6;
                n10 = n4;
            }
            if (n5 < n == n <= n3 && (n2 - n9) * (n8 - n7) < (n10 - n9) * (n - n7)) {
                b = !b;
            }
            n3 = n5;
            n4 = n6;
        }
        return b;
    }
    
    public boolean intersects(final Class186 class186) {
        this.checkPoints();
        boolean b = false;
        final float[] array = this.getPoints();
        final float[] array2 = class186.getPoints();
        int length = array.length;
        int length2 = array2.length;
        if (!this.closed()) {
            length -= 2;
        }
        if (!class186.closed()) {
            length2 -= 2;
        }
        for (int i = 0; i < length; i += 2) {
            int n = i + 2;
            if (n >= array.length) {
                n = 0;
            }
            for (int j = 0; j < length2; j += 2) {
                int n2 = j + 2;
                if (n2 >= array2.length) {
                    n2 = 0;
                }
                final double n3 = ((array[n] - array[i]) * (array2[j + 1] - array[i + 1]) - (array[n + 1] - array[i + 1]) * (array2[j] - array[i])) / ((array[n + 1] - array[i + 1]) * (array2[n2] - array2[j]) - (array[n] - array[i]) * (array2[n2 + 1] - array2[j + 1]));
                final double n4 = ((array2[n2] - array2[j]) * (array2[j + 1] - array[i + 1]) - (array2[n2 + 1] - array2[j + 1]) * (array2[j] - array[i])) / ((array[n + 1] - array[i + 1]) * (array2[n2] - array2[j]) - (array[n] - array[i]) * (array2[n2 + 1] - array2[j + 1]));
                if (n3 >= 0.0 && n3 <= 1.0 && n4 >= 0.0 && n4 <= 1.0) {
                    b = true;
                    break;
                }
            }
            if (b) {
                break;
            }
        }
        return b;
    }
    
    public boolean hasVertex(final float n, final float n2) {
        if (this.points.length == 0) {
            return false;
        }
        this.checkPoints();
        for (int i = 0; i < this.points.length; i += 2) {
            if (this.points[i] == n && this.points[i + 1] == n2) {
                return true;
            }
        }
        return false;
    }
    
    protected void findCenter() {
        this.center = new float[] { 0.0f, 0.0f };
        final int length = this.points.length;
        for (int i = 0; i < length; i += 2) {
            final float[] center = this.center;
            final int n = 0;
            center[n] += this.points[i];
            final float[] center2 = this.center;
            final int n2 = 1;
            center2[n2] += this.points[i + 1];
        }
        final float[] center3 = this.center;
        final int n3 = 0;
        center3[n3] /= length / 2;
        final float[] center4 = this.center;
        final int n4 = 1;
        center4[n4] /= length / 2;
    }
    
    protected void calculateRadius() {
        this.boundingCircleRadius = 0.0f;
        for (int i = 0; i < this.points.length; i += 2) {
            final float n = (this.points[i] - this.center[0]) * (this.points[i] - this.center[0]) + (this.points[i + 1] - this.center[1]) * (this.points[i + 1] - this.center[1]);
            this.boundingCircleRadius = ((this.boundingCircleRadius > n) ? this.boundingCircleRadius : n);
        }
        this.boundingCircleRadius = (float)Math.sqrt(this.boundingCircleRadius);
    }
    
    protected void calculateTriangles() {
        if (!this.trianglesDirty && this.tris != null) {
            return;
        }
        if (this.points.length >= 6) {
            float n = 0.0f;
            for (int i = 0; i < this.points.length / 2 - 1; ++i) {
                n += this.points[i * 2] * this.points[i * 2 + 3] - this.points[i * 2 + 1] * this.points[i * 2 + 2];
            }
            final boolean b = n / 2.0f > 0.0f;
            this.tris = new Class248();
            for (int j = 0; j < this.points.length; j += 2) {
                this.tris.addPolyPoint(this.points[j], this.points[j + 1]);
            }
            this.tris.triangulate();
        }
        this.trianglesDirty = false;
    }
    
    public void increaseTriangulation() {
        this.checkPoints();
        this.calculateTriangles();
        this.tris = new Class234(this.tris);
    }
    
    public Class92 getTriangles() {
        this.checkPoints();
        this.calculateTriangles();
        return this.tris;
    }
    
    protected final void checkPoints() {
        if (this.pointsDirty) {
            this.createPoints();
            this.findCenter();
            this.calculateRadius();
            if (this.points.length > 0) {
                this.maxX = this.points[0];
                this.maxY = this.points[1];
                this.minX = this.points[0];
                this.minY = this.points[1];
                for (int i = 0; i < this.points.length / 2; ++i) {
                    this.maxX = Math.max(this.points[i * 2], this.maxX);
                    this.maxY = Math.max(this.points[i * 2 + 1], this.maxY);
                    this.minX = Math.min(this.points[i * 2], this.minX);
                    this.minY = Math.min(this.points[i * 2 + 1], this.minY);
                }
            }
            this.pointsDirty = false;
            this.trianglesDirty = true;
        }
    }
    
    public void preCache() {
        this.checkPoints();
        this.getTriangles();
    }
    
    public boolean closed() {
        return true;
    }
    
    public Class186 prune() {
        final Class269 class269 = new Class269();
        for (int i = 0; i < this.getPointCount(); ++i) {
            final int n = (i + 1 >= this.getPointCount()) ? 0 : (i + 1);
            final int n2 = (i - 1 < 0) ? (this.getPointCount() - 1) : (i - 1);
            final float n3 = this.getPoint(i)[0] - this.getPoint(n2)[0];
            final float n4 = this.getPoint(i)[1] - this.getPoint(n2)[1];
            final float n5 = this.getPoint(n)[0] - this.getPoint(i)[0];
            final float n6 = this.getPoint(n)[1] - this.getPoint(i)[1];
            final float n7 = (float)Math.sqrt(n3 * n3 + n4 * n4);
            final float n8 = (float)Math.sqrt(n5 * n5 + n6 * n6);
            final float n9 = n3 / n7;
            final float n10 = n4 / n7;
            final float n11 = n5 / n8;
            final float n12 = n6 / n8;
            if (n9 != n11 || n10 != n12) {
                class269.addPoint(this.getPoint(i)[0], this.getPoint(i)[1]);
            }
        }
        return class269;
    }
    
    public Class186[] subtract(final Class186 class186) {
        return new Class146().subtract(this, class186);
    }
    
    public Class186[] union(final Class186 class186) {
        return new Class146().union(this, class186);
    }
    
    public float getWidth() {
        return this.maxX - this.minX;
    }
    
    public float getHeight() {
        return this.maxY - this.minY;
    }
}
