package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class BlobbyTransition implements Transition
{
    protected static SGL HorizonCode_Horizon_È;
    private GameState Â;
    private boolean Ý;
    private Color Ø­áŒŠá;
    private ArrayList Âµá€;
    private int Ó;
    private int à;
    
    static {
        BlobbyTransition.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public BlobbyTransition() {
        this.Âµá€ = new ArrayList();
        this.Ó = 1000;
        this.à = 10;
    }
    
    public BlobbyTransition(final Color background) {
        this.Âµá€ = new ArrayList();
        this.Ó = 1000;
        this.à = 10;
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
        MaskUtil.Âµá€();
    }
    
    @Override
    public void Â(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        this.Â.HorizonCode_Horizon_È(container, game, g);
        MaskUtil.HorizonCode_Horizon_È();
        for (int i = 0; i < this.Âµá€.size(); ++i) {
            this.Âµá€.get(i).HorizonCode_Horizon_È(g);
        }
        MaskUtil.Â();
        MaskUtil.Ý();
        if (this.Ø­áŒŠá != null) {
            final Color c = g.áˆºÑ¢Õ();
            g.Â(this.Ø­áŒŠá);
            g.Ø­áŒŠá(0.0f, 0.0f, container.µà(), container.£à());
            g.Â(c);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final int delta) throws SlickException {
        if (this.Âµá€.size() == 0) {
            for (int i = 0; i < this.à; ++i) {
                this.Âµá€.add(new HorizonCode_Horizon_È(container));
            }
        }
        for (int i = 0; i < this.Âµá€.size(); ++i) {
            this.Âµá€.get(i).HorizonCode_Horizon_È(delta);
        }
        this.Ó -= delta;
        if (this.Ó < 0) {
            this.Ý = true;
        }
    }
    
    private class HorizonCode_Horizon_È
    {
        private float Â;
        private float Ý;
        private float Ø­áŒŠá;
        private float Âµá€;
        
        public HorizonCode_Horizon_È(final GameContainer container) {
            this.Â = (float)(Math.random() * container.µà());
            this.Ý = (float)(Math.random() * container.µà());
            this.Ø­áŒŠá = (float)(1.0 + Math.random() * 1.0);
        }
        
        public void HorizonCode_Horizon_È(final int delta) {
            this.Âµá€ += this.Ø­áŒŠá * delta * 0.4f;
        }
        
        public void HorizonCode_Horizon_È(final Graphics g) {
            g.Ó(this.Â - this.Âµá€, this.Ý - this.Âµá€, this.Âµá€ * 2.0f, this.Âµá€ * 2.0f);
        }
    }
}
