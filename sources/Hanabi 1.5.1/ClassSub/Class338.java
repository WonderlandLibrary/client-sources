package ClassSub;

private class Class338
{
    private float x;
    private float y;
    private float[] array;
    final Class278 this$0;
    
    
    public Class338(final Class278 this$0, final float x, final float y) {
        this.this$0 = this$0;
        this.x = x;
        this.y = y;
        this.array = new float[] { x, y };
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float[] toArray() {
        return this.array;
    }
    
    @Override
    public int hashCode() {
        return (int)(this.x * this.y * 31.0f);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Class338) {
            final Class338 class338 = (Class338)o;
            return class338.x == this.x && class338.y == this.y;
        }
        return false;
    }
    
    static float access$000(final Class338 class338) {
        return class338.x;
    }
    
    static float access$100(final Class338 class338) {
        return class338.y;
    }
}
