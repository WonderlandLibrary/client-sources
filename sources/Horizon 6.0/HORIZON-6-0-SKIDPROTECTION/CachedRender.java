package HORIZON-6-0-SKIDPROTECTION;

public class CachedRender
{
    protected static SGL HorizonCode_Horizon_È;
    private Runnable Â;
    private int Ý;
    
    static {
        CachedRender.HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public CachedRender(final Runnable runnable) {
        this.Ý = -1;
        this.Â = runnable;
        this.Ý();
    }
    
    private void Ý() {
        if (this.Ý == -1) {
            this.Ý = CachedRender.HorizonCode_Horizon_È.Ó(1);
            SlickCallable.HorizonCode_Horizon_È();
            CachedRender.HorizonCode_Horizon_È.Âµá€(this.Ý, 4864);
            this.Â.run();
            CachedRender.HorizonCode_Horizon_È.Â();
            SlickCallable.Â();
            return;
        }
        throw new RuntimeException("Attempt to build the display list more than once in CachedRender");
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Ý == -1) {
            throw new RuntimeException("Attempt to render cached operations that have been destroyed");
        }
        SlickCallable.HorizonCode_Horizon_È();
        CachedRender.HorizonCode_Horizon_È.Â(this.Ý);
        SlickCallable.Â();
    }
    
    public void Â() {
        CachedRender.HorizonCode_Horizon_È.Ó(this.Ý, 1);
        this.Ý = -1;
    }
}
