package HORIZON-6-0-SKIDPROTECTION;

public class RotateTransition implements Transition
{
    private GameState HorizonCode_Horizon_È;
    private float Â;
    private boolean Ý;
    private float Ø­áŒŠá;
    private Color Âµá€;
    
    public RotateTransition() {
        this.Ø­áŒŠá = 1.0f;
    }
    
    public RotateTransition(final Color background) {
        this.Ø­áŒŠá = 1.0f;
        this.Âµá€ = background;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameState firstState, final GameState secondState) {
        this.HorizonCode_Horizon_È = secondState;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        g.Â(container.µà() / 2, (float)(container.£à() / 2));
        g.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Ø­áŒŠá);
        g.HorizonCode_Horizon_È(0.0f, 0.0f, this.Â);
        g.Â(-container.µà() / 2, (float)(-container.£à() / 2));
        if (this.Âµá€ != null) {
            final Color c = g.áˆºÑ¢Õ();
            g.Â(this.Âµá€);
            g.Ø­áŒŠá(0.0f, 0.0f, container.µà(), container.£à());
            g.Â(c);
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(container, game, g);
        g.Â(container.µà() / 2, (float)(container.£à() / 2));
        g.HorizonCode_Horizon_È(0.0f, 0.0f, -this.Â);
        g.HorizonCode_Horizon_È(1.0f / this.Ø­áŒŠá, 1.0f / this.Ø­áŒŠá);
        g.Â(-container.µà() / 2, (float)(-container.£à() / 2));
    }
    
    @Override
    public void Â(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final int delta) throws SlickException {
        this.Â += delta * 0.5f;
        if (this.Â > 500.0f) {
            this.Ý = true;
        }
        this.Ø­áŒŠá -= delta * 0.001f;
        if (this.Ø­áŒŠá < 0.0f) {
            this.Ø­áŒŠá = 0.0f;
        }
    }
}
