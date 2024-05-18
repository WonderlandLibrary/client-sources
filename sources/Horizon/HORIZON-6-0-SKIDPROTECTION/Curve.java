package HORIZON-6-0-SKIDPROTECTION;

public class Curve extends Shape
{
    private Vector2f HorizonCode_Horizon_È;
    private Vector2f Â;
    private Vector2f Ý;
    private Vector2f Ø­áŒŠá;
    private int Âµá€;
    
    public Curve(final Vector2f p1, final Vector2f c1, final Vector2f c2, final Vector2f p2) {
        this(p1, c1, c2, p2, 20);
    }
    
    public Curve(final Vector2f p1, final Vector2f c1, final Vector2f c2, final Vector2f p2, final int segments) {
        this.HorizonCode_Horizon_È = new Vector2f(p1);
        this.Â = new Vector2f(c1);
        this.Ý = new Vector2f(c2);
        this.Ø­áŒŠá = new Vector2f(p2);
        this.Âµá€ = segments;
        this.µà = true;
    }
    
    public Vector2f HorizonCode_Horizon_È(final float t) {
        final float a = 1.0f - t;
        final float f1 = a * a * a;
        final float f2 = 3.0f * a * a * t;
        final float f3 = 3.0f * a * t * t;
        final float f4 = t * t * t;
        final float nx = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È * f1 + this.Â.HorizonCode_Horizon_È * f2 + this.Ý.HorizonCode_Horizon_È * f3 + this.Ø­áŒŠá.HorizonCode_Horizon_È * f4;
        final float ny = this.HorizonCode_Horizon_È.Â * f1 + this.Â.Â * f2 + this.Ý.Â * f3 + this.Ø­áŒŠá.Â * f4;
        return new Vector2f(nx, ny);
    }
    
    @Override
    protected void Ø­áŒŠá() {
        final float step = 1.0f / this.Âµá€;
        this.Ø = new float[(this.Âµá€ + 1) * 2];
        for (int i = 0; i < this.Âµá€ + 1; ++i) {
            final float t = i * step;
            final Vector2f p = this.HorizonCode_Horizon_È(t);
            this.Ø[i * 2] = p.HorizonCode_Horizon_È;
            this.Ø[i * 2 + 1] = p.Â;
        }
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        final float[] pts = new float[8];
        final float[] dest = new float[8];
        pts[0] = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        pts[1] = this.HorizonCode_Horizon_È.Â;
        pts[2] = this.Â.HorizonCode_Horizon_È;
        pts[3] = this.Â.Â;
        pts[4] = this.Ý.HorizonCode_Horizon_È;
        pts[5] = this.Ý.Â;
        pts[6] = this.Ø­áŒŠá.HorizonCode_Horizon_È;
        pts[7] = this.Ø­áŒŠá.Â;
        transform.HorizonCode_Horizon_È(pts, 0, dest, 0, 4);
        return new Curve(new Vector2f(dest[0], dest[1]), new Vector2f(dest[2], dest[3]), new Vector2f(dest[4], dest[5]), new Vector2f(dest[6], dest[7]));
    }
    
    @Override
    public boolean à() {
        return false;
    }
}
