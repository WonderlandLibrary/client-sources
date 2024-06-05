package ClassSub;

public strictfp class Class56 extends Class103
{
    public float radius;
    
    
    public Class56(final float n, final float n2, final float n3) {
        this(n, n2, n3, 50);
    }
    
    public Class56(final float n, final float n2, final float n3, final int n4) {
        super(n, n2, n3, n3, n4);
        this.x = n - n3;
        this.y = n2 - n3;
        this.radius = n3;
        this.boundingCircleRadius = n3;
    }
    
    @Override
    public strictfp float getCenterX() {
        return this.getX() + this.radius;
    }
    
    @Override
    public strictfp float getCenterY() {
        return this.getY() + this.radius;
    }
    
    public strictfp void setRadius(final float radius) {
        if (radius != this.radius) {
            this.pointsDirty = true;
            this.setRadii(this.radius = radius, radius);
        }
    }
    
    public strictfp float getRadius() {
        return this.radius;
    }
    
    @Override
    public strictfp boolean intersects(final Class186 class186) {
        if (class186 instanceof Class56) {
            final Class56 class187 = (Class56)class186;
            final float n = this.getRadius() + class187.getRadius();
            if (Math.abs(class187.getCenterX() - this.getCenterX()) > n) {
                return false;
            }
            if (Math.abs(class187.getCenterY() - this.getCenterY()) > n) {
                return false;
            }
            final float n2 = n * n;
            final float abs = Math.abs(class187.getCenterX() - this.getCenterX());
            final float abs2 = Math.abs(class187.getCenterY() - this.getCenterY());
            return n2 >= abs * abs + abs2 * abs2;
        }
        else {
            if (class186 instanceof Class324) {
                return this.intersects((Class324)class186);
            }
            return super.intersects(class186);
        }
    }
    
    @Override
    public strictfp boolean contains(final float n, final float n2) {
        return (n - this.getX()) * (n - this.getX()) + (n2 - this.getY()) * (n2 - this.getY()) < this.getRadius() * this.getRadius();
    }
    
    private strictfp boolean contains(final Class303 class303) {
        return this.contains(class303.getX1(), class303.getY1()) && this.contains(class303.getX2(), class303.getY2());
    }
    
    @Override
    protected strictfp void findCenter() {
        (this.center = new float[2])[0] = this.x + this.radius;
        this.center[1] = this.y + this.radius;
    }
    
    @Override
    protected strictfp void calculateRadius() {
        this.boundingCircleRadius = this.radius;
    }
    
    private strictfp boolean intersects(final Class324 class324) {
        if (class324.contains(this.x + this.radius, this.y + this.radius)) {
            return true;
        }
        final float x = class324.getX();
        final float y = class324.getY();
        final float n = class324.getX() + class324.getWidth();
        final float n2 = class324.getY() + class324.getHeight();
        final Class303[] array = { new Class303(x, y, n, y), new Class303(n, y, n, n2), new Class303(n, n2, x, n2), new Class303(x, n2, x, y) };
        final float n3 = this.getRadius() * this.getRadius();
        final Class224 class325 = new Class224(this.getCenterX(), this.getCenterY());
        for (int i = 0; i < 4; ++i) {
            if (array[i].distanceSquared(class325) < n3) {
                return true;
            }
        }
        return false;
    }
    
    private strictfp boolean intersects(final Class303 class303) {
        final Class224 class304 = new Class224(class303.getX1(), class303.getY1());
        final Class224 class305 = new Class224(class303.getX2(), class303.getY2());
        final Class224 class306 = new Class224(this.getCenterX(), this.getCenterY());
        final Class224 sub = class305.copy().sub(class304);
        final Class224 sub2 = class306.copy().sub(class304);
        final float length = sub.length();
        final float n = sub2.dot(sub) / length;
        Class224 add;
        if (n < 0.0f) {
            add = class304;
        }
        else if (n > length) {
            add = class305;
        }
        else {
            add = class304.copy().add(sub.copy().scale(n / length));
        }
        return class306.copy().sub(add).lengthSquared() <= this.getRadius() * this.getRadius();
    }
}
