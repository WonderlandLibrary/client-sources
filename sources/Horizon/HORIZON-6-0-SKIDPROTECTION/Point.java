package HORIZON-6-0-SKIDPROTECTION;

public class Point extends Shape
{
    public Point(final float x, final float y) {
        this.áˆºÑ¢Õ = x;
        this.ÂµÈ = y;
        this.ÇŽÉ();
    }
    
    @Override
    public Shape HorizonCode_Horizon_È(final Transform transform) {
        final float[] result = new float[this.Ø.length];
        transform.HorizonCode_Horizon_È(this.Ø, 0, result, 0, this.Ø.length / 2);
        return new Point(this.Ø[0], this.Ø[1]);
    }
    
    @Override
    protected void Ø­áŒŠá() {
        (this.Ø = new float[2])[0] = this.£á();
        this.Ø[1] = this.Å();
        this.á = this.áˆºÑ¢Õ;
        this.ˆÏ­ = this.ÂµÈ;
        this.£á = this.áˆºÑ¢Õ;
        this.Å = this.ÂµÈ;
        this.Âµá€();
        this.Ó();
    }
    
    @Override
    protected void Âµá€() {
        (this.áŒŠÆ = new float[2])[0] = this.Ø[0];
        this.áŒŠÆ[1] = this.Ø[1];
    }
    
    @Override
    protected void Ó() {
        this.£à = 0.0f;
    }
}
