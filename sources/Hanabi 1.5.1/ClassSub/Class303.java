package ClassSub;

public class Class303 extends Class186
{
    private Class224 start;
    private Class224 end;
    private Class224 vec;
    private float lenSquared;
    private Class224 loc;
    private Class224 v;
    private Class224 v2;
    private Class224 proj;
    private Class224 closest;
    private Class224 other;
    private boolean outerEdge;
    private boolean innerEdge;
    
    
    public Class303(final float n, final float n2, final boolean b, final boolean b2) {
        this(0.0f, 0.0f, n, n2);
    }
    
    public Class303(final float n, final float n2) {
        this(n, n2, true, true);
    }
    
    public Class303(final float n, final float n2, final float n3, final float n4) {
        this(new Class224(n, n2), new Class224(n3, n4));
    }
    
    public Class303(final float n, final float n2, final float n3, final float n4, final boolean b) {
        this(new Class224(n, n2), new Class224(n + n3, n2 + n4));
    }
    
    public Class303(final float[] array, final float[] array2) {
        this.loc = new Class224(0.0f, 0.0f);
        this.v = new Class224(0.0f, 0.0f);
        this.v2 = new Class224(0.0f, 0.0f);
        this.proj = new Class224(0.0f, 0.0f);
        this.closest = new Class224(0.0f, 0.0f);
        this.other = new Class224(0.0f, 0.0f);
        this.outerEdge = true;
        this.innerEdge = true;
        this.set(array, array2);
    }
    
    public Class303(final Class224 class224, final Class224 class225) {
        this.loc = new Class224(0.0f, 0.0f);
        this.v = new Class224(0.0f, 0.0f);
        this.v2 = new Class224(0.0f, 0.0f);
        this.proj = new Class224(0.0f, 0.0f);
        this.closest = new Class224(0.0f, 0.0f);
        this.other = new Class224(0.0f, 0.0f);
        this.outerEdge = true;
        this.innerEdge = true;
        this.set(class224, class225);
    }
    
    public void set(final float[] array, final float[] array2) {
        this.set(array[0], array[1], array2[0], array2[1]);
    }
    
    public Class224 getStart() {
        return this.start;
    }
    
    public Class224 getEnd() {
        return this.end;
    }
    
    public float length() {
        return this.vec.length();
    }
    
    public float lengthSquared() {
        return this.vec.lengthSquared();
    }
    
    public void set(final Class224 class224, final Class224 class225) {
        super.pointsDirty = true;
        if (this.start == null) {
            this.start = new Class224();
        }
        this.start.set(class224);
        if (this.end == null) {
            this.end = new Class224();
        }
        this.end.set(class225);
        (this.vec = new Class224(class225)).sub(class224);
        this.lenSquared = this.vec.lengthSquared();
    }
    
    public void set(final float n, final float n2, final float n3, final float n4) {
        super.pointsDirty = true;
        this.start.set(n, n2);
        this.end.set(n3, n4);
        final float n5 = n3 - n;
        final float n6 = n4 - n2;
        this.vec.set(n5, n6);
        this.lenSquared = n5 * n5 + n6 * n6;
    }
    
    public float getDX() {
        return this.end.getX() - this.start.getX();
    }
    
    public float getDY() {
        return this.end.getY() - this.start.getY();
    }
    
    @Override
    public float getX() {
        return this.getX1();
    }
    
    @Override
    public float getY() {
        return this.getY1();
    }
    
    public float getX1() {
        return this.start.getX();
    }
    
    public float getY1() {
        return this.start.getY();
    }
    
    public float getX2() {
        return this.end.getX();
    }
    
    public float getY2() {
        return this.end.getY();
    }
    
    public float distance(final Class224 class224) {
        return (float)Math.sqrt(this.distanceSquared(class224));
    }
    
    public boolean on(final Class224 class224) {
        this.getClosestPoint(class224, this.closest);
        return class224.equals(this.closest);
    }
    
    public float distanceSquared(final Class224 class224) {
        this.getClosestPoint(class224, this.closest);
        this.closest.sub(class224);
        return this.closest.lengthSquared();
    }
    
    public void getClosestPoint(final Class224 class224, final Class224 class225) {
        this.loc.set(class224);
        this.loc.sub(this.start);
        final float n = this.vec.dot(this.loc) / this.vec.lengthSquared();
        if (n < 0.0f) {
            class225.set(this.start);
            return;
        }
        if (n > 1.0f) {
            class225.set(this.end);
            return;
        }
        class225.x = this.start.getX() + n * this.vec.getX();
        class225.y = this.start.getY() + n * this.vec.getY();
    }
    
    @Override
    public String toString() {
        return "[Line " + this.start + "," + this.end + "]";
    }
    
    public Class224 intersect(final Class303 class303) {
        return this.intersect(class303, false);
    }
    
    public Class224 intersect(final Class303 class303, final boolean b) {
        final Class224 class304 = new Class224();
        if (!this.intersect(class303, b, class304)) {
            return null;
        }
        return class304;
    }
    
    public boolean intersect(final Class303 class303, final boolean b, final Class224 class304) {
        final float n = this.end.getX() - this.start.getX();
        final float n2 = class303.end.getX() - class303.start.getX();
        final float n3 = this.end.getY() - this.start.getY();
        final float n4 = class303.end.getY() - class303.start.getY();
        final float n5 = n4 * n - n2 * n3;
        if (n5 == 0.0f) {
            return false;
        }
        final float n6 = (n2 * (this.start.getY() - class303.start.getY()) - n4 * (this.start.getX() - class303.start.getX())) / n5;
        final float n7 = (n * (this.start.getY() - class303.start.getY()) - n3 * (this.start.getX() - class303.start.getX())) / n5;
        if (b && (n6 < 0.0f || n6 > 1.0f || n7 < 0.0f || n7 > 1.0f)) {
            return false;
        }
        final float n8 = n6;
        class304.set(this.start.getX() + n8 * (this.end.getX() - this.start.getX()), this.start.getY() + n8 * (this.end.getY() - this.start.getY()));
        return true;
    }
    
    @Override
    protected void createPoints() {
        (this.points = new float[4])[0] = this.getX1();
        this.points[1] = this.getY1();
        this.points[2] = this.getX2();
        this.points[3] = this.getY2();
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        final float[] array = new float[4];
        this.createPoints();
        class141.transform(this.points, 0, array, 0, 2);
        return new Class303(array[0], array[1], array[2], array[3]);
    }
    
    @Override
    public boolean closed() {
        return false;
    }
    
    @Override
    public boolean intersects(final Class186 class186) {
        if (class186 instanceof Class56) {
            return class186.intersects(this);
        }
        return super.intersects(class186);
    }
}
