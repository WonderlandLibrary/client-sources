package ClassSub;

public class Class183 extends Class186
{
    
    
    public Class183(final float x, final float y) {
        this.x = x;
        this.y = y;
        this.checkPoints();
    }
    
    @Override
    public Class186 transform(final Class141 class141) {
        class141.transform(this.points, 0, new float[this.points.length], 0, this.points.length / 2);
        return new Class183(this.points[0], this.points[1]);
    }
    
    @Override
    protected void createPoints() {
        (this.points = new float[2])[0] = this.getX();
        this.points[1] = this.getY();
        this.maxX = this.x;
        this.maxY = this.y;
        this.minX = this.x;
        this.minY = this.y;
        this.findCenter();
        this.calculateRadius();
    }
    
    @Override
    protected void findCenter() {
        (this.center = new float[2])[0] = this.points[0];
        this.center[1] = this.points[1];
    }
    
    @Override
    protected void calculateRadius() {
        this.boundingCircleRadius = 0.0f;
    }
}
