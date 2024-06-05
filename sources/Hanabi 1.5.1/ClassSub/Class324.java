package ClassSub;

public class Class324 extends Class186
{
    protected float width;
    protected float height;
    
    
    public Class324(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxX = x + width;
        this.maxY = y + height;
        this.checkPoints();
    }
    
    @Override
    public boolean contains(final float n, final float n2) {
        return n > this.getX() && n2 > this.getY() && n < this.maxX && n2 < this.maxY;
    }
    
    public void setBounds(final Class324 class324) {
        this.setBounds(class324.getX(), class324.getY(), class324.getWidth(), class324.getHeight());
    }
    
    public void setBounds(final float x, final float y, final float n, final float n2) {
        this.setX(x);
        this.setY(y);
        this.setSize(n, n2);
    }
    
    public void setSize(final float width, final float height) {
        this.setWidth(width);
        this.setHeight(height);
    }
    
    @Override
    public float getWidth() {
        return this.width;
    }
    
    @Override
    public float getHeight() {
        return this.height;
    }
    
    public void grow(final float n, final float n2) {
        this.setX(this.getX() - n);
        this.setY(this.getY() - n2);
        this.setWidth(this.getWidth() + n * 2.0f);
        this.setHeight(this.getHeight() + n2 * 2.0f);
    }
    
    public void scaleGrow(final float n, final float n2) {
        this.grow(this.getWidth() * (n - 1.0f), this.getHeight() * (n2 - 1.0f));
    }
    
    public void setWidth(final float width) {
        if (width != this.width) {
            this.pointsDirty = true;
            this.width = width;
            this.maxX = this.x + width;
        }
    }
    
    public void setHeight(final float height) {
        if (height != this.height) {
            this.pointsDirty = true;
            this.height = height;
            this.maxY = this.y + height;
        }
    }
    
    @Override
    public boolean intersects(final Class186 class186) {
        if (class186 instanceof Class324) {
            final Class324 class187 = (Class324)class186;
            return this.x <= class187.x + class187.width && this.x + this.width >= class187.x && this.y <= class187.y + class187.height && this.y + this.height >= class187.y;
        }
        if (class186 instanceof Class56) {
            return this.intersects((Class56)class186);
        }
        return super.intersects(class186);
    }
    
    @Override
    protected void createPoints() {
        final float width = this.width;
        final float height = this.height;
        (this.points = new float[8])[0] = this.x;
        this.points[1] = this.y;
        this.points[2] = this.x + width;
        this.points[3] = this.y;
        this.points[4] = this.x + width;
        this.points[5] = this.y + height;
        this.points[6] = this.x;
        this.points[7] = this.y + height;
        this.maxX = this.points[2];
        this.maxY = this.points[5];
        this.minX = this.points[0];
        this.minY = this.points[1];
        this.findCenter();
        this.calculateRadius();
    }
    
    private boolean intersects(final Class56 class56) {
        return class56.intersects((Class186)this);
    }
    
    @Override
    public String toString() {
        return "[Rectangle " + this.width + "x" + this.height + "]";
    }
    
    public static boolean contains(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return n >= n3 && n2 >= n4 && n <= n3 + n5 && n2 <= n4 + n6;
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        this.checkPoints();
        final Class269 class142 = new Class269();
        final float[] points = new float[this.points.length];
        class141.transform(this.points, 0, points, 0, this.points.length / 2);
        class142.points = points;
        class142.findCenter();
        class142.checkPoints();
        return class142;
    }
}
