package HORIZON-6-0-SKIDPROTECTION;

public class Line extends Shape
{
    private Vector2f HorizonCode_Horizon_È;
    private Vector2f Â;
    private Vector2f Ý;
    private float Ø­áŒŠá;
    private Vector2f Âµá€;
    private Vector2f Ó;
    private Vector2f à;
    private Vector2f Ø­à;
    private Vector2f µÕ;
    private Vector2f Æ;
    private boolean Šáƒ;
    private boolean Ï­Ðƒà;
    
    public Line(final float x, final float y, final boolean inner, final boolean outer) {
        this(0.0f, 0.0f, x, y);
    }
    
    public Line(final float x, final float y) {
        this(x, y, true, true);
    }
    
    public Line(final float x1, final float y1, final float x2, final float y2) {
        this(new Vector2f(x1, y1), new Vector2f(x2, y2));
    }
    
    public Line(final float x1, final float y1, final float dx, final float dy, final boolean dummy) {
        this(new Vector2f(x1, y1), new Vector2f(x1 + dx, y1 + dy));
    }
    
    public Line(final float[] start, final float[] end) {
        this.Âµá€ = new Vector2f(0.0f, 0.0f);
        this.Ó = new Vector2f(0.0f, 0.0f);
        this.à = new Vector2f(0.0f, 0.0f);
        this.Ø­à = new Vector2f(0.0f, 0.0f);
        this.µÕ = new Vector2f(0.0f, 0.0f);
        this.Æ = new Vector2f(0.0f, 0.0f);
        this.Šáƒ = true;
        this.Ï­Ðƒà = true;
        this.HorizonCode_Horizon_È(start, end);
    }
    
    public Line(final Vector2f start, final Vector2f end) {
        this.Âµá€ = new Vector2f(0.0f, 0.0f);
        this.Ó = new Vector2f(0.0f, 0.0f);
        this.à = new Vector2f(0.0f, 0.0f);
        this.Ø­à = new Vector2f(0.0f, 0.0f);
        this.µÕ = new Vector2f(0.0f, 0.0f);
        this.Æ = new Vector2f(0.0f, 0.0f);
        this.Šáƒ = true;
        this.Ï­Ðƒà = true;
        this.HorizonCode_Horizon_È(start, end);
    }
    
    public void HorizonCode_Horizon_È(final float[] start, final float[] end) {
        this.HorizonCode_Horizon_È(start[0], start[1], end[0], end[1]);
    }
    
    public Vector2f Ø() {
        return this.HorizonCode_Horizon_È;
    }
    
    public Vector2f áŒŠÆ() {
        return this.Â;
    }
    
    public float áˆºÑ¢Õ() {
        return this.Ý.áˆºÑ¢Õ();
    }
    
    public float ÂµÈ() {
        return this.Ý.áŒŠÆ();
    }
    
    public void HorizonCode_Horizon_È(final Vector2f start, final Vector2f end) {
        super.µà = true;
        if (this.HorizonCode_Horizon_È == null) {
            this.HorizonCode_Horizon_È = new Vector2f();
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(start);
        if (this.Â == null) {
            this.Â = new Vector2f();
        }
        this.Â.HorizonCode_Horizon_È(end);
        (this.Ý = new Vector2f(end)).Ø­áŒŠá(start);
        this.Ø­áŒŠá = this.Ý.áŒŠÆ();
    }
    
    public void HorizonCode_Horizon_È(final float sx, final float sy, final float ex, final float ey) {
        super.µà = true;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(sx, sy);
        this.Â.HorizonCode_Horizon_È(ex, ey);
        final float dx = ex - sx;
        final float dy = ey - sy;
        this.Ý.HorizonCode_Horizon_È(dx, dy);
        this.Ø­áŒŠá = dx * dx + dy * dy;
    }
    
    public float á() {
        return this.Â.Â() - this.HorizonCode_Horizon_È.Â();
    }
    
    public float ˆÏ­() {
        return this.Â.Ý() - this.HorizonCode_Horizon_È.Ý();
    }
    
    @Override
    public float £á() {
        return this.£à();
    }
    
    @Override
    public float Å() {
        return this.µà();
    }
    
    public float £à() {
        return this.HorizonCode_Horizon_È.Â();
    }
    
    public float µà() {
        return this.HorizonCode_Horizon_È.Ý();
    }
    
    public float ˆà() {
        return this.Â.Â();
    }
    
    public float ¥Æ() {
        return this.Â.Ý();
    }
    
    public float HorizonCode_Horizon_È(final Vector2f point) {
        return (float)Math.sqrt(this.Ý(point));
    }
    
    public boolean Â(final Vector2f point) {
        this.Â(point, this.µÕ);
        return point.equals(this.µÕ);
    }
    
    public float Ý(final Vector2f point) {
        this.Â(point, this.µÕ);
        this.µÕ.Ø­áŒŠá(point);
        final float result = this.µÕ.áŒŠÆ();
        return result;
    }
    
    public void Â(final Vector2f point, final Vector2f result) {
        this.Âµá€.HorizonCode_Horizon_È(point);
        this.Âµá€.Ø­áŒŠá(this.HorizonCode_Horizon_È);
        float projDistance = this.Ý.Â(this.Âµá€);
        projDistance /= this.Ý.áŒŠÆ();
        if (projDistance < 0.0f) {
            result.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            return;
        }
        if (projDistance > 1.0f) {
            result.HorizonCode_Horizon_È(this.Â);
            return;
        }
        result.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È.Â() + projDistance * this.Ý.Â();
        result.Â = this.HorizonCode_Horizon_È.Ý() + projDistance * this.Ý.Ý();
    }
    
    @Override
    public String toString() {
        return "[Line " + this.HorizonCode_Horizon_È + "," + this.Â + "]";
    }
    
    public Vector2f HorizonCode_Horizon_È(final Line other) {
        return this.HorizonCode_Horizon_È(other, false);
    }
    
    public Vector2f HorizonCode_Horizon_È(final Line other, final boolean limit) {
        final Vector2f temp = new Vector2f();
        if (!this.HorizonCode_Horizon_È(other, limit, temp)) {
            return null;
        }
        return temp;
    }
    
    public boolean HorizonCode_Horizon_È(final Line other, final boolean limit, final Vector2f result) {
        final float dx1 = this.Â.Â() - this.HorizonCode_Horizon_È.Â();
        final float dx2 = other.Â.Â() - other.HorizonCode_Horizon_È.Â();
        final float dy1 = this.Â.Ý() - this.HorizonCode_Horizon_È.Ý();
        final float dy2 = other.Â.Ý() - other.HorizonCode_Horizon_È.Ý();
        final float denom = dy2 * dx1 - dx2 * dy1;
        if (denom == 0.0f) {
            return false;
        }
        float ua = dx2 * (this.HorizonCode_Horizon_È.Ý() - other.HorizonCode_Horizon_È.Ý()) - dy2 * (this.HorizonCode_Horizon_È.Â() - other.HorizonCode_Horizon_È.Â());
        ua /= denom;
        float ub = dx1 * (this.HorizonCode_Horizon_È.Ý() - other.HorizonCode_Horizon_È.Ý()) - dy1 * (this.HorizonCode_Horizon_È.Â() - other.HorizonCode_Horizon_È.Â());
        ub /= denom;
        if (limit && (ua < 0.0f || ua > 1.0f || ub < 0.0f || ub > 1.0f)) {
            return false;
        }
        final float u = ua;
        final float ix = this.HorizonCode_Horizon_È.Â() + u * (this.Â.Â() - this.HorizonCode_Horizon_È.Â());
        final float iy = this.HorizonCode_Horizon_È.Ý() + u * (this.Â.Ý() - this.HorizonCode_Horizon_È.Ý());
        result.HorizonCode_Horizon_È(ix, iy);
        return true;
    }
    
    @Override
    protected void Ø­áŒŠá() {
        (this.Ø = new float[4])[0] = this.£à();
        this.Ø[1] = this.µà();
        this.Ø[2] = this.ˆà();
        this.Ø[3] = this.¥Æ();
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        final float[] temp = new float[4];
        this.Ø­áŒŠá();
        transform.HorizonCode_Horizon_È(this.Ø, 0, temp, 0, 2);
        return new Line(temp[0], temp[1], temp[2], temp[3]);
    }
    
    @Override
    public boolean à() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Shape shape) {
        if (shape instanceof Circle) {
            return shape.HorizonCode_Horizon_È(this);
        }
        return super.HorizonCode_Horizon_È(shape);
    }
}
