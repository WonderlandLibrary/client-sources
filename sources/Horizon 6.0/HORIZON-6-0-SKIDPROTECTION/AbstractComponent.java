package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractComponent extends InputAdapter
{
    private static AbstractComponent Ø­áŒŠá;
    protected GUIContext HorizonCode_Horizon_È;
    protected Set Â;
    private boolean Âµá€;
    protected Input Ý;
    
    static {
        AbstractComponent.Ø­áŒŠá = null;
    }
    
    public AbstractComponent(final GUIContext container) {
        this.Âµá€ = false;
        this.HorizonCode_Horizon_È = container;
        this.Â = new HashSet();
        (this.Ý = container.á€()).Â(this);
        this.Ý(0, 0);
    }
    
    public void HorizonCode_Horizon_È(final ComponentListener listener) {
        this.Â.add(listener);
    }
    
    public void Â(final ComponentListener listener) {
        this.Â.remove(listener);
    }
    
    protected void HorizonCode_Horizon_È() {
        final Iterator it = this.Â.iterator();
        while (it.hasNext()) {
            it.next().HorizonCode_Horizon_È(this);
        }
    }
    
    public abstract void HorizonCode_Horizon_È(final GUIContext p0, final Graphics p1) throws SlickException;
    
    public abstract void Ý(final int p0, final int p1);
    
    public abstract int Â();
    
    public abstract int Ó();
    
    public abstract int à();
    
    public abstract int Ø();
    
    public void HorizonCode_Horizon_È(final boolean focus) {
        if (focus) {
            if (AbstractComponent.Ø­áŒŠá != null) {
                AbstractComponent.Ø­áŒŠá.HorizonCode_Horizon_È(false);
            }
            AbstractComponent.Ø­áŒŠá = this;
        }
        else if (AbstractComponent.Ø­áŒŠá == this) {
            AbstractComponent.Ø­áŒŠá = null;
        }
        this.Âµá€ = focus;
    }
    
    public boolean áŒŠÆ() {
        return this.Âµá€;
    }
    
    protected void áˆºÑ¢Õ() {
        this.Ý.£à();
    }
    
    @Override
    public void Â(final int button, final int x, final int y) {
        this.HorizonCode_Horizon_È(Rectangle.HorizonCode_Horizon_È(x, y, this.Â(), this.Ó(), this.à(), this.Ø()));
    }
}
