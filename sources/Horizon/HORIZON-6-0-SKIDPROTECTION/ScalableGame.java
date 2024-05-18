package HORIZON-6-0-SKIDPROTECTION;

public class ScalableGame implements Game
{
    private static SGL HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    private Game Ø­áŒŠá;
    private boolean Âµá€;
    private int Ó;
    private int à;
    private GameContainer Ø;
    
    static {
        ScalableGame.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public ScalableGame(final Game held, final int normalWidth, final int normalHeight) {
        this(held, normalWidth, normalHeight, false);
    }
    
    public ScalableGame(final Game held, final int normalWidth, final int normalHeight, final boolean maintainAspect) {
        this.Ø­áŒŠá = held;
        this.Â = normalWidth;
        this.Ý = normalHeight;
        this.Âµá€ = maintainAspect;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ø = container;
        this.Ý();
        this.Ø­áŒŠá.HorizonCode_Horizon_È(container);
    }
    
    public void Ý() throws SlickException {
        this.Ó = this.Ø.µà();
        this.à = this.Ø.£à();
        if (this.Âµá€) {
            final boolean normalIsWide = this.Â / this.Ý > 1.6;
            final boolean containerIsWide = this.Ó / this.à > 1.6;
            final float wScale = this.Ó / this.Â;
            final float hScale = this.à / this.Ý;
            if (normalIsWide & containerIsWide) {
                final float scale = (wScale < hScale) ? wScale : hScale;
                this.Ó = (int)(this.Â * scale);
                this.à = (int)(this.Ý * scale);
            }
            else if (normalIsWide & !containerIsWide) {
                this.Ó = (int)(this.Â * wScale);
                this.à = (int)(this.Ý * wScale);
            }
            else if (!normalIsWide & containerIsWide) {
                this.Ó = (int)(this.Â * hScale);
                this.à = (int)(this.Ý * hScale);
            }
            else {
                final float scale = (wScale < hScale) ? wScale : hScale;
                this.Ó = (int)(this.Â * scale);
                this.à = (int)(this.Ý * scale);
            }
        }
        if (this.Ø­áŒŠá instanceof InputListener) {
            this.Ø.á€().HorizonCode_Horizon_È((InputListener)this.Ø­áŒŠá);
        }
        this.Ø.á€().HorizonCode_Horizon_È(this.Â / this.Ó, this.Ý / this.à);
        int yoffset = 0;
        int xoffset = 0;
        if (this.à < this.Ø.£à()) {
            yoffset = (this.Ø.£à() - this.à) / 2;
        }
        if (this.Ó < this.Ø.µà()) {
            xoffset = (this.Ø.µà() - this.Ó) / 2;
        }
        this.Ø.á€().Â(-xoffset / (this.Ó / this.Â), -yoffset / (this.à / this.Ý));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        if (this.à != container.£à() || this.Ó != container.µà()) {
            this.Ý();
        }
        this.Ø­áŒŠá.HorizonCode_Horizon_È(container, delta);
    }
    
    @Override
    public final void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        int yoffset = 0;
        int xoffset = 0;
        if (this.à < container.£à()) {
            yoffset = (container.£à() - this.à) / 2;
        }
        if (this.Ó < container.µà()) {
            xoffset = (container.µà() - this.Ó) / 2;
        }
        SlickCallable.HorizonCode_Horizon_È();
        g.HorizonCode_Horizon_È(xoffset, yoffset, this.Ó, this.à);
        ScalableGame.HorizonCode_Horizon_È.Â(xoffset, yoffset, 0.0f);
        g.HorizonCode_Horizon_È(this.Ó / this.Â, this.à / this.Ý);
        ScalableGame.HorizonCode_Horizon_È.Âµá€();
        this.Ø­áŒŠá.HorizonCode_Horizon_È(container, g);
        ScalableGame.HorizonCode_Horizon_È.Ø­áŒŠá();
        g.ÂµÈ();
        SlickCallable.Â();
        this.Â(container, g);
    }
    
    protected void Â(final GameContainer container, final Graphics g) {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá.HorizonCode_Horizon_È();
    }
    
    @Override
    public String Â() {
        return this.Ø­áŒŠá.Â();
    }
}
