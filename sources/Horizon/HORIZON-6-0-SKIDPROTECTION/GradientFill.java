package HORIZON-6-0-SKIDPROTECTION;

public class GradientFill implements ShapeFill
{
    private Vector2f HorizonCode_Horizon_È;
    private Vector2f Â;
    private Vector2f Ý;
    private Color Ø­áŒŠá;
    private Color Âµá€;
    private boolean Ó;
    
    public GradientFill(final float sx, final float sy, final Color startCol, final float ex, final float ey, final Color endCol) {
        this(sx, sy, startCol, ex, ey, endCol, false);
    }
    
    public GradientFill(final float sx, final float sy, final Color startCol, final float ex, final float ey, final Color endCol, final boolean local) {
        this(new Vector2f(sx, sy), startCol, new Vector2f(ex, ey), endCol, local);
    }
    
    public GradientFill(final Vector2f start, final Color startCol, final Vector2f end, final Color endCol, final boolean local) {
        this.HorizonCode_Horizon_È = new Vector2f(0.0f, 0.0f);
        this.Ó = false;
        this.Â = new Vector2f(start);
        this.Ý = new Vector2f(end);
        this.Ø­áŒŠá = new Color(startCol);
        this.Âµá€ = new Color(endCol);
        this.Ó = local;
    }
    
    public GradientFill HorizonCode_Horizon_È() {
        return new GradientFill(this.Â, this.Âµá€, this.Ý, this.Ø­áŒŠá, this.Ó);
    }
    
    public void HorizonCode_Horizon_È(final boolean local) {
        this.Ó = local;
    }
    
    public Vector2f Â() {
        return this.Â;
    }
    
    public Vector2f Ý() {
        return this.Ý;
    }
    
    public Color Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public Color Âµá€() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y) {
        this.HorizonCode_Horizon_È(new Vector2f(x, y));
    }
    
    public void HorizonCode_Horizon_È(final Vector2f start) {
        this.Â = new Vector2f(start);
    }
    
    public void Â(final float x, final float y) {
        this.Â(new Vector2f(x, y));
    }
    
    public void Â(final Vector2f end) {
        this.Ý = new Vector2f(end);
    }
    
    public void HorizonCode_Horizon_È(final Color color) {
        this.Ø­áŒŠá = new Color(color);
    }
    
    public void Â(final Color color) {
        this.Âµá€ = new Color(color);
    }
    
    @Override
    public Color HorizonCode_Horizon_È(final Shape shape, final float x, final float y) {
        if (this.Ó) {
            return this.Ý(x - shape.HorizonCode_Horizon_È(), y - shape.Â());
        }
        return this.Ý(x, y);
    }
    
    public Color Ý(final float x, final float y) {
        final float dx1 = this.Ý.Â() - this.Â.Â();
        final float dy1 = this.Ý.Ý() - this.Â.Ý();
        final float dx2 = -dy1;
        final float dy2 = dx1;
        final float denom = dy2 * dx1 - dx2 * dy1;
        if (denom == 0.0f) {
            return Color.Ø;
        }
        float ua = dx2 * (this.Â.Ý() - y) - dy2 * (this.Â.Â() - x);
        ua /= denom;
        float ub = dx1 * (this.Â.Ý() - y) - dy1 * (this.Â.Â() - x);
        ub /= denom;
        float u = ua;
        if (u < 0.0f) {
            u = 0.0f;
        }
        if (u > 1.0f) {
            u = 1.0f;
        }
        final float v = 1.0f - u;
        final Color col = new Color(1, 1, 1, 1);
        col.£à = u * this.Âµá€.£à + v * this.Ø­áŒŠá.£à;
        col.ˆà = u * this.Âµá€.ˆà + v * this.Ø­áŒŠá.ˆà;
        col.µà = u * this.Âµá€.µà + v * this.Ø­áŒŠá.µà;
        col.¥Æ = u * this.Âµá€.¥Æ + v * this.Ø­áŒŠá.¥Æ;
        return col;
    }
    
    @Override
    public Vector2f Â(final Shape shape, final float x, final float y) {
        return this.HorizonCode_Horizon_È;
    }
}
