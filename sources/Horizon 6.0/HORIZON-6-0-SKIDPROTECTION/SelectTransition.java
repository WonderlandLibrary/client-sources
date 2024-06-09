package HORIZON-6-0-SKIDPROTECTION;

public class SelectTransition implements Transition
{
    protected static SGL HorizonCode_Horizon_È;
    private GameState Â;
    private boolean Ý;
    private Color Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private float à;
    private float Ø;
    private float áŒŠÆ;
    private float áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private boolean á;
    private int ˆÏ­;
    
    static {
        SelectTransition.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public SelectTransition() {
        this.Âµá€ = 1.0f;
        this.Ó = 0.0f;
        this.à = 0.0f;
        this.Ø = 0.4f;
        this.áŒŠÆ = 0.0f;
        this.áˆºÑ¢Õ = 0.0f;
        this.ÂµÈ = false;
        this.á = false;
        this.ˆÏ­ = 300;
    }
    
    public SelectTransition(final Color background) {
        this.Âµá€ = 1.0f;
        this.Ó = 0.0f;
        this.à = 0.0f;
        this.Ø = 0.4f;
        this.áŒŠÆ = 0.0f;
        this.áˆºÑ¢Õ = 0.0f;
        this.ÂµÈ = false;
        this.á = false;
        this.ˆÏ­ = 300;
        this.Ø­áŒŠá = background;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameState firstState, final GameState secondState) {
        this.Â = secondState;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        g.Ø();
        if (!this.á) {
            g.Â(this.Ó, this.à);
            g.HorizonCode_Horizon_È(this.Âµá€, this.Âµá€);
            g.HorizonCode_Horizon_È((int)this.Ó, (int)this.à, (int)(this.Âµá€ * container.µà()), (int)(this.Âµá€ * container.£à()));
            this.Â.HorizonCode_Horizon_È(container, game, g);
            g.Ø();
            g.ÂµÈ();
        }
    }
    
    @Override
    public void Â(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        if (this.á) {
            g.Â(this.Ó, this.à);
            g.HorizonCode_Horizon_È(this.Âµá€, this.Âµá€);
            g.HorizonCode_Horizon_È((int)this.Ó, (int)this.à, (int)(this.Âµá€ * container.µà()), (int)(this.Âµá€ * container.£à()));
            this.Â.HorizonCode_Horizon_È(container, game, g);
            g.Ø();
            g.ÂµÈ();
        }
        g.Â(this.áŒŠÆ, this.áˆºÑ¢Õ);
        g.HorizonCode_Horizon_È(this.Ø, this.Ø);
        g.HorizonCode_Horizon_È((int)this.áŒŠÆ, (int)this.áˆºÑ¢Õ, (int)(this.Ø * container.µà()), (int)(this.Ø * container.£à()));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final int delta) throws SlickException {
        if (!this.ÂµÈ) {
            this.ÂµÈ = true;
            this.áŒŠÆ = container.µà() / 2 + 50;
            this.áˆºÑ¢Õ = container.£à() / 4;
        }
        if (!this.á) {
            if (this.Âµá€ > 0.4f) {
                this.Âµá€ -= delta * 0.002f;
                if (this.Âµá€ <= 0.4f) {
                    this.Âµá€ = 0.4f;
                }
                this.Ó += delta * 0.3f;
                if (this.Ó > 50.0f) {
                    this.Ó = 50.0f;
                }
                this.à += delta * 0.5f;
                if (this.à > container.£à() / 4) {
                    this.à = container.£à() / 4;
                }
            }
            else {
                this.á = true;
            }
        }
        else {
            this.ˆÏ­ -= delta;
            if (this.ˆÏ­ > 0) {
                return;
            }
            if (this.Ø < 1.0f) {
                this.Ø += delta * 0.002f;
                if (this.Ø >= 1.0f) {
                    this.Ø = 1.0f;
                }
                this.áŒŠÆ -= delta * 1.5f;
                if (this.áŒŠÆ < 0.0f) {
                    this.áŒŠÆ = 0.0f;
                }
                this.áˆºÑ¢Õ -= delta * 0.5f;
                if (this.áˆºÑ¢Õ < 0.0f) {
                    this.áˆºÑ¢Õ = 0.0f;
                }
            }
            else {
                this.Ý = true;
            }
        }
    }
}
