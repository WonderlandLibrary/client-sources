package HORIZON-6-0-SKIDPROTECTION;

public class VerticalSplitTransition implements Transition
{
    protected static SGL HorizonCode_Horizon_È;
    private GameState Â;
    private float Ý;
    private boolean Ø­áŒŠá;
    private Color Âµá€;
    
    static {
        VerticalSplitTransition.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public VerticalSplitTransition() {
    }
    
    public VerticalSplitTransition(final Color background) {
        this.Âµá€ = background;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameState firstState, final GameState secondState) {
        this.Â = secondState;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        g.Â(0.0f, -this.Ý);
        g.HorizonCode_Horizon_È(0, (int)(-this.Ý), container.µà(), container.£à() / 2);
        if (this.Âµá€ != null) {
            final Color c = g.áˆºÑ¢Õ();
            g.Â(this.Âµá€);
            g.Ø­áŒŠá(0.0f, 0.0f, container.µà(), container.£à());
            g.Â(c);
        }
        VerticalSplitTransition.HorizonCode_Horizon_È.Âµá€();
        this.Â.HorizonCode_Horizon_È(container, game, g);
        VerticalSplitTransition.HorizonCode_Horizon_È.Ø­áŒŠá();
        g.ÂµÈ();
        g.Ø();
        g.Â(0.0f, this.Ý);
        g.HorizonCode_Horizon_È(0, (int)(container.£à() / 2 + this.Ý), container.µà(), container.£à() / 2);
        if (this.Âµá€ != null) {
            final Color c = g.áˆºÑ¢Õ();
            g.Â(this.Âµá€);
            g.Ø­áŒŠá(0.0f, 0.0f, container.µà(), container.£à());
            g.Â(c);
        }
        VerticalSplitTransition.HorizonCode_Horizon_È.Âµá€();
        this.Â.HorizonCode_Horizon_È(container, game, g);
        VerticalSplitTransition.HorizonCode_Horizon_È.Ø­áŒŠá();
        g.ÂµÈ();
        g.Â(0.0f, -this.Ý);
    }
    
    @Override
    public void Â(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final int delta) throws SlickException {
        this.Ý += delta * 1.0f;
        if (this.Ý > container.£à() / 2) {
            this.Ø­áŒŠá = true;
        }
    }
}
