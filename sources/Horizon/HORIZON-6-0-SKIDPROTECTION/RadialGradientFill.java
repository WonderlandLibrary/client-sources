package HORIZON-6-0-SKIDPROTECTION;

public class RadialGradientFill implements TexCoordGenerator
{
    private Vector2f HorizonCode_Horizon_È;
    private float Â;
    private Gradient Ý;
    private Shape Ø­áŒŠá;
    
    public RadialGradientFill(final Shape shape, final Transform trans, final Gradient gradient) {
        this.Ý = gradient;
        this.Â = gradient.Âµá€();
        final float x = gradient.Ó();
        final float y = gradient.Ø();
        final float[] c = { x, y };
        gradient.Â().HorizonCode_Horizon_È(c, 0, c, 0, 1);
        trans.HorizonCode_Horizon_È(c, 0, c, 0, 1);
        final float[] rt = { x, y - this.Â };
        gradient.Â().HorizonCode_Horizon_È(rt, 0, rt, 0, 1);
        trans.HorizonCode_Horizon_È(rt, 0, rt, 0, 1);
        this.HorizonCode_Horizon_È = new Vector2f(c[0], c[1]);
        final Vector2f dis = new Vector2f(rt[0], rt[1]);
        this.Â = dis.Âµá€(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public Vector2f HorizonCode_Horizon_È(final float x, final float y) {
        float u = this.HorizonCode_Horizon_È.Âµá€(new Vector2f(x, y));
        u /= this.Â;
        if (u > 0.99f) {
            u = 0.99f;
        }
        return new Vector2f(u, 0.0f);
    }
}
