package HORIZON-6-0-SKIDPROTECTION;

public class HorizontalSplitTransition implements Transition
{
    protected static SGL HorizonCode_Horizon_È;
    private GameState Â;
    private float Ý;
    private boolean Ø­áŒŠá;
    private Color Âµá€;
    
    static {
        HorizontalSplitTransition.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public HorizontalSplitTransition() {
    }
    
    public HorizontalSplitTransition(final Color background) {
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
        g.Â(-this.Ý, 0.0f);
        g.HorizonCode_Horizon_È((int)(-this.Ý), 0, container.µà() / 2, container.£à());
        if (this.Âµá€ != null) {
            final Color c = g.áˆºÑ¢Õ();
            g.Â(this.Âµá€);
            g.Ø­áŒŠá(0.0f, 0.0f, container.µà(), container.£à());
            g.Â(c);
        }
        HorizontalSplitTransition.HorizonCode_Horizon_È.Âµá€();
        this.Â.HorizonCode_Horizon_È(container, game, g);
        HorizontalSplitTransition.HorizonCode_Horizon_È.Ø­áŒŠá();
        g.ÂµÈ();
        g.Â(this.Ý * 2.0f, 0.0f);
        g.HorizonCode_Horizon_È((int)(container.µà() / 2 + this.Ý), 0, container.µà() / 2, container.£à());
        if (this.Âµá€ != null) {
            final Color c = g.áˆºÑ¢Õ();
            g.Â(this.Âµá€);
            g.Ø­áŒŠá(0.0f, 0.0f, container.µà(), container.£à());
            g.Â(c);
        }
        HorizontalSplitTransition.HorizonCode_Horizon_È.Âµá€();
        this.Â.HorizonCode_Horizon_È(container, game, g);
        HorizontalSplitTransition.HorizonCode_Horizon_È.Ø­áŒŠá();
        g.ÂµÈ();
        g.Â(-this.Ý, 0.0f);
    }
    
    @Override
    public void Â(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final int delta) throws SlickException {
        this.Ý += delta * 1.0f;
        if (this.Ý > container.µà() / 2) {
            this.Ø­áŒŠá = true;
        }
    }
}
