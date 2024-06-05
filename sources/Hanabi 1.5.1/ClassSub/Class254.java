package ClassSub;

public class Class254 extends Class186
{
    private Class224 p1;
    private Class224 c1;
    private Class224 c2;
    private Class224 p2;
    private int segments;
    
    
    public Class254(final Class224 class224, final Class224 class225, final Class224 class226, final Class224 class227) {
        this(class224, class225, class226, class227, 20);
    }
    
    public Class254(final Class224 class224, final Class224 class225, final Class224 class226, final Class224 class227, final int segments) {
        this.p1 = new Class224(class224);
        this.c1 = new Class224(class225);
        this.c2 = new Class224(class226);
        this.p2 = new Class224(class227);
        this.segments = segments;
        this.pointsDirty = true;
    }
    
    public Class224 pointAt(final float n) {
        final float n2 = 1.0f - n;
        final float n3 = n2 * n2 * n2;
        final float n4 = 3.0f * n2 * n2 * n;
        final float n5 = 3.0f * n2 * n * n;
        final float n6 = n * n * n;
        return new Class224(this.p1.x * n3 + this.c1.x * n4 + this.c2.x * n5 + this.p2.x * n6, this.p1.y * n3 + this.c1.y * n4 + this.c2.y * n5 + this.p2.y * n6);
    }
    
    @Override
    protected void createPoints() {
        final float n = 1.0f / this.segments;
        this.points = new float[(this.segments + 1) * 2];
        for (int i = 0; i < this.segments + 1; ++i) {
            final Class224 point = this.pointAt(i * n);
            this.points[i * 2] = point.x;
            this.points[i * 2 + 1] = point.y;
        }
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        final float[] array = new float[8];
        final float[] array2 = new float[8];
        array[0] = this.p1.x;
        array[1] = this.p1.y;
        array[2] = this.c1.x;
        array[3] = this.c1.y;
        array[4] = this.c2.x;
        array[5] = this.c2.y;
        array[6] = this.p2.x;
        array[7] = this.p2.y;
        class141.transform(array, 0, array2, 0, 4);
        return new Class254(new Class224(array2[0], array2[1]), new Class224(array2[2], array2[3]), new Class224(array2[4], array2[5]), new Class224(array2[6], array2[7]));
    }
    
    @Override
    public boolean closed() {
        return false;
    }
}
