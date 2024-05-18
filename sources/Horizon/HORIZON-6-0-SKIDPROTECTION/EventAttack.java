package HORIZON-6-0-SKIDPROTECTION;

public class EventAttack extends Event
{
    private HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private Entity Â;
    
    public Event HorizonCode_Horizon_È(final HorizonCode_Horizon_È type) {
        this.HorizonCode_Horizon_È = type;
        return super.Â();
    }
    
    public final HorizonCode_Horizon_È Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public final Entity Ø­áŒŠá() {
        return this.Â;
    }
    
    public final void HorizonCode_Horizon_È(final Entity entity) {
        this.Â = entity;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("PRE", 0, "PRE", 0), 
        Â("POST", 1, "POST", 1);
        
        private static final HorizonCode_Horizon_È[] Ý;
        
        static {
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String var1, final int var2) {
        }
    }
}
