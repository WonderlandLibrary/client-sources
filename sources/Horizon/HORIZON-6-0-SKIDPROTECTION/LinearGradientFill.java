package HORIZON-6-0-SKIDPROTECTION;

public class LinearGradientFill implements TexCoordGenerator
{
    private Vector2f HorizonCode_Horizon_È;
    private Vector2f Â;
    private Gradient Ý;
    private Line Ø­áŒŠá;
    private Shape Âµá€;
    
    public LinearGradientFill(final Shape shape, final Transform trans, final Gradient gradient) {
        this.Ý = gradient;
        final float x = gradient.Ó();
        final float y = gradient.Ø();
        final float mx = gradient.à();
        final float my = gradient.áŒŠÆ();
        final float h = my - y;
        final float w = mx - x;
        final float[] s = { x, y + h / 2.0f };
        gradient.Â().HorizonCode_Horizon_È(s, 0, s, 0, 1);
        trans.HorizonCode_Horizon_È(s, 0, s, 0, 1);
        final float[] e = { x + w, y + h / 2.0f };
        gradient.Â().HorizonCode_Horizon_È(e, 0, e, 0, 1);
        trans.HorizonCode_Horizon_È(e, 0, e, 0, 1);
        this.HorizonCode_Horizon_È = new Vector2f(s[0], s[1]);
        this.Â = new Vector2f(e[0], e[1]);
        this.Ø­áŒŠá = new Line(this.HorizonCode_Horizon_È, this.Â);
    }
    
    @Override
    public Vector2f HorizonCode_Horizon_È(final float x, final float y) {
        final Vector2f result = new Vector2f();
        this.Ø­áŒŠá.Â(new Vector2f(x, y), result);
        float u = result.Âµá€(this.HorizonCode_Horizon_È);
        u /= this.Ø­áŒŠá.áˆºÑ¢Õ();
        return new Vector2f(u, 0.0f);
    }
}
