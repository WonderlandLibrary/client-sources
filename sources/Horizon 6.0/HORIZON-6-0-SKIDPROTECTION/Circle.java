package HORIZON-6-0-SKIDPROTECTION;

public strictfp class Circle extends Ellipse
{
    public float HorizonCode_Horizon_È;
    
    public Circle(final float centerPointX, final float centerPointY, final float radius) {
        this(centerPointX, centerPointY, radius, 50);
    }
    
    public Circle(final float centerPointX, final float centerPointY, final float radius, final int segmentCount) {
        super(centerPointX, centerPointY, radius, radius, segmentCount);
        this.áˆºÑ¢Õ = centerPointX - radius;
        this.ÂµÈ = centerPointY - radius;
        this.HorizonCode_Horizon_È = radius;
        this.£à = radius;
    }
    
    @Override
    public strictfp float HorizonCode_Horizon_È() {
        return this.£á() + this.HorizonCode_Horizon_È;
    }
    
    @Override
    public strictfp float Â() {
        return this.Å() + this.HorizonCode_Horizon_È;
    }
    
    @Override
    public strictfp float[] Ý() {
        return new float[] { this.HorizonCode_Horizon_È(), this.Â() };
    }
    
    public strictfp void HorizonCode_Horizon_È(final float radius) {
        if (radius != this.HorizonCode_Horizon_È) {
            this.µà = true;
            this.Â(this.HorizonCode_Horizon_È = radius, radius);
        }
    }
    
    public strictfp float D_() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public strictfp boolean HorizonCode_Horizon_È(final Shape shape) {
        if (shape instanceof Circle) {
            final Circle other = (Circle)shape;
            float totalRad2 = this.D_() + other.D_();
            if (Math.abs(other.HorizonCode_Horizon_È() - this.HorizonCode_Horizon_È()) > totalRad2) {
                return false;
            }
            if (Math.abs(other.Â() - this.Â()) > totalRad2) {
                return false;
            }
            totalRad2 *= totalRad2;
            final float dx = Math.abs(other.HorizonCode_Horizon_È() - this.HorizonCode_Horizon_È());
            final float dy = Math.abs(other.Â() - this.Â());
            return totalRad2 >= dx * dx + dy * dy;
        }
        else {
            if (shape instanceof Rectangle) {
                return this.HorizonCode_Horizon_È((Rectangle)shape);
            }
            return super.HorizonCode_Horizon_È(shape);
        }
    }
    
    @Override
    public strictfp boolean HorizonCode_Horizon_È(final float x, final float y) {
        final float xDelta = x - this.HorizonCode_Horizon_È();
        final float yDelta = y - this.Â();
        return xDelta * xDelta + yDelta * yDelta < this.D_() * this.D_();
    }
    
    private strictfp boolean HorizonCode_Horizon_È(final Line line) {
        return this.HorizonCode_Horizon_È(line.£à(), line.µà()) && this.HorizonCode_Horizon_È(line.ˆà(), line.¥Æ());
    }
    
    @Override
    protected strictfp void Âµá€() {
        (this.áŒŠÆ = new float[2])[0] = this.áˆºÑ¢Õ + this.HorizonCode_Horizon_È;
        this.áŒŠÆ[1] = this.ÂµÈ + this.HorizonCode_Horizon_È;
    }
    
    @Override
    protected strictfp void Ó() {
        this.£à = this.HorizonCode_Horizon_È;
    }
    
    private strictfp boolean HorizonCode_Horizon_È(final Rectangle other) {
        if (other.HorizonCode_Horizon_È(this.áˆºÑ¢Õ + this.HorizonCode_Horizon_È, this.ÂµÈ + this.HorizonCode_Horizon_È)) {
            return true;
        }
        final float x1 = other.£á();
        final float y1 = other.Å();
        final float x2 = other.£á() + other.F_();
        final float y2 = other.Å() + other.G_();
        final Line[] lines = { new Line(x1, y1, x2, y1), new Line(x2, y1, x2, y2), new Line(x2, y2, x1, y2), new Line(x1, y2, x1, y1) };
        final float r2 = this.D_() * this.D_();
        final Vector2f pos = new Vector2f(this.HorizonCode_Horizon_È(), this.Â());
        for (int i = 0; i < 4; ++i) {
            final float dis = lines[i].Ý(pos);
            if (dis < r2) {
                return true;
            }
        }
        return false;
    }
    
    private strictfp boolean Â(final Line other) {
        final Vector2f lineSegmentStart = new Vector2f(other.£à(), other.µà());
        final Vector2f lineSegmentEnd = new Vector2f(other.ˆà(), other.¥Æ());
        final Vector2f circleCenter = new Vector2f(this.HorizonCode_Horizon_È(), this.Â());
        final Vector2f segv = lineSegmentEnd.ÂµÈ().Ø­áŒŠá(lineSegmentStart);
        final Vector2f ptv = circleCenter.ÂµÈ().Ø­áŒŠá(lineSegmentStart);
        final float segvLength = segv.áˆºÑ¢Õ();
        final float projvl = ptv.Â(segv) / segvLength;
        Vector2f closest;
        if (projvl < 0.0f) {
            closest = lineSegmentStart;
        }
        else if (projvl > segvLength) {
            closest = lineSegmentEnd;
        }
        else {
            final Vector2f projv = segv.ÂµÈ().HorizonCode_Horizon_È(projvl / segvLength);
            closest = lineSegmentStart.ÂµÈ().Ý(projv);
        }
        final boolean intersects = circleCenter.ÂµÈ().Ø­áŒŠá(closest).áŒŠÆ() <= this.D_() * this.D_();
        return intersects;
    }
}
