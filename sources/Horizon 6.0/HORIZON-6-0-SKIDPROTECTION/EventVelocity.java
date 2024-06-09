package HORIZON-6-0-SKIDPROTECTION;

public final class EventVelocity extends EventMain implements Cancellable
{
    private boolean HorizonCode_Horizon_È;
    private final Entity Â;
    private final HorizonCode_Horizon_È Ý;
    
    public EventVelocity(final HorizonCode_Horizon_È type, final Entity entity) {
        this.Ý = type;
        this.Â = entity;
    }
    
    public Entity Â() {
        return this.Â;
    }
    
    public HorizonCode_Horizon_È Ý() {
        return this.Ý;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean cancel) {
        this.HorizonCode_Horizon_È = cancel;
    }
    
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("KNOCKBACK", 0), 
        Â("WATER", 1);
        
        static {
            Ý = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n) {
        }
    }
}
