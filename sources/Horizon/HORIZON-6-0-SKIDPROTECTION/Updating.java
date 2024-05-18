package HORIZON-6-0-SKIDPROTECTION;

public class Updating
{
    TimeHelper HorizonCode_Horizon_È;
    
    public Updating() {
        this.HorizonCode_Horizon_È = new TimeHelper();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventTick e) {
        if (Minecraft.áŒŠà().á != null && this.HorizonCode_Horizon_È.Â(4000L)) {
            Horizon.à¢.Ñ¢Â.HorizonCode_Horizon_È();
            Horizon.à¢.Ø­Âµ.HorizonCode_Horizon_È();
            Horizon.à¢.áˆºÏ.Ý();
            this.HorizonCode_Horizon_È.Ø­áŒŠá();
        }
    }
}
