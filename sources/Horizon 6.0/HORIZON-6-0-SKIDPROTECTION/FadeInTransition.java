package HORIZON-6-0-SKIDPROTECTION;

public class FadeInTransition implements Transition
{
    private Color HorizonCode_Horizon_È;
    private int Â;
    
    public FadeInTransition() {
        this(Color.Ø, 500);
    }
    
    public FadeInTransition(final Color color) {
        this(color, 500);
    }
    
    public FadeInTransition(final Color color, final int fadeTime) {
        this.Â = 500;
        this.HorizonCode_Horizon_È = new Color(color);
        this.HorizonCode_Horizon_È.¥Æ = 1.0f;
        this.Â = fadeTime;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.¥Æ <= 0.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final Graphics g) {
        final Color old = g.áˆºÑ¢Õ();
        g.Â(this.HorizonCode_Horizon_È);
        g.Ø­áŒŠá(0.0f, 0.0f, container.µà() * 2, container.£à() * 2);
        g.Â(old);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final int delta) {
        final Color horizonCode_Horizon_È = this.HorizonCode_Horizon_È;
        horizonCode_Horizon_È.¥Æ -= delta * (1.0f / this.Â);
        if (this.HorizonCode_Horizon_È.¥Æ < 0.0f) {
            this.HorizonCode_Horizon_È.¥Æ = 0.0f;
        }
    }
    
    @Override
    public void Â(final StateBasedGame game, final GameContainer container, final Graphics g) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameState firstState, final GameState secondState) {
    }
}
