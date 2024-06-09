package HORIZON-6-0-SKIDPROTECTION;

public class SimpleDiagramRenderer
{
    protected static SGL HorizonCode_Horizon_È;
    public Diagram Â;
    public int Ý;
    
    static {
        SimpleDiagramRenderer.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public SimpleDiagramRenderer(final Diagram diagram) {
        this.Ý = -1;
        this.Â = diagram;
    }
    
    public void HorizonCode_Horizon_È(final Graphics g) {
        if (this.Ý == -1) {
            this.Ý = SimpleDiagramRenderer.HorizonCode_Horizon_È.Ó(1);
            SimpleDiagramRenderer.HorizonCode_Horizon_È.Âµá€(this.Ý, 4864);
            HorizonCode_Horizon_È(g, this.Â);
            SimpleDiagramRenderer.HorizonCode_Horizon_È.Â();
        }
        SimpleDiagramRenderer.HorizonCode_Horizon_È.Â(this.Ý);
        TextureImpl.£à();
    }
    
    public static void HorizonCode_Horizon_È(final Graphics g, final Diagram diagram) {
        for (int i = 0; i < diagram.Ø­áŒŠá(); ++i) {
            final Figure figure = diagram.HorizonCode_Horizon_È(i);
            if (figure.Ø­áŒŠá().Â()) {
                if (figure.Ø­áŒŠá().HorizonCode_Horizon_È("fill")) {
                    g.Â(figure.Ø­áŒŠá().Ý("fill"));
                    g.Â(diagram.HorizonCode_Horizon_È(i).Ý());
                    g.HorizonCode_Horizon_È(true);
                    g.HorizonCode_Horizon_È(diagram.HorizonCode_Horizon_È(i).Ý());
                    g.HorizonCode_Horizon_È(false);
                }
                final String fill = figure.Ø­áŒŠá().Ø­áŒŠá("fill");
                if (diagram.HorizonCode_Horizon_È(fill) != null) {
                    System.out.println("PATTERN");
                }
                if (diagram.Â(fill) != null) {
                    final Gradient gradient = diagram.Â(fill);
                    final Shape shape = diagram.HorizonCode_Horizon_È(i).Ý();
                    TexCoordGenerator fg = null;
                    if (gradient.HorizonCode_Horizon_È()) {
                        fg = new RadialGradientFill(shape, diagram.HorizonCode_Horizon_È(i).HorizonCode_Horizon_È(), gradient);
                    }
                    else {
                        fg = new LinearGradientFill(shape, diagram.HorizonCode_Horizon_È(i).HorizonCode_Horizon_È(), gradient);
                    }
                    Color.Ý.HorizonCode_Horizon_È();
                    ShapeRenderer.HorizonCode_Horizon_È(shape, gradient.Ø­áŒŠá(), fg);
                }
            }
            if (figure.Ø­áŒŠá().Ý() && figure.Ø­áŒŠá().HorizonCode_Horizon_È("stroke")) {
                g.Â(figure.Ø­áŒŠá().Ý("stroke"));
                g.HorizonCode_Horizon_È(figure.Ø­áŒŠá().Âµá€("stroke-width"));
                g.HorizonCode_Horizon_È(true);
                g.HorizonCode_Horizon_È(diagram.HorizonCode_Horizon_È(i).Ý());
                g.HorizonCode_Horizon_È(false);
                g.£à();
            }
        }
    }
}
