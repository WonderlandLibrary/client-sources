package HORIZON-6-0-SKIDPROTECTION;

public class Rectangle extends Shape
{
    public float HorizonCode_Horizon_È;
    public float Â;
    
    public Rectangle(final float x, final float y, final float width, final float height) {
        this.áˆºÑ¢Õ = x;
        this.ÂµÈ = y;
        this.HorizonCode_Horizon_È = width;
        this.Â = height;
        this.á = x + width;
        this.ˆÏ­ = y + height;
        this.ÇŽÉ();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final float xp, final float yp) {
        return xp > this.£á() && yp > this.Å() && xp < this.á && yp < this.ˆÏ­;
    }
    
    public void HorizonCode_Horizon_È(final Rectangle other) {
        this.HorizonCode_Horizon_È(other.£á(), other.Å(), other.F_(), other.G_());
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y, final float width, final float height) {
        this.c_(x);
        this.d_(y);
        this.Â(width, height);
    }
    
    public void Â(final float width, final float height) {
        this.Ý(width);
        this.Ø­áŒŠá(height);
    }
    
    @Override
    public float F_() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public float G_() {
        return this.Â;
    }
    
    public void Ý(final float h, final float v) {
        this.c_(this.£á() - h);
        this.d_(this.Å() - v);
        this.Ý(this.F_() + h * 2.0f);
        this.Ø­áŒŠá(this.G_() + v * 2.0f);
    }
    
    public void Ø­áŒŠá(final float h, final float v) {
        this.Ý(this.F_() * (h - 1.0f), this.G_() * (v - 1.0f));
    }
    
    public void Ý(final float width) {
        if (width != this.HorizonCode_Horizon_È) {
            this.µà = true;
            this.HorizonCode_Horizon_È = width;
            this.á = this.áˆºÑ¢Õ + width;
        }
    }
    
    public void Ø­áŒŠá(final float height) {
        if (height != this.Â) {
            this.µà = true;
            this.Â = height;
            this.ˆÏ­ = this.ÂµÈ + height;
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Shape shape) {
        if (shape instanceof Rectangle) {
            final Rectangle other = (Rectangle)shape;
            return this.áˆºÑ¢Õ <= other.áˆºÑ¢Õ + other.HorizonCode_Horizon_È && this.áˆºÑ¢Õ + this.HorizonCode_Horizon_È >= other.áˆºÑ¢Õ && this.ÂµÈ <= other.ÂµÈ + other.Â && this.ÂµÈ + this.Â >= other.ÂµÈ;
        }
        if (shape instanceof Circle) {
            return this.HorizonCode_Horizon_È((Circle)shape);
        }
        return super.HorizonCode_Horizon_È(shape);
    }
    
    @Override
    protected void Ø­áŒŠá() {
        final float useWidth = this.HorizonCode_Horizon_È;
        final float useHeight = this.Â;
        (this.Ø = new float[8])[0] = this.áˆºÑ¢Õ;
        this.Ø[1] = this.ÂµÈ;
        this.Ø[2] = this.áˆºÑ¢Õ + useWidth;
        this.Ø[3] = this.ÂµÈ;
        this.Ø[4] = this.áˆºÑ¢Õ + useWidth;
        this.Ø[5] = this.ÂµÈ + useHeight;
        this.Ø[6] = this.áˆºÑ¢Õ;
        this.Ø[7] = this.ÂµÈ + useHeight;
        this.á = this.Ø[2];
        this.ˆÏ­ = this.Ø[5];
        this.£á = this.Ø[0];
        this.Å = this.Ø[1];
        this.Âµá€();
        this.Ó();
    }
    
    private boolean HorizonCode_Horizon_È(final Circle other) {
        return other.HorizonCode_Horizon_È((Shape)this);
    }
    
    @Override
    public String toString() {
        return "[Rectangle " + this.HorizonCode_Horizon_È + "x" + this.Â + "]";
    }
    
    public static boolean HorizonCode_Horizon_È(final float xp, final float yp, final float xr, final float yr, final float widthr, final float heightr) {
        return xp >= xr && yp >= yr && xp <= xr + widthr && yp <= yr + heightr;
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        this.ÇŽÉ();
        final Polygon resultPolygon = new Polygon();
        final float[] result = new float[this.Ø.length];
        transform.HorizonCode_Horizon_È(this.Ø, 0, result, 0, this.Ø.length / 2);
        resultPolygon.Ø = result;
        resultPolygon.Âµá€();
        resultPolygon.ÇŽÉ();
        return resultPolygon;
    }
}
