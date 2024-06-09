package HORIZON-6-0-SKIDPROTECTION;

public abstract class BasicComponent extends AbstractComponent
{
    protected int Ø­áŒŠá;
    protected int Âµá€;
    protected int Ó;
    protected int à;
    
    public BasicComponent(final GUIContext container) {
        super(container);
    }
    
    @Override
    public int Ø() {
        return this.à;
    }
    
    @Override
    public int à() {
        return this.Ó;
    }
    
    @Override
    public int Â() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public int Ó() {
        return this.Âµá€;
    }
    
    public abstract void Â(final GUIContext p0, final Graphics p1);
    
    @Override
    public void HorizonCode_Horizon_È(final GUIContext container, final Graphics g) throws SlickException {
        this.Â(container, g);
    }
    
    @Override
    public void Ý(final int x, final int y) {
        this.Ø­áŒŠá = x;
        this.Âµá€ = y;
    }
}
